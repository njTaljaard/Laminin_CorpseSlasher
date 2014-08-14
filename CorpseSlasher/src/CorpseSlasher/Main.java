package CorpseSlasher;

//~--- non-JDK imports --------------------------------------------------------
import GUI.UserInterfaceManager;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
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

import java.util.Scanner;

/**
 * @author normenhansen
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301 Main class to handle program start up until graphical main loop
 * is reached.
 */
@SuppressWarnings("*")
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
        gSettings = new AppSettings(true);
        UI.init(assetManager, inputManager, audioRenderer, guiViewPort, stateManager, this);
        settingsF = loadSettings();
        loggedIn = false;
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(true);
        inputManager.deleteMapping(INPUT_MAPPING_EXIT);
        ClientConnection.StartClientConnection();
        UI.leaderBoard();
        UI.optionScreen();
        UI.loginScreen();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (loggedIn) {
            gameScene.update(bulletAppState, cam, timeOfDay, tpf);
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
        int height = 600;
        int width = 800;
        String previous = "";
        try {
            try (Scanner in = new Scanner(new FileReader("GameSettings.txt")).useDelimiter("=")) {
                while (in.hasNext()) {
                    String next = in.next().replace("\n", "");
                    next = next.replace("\r", "");
                    if (next.equals("true")) {
                        _settings.updateSettings(previous, next);
                    }

                    if (next.equals("false")) {
                        _settings.updateSettings(previous, next);
                    }
                    if (next.equals("width")) {
                        width = in.nextInt();
                        _settings.updateSettings(next, "" + width);
                    }
                    if (next.equals("height")) {
                        height = in.nextInt();
                        _settings.updateSettings(next, "" + height);
                    }
                    previous = next;
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
        System.out.println(width+" "+height);
        //AppSettings setting = new AppSettings(true);
        UI.updateRes(width, height);
        gSettings.setResolution(width, height);
        gSettings.setFullscreen(true);
        //setting.setFullscreen(true);
        //setting.setResolution(width, height);
        this.setSettings(gSettings);
        restart();
        return _settings;
    }

    public void loadGame() {
        // Settings file loaded and now must be used in program

        inputManager.setCursorVisible(false);
        flyCam.setEnabled(true);
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        gameScene = new GameScene(0, assetManager, viewPort, cam, bulletAppState, inputManager, UI.getLoadingScreen(),
                settingsF);
        rootNode.attachChildAt(gameScene.retrieveSceneNode(), 0);

        SkyControl skyControl = rootNode.getChild("BasicScene").getControl(SkyControl.class);

        skyControl.setEnabled(true);
        timeOfDay = new TimeOfDay(5.5f);
        stateManager.attach(timeOfDay);
        timeOfDay.setRate(350f);
        loggedIn = true;
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        UI.changeState();
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
        usernameTxt = screen.findNiftyControl("Username_Input_ID", TextField.class);
        passwordTxt = screen.findNiftyControl("Password_Input_ID", TextField.class);
        accUser = screen.findNiftyControl("Username_Input_ID_2", TextField.class);
        accEmail = screen.findNiftyControl("Email_Input_ID", TextField.class);
        accSurname = screen.findNiftyControl("Surname_Input_ID", TextField.class);
        accName = screen.findNiftyControl("Name_Input_ID", TextField.class);
        accPassword = screen.findNiftyControl("Password_Input_ID_2", TextField.class);
        accPasswordRE = screen.findNiftyControl("Password_Input_ID_2_2", TextField.class);
        retUser = screen.findNiftyControl("Username_Input_ID_3", TextField.class);

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
     *
     * @param id the ID of radio button selected
     * @param event the state of the selected radio button on a state change the
     * button event is updated
     */
    @NiftyEventSubscriber(id = "Selections")
    public void radioButtons(final String id, final RadioButtonGroupStateChangedEvent event) {
        selectedButton = event;
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

            // UI.loadingScreen();
            loadGame();
        } else {
            System.out.println("Username or password incorrect");
            guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());

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

    /**
     * Goes to the retrieve password screen
     */
    public void retrievePassword() {
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        UI.retrievePassword();
    }

    /**
     * Goes to the login screen
     */
    public void loginScreen() {
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        ClientConnection.RetrievePassword(retUser.getRealText());
        UI.loginScreen();
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
        UI.goTo(screen);
    }

    /**
     * Goes back to the login screen
     */
    public void goBack() {
        UI.goTo("Login_Screen");
    }

    /**
     * Logins in via the selected social media
     */
    public void socialLogin(String type) {
        switch (type) {
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
