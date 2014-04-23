package CorpseSlasher;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * @author normenhansen
 * @author Laminin
 * @param  Derivco
 * Main class to handle program start up until graphical main loop is reached.
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    /**
     * This function only gets called once. Use this function to setup the entire scene.
     * This will inculde add all objects to the scene, set all bound values ex. Water settings.
     */
    @Override
    public void simpleInitApp() {
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
