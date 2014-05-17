package CorpseSlasher;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.water.WaterFilter;
import jme3utilities.TimeOfDay;
import jme3utilities.sky.SkyControl;

/**
 * @author normenhansen
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Main class to handle program start up until graphical main loop is reached.
 */
public class Main extends SimpleApplication {

    TimeOfDay timeOfDay;
    boolean sky;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    /**
     * This function only gets called once. Use this function to setup the entire scene.
     * This will inculde add all objects to the scene, set all bound values ex. Water settings.
     */
    @Override
    public void simpleInitApp() {/**
         * Turn this value up to move faster.
         */
        flyCam.setMoveSpeed(50f);
        
        cam.setLocation(new Vector3f(0.0f, 60.0f, 0.0f));
        
        loadGame();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (sky) {
            SkyControl sc = rootNode.getChild("BasicScene").getControl(SkyControl.class);
            sc.update(tpf);
            sc.getSunAndStars().setHour(timeOfDay.getHour());

            Vector3f scLight = sc.getUpdater().getDirection();
            DirectionalLight sun = (DirectionalLight) rootNode.getChild("BasicScene").getLocalLightList().get(1);
            sun.setDirection(scLight);

            FilterPostProcessor waterProcessor = (FilterPostProcessor) viewPort.getProcessors().get(0);
            WaterFilter water = waterProcessor.getFilter(WaterFilter.class);
            water.setLightDirection(scLight);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public void loadGame() {
        /**
        * @TODO load settings here.
        */
        sky = true;
        
        GameScene gameScene = new GameScene(0, assetManager, viewPort, cam);
        
        rootNode.attachChildAt(gameScene.retrieveSceneNode(), 0); //BasicScene objects
        
        if (sky) {
            SkyControl skyControl = rootNode.getChild("BasicScene").getControl(SkyControl.class);
            skyControl.setEnabled(true);
        
            timeOfDay = new TimeOfDay(3.0f);
            stateManager.attach(timeOfDay);
            timeOfDay.setRate(1000f);
        }
    }
}
