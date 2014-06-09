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
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

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
    private AnimEventListener event;
    private CharacterControl character;
    private Vector3f walkDirection;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    
    public Character(AssetManager assMan, InputManager inMan, BulletAppState bullet) {
        playerNode = new Node("Player");
        walkDirection = new Vector3f();
        //initAnimEventListener();
        initModel(assMan, bullet);
        initKeys(inMan);
    }
    
    private void initModel(AssetManager assMan, BulletAppState bullet) {
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        character = new CharacterControl(capsuleShape, 0.05f);
        character.setPhysicsLocation(new Vector3f(0.0f, 70.0f, 0.0f));
        bullet.getPhysicsSpace().add(character);
        
        /*player = (Node) assMan.loadModel("Models/cyborgXML/Cube.mesh.xml");
        player.setLocalTranslation(10, 55, -10);
        player.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        playerNode.attachChild(player);
        //player.addControl(new AnimControl());
        
        control = player.getControl(AnimControl.class);
        
        SkeletonDebugger skeletonDebug = new SkeletonDebugger("skeleton1", control.getSkeleton());
        Material mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", ColorRGBA.Green);
        mat.getAdditionalRenderState().setDepthTest(false);
        skeletonDebug.setMaterial(mat);
        player.attachChild(skeletonDebug);
        
        for (String anim : control.getAnimationNames())
            System.out.println(anim);
        
        control.addListener(event);
        channel = control.createChannel();
        channel.setAnim("Slash");
        
        //playerNode.attachChild(player);
        
        /*
        player = (Node) rootNode.getChild("Player");
        control = player.getControl(AnimControl.class);
        
        System.out.println("before");
        for (String anim : control.getAnimationNames())
            System.out.println(anim);
        System.out.println("after");

        control.addListener(event);
        channel = control.createChannel();
        channel.setAnim("Stand");
        */
        //playerNode.attachChild(player);
    }
    
    /*private void initAnimEventListener() {
        event = new AnimEventListener() {
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                if (animName.equals("Walk")) {
                    channel.setAnim("Stand", 0.50f);
                    channel.setLoopMode(LoopMode.DontLoop);
                    channel.setSpeed(1f);
                }
            }

            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                System.out.println("bo");
            }
        };
    }*/
    
    private void initKeys(InputManager inMan) {
        /*inMan.addMapping("Walk", new KeyTrigger(KeyInput.KEY_SPACE));
        inMan.addListener(actionListener, "Walk");*/
        inMan.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inMan.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inMan.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inMan.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inMan.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inMan.addListener(actionListener, "Left");
        inMan.addListener(actionListener, "Right");
        inMan.addListener(actionListener, "Up");
        inMan.addListener(actionListener, "Down");
        inMan.addListener(actionListener, "Jump");
    }
      
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String binding, boolean value, float tpf) {
            /*if (name.equals("Walk") && !keyPressed) {
                if (!channel.getAnimationName().equals("Walk")) {
                    channel.setAnim("Walk", 0.50f);
                    channel.setLoopMode(LoopMode.Loop);
                }
            }*/
            switch (binding) {
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
        character.setWalkDirection(walkDirection);
        
        return character.getPhysicsLocation();
    }
    
    public Node retrievePlayerNode() {
        return playerNode;
    }
}
