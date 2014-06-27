package CorpseSlasher;

//~--- non-JDK imports --------------------------------------------------------

import GUI.LoadingScreen;
import GUI.LoginScreen;
import GUI.UserInterfaceManager;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.RadioButtonGroupStateChangedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.xml.xpp3.Attributes;

import jme3utilities.TimeOfDay;

import jme3utilities.sky.SkyControl;

//~--- JDK imports ------------------------------------------------------------

import java.util.Properties;

/**
 * @author normenhansen
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Main class to handle program start up until graphical main loop is reached.
 */
public class Main extends SimpleApplication implements ScreenController {
    static int                        byPass = 0;
    UserInterfaceManager              UI     = new UserInterfaceManager();
    GameScene                         gameScene;
    BulletAppState                    bulletAppState;
    TimeOfDay                         timeOfDay;
    LoginScreen                       login;
    boolean                           loggedIn;
    TextField                         usernameTxt;
    TextField                         passwordTxt;
    TextField                         accUser;
    TextField                         accEmail;
    TextField                         accSurname;
    TextField                         accName;
    TextField                         accPassword;
    TextField                         accPasswordRE;
    RadioButtonGroupStateChangedEvent selectedButton;
    TextField                         retUser;
    TextRenderer                      textRenderer;
    Element                           progressBarElement;

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

        inputManager.deleteMapping(INPUT_MAPPING_EXIT);
        UI.init(assetManager, inputManager, audioRenderer, guiViewPort, stateManager, this);
        ClientConnection.StartClientConnection();
        UI.optionScreen();
        UI.loginScreen();

        // UI.loadingScreen();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (loggedIn) {
            gameScene.update(cam, timeOfDay, tpf);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {

        // TODO: add render code
    }

    public void loadGame() {

        /**
         * @TODO load settings here.
         */
        inputManager.setCursorVisible(false);
        flyCam.setEnabled(true);
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        gameScene = new GameScene(0, assetManager, viewPort, cam, bulletAppState, 
                inputManager, UI.getLoadingScreen());
        rootNode.attachChildAt(gameScene.retrieveSceneNode(), 0);

        SkyControl skyControl = rootNode.getChild("BasicScene").getControl(SkyControl.class);

        skyControl.setEnabled(true);
        timeOfDay = new TimeOfDay(5.5f);
        stateManager.attach(timeOfDay);
        timeOfDay.setRate(350f);
        loggedIn = true;
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());       
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        usernameTxt   = screen.findNiftyControl("Username_Input_ID", TextField.class);
        passwordTxt   = screen.findNiftyControl("Password_Input_ID", TextField.class);
        accUser       = screen.findNiftyControl("Username_Input_ID_2", TextField.class);
        accEmail      = screen.findNiftyControl("Email_Input_ID", TextField.class);
        accSurname    = screen.findNiftyControl("Surname_Input_ID", TextField.class);
        accName       = screen.findNiftyControl("Name_Input_ID", TextField.class);
        accPassword   = screen.findNiftyControl("Password_Input_ID_2", TextField.class);
        accPasswordRE = screen.findNiftyControl("Password_Input_ID_2_2", TextField.class);
        retUser       = screen.findNiftyControl("Username_Input_ID_3", TextField.class);
    }

    @Override
    public void onStartScreen() {}

    @Override
    public void onEndScreen() {}

    @NiftyEventSubscriber(id = "Selections")
    public void radioButtons(final String id, final RadioButtonGroupStateChangedEvent event) {
        selectedButton = event;
        System.out.println(selectedButton.getSelectedId() + " was chosen");
    }

    public void loadingScreen() {

        // System.out.println(selectedButton.getSelectedId() + " was chosen");
        boolean success = ClientConnection.Login(usernameTxt.getRealText(), passwordTxt.getRealText());
        
        if (success) {
            guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());           
            UI.loadingScreen();
            loadGame();
        } else {
            System.out.println("Username or password incorrect");
            guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());   
            UI.loadingScreen();
            loadGame();
        }
    }

    public void newAccount() {
        guiViewPort.getProcessors().remove(0);
        UI.newAccount();
    }

    public void createNewAccount() {
        if (accPassword.getRealText().equals(accPasswordRE.getRealText())) {
            if (ClientConnection.CheckUsernameAvailable(accUser.getRealText())) {
                boolean success = ClientConnection.AddUser(accUser.getRealText(), accPassword.getRealText(),
                                      accName.getRealText(), accSurname.getRealText(), accEmail.getRealText());

                if (success) {
                    guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
                    loginScreen();
                } else {
                    System.out.println("Failed adding user");
                }
            } else {
                System.out.println("Username already exists please try again");
            }
        } else {
            System.out.println("Missmatch password");
        }
    }

    public void retrievePassword() {
        guiViewPort.getProcessors().remove(0);
        UI.retrievePassword();
    }

    public void loginScreen() {
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        ClientConnection.RetrievePassword(retUser.getRealText());
        UI.loginScreen();
    }
    public void quitGame(){
     System.exit(0);
    }    
    public void graphicsScreen(){
        UI.goTo("Graphics_Settings");
    }    
    public void audioScreen(){
        UI.goTo("Audio_Settings");
    }    
    public void difficultyScreen(){
        UI.goTo("Difficulty_Settings");
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
