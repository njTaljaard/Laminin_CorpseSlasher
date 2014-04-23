package CorpseSlasher;

import com.jme3.asset.AssetManager;

/**
 * @author Laminin
 * @param  Derivco
 * GameScene will load and compile the scene. It implements runnable to improve
 * loading of models into the scene.
 */
public class GameScene implements Runnable {
    
    /**
     * All global variables required to initialize each scene element.
     */
    AssetManager assetManager;
    
    public GameScene() {
        try {
            assetManager = AssetManager.class.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            //TODO: extend to use ExceptionHandler
        }
    }

    /**
     * This is a thread run to compose scene elements.
     */
    public void run() {
        
    }
}
