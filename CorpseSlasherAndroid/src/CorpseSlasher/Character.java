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
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.math.FastMath;
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
    private Camera cam;
    private AssetManager assetManager;
    private BulletAppState bullet;
    private AnimChannel channel;
    private AnimControl control;
    private GhostControl swordControl;
    private BetterCharacterControl characterControl;
    private CharacterAnimControl animController;
    private CharacterCameraControl cameraController;
    private CharacterMotionControl motionController;
    private ModelRagdoll ragdoll;
    private final float walkSpeed = 3.75f;
    private Vector3f walkDirection;
    private float health;
    private float eighth_pi;
    private boolean alive;
    private long deathTime, spawnTime, regenTime, regenInterval;
    
    /**
     * Character will consist of loading the model with its materials and rigging,
     * add physics to the model and bind the camera to the model.
     * @param assMan - AssetManager required to load model and material.
     * @param inMan - InputManager required to set up key bindings.
     * @param bullet - BulletAppState required to add physics to player and camera.
     */
    public Character(AssetManager assMan, InputManager inMan, BulletAppState bullet,
            Camera cam) {
        this.playerNode = new Node("Player");
        this.animController = new CharacterAnimControl();
        this.motionController = new CharacterMotionControl(cam);
        this.walkDirection = new Vector3f();
        this.health = 100;
        this.eighth_pi = FastMath.PI * 0.125f;
        this.alive = true;
        this.spawnTime = new Long("5000000000");
        this.regenInterval = new Long("2000000000");
        this.regenTime = new Long("0");
        this.cam = cam;
        this.assetManager = assMan;
        this.bullet = bullet;
        
        initModel();
        initControl();
        initSwordGhost();
        //initRagdoll();
        assemblePlayer();
        initCamera();
        initAnim();
        initKeys(inMan);
    }
    
    /**
     * initModel loads the model and sets it to the specified position.
     */
    private void initModel() {
        player = (Node) assetManager.loadModel("Models/cyborg/cyborg.j3o");
        player.setName("Player");
        player.setLocalTranslation(cam.getLocation().add(-15.8f, -5.5f, -16.2f));
        player.lookAt(cam.getDirection().negate(), cam.getUp());
    }
    
    /**
     * initControl sets up the character controller responsible for collision,
     * motion and forces control.
     */
    private void initControl() {
        characterControl = new BetterCharacterControl(0.2f, 0.85f, 13.5f);
        characterControl.setGravity(new Vector3f(0, -200, 0));
        characterControl.setJumpForce(new Vector3f(0, 4, 0));
        characterControl.setApplyPhysicsLocal(true);
        characterControl.setJumpForce(new Vector3f(0,0,0));
    }
    
    /*
     * initRagdoll will create the ragdoll required for death animation and assign
     * the required limbs.
     */
    private void initRagdoll() {
        ragdoll = new ModelRagdoll(0.5f, "Cube-ogremesh");
        ragdoll.addBoneName("spine1");
        ragdoll.addBoneName("spine2");
        ragdoll.addBoneName("spine3");
        ragdoll.addBoneName("spine4");
        ragdoll.addBoneName("spine5");
        ragdoll.addBoneName("neck");
        ragdoll.addBoneName("head");
        ragdoll.addBoneName("upper_arm.r");
        ragdoll.addBoneName("upper_arm.l");
        ragdoll.addBoneName("lower_arm.r");
        ragdoll.addBoneName("lower_arm.l");
        ragdoll.addBoneName("hand.r");
        ragdoll.addBoneName("hand.l");
        ragdoll.addBoneName("upper_leg.r");
        ragdoll.addBoneName("upper_leg.l");
        ragdoll.addBoneName("lower_leg.r");
        ragdoll.addBoneName("lower_leg.l");
        ragdoll.addBoneName("foot.r");
        ragdoll.addBoneName("foot.l");
        ragdoll.setEnabled(false);
    }
    
    /**
     * initSwordGhost sets up the collision box that will be bound to the players
     * sword in order to determine if any collision has occured with the sword
     * and a mob.
     */
    private void initSwordGhost() {
        swordControl = new GhostControl(new BoxCollisionShape(new Vector3f(0.05f, 0.25f, 0.15f)));
        swordControl.setCollisionGroup(8);
        swordControl.setCollideWithGroups(6);
    }
    
    /**
     * assemblePlayer add the controllers to the player and to the physics handler.
     */
    private void assemblePlayer() {
        bullet.getPhysicsSpace().add(characterControl);
        bullet.getPhysicsSpace().add(swordControl);
        bullet.getPhysicsSpace().addAll(player);
        player.addControl(characterControl);
        playerNode.attachChild(player); 
        //player.getChild("Cube-ogremesh").getControl(SkeletonControl.class).getAttachmentsNode("Sword").addControl(swordControl);
    }
    
    /**
     * initCamera will attach the camera to the player for motion control.
     */
    private void initCamera() {
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
     * @param playerHits - number of mobs hit, to check if player is currently in
     * an attacking position.
     * @param tpf - Time per frame to update ragdoll position.
     */
    public boolean updateCharacterPostion(int playerHits, float tpf, boolean aggro) {
        if (alive) {
            walkDirection = motionController.updateCharacterMotion();
            characterControl.setWalkDirection(walkDirection.normalize().multLocal(walkSpeed));

            /*if (motionController.walk) {
                Audio.playCharacterWalk();
            } else {
                Audio.pauseCharacterWalk();
            }*/
            
            motionController.slash = animController.updateCharacterAnimations(channel, 
                    motionController.slash, motionController.walk, alive);
            motionController.slash = false;
            
            if (!aggro && regenTime == new Long("0")) {
                regenTime = System.nanoTime();
            } else if (System.nanoTime() - regenTime > regenInterval && health != 100 && !aggro) {
                health += 5;
                regenTime = new Long("0");
                //System.out.println("Regen time, health is : " + health);
            }
            
            if (animController.attacking && playerHits > 0) {
                animController.attacking = false;
                return true;
            } else {
                return false;
            }                
        } else {
            //ragdoll.update(tpf);
            if (System.nanoTime() - deathTime > spawnTime) {
                alive = true;
                health = 100;
                //swapControllers();
            }
            return false;
        }
    }
    
    /**
     * processKnocks will process all the landed attacks from mobs.
     * @param knocks - ArraysList of mob names that have hit player.
     */
    public void processKnocks(ArrayList<String> knocks) {
        if (alive) {
            if (knocks != null) {
                for (int i = 0; i < knocks.size(); i++) {
                    health -= 10;
                    //System.out.println("Player : ive been slapped by " + knocks.get(i) 
                    //        + ". Health is " + health);

                    if (health <= 0) {
                        health = 0;
                        alive = false;
                        deathTime = System.nanoTime();
                        //System.out.println("You were killed by : " + knocks.get(i));
                        //swapControllers();
                    }
                }
            }
        }
    }
        
    /**
     * swapControllers will swap between controllers from BetterCharacterControl
     * will ghost boxes for alive and ragdoll for death.
     */
    private void swapControllers() {
        if (alive) {            
            cam.setLocation(new Vector3f(195.0f, 36.0f, -225.0f));
            player.setLocalTranslation(cam.getLocation().add(0.8f, -5.5f, -6.2f));
            ragdoll.setKinematicMode();
            ragdoll.setEnabled(false);
            player.removeControl(ModelRagdoll.class);
            bullet.getPhysicsSpace().remove(ragdoll);
            player.addControl(characterControl);
            player.getChild("Cube-ogremesh").getControl(SkeletonControl.class).getAttachmentsNode("Sword").addControl(swordControl);
            bullet.getPhysicsSpace().add(characterControl);
            bullet.getPhysicsSpace().add(swordControl);
            characterControl.setEnabled(true);
        } else {
            characterControl.setEnabled(false);
            player.removeControl(BetterCharacterControl.class);
            player.removeControl(GhostControl.class);
            bullet.getPhysicsSpace().remove(characterControl);
            bullet.getPhysicsSpace().remove(swordControl);
            player.addControl(ragdoll);
            ragdoll.setJointLimit("spine1", eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi);
            ragdoll.setJointLimit("spine2", eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi);
            ragdoll.setJointLimit("spine3", eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi);
            ragdoll.setJointLimit("spine4", eighth_pi, eighth_pi, 0, 0, eighth_pi, eighth_pi);
            ragdoll.setJointLimit("spine5", eighth_pi, eighth_pi, 0, 0, eighth_pi, eighth_pi);
            ragdoll.setEnabled(true);
            ragdoll.setRagdollMode();
            bullet.getPhysicsSpace().add(ragdoll);
        }
    }
    
    /**
     * initKeys sets up the key bindings that will be used to control the player model.
     * @param inMan - InputManager add all the required key mappings to be triggered
     * when pressed.
     */
    private void initKeys(InputManager inMan) {
        inMan.addMapping("MoveTouch", new TouchTrigger(0));
        inMan.addListener(motionController.getTouchListener(), "MoveTouch");
        
        inMan.addMapping("LookTouch", new TouchTrigger(0));
        inMan.addListener(cameraController.getTouchListener(), "LookTouch");
        
        /*inMan.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
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
        inMan.addListener(cameraController.getAnalogListener(), "LookDown");*/
    }
    
    /**
     * getPosition accessor to player model locations.
     * @return location - Vector3f
     */
    public Vector3f getPosition() {
        return player.getLocalTranslation();
    }
    
    /**
     *
     */
    public void setPosition(Camera cam) {
        characterControl.setEnabled(false);
        player.setLocalTranslation(cam.getLocation().add(0.8f, -5.5f, -6.2f));
        player.lookAt(cam.getDirection(), cam.getUp());
        characterControl.setEnabled(true);
    }
    
    /**
     *
     * @return
     */
    public float getHealth() {
        return health;
    }
    
    /**
     * retrievePlayerNode an accessor to the game node containing the player model.
     * @return playernode - Node containing the model data.
     */
    public Node retrievePlayerNode() {
        return playerNode;
    }
}
