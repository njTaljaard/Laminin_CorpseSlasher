package CorpseSlasher;

//~--- non-JDK imports --------------------------------------------------------

import GUI.OAuth.OAuth;

import GUI.UserInterfaceManager;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.RadioButtonGroupStateChangedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import jme3utilities.TimeOfDay;

import jme3utilities.sky.SkyControl;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

//~--- JDK imports ------------------------------------------------------------

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;

/**
 * @author normenhansen
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301 Main class to handle program start up until graphical main loop
 * is reached.
 */
public class Main extends SimpleApplication implements ScreenController {
    static int                        byPass     = 0;
    UserInterfaceManager              UI         = new UserInterfaceManager();
    boolean                           errorFound = false;
    GameScene                         gameScene;
    BulletAppState                    bulletAppState;
    TimeOfDay                         timeOfDay;
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
    AppSettings                       gSettings;
    Nifty                             nifty;
    GameSettings                      settingsF;
    Screen                            screen;
    Picture                           healthBorder;
    Picture                           health;
    Picture                           splatter;
    Picture                           aggro;
    int                               width, height;
    String                            message;
    Element                           ele;

    public static void main(String[] args) {
        Main app = new Main();

        app.setShowSettings(false);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        app.start();
    }

    /**
     * This function only gets called once. Use this function to create the
     * login screen where after the scene will be compiled during loading
     * screen.
     */
    @Override
    public void simpleInitApp() {
        width               = 1366;
        height              = 768;
        Audio.assetManager  = assetManager;
        Audio.audioRenderer = audioRenderer;
        ClientConnection.StartClientConnection();
        gSettings = new AppSettings(true);
        gSettings.setResolution(width, height);
        UI.init(assetManager, inputManager, audioRenderer, guiViewPort, stateManager, this, gameScene);
        UI.setRes(width, height);
        flyCam.setEnabled(false);
        inputManager.deleteMapping(INPUT_MAPPING_EXIT);
        this.setSettings(gSettings);
        restart();
        inputManager.setCursorVisible(true);
        UI.loginScreen();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (ClientConnection.loggedIn) {
            gameScene.update(timeOfDay, tpf);
            health.setWidth((gameScene.getHealth() / 100f) * (gSettings.getWidth() / 2.1f));
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {

        // TODO: add render code
    }

    /**
     *
     * @return an array of booleans checking which settings to activate. Loading
     * the settings based on the .txt file
     */
    public GameSettings loadSettings() {
        GameSettings _settings = new GameSettings();

        try {
            try (Scanner in = new Scanner(new FileReader("GameSettings.ini"))) {
                while (in.hasNextLine()) {
                    String nextLine = in.nextLine();
                    String parts[]  = nextLine.split("=");
                    String set      = parts[0];
                    String value    = parts[1];

                    if (value.equals("true")) {
                        _settings.updateSettings(set, value);
                    }

                    if (value.equals("false")) {
                        _settings.updateSettings(set, value);
                    }

                    if (set.equals("width")) {
                        width = Integer.parseInt(parts[1]);
                        _settings.updateSettings(set, "" + width);
                    }

                    if (set.equals("height")) {
                        height = Integer.parseInt(parts[1]);
                        _settings.updateSettings(set, "" + height);
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            if (ex instanceof NumberFormatException) {
                width = 800;
                _settings.updateSettings("width", "" + width);
                height = 600;
                _settings.updateSettings("heigth", "" + height);
            } else {
                ex.printStackTrace();
            }
        }

        UI.setRes(width, height);
        UI.updateRes();
        gSettings.setFullscreen(false);
        gSettings.setResolution(width, height);
        this.setSettings(gSettings);
        UI.setSettings(gSettings);
        restart();

        return _settings;
    }

    public void loadGame() {
        settingsF      = loadSettings();
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        gameScene = new GameScene(0, assetManager, viewPort, cam, bulletAppState, inputManager);
        rootNode.attachChildAt(gameScene.retrieveSceneNode(), 0);

        SkyControl skyControl = rootNode.getChild("BasicScene").getControl(SkyControl.class);

        skyControl.setEnabled(true);
        timeOfDay = new TimeOfDay(2.5f);
        stateManager.attach(timeOfDay);
        timeOfDay.setRate(350f);
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        UserInterfaceManager.changeState();
        nifty.setIgnoreKeyboardEvents(true);
        inputManager.setCursorVisible(false);
        guiNode.setQueueBucket(Bucket.Gui);
        buildPictures();
        UI.setGuis(healthBorder, health, splatter, aggro, guiNode);
        ClientConnection.loggedIn = true;
        UI.destroyLogin();
    }

    public void buildPictures() {
        healthBorder = new Picture("Health Bar");
        health       = new Picture("Health");
        aggro        = new Picture("Agro Detection");
        splatter     = new Picture("Blood Splatter");
        healthBorder.setImage(assetManager, "HUD/Healthbar.png", true);
        healthBorder.setWidth(gSettings.getWidth() / 2);
        healthBorder.setHeight(gSettings.getHeight() / 10);
        healthBorder.setPosition(gSettings.getWidth() / 4f, gSettings.getHeight() / 1.12f);
        guiNode.attachChild(healthBorder);
        health.setImage(assetManager, "HUD/Health.png", true);
        health.setWidth(gSettings.getWidth() / 2.5f);
        health.setHeight(gSettings.getHeight() / 12);
        health.setPosition(gSettings.getWidth() / 3.83f, gSettings.getHeight() / 1.109f);
        guiNode.attachChild(health);
        aggro.setImage(assetManager, "Aggro/frame.png", true);
        aggro.setName("aggroImage");
        aggro.setWidth(gSettings.getWidth() / 1f);
        aggro.setHeight(gSettings.getHeight() / 1f);
        aggro.setPosition(0, 0);
        splatter.setImage(assetManager, "Aggro/splatter.png", true);
        splatter.setWidth(gSettings.getWidth() / 1f);
        splatter.setHeight(gSettings.getHeight() / 1f);
        splatter.setPosition(0, 0);
    }

    public void relog() {
        System.out.println("relog function");
        gameScene.relog(cam, 0);
        ClientConnection.loggedIn = true;
        ClientConnection.relog    = false;
        guiNode.attachChild(healthBorder);
        guiNode.attachChild(health);
        UserInterfaceManager.changeState();
        UI.destroyLogin();
    }

    /**
     *
     * @param nifty the object containing all the components of the GUI
     * @param screen the actual screen panel of the nifty GUI at the binding of
     * the create account screen all the appropriate text field's data are
     * collected and checked
     */
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty  = nifty;
        this.screen = screen;
        nifty.setIgnoreKeyboardEvents(true);
    }

    @Override
    public void onStartScreen() {
        nifty.setIgnoreKeyboardEvents(false);
        ele = nifty.getScreen("#Login_Screen").findElementByName("#ErrorMessage");

        if (!errorFound) {
            ele.hide();
        } else {
            errorFound = false;
        }
    }

    @Override
    public void onEndScreen() {
        nifty.setIgnoreKeyboardEvents(true);
    }

    /**
     * After a successful login the loading screen is loaded and the game starts
     * to load
     */
    public void loadingScreen() {
        usernameTxt = nifty.getScreen("#Login_Screen").findNiftyControl("#Username_Input_ID", TextField.class);
        passwordTxt = nifty.getScreen("#Login_Screen").findNiftyControl("#Password_Input_ID", TextField.class);

        boolean success = ClientConnection.Login(usernameTxt.getRealText(), passwordTxt.getRealText());

        System.out.println("loading screen - " + success + " " + usernameTxt.getRealText() + " "
                           + passwordTxt.getRealText());

        String username = usernameTxt.getRealText();

        if (success) {
            inputManager.setCursorVisible(false);

            if (ClientConnection.relog) {
                ClientConnection.setUsername(username);
                UI.destroyLogin();
                relog();

                return;
            } else {
                ClientConnection.setUsername(username);
                UI.destroyLogin();
                loadGame();

                return;
            }
        } else {
            message = "Username or password incorrect";

            /*
             *  guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
             * inputManager.setCursorVisible(false);
             *
             * if (ClientConnection.relog) {
             *    relog();
             * } else {
             *    loadGame();
             *    UI.destroyLogin();
             * }
             */
        }

        ele.show();

        TextField tf = nifty.getScreen("#Login_Screen").findNiftyControl("#ErrorMessage", TextField.class);

        errorFound = true;
        tf.setText(message);
    }

    /**
     * Changes the screen to the new account screen
     */
    public void newAccount() {
        UI.newAccount();
    }

    /**
     * Checks all the appropriate fields whther they are valid and then adds the
     * data to the database and logins in
     */
    public void createNewAccount() {
        accUser       = nifty.getScreen("New_Account_Screen").findNiftyControl("#Username_Input_ID_2", TextField.class);
        accEmail      = nifty.getScreen("New_Account_Screen").findNiftyControl("#Email_Input_ID", TextField.class);
        accSurname    = nifty.getScreen("New_Account_Screen").findNiftyControl("#Surname_Input_ID", TextField.class);
        accName       = nifty.getScreen("New_Account_Screen").findNiftyControl("#Name_Input_ID", TextField.class);
        accPassword   = nifty.getScreen("New_Account_Screen").findNiftyControl("#Password_Input_ID_2", TextField.class);
        accPasswordRE = nifty.getScreen("New_Account_Screen").findNiftyControl("#Password_Input_ID_2_2",
                                        TextField.class);

        String username = accUser.getRealText();

        if (accPassword.getRealText().equals(accPasswordRE.getRealText())) {
            if (!username.contains("@") &&!username.contains("!") &&!username.contains("#") &&!username.contains("$")
                    &&!username.contains("%") &&!username.contains("^") &&!username.contains("&")
                    &&!username.contains("*")) {
                if (ClientConnection.CheckUsernameAvailable(accUser.getRealText())) {
                    boolean success = ClientConnection.AddUser(accUser.getRealText(), accPassword.getRealText(),
                                          accName.getRealText(), accSurname.getRealText(), accEmail.getRealText());

                    if (success) {
                        if (ClientConnection.relog) {
                            ClientConnection.setUsername(username);
                            UI.destroyLogin();
                            relog();

                            return;
                        } else {
                            ClientConnection.setUsername(username);
                            UI.destroyLogin();
                            loadGame();

                            return;
                        }
                    } else {
                        message = "Failed adding user";
                    }
                } else {
                    message = "Username already exists please try again";
                }
            } else {
                message = "Usernames may only contain Alphanumeric values and _ - ( )";
            }
        } else {
            message = "Missmatch password";
        }

        ele.show();

        TextField tf = nifty.getScreen("#Login_Screen").findNiftyControl("#ErrorMessage", TextField.class);

        tf.setText(message);
        errorFound = true;
        goBack();
    }

    /**
     * Goes to the retrieve password screen
     */
    public void retrievePassword() {
        UI.retrievePassword();
    }

    public void erase(String id) {
        TextField text = screen.findNiftyControl(id, TextField.class);

        text.setText("");
    }

    /**
     * Goes to the login screen
     */
    public void retrievePasswordAndGoBack() {
        retUser = nifty.getScreen("Retrieve_Password").findNiftyControl("#Username_Input_ID_3", TextField.class);

        if (retUser.getRealText().contains("@")) {
            ClientConnection.RetrievePasswordInputEmail(retUser.getRealText());
            nifty.gotoScreen("#Login_Screen");
        } else {
            ClientConnection.RetrievePassword(retUser.getRealText());
            nifty.gotoScreen("#Login_Screen");
        }
    }

    /**
     * Quits the game
     */
    public void quitGame() {
        stop(false);
        System.exit(0);
    }

    /**
     * Goes to screen selected
     */
    public void goTo(String screen) {
        if (screen.equals("#Login_Screen")) {
            ClientConnection.loggedIn = false;
            ClientConnection.relog    = true;
            UserInterfaceManager.changeState();
        }

        nifty.gotoScreen(screen);
        Audio.pauseAmbient();
    }

    /**
     * Goes back to the login screen
     */
    public void goBack() {
        nifty.gotoScreen("#Login_Screen");
    }

    /**
     * Logins in via the selected social media
     */
    public void socialLogin(String type) {
        switch (type) {
        case "1" :
            try {
                boolean success = OAuth.facebookLogin();

                if (success) {
                    if (ClientConnection.relog) {
                        UI.destroyLogin();
                        relog();

                        return;
                    } else {
                        UI.destroyLogin();
                        loadGame();

                        return;
                    }
                } else {
                    message = "Incorrect details";
                }
            } catch (OAuthSystemException | IOException ex) {
                message = "Error occurred (1FB)";
            }

            break;

        case "2" :
            try {
                boolean success = OAuth.googleLogin();

                if (success) {
                    if (ClientConnection.relog) {
                        UI.destroyLogin();
                        relog();

                        return;
                    } else {
                        UI.destroyLogin();
                        loadGame();

                        return;
                    }
                } else {
                    message = "Incorrect details";
                }
            } catch (OAuthSystemException | IOException ex) {
                message = "Error occurred (2G+)";
            }

            break;

        default :
        }

        ele.show();

        TextField tf = nifty.getScreen("#Login_Screen").findNiftyControl("#ErrorMessage", TextField.class);

        tf.setText(message);
        errorFound = true;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
