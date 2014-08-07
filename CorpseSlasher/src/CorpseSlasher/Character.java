package CorpseSlasher;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SkeletonControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.KinematicRagdollControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Character will load the player model with material and rigging, control main 
 * character animations with keybindings and control the update of model and 
 * camera movement and positions.
 */
public class Character {
    
    private Node player;
    private Node playerNode;
    private AnimChannel channel;
    private AnimControl control;
    private GhostControl swordControl;
    private BetterCharacterControl characterControl;
    private CharacterAnimControl animController;
    private CharacterCameraControl cameraController;
    private CharacterMotionControl motionController;
    private CharacterAttackControl attackController;
    private KinematicRagdollControl ragdoll;
    private final float walkSpeed = 15.0f;
    private Vector3f walkDirection;
    private float health;
    private boolean alive;
    
    /**
     * Character will consist of loading the model with its materials and rigging,
     * add physics to the model and bind the camera to the model.
     * @param assMan - AssetManager required to load model and material.
     * @param inMan - InputManager required to set up key bindings.
     * @param bullet - BulletAppState required to add physics to player and camera.
     */
    public Character(AssetManager assMan, InputManager inMan, BulletAppState bullet,
            Camera cam) {
        playerNode = new Node("Player");
        animController = new CharacterAnimControl();
        motionController = new CharacterMotionControl();
        attackController = new CharacterAttackControl();
        walkDirection = new Vector3f();
        health = 100;
        alive = true;
        
        initModel(assMan, cam);
        initControl();
        initSwordGhost();
        assemblePlayer(bullet);
        initCamera(cam);
        initAnim();
        initKeys(inMan);
    }
    
    /**
     * initModel loads the model and sets it to the specified position.
     * @param assMan - AssetManager required to load model and material.
     * @param cam - Camera required to obtain camera position and look at.
     */
    private void initModel(AssetManager assMan, Camera cam) {
        player = (Node) assMan.loadModel("Models/cyborg/cyborg.j3o");
        player.setName("Player");
        player.setLocalTranslation(cam.getLocation().add(0.8f, -5.5f, -6.2f));
        player.lookAt(cam.getDirection(), cam.getUp());
    }
    
    /**
     * initControl sets up the character controller responsible for collision,
     * motion and forces control.
     */
    private void initControl() {
        characterControl = new BetterCharacterControl(0.8f, 4.85f, 50);
        characterControl.setGravity(new Vector3f(0, -800, 0));
        characterControl.setJumpForce(new Vector3f(0, 4, 0));
        characterControl.setApplyPhysicsLocal(true);
        characterControl.setJumpForce(new Vector3f(0,0,0));
        ragdoll = new KinematicRagdollControl(0.5f);
        ragdoll.setEnabled(false);
    }
    
    /**
     * initSwordGhost sets up the collision box that will be bound to the players
     * sword in order to determine if any collision has occured with the sword
     * and a mob.
     */
    private void initSwordGhost() {
        swordControl = new GhostControl(new BoxCollisionShape(new Vector3f(0.05f, 1.65f, 0.15f)));
        swordControl.setCollisionGroup(8);
        swordControl.setCollideWithGroups(6);
    }
    
    /**
     * assemblePlayer add the controllers to the player and to the physics handler.
     * @param bullet - BulletAppState physics controller.
     */
    private void assemblePlayer(BulletAppState bullet) {
        bullet.getPhysicsSpace().addCollisionListener(attackController);
        bullet.getPhysicsSpace().add(characterControl);
        bullet.getPhysicsSpace().add(swordControl);
        bullet.getPhysicsSpace().addAll(player);
        bullet.getPhysicsSpace().add(ragdoll);
        player.addControl(characterControl);
        playerNode.attachChild(player); 
        player.getChild("Cube-ogremesh").getControl(SkeletonControl.class).getAttachmentsNode("Sword").addControl(swordControl);
    }
    
    /**
     * initCamera will attach the camera to the player for motion control.
     * @param cam - Camera to be attach to the player.
     */
    private void initCamera(Camera cam) {
        cameraController = new CharacterCameraControl("3rdCam", cam, player, characterControl);
    }
    
