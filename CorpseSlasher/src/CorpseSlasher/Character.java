package CorpseSlasher;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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
    private AnimEventListener animationListener;
    private ActionListener actionListener;
    private AnalogListener analogListener;
    private CharacterControl characterControl;
    private Vector3f walkDirection;
    private ThirdPersonCamera camera;
    private boolean slash, left, right, up, down, jump, walkFlag;
    
    private Node pivot;
    private float walkSpeed = 0.15f;
    private float verticalAngle = 30 * FastMath.DEG_TO_RAD;
    private float maxVerticalAngle = 85 * FastMath.DEG_TO_RAD;
    private float minVerticalAngle = 5 * FastMath.DEG_TO_RAD;
    
    /**
     * @TODO remove and read directly from settings.
     */
    private float xMovementSpeed;
    private float yMovementSpeed;
    
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
        walkDirection = new Vector3f();
        slash = left = right = up = down = jump = walkFlag = false;
        
        /**
         * @TODO add mouse sensitivity to settings.
         */
        xMovementSpeed = 0.5f;
        yMovementSpeed = 0.5f;
        
        initActionListener();
        initAnimEventListener();
        initModel(assMan, bullet, cam);
        initKeys(inMan);
    }
    
    /**
     * initModel loads model, adds physics to it and bind the camera to it.
     * @param assMan - AssetManager required to load model and material.
     * @param inMan - InputManager required to set up key bindings.
     * @param cam - Camera required to obtain camera position and look at.
     */
    private void initModel(AssetManager assMan, BulletAppState bullet, Camera cam) {
        /**
         * Fix camera to be controlled by physics.
         */
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(0.01f, 0.05f, 1);
        characterControl = new CharacterControl(capsuleShape, 1.0f);
        characterControl.setGravity(10.0f);
        characterControl.setJumpSpeed(50.0f);
        bullet.getPhysicsSpace().add(characterControl);
        
        /**
         * Load player model.
         */
        player = (Node) assMan.loadModel("Models/cyborg/cyborg.j3o");
        player.setLocalTranslation(cam.getLocation().add(0.8f, -6.5f, -4.2f));
        player.lookAt(cam.getDirection(), cam.getUp());
        player.addControl(characterControl);
        playerNode.attachChild(player); 
        Material mat1 = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Red);
        player.setMaterial(mat1);
        
        camera = new ThirdPersonCamera("3rdCam", cam, player);
        
        /**
         * Set animation.
         */
        control = player.getChild("Cube-ogremesh").getControl(AnimControl.class);
        control.addListener(animationListener);
        channel = control.createChannel();
        channel.setAnim("Stand");
        channel.setLoopMode(LoopMode.Cycle);
        
        /**
         * Display rigging.
         */
        /*SkeletonDebugger skeletonDebug = new SkeletonDebugger("skeleton", control.getSkeleton());
        Material mat2 = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.getAdditionalRenderState().setWireframe(true);
        mat2.setColor("Color", ColorRGBA.Green);
        mat2.getAdditionalRenderState().setDepthTest(false);
        skeletonDebug.setMaterial(mat2);
        player.attachChild(skeletonDebug);*/        
    }
    
    /**
     * updateCharacterPosition will be called from the main game update function 
     * on every frame update, where this will move the player as well as the 
     * camera according to the corrisponding key bindings press.
     * @param cam - Camera to retrieve the directional vectors required to calculate
     * the direction to move the camera and model in.
     */
    public void updateCharacterPostion(Camera cam) {
        Vector3f camDir = cam.getDirection().clone().multLocal(0.3f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.2f);
        camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        
        if (left) { walkDirection.addLocal(camLeft); }
        
        if (right) { walkDirection.addLocal(camLeft.negate()); }
        
        if (up) { walkDirection.addLocal(camDir); }
        
        if (down) { walkDirection.addLocal(camDir.negate()); }
        
        characterControl.setWalkDirection(walkDirection.normalize().multLocal(walkSpeed));
        handleAnimations();
    }
    
    /**
     * handleAnimations updates animations where required.
     */
    private void handleAnimations() {        
        if (slash) {
            if (channel.getAnimationName().equals("Stand")) {
                channel.setAnim("Slash");
                channel.setLoopMode(LoopMode.DontLoop);
                channel.setSpeed(1.15f);
            } else {
                if (!channel.getAnimationName().equals("Slash")) {
                    channel.setAnim("Slash", 1.0f);
                    channel.setLoopMode(LoopMode.DontLoop);
                    channel.setSpeed(1.15f);
                }
            }
            slash = false;
        }
        
        if (left || right || up || down) {
            if (channel.getAnimationName().equals("Stand")) {
                channel.setAnim("Walk");
                channel.setLoopMode(LoopMode.Loop);
                channel.setSpeed(1.5f);
            }
        } else {
            if (!channel.getAnimationName().equals("Slash")) {
                channel.setAnim("Stand");
                channel.setLoopMode(LoopMode.Cycle);                    
            }
        }
        
        if (jump) {
            /**
             * @TODO add jump animation and action channel change.
             */
            characterControl.jump();
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
        
        inMan.addListener(actionListener, "Left");
        inMan.addListener(actionListener, "Right");
        inMan.addListener(actionListener, "Up");
        inMan.addListener(actionListener, "Down");
        inMan.addListener(actionListener, "Jump");
        inMan.addListener(actionListener, "Walk");
        
        inMan.addListener(actionListener, "Slash");
        inMan.addListener(analogListener, "TurnLeft");
        inMan.addListener(analogListener, "TurnRight");
        inMan.addListener(analogListener, "LookUp");
        inMan.addListener(analogListener, "LookDown");
    }
      
    /**
     * initActionListener to update the required keys being pressed and mouse
     * motion.
     */
    private void initActionListener() {
        actionListener = new ActionListener() {
            @Override
            public void onAction(String binding, boolean value, float tpf) {
                switch (binding) {
                    case "Slash":
                        if (value) 
                            slash = true;
                        break;
                    case "Left":
                        if (value) { 
                            left = true; 
                        } else { 
                            left = false; 
                        }
                        break;
                    case "Right":
                        if (value) { 
                            right = true; 
                        } else { 
                            right = false; 
                        }
                        break;
                    case "Up":
                        if (value) { 
                            up = true; 
                        } else { 
                            up = false; 
                        }
                        break;
                    case "Down":
                        if (value) {
                            down = true; 
                        } else { 
                            down = false; 
                        }
                        break;
                    case "Jump":
                        if (value) {
                            jump = true;
                        } else {
                            jump = false;
                        }
                        break;
                }
            }
        };
            
        analogListener = new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {
                Quaternion turn = new Quaternion();
                switch(name) {
                    case "TurnLeft":
                        turn.fromAngleAxis(xMovementSpeed * value, Vector3f.UNIT_Y);
                        characterControl.setViewDirection(turn.mult(characterControl.getViewDirection()));
                        break;
                    case "TurnRight":
                        turn.fromAngleAxis(-xMovementSpeed * value, Vector3f.UNIT_Y);
                        characterControl.setViewDirection(turn.mult(characterControl.getViewDirection()));
                        break;
                    case "LookUp":
                        camera.verticalRotate(yMovementSpeed*value);
                        break;
                    case "LookDown":
                        camera.verticalRotate(-yMovementSpeed*value);
                        break;
                }
            }
        };
    }
    
    
    
    /**
     * initAminEventListener will trigger on animation change as well as when a 
     * cycle of an animations if completed to run it again.
     */
    private void initAnimEventListener() {
        animationListener = new AnimEventListener() {
            @Override
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                switch (animName) {
                    case "Walk":
                        if (channel.getLoopMode().equals(LoopMode.Loop)) {
                            channel.setAnim("Walk", 0.0f);
                            channel.setLoopMode(LoopMode.Loop);
                            channel.setSpeed(1.5f);
                        } else {
                            channel.setAnim("Stand", 0.0f);
                            channel.setLoopMode(LoopMode.Loop);
                        }
                        break;                    
                    case "Stand":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1f);
                        break;                        
                    case "Slash":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1f);
                        break;
                }
            }

            @Override
            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {}
        };
    }
    
    public void verticalRotate(float angle)
    {
	verticalAngle += angle;
 
	if(verticalAngle > maxVerticalAngle)
	{
	    verticalAngle = maxVerticalAngle;
	}
	else if(verticalAngle < minVerticalAngle)
	{
	    verticalAngle = minVerticalAngle;
	}
 
	pivot.getLocalRotation().fromAngleAxis(-verticalAngle, Vector3f.UNIT_X);
    }
    
    /**
     * retrievePlayerNode an accessor to the game node containing the player model.
     * @return player node containing the model data.
     */
    public Node retrievePlayerNode() {
        return playerNode;
    }
}
