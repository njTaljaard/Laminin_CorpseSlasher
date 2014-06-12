package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

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
    
    /**
     * 
     * @param selectedMap - the desired map to load.
     * @param Assetmanager - Assetmanager passed through from main game.
     * @param ViewPort - ViewPort required for water, contains position of camara.
     */
    public GameScene(int selectedMap, AssetManager assestManager, ViewPort viewPort, 
            Camera cam, BulletAppState bullet, InputManager inMan) {
        sceneNode = new Node("GameScene");
        initCameraPosition(cam, selectedMap);
        initScene(assestManager, viewPort, cam, bullet, selectedMap);
        initMainCharacter(assestManager, inMan, bullet, cam);
    }
    
    /**
     * 
     * @param assestManager
     * @param viewPort
     * @param cam
     * @param bullet
     * @param map 
     */
    private void initScene(AssetManager assestManager, ViewPort viewPort, Camera cam,
            BulletAppState bullet, int map) {
        switch(map) {
            case(0) :   
                basicScene = new BasicScene("ZombieScene1");
                basicScene.createScene(assestManager, viewPort, cam, bullet);
                sceneNode.attachChild(basicScene.retrieveSceneNode());
                break;
            default :
                break;
        }
    }
    
    /**
     * 
     * @param assMan
     * @param inMan
     * @param bullet 
     */
    private void initMainCharacter(AssetManager assMan, InputManager inMan, 
            BulletAppState bullet, Camera cam) {
        character = new Character(assMan, inMan, bullet, cam);
        sceneNode.attachChild(character.retrievePlayerNode());
    }
    
    /**
     * 
     * @param cam
     * @param map 
     */
    private void initCameraPosition(Camera cam, int map) {
        switch(map) {
            case(0) :
                cam.setLocation(new Vector3f(195.0f, 37.0f, -225.0f));
                cam.lookAt(new Vector3f(200, 25, -500), cam.getUp());
                break;
            default :
                break;
        }
    }
    
    /**
     * 
     * @param cam 
     */
    public void updateCharacterPosition(Camera cam) {
        character.updateCharacterPostion(cam);
    }
    
    /**
     * 
     * @return 
     */
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
