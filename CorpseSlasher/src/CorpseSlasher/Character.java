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
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

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
    private final float walkSpeed = 15.0f;
    private Vector3f walkDirection;
    
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
        walkDirection = new Vector3f();
        
        initModel(assMan, cam);
        initControl();
        initSwordGhost();
        assemblePlayer(bullet);
        setupCamera(cam);
        setupAnim();
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
        characterControl = new BetterCharacterControl(1.0f, 5, 50);
        characterControl.setGravity(new Vector3f(0, -800, 0));
        characterControl.setJumpForce(new Vector3f(0, 4, 0));
        characterControl.setApplyPhysicsLocal(true);
    }
    
    /**
     * 
     */
    private void initSwordGhost() {
        swordControl = new GhostControl(new BoxCollisionShape(new Vector3f(0.05f, 1.65f, 0.15f)));
        swordControl.setCollisionGroup(7);
        swordControl.setCollideWithGroups(6);
    }
    
    /**
     * assemblePlayer add the controllers to the player and to the physics handler.
     * @param bullet - BulletAppState physics controller.
     */
    private void assemblePlayer(BulletAppState bullet) {
        bullet.getPhysicsSpace().add(characterControl);
        bullet.getPhysicsSpace().add(swordControl);
        bullet.getPhysicsSpace().addAll(player);
        player.addControl(characterControl);
        playerNode.attachChild(player); 
        player.getChild("Cube-ogremesh").getControl(SkeletonControl.class).getAttachmentsNode("Sword").addControl(swordControl);
    }
    
    /**
     * setupCamera will attach the camera to the player for motion control.
     * @param cam - Camera to be attach to the player.
     */
    private void setupCamera(Camera cam) {
        cameraController = new CharacterCameraControl("3rdCam", cam, player, characterControl);
    }
    
    /**
     * setupAnim
     */
    private void setupAnim() {
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
    public void updateCharacterPostion(Camera cam) {
        walkDirection = motionController.updateCharacterMotion(cam);
        characterControl.setWalkDirection(walkDirection.normalize().multLocal(walkSpeed));
        
        motionController.slash = animController.updateCharacterAnimations(channel, 
                motionController.slash, motionController.walk);
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
