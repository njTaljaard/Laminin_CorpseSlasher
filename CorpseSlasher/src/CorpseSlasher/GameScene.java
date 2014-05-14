package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import jme3utilities.sky.SkyControl;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * GameScene will load and compile the scene with its various assets.
 */
public class GameScene {
    
    /**
     * All global variables required to initialize each scene element.
     */
    private Node sceneNode;
    private BasicScene basicScene;
    private int map;
    
    /**
     * @param selectedMap - the desired map to load.
     * @param Assetmanager - Assetmanager passed through from main game.
     * @param ViewPort - ViewPort required for water, contains position of camara.
     */
    public GameScene(int selectedMap, AssetManager assestManager, ViewPort viewPort, Camera cam) {
        map = selectedMap;
        sceneNode = new Node("GameScene");
        initScene(assestManager, viewPort, cam);
    }
    
    private void initScene(AssetManager assestManager, ViewPort viewPort, Camera cam) {
        switch(map) {
            case(0) :   
                basicScene = new BasicScene("ZombieScene1");
                basicScene.createScene(assestManager, viewPort, cam);
                sceneNode.attachChild(basicScene.retrieveSceneNode());
                break;
            default :
                break;
        }
    }
    
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