    /**
     * initAnim creates the controller and animations channel required to
     * access all available animations, set the current animation and the type
     * of trigger at the end of a animations cycle.
     */
    private void initAnim() {
        control = player.getChild("Cube-ogremesh").getControl(AnimControl.class);
        control.addListener(animController.getAnimationListener());
        channel = control.createChannel();
        channel.setAnim("Stand");
        channel.setLoopMode(LoopMode.Cycle);        
    }
    
    /**
     * updateCharacterPosition will be called from the main game update function 
     * on every frame update, where this will call the motion and animation
     * updater separetly.
     * @param cam - Camera to retrieve the directional vectors required to calculate
     * the direction to move the camera and model in.
     */
    public ArrayList<String> updateCharacterPostion(Camera cam, float tpf) {
        if (alive) {
            walkDirection = motionController.updateCharacterMotion(cam);
            characterControl.setWalkDirection(walkDirection.normalize().multLocal(walkSpeed));

            motionController.slash = animController.updateCharacterAnimations(channel, 
                    motionController.slash, motionController.walk, alive);
            return testLandedAttack();
        } else {
            ragdoll.update(tpf);
            return new ArrayList<>();
        }
    }
    
    /**
     * 
     * @param knocks 
     */
    public void processKnocks(ArrayList<String> knocks) {
        for (int i = 0; i < knocks.size(); i++) {
            health -= 10;
            System.out.println("Player : ive been slapped by " + knocks.get(i));
            if (health <= 0) {
                alive = false;
                System.out.println("YOUR DEAD!!!!");
                //swapControllers();
            }
        }
    }
    
    
    /**
     * 
     */
    private ArrayList<String> testLandedAttack() {
        if (animController.attacking && attackController.mobsHit().size() > 0) {
            animController.attacking = false;

            ArrayList<String> hits = new ArrayList<>();
            for (int i = 0; i < attackController.getHitsSize(); i++)
                hits.add(attackController.mobsHit().get(i));
            attackController.attacksProcessed();
            
            return hits;
        } else {
            attackController.attacksProcessed();
            return attackController.mobsHit();
        }
    }
    
    /**
     * 
     */
    private void swapControllers() {
        if (ragdoll.isEnabled()) {
            ragdoll.setEnabled(false);
            player.removeControl(KinematicRagdollControl.class);
            player.addControl(characterControl);
            characterControl.setEnabled(true);
        } else {
            characterControl.setEnabled(false);
            player.removeControl(BetterCharacterControl.class);
            player.addControl(ragdoll);
            ragdoll.setEnabled(true);
            ragdoll.setRagdollMode();
        }
    }
    
    /**
     * initKeys sets up the key bindings that will be used to control the player model.
     * @param inMan - InputManager add all the required key mappings to be triggered
     * when pressed.
     */
    private void initKeys(InputManager inMan) {
        inMan.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inMan.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inMan.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inMan.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inMan.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inMan.addMapping("Slash", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));        
        inMan.addMapping("TurnLeft", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inMan.addMapping("TurnRight", new MouseAxisTrigger(MouseInput.AXIS_X, false));      
        inMan.addMapping("LookUp", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inMan.addMapping("LookDown", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        
        inMan.addListener(motionController.getMotionController(), "Left");
        inMan.addListener(motionController.getMotionController(), "Right");
        inMan.addListener(motionController.getMotionController(), "Up");
        inMan.addListener(motionController.getMotionController(), "Down");
        inMan.addListener(motionController.getMotionController(), "Jump");
        
        inMan.addListener(motionController.getMotionController(), "Slash");
        inMan.addListener(cameraController.getAnalogListener(), "TurnLeft");
        inMan.addListener(cameraController.getAnalogListener(), "TurnRight");
        inMan.addListener(cameraController.getAnalogListener(), "LookUp");
        inMan.addListener(cameraController.getAnalogListener(), "LookDown");
    }
    
    public Vector3f getPosition() {
        return player.getLocalTranslation();
    }
    
    /**
     * retrievePlayerNode an accessor to the game node containing the player model.
     * @return playernode - Node containing the model data.
     */
    public Node retrievePlayerNode() {
        return playerNode;
    }
}
