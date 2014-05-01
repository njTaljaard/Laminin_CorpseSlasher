package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

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
    private Spatial sceneModel;
    private Node sceneNode;
    private Node lightNode;
    private int map;
    
    /**
     * @param selectedMap - the desired map to load.
     * @param Assetmanager - Assetmanager passed through from main game.
     * @param ViewPort - ViewPort required for water, contains position of camara.
     */
    public GameScene(int selectedMap, AssetManager assestManager, ViewPort viewPort) {
        map = selectedMap;
        sceneNode = new Node("scene");
        lightNode = new Node("light");
        initScene(assestManager, viewPort);
    }
    
    private void initScene(AssetManager assestManager, ViewPort viewPort) {
        switch(map) {
            case(0) :   
                BasicScene basicScene = new BasicScene("ZombieScene1", assestManager, viewPort);
                sceneNode.attachChild(basicScene.retrieveSceneNode());
                break;
            default :
                break;
        }
    }
    
    public Node retrieveSceneNode() {
        return sceneNode;
    }
    
    public Node retrieveLightNode() {
        return lightNode;
    }
}
