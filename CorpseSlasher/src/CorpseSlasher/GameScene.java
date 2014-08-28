package CorpseSlasher;

import GUI.LoadingScreen;
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
 * GameScene will load and compile the scene with its various assets.
 */
public class GameScene {
    
    private Node sceneNode;
    private BasicScene basicScene;
    private Character character;
    private MobsHandler mobHandler;
    private CollisionController collController;
    
    /**
     * GameScene will combine all the entities of the entire game into a node 
     * for a reverance point for the game engine.
     * @param selectedMap - the desired map to load.
     * @param assetManager - Assetmanager passed through from main game.
     * @param viewPort - ViewPort required for water, contains position of camara.
     */
    public GameScene(int selectedMap, AssetManager assestManager, ViewPort viewPort, 
            Camera cam, BulletAppState bullet, InputManager inMan, LoadingScreen ui, GameSettings settings) {
        sceneNode = new Node("GameScene");
        
        initCameraPosition(cam, selectedMap);
        initScene(assestManager, viewPort, cam, bullet, selectedMap, ui, settings);
        initMainCharacter(assestManager, inMan, bullet, cam);
        initMobs(bullet, assestManager);
        
        collController = new CollisionController();
        bullet.getPhysicsSpace().addCollisionListener(collController);
    }
    
    public void reloadScene(AssetManager assMan, ViewPort vp, Camera cam) {
        basicScene.reloadScene(assMan, vp, cam);
    }
    
    /**
     * initScene will retrieve and combine all the variase assets that will
     * make up the entire scene.
     * @param assestManager - AssestManager for loading all required models and
     * textures.
     * @param viewPort - ViewPort required for post processing filter: Water.
     * @param cam - Camera required for the sky controller to update the postion
     * of assets.
     * @param bullet - BulletAppState to add collision detection to the terrain
     * and its added assest.
     * @param map - Corresponding scene number to assign the required terrain.
     */
    private void initScene(AssetManager assestManager, ViewPort viewPort, Camera cam,
            BulletAppState bullet, int map, LoadingScreen ui,GameSettings settings) {
        switch(map) {
            case(0) :   
                basicScene = new BasicScene("ZombieScene1");
                basicScene.createScene(assestManager, viewPort, cam, bullet, ui,settings);
                sceneNode = basicScene.retrieveSceneNode();
                break;
            default :
                break;
        }
    }
    
    /**
     * initMainCharacter will load the main player model, add it to a character
     * handler to be able to add gravity and move it around, also add the key
     * and mouse functionality to control and update it the model and camera
     * positions.
     * @param assMan - AssetManager to be able to load models and textures.
     * @param inMan - InputManager for adding key and mouse binding to be triggered.
     * @param bullet - BulletAppState to add the model to the physics handler.
     */
    private void initMainCharacter(AssetManager assMan, InputManager inMan, 
            BulletAppState bullet, Camera cam) {
        character = new Character(assMan, inMan, bullet, cam);
        sceneNode.attachChild(character.retrievePlayerNode());
    }
    
    /**
     * initMobs will be responsible for creating all the mobs at specified positions
     * also update its position, animation and aggression control.
     * @param bullet - BulletAppState for add collision boxes and controllers.
     * @param assMan - AssetManager for loading the model.
     */
    private void initMobs(BulletAppState bullet, AssetManager assMan) {
        mobHandler = new MobsHandler(bullet, assMan);
        sceneNode.attachChild(mobHandler.retrieveMobs());
    }
    
    /**
     * initCameraPosition will be used the multiple maps are create to define the
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
     * update will update the all the corrisponding assets of the game. From 
     * light direction, time of day, water light reflection, character position.
     * @param cam - Camera to be use for updating camera position.
     * @param tod - TimeOfDay to update the skycontrol time of day.
     * @param tpf - Update value of time between frames.
     */
    public void update(BulletAppState bullet, Camera cam, TimeOfDay tod, float tpf) {
        basicScene.update(tod, tpf);
        boolean playerAttacking = character.updateCharacterPostion(cam, 
                collController.getPlayerHitSize(), tpf);
        ArrayList<String> mobHits;
        
        if (playerAttacking) {
            mobHits= mobHandler.updateMobs(bullet, character.getPosition(), 
                    collController.getPlayerHits(), collController.getMobHits(), tpf);
        } else {
            mobHits = mobHandler.updateMobs(bullet, character.getPosition(), 
                    new ArrayList<String>(), collController.getMobHits(), tpf);
        }
        character.processKnocks(mobHits);
        collController.attacksProcessed();
    }
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
