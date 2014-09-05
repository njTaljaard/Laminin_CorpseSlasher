package CorpseSlasher;

//~--- non-JDK imports --------------------------------------------------------
import GUI.OAuth.OAuth;
import GUI.UserInterfaceManager;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
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

//~--- JDK imports ------------------------------------------------------------

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * @author normenhansen
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301 Main class to handle program start up until graphical main loop
 * is reached.
 */
public class Main extends SimpleApplication implements ScreenController {

    static int byPass = 0;
    UserInterfaceManager UI = new UserInterfaceManager();
    GameScene gameScene;
    BulletAppState bulletAppState;
    TimeOfDay timeOfDay;
    boolean loggedIn;
    TextField usernameTxt;
    TextField passwordTxt;
    TextField accUser;
    TextField accEmail;
    TextField accSurname;
    TextField accName;
    TextField accPassword;
    TextField accPasswordRE;
    RadioButtonGroupStateChangedEvent selectedButton;
    TextField retUser;
    TextRenderer textRenderer;
    Element progressBarElement;
    AppSettings gSettings;
    Nifty nifty;
    GameSettings settingsF;
    Screen screen;
    Picture healthBorder;
    Picture health;
    int width, height;

    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        app.setDisplayFps(true);
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
        width = 1366;
        height = 768;
        Audio.assetManager = assetManager;
        Audio.audioRenderer = audioRenderer;
        ClientConnection client = new ClientConnection();
        client.StartClientConnection();
        gSettings = new AppSettings(true);
        gSettings.setResolution(width, height);
        UI.init(assetManager, inputManager, audioRenderer, guiViewPort, stateManager, this, gameScene, client);
        UI.setRes(width, height);
        loggedIn = false;
        flyCam.setEnabled(false);
        inputManager.deleteMapping(INPUT_MAPPING_EXIT);
        this.setSettings(gSettings);
        restart();
        inputManager.setCursorVisible(true);
        UI.loginScreen();
        //loadGame();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (loggedIn) {
            gameScene.update(timeOfDay, tpf);
            health.setWidth((gameScene.getHealth()/100f)*(gSettings.getWidth()/2.1f));
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
            try (Scanner in = new Scanner(new FileReader("GameSettings.txt"))) {
                while (in.hasNextLine()) {
                    String nextLine = in.nextLine();
                    String parts[] = nextLine.split("=");
                    String set = parts[0];
                    String value = parts[1];
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
        UI.updateRes(width, height);
        gSettings.setFullscreen(false);
        gSettings.setResolution(width, height);
        gSettings.setFullscreen(true);
        this.setSettings(gSettings);
        UI.setSettings(gSettings);
        restart();
        return _settings;
    }

    public void loadGame() {
        settingsF = loadSettings();
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        gameScene = new GameScene(0, assetManager, viewPort, cam, bulletAppState, inputManager/*, UI.getLoadingScreen()*/,
                settingsF);
        rootNode.attachChildAt(gameScene.retrieveSceneNode(), 0);

        SkyControl skyControl = rootNode.getChild("BasicScene").getControl(SkyControl.class);

        skyControl.setEnabled(true);
        timeOfDay = new TimeOfDay(5.5f);
        stateManager.attach(timeOfDay);
        timeOfDay.setRate(350f);
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        UI.changeState();
        nifty.setIgnoreKeyboardEvents(true);
        inputManager.setCursorVisible(false);
        guiNode.setQueueBucket(Bucket.Gui);
        healthBorder = new Picture("Health Bar");
        health = new Picture("Health");
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
        UI.setGuis(healthBorder,health, guiNode);
        loggedIn = true;
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
        this.nifty = nifty;
        this.screen = screen;
        usernameTxt = screen.findNiftyControl("#Username_Input_ID", TextField.class);
        passwordTxt = screen.findNiftyControl("#Password_Input_ID", TextField.class);
        accUser = screen.findNiftyControl("#Username_Input_ID_2", TextField.class);
        accEmail = screen.findNiftyControl("#Email_Input_ID", TextField.class);
        accSurname = screen.findNiftyControl("#Surname_Input_ID", TextField.class);
        accName = screen.findNiftyControl("#Name_Input_ID", TextField.class);
        accPassword = screen.findNiftyControl("#Password_Input_ID_2", TextField.class);
        accPasswordRE = screen.findNiftyControl("#Password_Input_ID_2_2", TextField.class);
        retUser = screen.findNiftyControl("#Username_Input_ID_3", TextField.class);
    }

    @Override
    public void onStartScreen() {
        nifty.setIgnoreKeyboardEvents(false);
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

        // System.out.println(selectedButton.getSelectedId() + " was chosen");
        boolean success = ClientConnection.Login(usernameTxt.getRealText(), passwordTxt.getRealText());

        if (success) {
            guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
            inputManager.setCursorVisible(false);
            // UI.loadingScreen();
            loadGame();
        } else {
            System.out.println("Username or password incorrect");
            guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
            inputManager.setCursorVisible(false);
            // UI.loadingScreen();
            loadGame();
        }
    }

    /**
     * Changes the screen to the new account screen
     */
    public void newAccount() {
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        UI.newAccount();
    }

    /**
     * Checks all the appropriate fields whther they are valid and then adds the
     * data to the database and logins in
     */
    public void createNewAccount() {
        String username = accUser.getRealText();
        if (accPassword.getRealText().equals(accPasswordRE.getRealText())) {
            if (!username.contains("@") && !username.contains("!") && !username.contains("#") && !username.contains("$") && !username.contains("%") && !username.contains("^") && !username.contains("&") && !username.contains("*")) {
                if (ClientConnection.CheckUsernameAvailable(accUser.getRealText())) {
                    boolean success = ClientConnection.AddUser(accUser.getRealText(), accPassword.getRealText(),
                            accName.getRealText(), accSurname.getRealText(), accEmail.getRealText());

                    if (success) {
                        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
                        //loginScreen();
                        loadGame();
                    } else {
                        System.out.println("Failed adding user");
                    }
                } else {
                    System.out.println("Username already exists please try again");
                }
            } else {
                System.out.println("Usernames may only contain Alphanumeric values and _ - ( )");
            }
        } else {
            System.out.println("Missmatch password");
        }
    }

    /**
     * Goes to the retrieve password screen
     */
    public void retrievePassword() {
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
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
    }

    /**
     * Goes to screen selected
     */
    public void goTo(String screen) {
        nifty.gotoScreen(screen);
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
            case "1":
                try {
                    boolean success = OAuth.facebookLogin();
                    if (success) {
                        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
                        loadGame();
                    } else {
                        System.out.println("Incorrect details");
                    }
                } catch (OAuthSystemException | IOException ex) {
                    System.out.println("Error occurred (1FB)");
                }
                break;
            case "2":
                try {
                    boolean success = OAuth.googleLogin();
                    if (success) {
                        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
                        loadGame();
                    } else {
                        System.out.println("Incorrect details");
                    }
                } catch (OAuthSystemException | IOException ex) {
                    System.out.println("Error occurred (2G+)");
                }
                break;
            default:
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
