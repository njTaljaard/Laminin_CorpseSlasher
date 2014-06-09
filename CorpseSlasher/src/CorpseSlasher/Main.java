package CorpseSlasher;

import GUI.LoginScreen;
import GUI.UserInterfaceManager;
//import GUI.UserInterfaceManager;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.water.WaterFilter;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
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
public class Main extends SimpleApplication implements ScreenController{
    
    GameScene gameScene;
    BulletAppState bulletAppState;
    TimeOfDay timeOfDay;
    LoginScreen login;
    boolean loggedIn;
    static int byPass = 0;
    UserInterfaceManager UI = new UserInterfaceManager();
    
    public static void main(String[] args) {
        Main app = new Main();       
        app.start();
    }

    /**
     * This function only gets called once. Use this function to create the login
     * screen where after the scene will be compiled during loading screen.
     */
    @Override
    public void simpleInitApp() {
        loggedIn = false;
        
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(true);
        UI.init(assetManager, inputManager, audioRenderer, guiViewPort, stateManager, this);
        UI.loginScreen();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (loggedIn) {
            /**
             * Update sunlight direction and time of day.
             */
            SkyControl sc = rootNode.getChild("BasicScene").getControl(SkyControl.class);
            sc.update(tpf);
            sc.getSunAndStars().setHour(timeOfDay.getHour());

            Vector3f scLight = sc.getUpdater().getDirection();
            DirectionalLight sun = (DirectionalLight) rootNode.getChild("BasicScene").getLocalLightList().get(1);
            sun.setDirection(scLight);

            FilterPostProcessor waterProcessor = (FilterPostProcessor) viewPort.getProcessors().get(0);
            WaterFilter water = waterProcessor.getFilter(WaterFilter.class);
            water.setLightDirection(scLight);
            
            /**
             * Update walk.
             */
            cam.setLocation(gameScene.updateCharacterPosition(cam));
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
        
        inputManager.setCursorVisible(false);
        flyCam.setEnabled(true);
        flyCam.setMoveSpeed(50f);
        //cam.setLocation(new Vector3f(0.0f, 70.0f, 0.0f));
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        gameScene = new GameScene(0, assetManager, viewPort, cam, bulletAppState, inputManager);
        rootNode.attachChildAt(gameScene.retrieveSceneNode(), 0);
        
        SkyControl skyControl = rootNode.getChild("BasicScene").getControl(SkyControl.class);
        skyControl.setEnabled(true);
                
        timeOfDay = new TimeOfDay(3.0f);
        stateManager.attach(timeOfDay);
        timeOfDay.setRate(1000f);
        loggedIn = true;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) 
    {
        
    }

    @Override
    public void onStartScreen() 
    {
        
    }

    @Override
    public void onEndScreen()
    {
        
    }
   
    public void quitGame()
    {
        guiViewPort.getProcessors().remove(0);
        loadGame();
    }
}
