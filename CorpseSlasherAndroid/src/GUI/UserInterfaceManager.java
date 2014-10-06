package GUI;

//~--- non-JDK imports --------------------------------------------------------

import CorpseSlasher.GameScene;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

/**
 *
 * @author Gerhard
 * Controls which screen must be displayed at what time.
 */
public final class UserInterfaceManager {
    private static boolean   loginScreen = true;
    private static Screens[] guiScreens;
    private AssetManager     assetManager;
    private InputManager     inputManager;
    private AudioRenderer    audioRenderer;
    private ViewPort         guiViewPort;
    private AppStateManager  appState;
    private Application      app;
    private NiftyJmeDisplay  Screen;
    private GameScene        scene;
    private int              width;
    private int              height;

    /**
     *
     * @param assetManager
     * @param inputManager
     * @param audioRenderer
     * @param guiViewPort
     * @param appState
     * @param app
     * initializes the user interface manager so it can interchange between different screens
     */
    public void init(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer,
                     ViewPort guiViewPort, AppStateManager appState, Application app, GameScene scene) {
        this.assetManager  = assetManager;
        this.inputManager  = inputManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort   = guiViewPort;
        this.app           = app;
        this.appState      = appState;
        Screen             = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        
        guiScreens         = new Screens[6];
        settingsScreen();
    }

    public void setRes(int width, int height) {
        this.width  = width;
        this.height = height;
    }

    public void updateRes(int width, int height) {
        for (Screens screen_1 : guiScreens) {
            if (screen_1 != null) {
                screen_1.updateRes(width, height);
            }
        }

        app.restart();
    }

    /**
     * Creates the login screen
     */
    public void loginScreen() {
        guiScreens[0] = new LoginScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        guiScreens[0].build();
        guiScreens[0].updateRes(width, height);
    }

    /**
     * Creates the new account account screen
     */
    public void newAccount() {
        guiScreens[1] = new NewAccount(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        guiScreens[1].build();
        guiScreens[1].updateRes(width, height);
    }

    /**
     * Creates the retrieve password screen
     */
    public void retrievePassword() {
        guiScreens[2] = new RetrievePassword(assetManager, inputManager, audioRenderer, guiViewPort, appState, app,
                Screen);
        guiScreens[2].build();
        guiScreens[2].updateRes(width, height);
    }

    public void leaderBoard() {
        guiScreens[5] = new Leaderboard(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        guiScreens[5].build();
        guiScreens[5].updateRes(width, height);
    }

    /**
     * Updates the state of the game so that settings menu can be called after login only
     */
    public void changeState() {
        loginScreen = !loginScreen;
    }

    public static void openSettings() {
        if (!loginScreen) {
            guiScreens[3].goTo("Option_Screen");
        }
    }

    /**
     * Adds the mapping of the ESC_KEY to the settings screen, so that it will be called
     * when the ESC_KEY is pressed
     */
    private void settingsScreen() {
        optionScreen();
        leaderBoard();
    }

    /**
     * Creates a new settings screen
     */
    private void optionScreen() {
        guiScreens[3] = new SettingsScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app,
                                           Screen, this, scene);
    }

    public void settings(String selection) {}

    /**
     * Creates a new loading screen object
     */
    public void loadingScreen() {
        guiScreens[4] = new LoadingScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app,
                                          Screen);

        // guiScreens[4].updateRes(1280, 800);
        guiScreens[4].updateRes(width, height);
    }

    /**
     *
     * @return the loading screen object
     * Returns the loading screen object to be updated as the game is being loaded
     */
    public LoadingScreen getLoadingScreen() {
        return (LoadingScreen) guiScreens[4];
    }

    /**
     *
     * @param _screen the ID of the screen to be changed to
     * Changes to the screen ID coming in
     */
    public void goTo(String _screen) {
        guiScreens[3].goTo(_screen);
    }

    public void destroyLogin() {
        guiScreens[0] = guiScreens[1] = guiScreens[2] = null;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
