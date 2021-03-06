package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import java.util.ArrayList;
import jme3utilities.TimeOfDay;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * GameScene - will load and compile the scene with its various assets.
 */
public class GameScene {
    
    private Node sceneNode;
    private BasicScene basicScene;
    private Character character;
    private MobsHandler mobHandler;
    private CollisionController collController;
    private ArrayList<String> mobHits;
    private boolean playerAttacking;
    
    /**
     * GameScene - will combine all the entities of the entire game into a node 
     * for a reverance point for the game engine.
     * @param selectedMap - the desired map to load.
     * @param assetManager - Assetmanager passed through from main game.
     * @param viewPort - ViewPort required for water, contains position of camara.
     * @param cam - Camera to assign to player and mouse motion.
     */
    public GameScene(int selectedMap, AssetManager assestManager, ViewPort viewPort, 
            Camera cam, BulletAppState bullet, InputManager inMan) {
        sceneNode = new Node("GameScene");
        GameWorld.assetManager = assestManager;
        GameWorld.bullet = bullet;
        
        initCameraPosition(cam, selectedMap);
        initScene(viewPort, cam, selectedMap);
        initMainCharacter(inMan, cam);
        initMobs();
        initAudio();
        
        collController = new CollisionController();
        bullet.getPhysicsSpace().addCollisionListener(collController);
        //bullet.getPhysicsSpace().enableDebug(assestManager);
    }
    
    /**
     * reloadScene - After graphic settings was changed.
     */
    public void reloadScene() {
        basicScene.reloadScene();
    }
    
    /** 
     * relog - After game was logged out and back in to reset to starting position.
     * @param cam - Replace player.
     * @param selectedMap - Determine camera location.
     */
    public void relog(Camera cam, int selectedMap) {
        initCameraPosition(cam, selectedMap);
        character.setPosition(cam);
        Audio.playAmbient();
    }
    
    /**
     * initScene - will retrieve and combine all the variase assets that will
     * make up the entire scene.
     * @param viewPort - ViewPort required for post processing filter: Water.
     * @param cam - Camera required for the sky controller to update the postion
     * of assets.
     * @param map - Corresponding scene number to assign the required terrain.
     */
    private void initScene(ViewPort viewPort, Camera cam, int map) {
        switch(map) {
            case(0) :   
                basicScene = new BasicScene("ZombieScene1");
                basicScene.createScene(viewPort, cam);
                sceneNode = basicScene.retrieveSceneNode();
                break;
            default :
                break;
        }
    }
    
    /**
     * initMainCharacter - will load the main player model, add it to a character
     * handler to be able to add gravity and move it around, also add the key
     * and mouse functionality to control and update it the model and camera
     * positions.
     * @param inMan - InputManager for adding key and mouse binding to be triggered.
     * @param cam - Camera to be bound to player model.
     */
    private void initMainCharacter(InputManager inMan, Camera cam) {
        character = new Character(inMan, cam);
        sceneNode.attachChild(character.retrievePlayerNode());
    }
    
    /**
     * initMobs - will be responsible for creating all the mobs at specified positions
     * also update its position, animation and aggression control.
     */
    private void initMobs() {
        mobHandler = new MobsHandler();
        sceneNode.attachChild(mobHandler.retrieveMobs());
    }
    
    /**
     * initCameraPosition - will be used the multiple maps are create to define the
     * correct camera position.
     * @param cam - Camera from main game.
     * @param map - The scene to be loaded.
     */
    private void initCameraPosition(Camera cam, int map) {
        switch(map) {
            case(0) :
                cam.setLocation(new Vector3f(195.0f, 36.0f, -225.0f));
                cam.lookAt(new Vector3f(200, 25, -500), cam.getUp());
                break;
            default :
                break;
        }
    }
    
    /**
     * initAudio - Loads all audio nodes required.
     */
    private void initAudio() {
        Audio.loadOcean();
        Audio.loadCharacterAudio();
        Audio.loadMobAudio();
        Audio.playAmbient();
    }
    
    /**
     * update - will update the all the corrisponding assets of the game. From 
     * light direction, time of day, water light reflection, character position.
     * @param tod - TimeOfDay to update the skycontrol time of day.
     * @param tpf - Update value of time between frames.
     */
    public void update(TimeOfDay tod, float tpf) {
        GameWorld.systemTime = (int) (System.nanoTime() / 100000);
                
        basicScene.update(tod, tpf);
        playerAttacking = character.updateCharacterPostion(collController.getPlayerHitSize(), tpf);
        
        GameWorld.removeAggro();
        
        if (playerAttacking) {
            mobHits= mobHandler.updateMobs(collController.getPlayerHits(), collController.getMobHits(), tpf);
        } else {
            mobHits = mobHandler.updateMobs(new ArrayList<String>(), collController.getMobHits(), tpf);
        }
        
        character.processKnocks(mobHits);
        collController.attacksProcessed();
        
        if (GameWorld.aggro && !GUI.UserInterfaceManager.guiNode.hasChild(GUI.UserInterfaceManager.aggro)) {
            GUI.UserInterfaceManager.guiNode.attachChild(GUI.UserInterfaceManager.aggro);
        }
    }
    
    /**
     * getHealth - Retrieve health of player.
     * @return 
     */
    public float getHealth() {
        return character.getHealth();
    }
    
    /**
     * retrieveSceneNode to obtain the node that contains all the assests of the
     * scene which will be added to the rootNode for it to be renderable.
     * @return sceneNode consisting of the entire scene.
     */
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
