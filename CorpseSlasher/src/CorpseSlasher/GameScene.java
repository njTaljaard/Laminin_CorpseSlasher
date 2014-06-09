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
    
    /**
     * All global variables required to initialize each scene element.
     */
    private Node sceneNode;
    private BasicScene basicScene;
    private Character character;
    
    /**
     * @param selectedMap - the desired map to load.
     * @param Assetmanager - Assetmanager passed through from main game.
     * @param ViewPort - ViewPort required for water, contains position of camara.
     */
    public GameScene(int selectedMap, AssetManager assestManager, ViewPort viewPort, 
            Camera cam, BulletAppState bullet, InputManager inMan) {
        sceneNode = new Node("GameScene");
        initScene(assestManager, viewPort, cam, bullet, selectedMap);
        initMainCharacter(assestManager, inMan, bullet);
    }
    
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
    
    private void initMainCharacter(AssetManager assMan, InputManager inMan, BulletAppState bullet) {
        character = new Character(assMan, inMan, bullet);        
        sceneNode.attachChild(character.retrievePlayerNode());
    }
    
    public Vector3f updateCharacterPosition(Camera cam) {
        return character.updateCharacterPostion(cam);
    }
    
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
