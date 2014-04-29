package CorpseSlasher;

import com.jme3.asset.AssetManager;
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
    private AssetManager assetManager;
    private Node sceneNode;
    private int map;
    
    public GameScene(int selectedMap) {
        map = selectedMap;
        initScene();
    }
    
    private void initScene() {
        switch(map) {
            case(0) :   
                BasicScene basicScene = new BasicScene("ZombieScene1");
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
