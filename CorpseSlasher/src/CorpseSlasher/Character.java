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
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.debug.SkeletonDebugger;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Character will control main character animations with keybindings.
 */
public class Character {
    
    private Node player;
    private Node playerNode;
    private AnimChannel channel;
    private AnimControl control;
    private AnimEventListener animationListener;
    private CharacterControl character;
    private Vector3f walkDirection;
    private boolean walk, slash, left, right, up, down;
    
    public Character(AssetManager assMan, InputManager inMan, BulletAppState bullet) {
        playerNode = new Node("Player");
        walkDirection = new Vector3f();
        walk = slash = left = right = up = down = false;
        initAnimEventListener();
        initModel(assMan, bullet);
        initKeys(inMan);
    }
    
    private void initModel(AssetManager assMan, BulletAppState bullet) {
        /**
         * Fix camera to be controlled by physics.
         */
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        character = new CharacterControl(capsuleShape, 0.05f);
        character.setPhysicsLocation(new Vector3f(0.0f, 47.0f, 0.0f));
        bullet.getPhysicsSpace().add(character);
        
        /**
         * Load player model.
         */
        player = (Node) assMan.loadModel("Models/cyborg/cyborg.j3o");
        player.setLocalTranslation(5.0f, 47.0f, 0.0f);
        playerNode.attachChild(player);  
        Material mat1 = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Red);
        player.setMaterial(mat1);
        
        /**
         * Display rigging.
         */
        control = player.getChild("Cube-ogremesh").getControl(AnimControl.class);
        SkeletonDebugger skeletonDebug = new SkeletonDebugger("skeleton", control.getSkeleton());
        Material mat2 = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.getAdditionalRenderState().setWireframe(true);
        mat2.setColor("Color", ColorRGBA.Green);
        mat2.getAdditionalRenderState().setDepthTest(false);
        skeletonDebug.setMaterial(mat2);
        player.attachChild(skeletonDebug);
        
        /**
         * Set animation.
         */
        control.addListener(animationListener);
        channel = control.createChannel();
        channel.setAnim("Stand");
        channel.setLoopMode(LoopMode.DontLoop);
    }
    
    private void initKeys(InputManager inMan) {
        inMan.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inMan.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inMan.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inMan.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inMan.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inMan.addMapping("Walk", new KeyTrigger(KeyInput.KEY_1));
        inMan.addMapping("Slash", new KeyTrigger(KeyInput.KEY_2));
        
        inMan.addListener(actionListener, "Left");
        inMan.addListener(actionListener, "Right");
        inMan.addListener(actionListener, "Up");
        inMan.addListener(actionListener, "Down");
        inMan.addListener(actionListener, "Jump");
        inMan.addListener(actionListener, "Walk");
        inMan.addListener(actionListener, "Slash");
    }
      
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String binding, boolean value, float tpf) {
            switch (binding) {
                case "Walk":
                    if (value) {
                        walk = true;
                    } else {
                        walk = false;
                    }
                    break;
                case "Slash":
                    if (value) {
                        slash = true;
                    } else {
                        slash = false;
                    }
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
                    break;
            }
        }
    };
    
    
    private void initAnimEventListener() {
        animationListener = new AnimEventListener() {
            @Override
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                switch (animName) {
                    case "Walk":
                        channel.setAnim("Walk", 0.0f);
                        channel.setLoopMode(LoopMode.Cycle);
                        channel.setSpeed(1f);
                        break;
                    
                    case "Stand":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.DontLoop);
                        channel.setSpeed(1f);
                        break;
                        
                    case "Slash":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.DontLoop);
                        channel.setSpeed(1f);
                        break;
                }
            }

            @Override
            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {}
        };
    }
    
    
    public Vector3f updateCharacterPostion(Camera cam) {
        Vector3f camDir = cam.getDirection().clone().multLocal(1.6f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(1.4f);
        walkDirection.set(0, 0, 0);
            
        if (left) { 
            walkDirection.addLocal(camLeft); 
        }
        
        if (right) { 
            walkDirection.addLocal(camLeft.negate()); 
        }
        
        if (up) { 
            walkDirection.addLocal(camDir); 
        }
        
        if (down) { 
            walkDirection.addLocal(camDir.negate()); 
        }
        
        if (walk) {
            if (channel.getAnimationName().equals("Stand")) {
                channel.setAnim("Walk", 0.0f);
                channel.setLoopMode(LoopMode.Cycle);
            }
        }
        
        if (slash) {
            if (channel.getAnimationName().equals("Stand")) {
                channel.setAnim("Slash", 1.0f);
                channel.setLoopMode(LoopMode.DontLoop);
            } else if (channel.getAnimationName().equals("Walk")) {
                channel.setAnim("Slash", 1.0f);
                channel.setLoopMode(LoopMode.Cycle);
            }
        }
        
        /*if (!walk && ! slash) {
            channel.setAnim("Stand", 1.0f);
            channel.setLoopMode(LoopMode.DontLoop);            
        }*/
        
        character.setWalkDirection(walkDirection);
        
        return character.getPhysicsLocation();
    }
    
    public Node retrievePlayerNode() {
        return playerNode;
    }
}
