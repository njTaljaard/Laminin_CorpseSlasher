package GUI;

//~--- non-JDK imports --------------------------------------------------------

import CorpseSlasher.GameScene;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

/**
 *
 * @author Gerhard
 * Controls which screen must be displayed at what time.
 */
public final class UserInterfaceManager {
    private static boolean      loginScreen = true;
    private static InputManager inputManager;
    public static Picture       aggro;
    public static Picture       splatter;
    public static Node          guiNode;
    private boolean             menuOpen = false;
    private AssetManager        assetManager;
    private AudioRenderer       audioRenderer;
    private ViewPort            guiViewPort;
    private AppStateManager     appState;
    private Application         app;
    private NiftyJmeDisplay     Screen;
    private ActionListener      action;
    private Screens[]           guiScreens;
    private GameScene           scene;
    private int                 width;
    private int                 height;
    private Picture             healthBorder;
    private Picture             health;
    private AppSettings         settings;

    /**
     * initializes the user interface manager so it can interchange between different screens   
     * @param assetManager
     * @param inputManager
     * @param audioRenderer
     * @param guiViewPort
     * @param appState
     * @param app
     */
    public void init(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer,
                     ViewPort guiViewPort, AppStateManager appState, Application app, GameScene scene) {
        this.assetManager                 = assetManager;
        UserInterfaceManager.inputManager = inputManager;
        this.audioRenderer                = audioRenderer;
        this.guiViewPort                  = guiViewPort;
        this.app                          = app;
        this.appState                     = appState;
        settingsScreen();
        Screen     = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        guiScreens = new Screens[6];
    }
    /**
     * Allows the guis to be accessed from anywhere else.
     * @param border the border around the health bar.
     * @param health the health bar. 
     * @param splatter the blood splatter that pops up when attacked.
     * @param aggro the aggro border,
     * @param guiNode the node that keeps all the pictures.
     */
    public void setGuis(Picture border, Picture health, Picture splatter, Picture aggro, Node guiNode) {
        UserInterfaceManager.guiNode  = guiNode;
        this.healthBorder             = border;
        UserInterfaceManager.aggro    = aggro;
        UserInterfaceManager.splatter = splatter;
        this.health                   = health;
    }
    /**
     * changes the width and the height of the screens to be updated    
     * @param width new width of the screenss.
     * @param height new height of the screens.
     */
    public void setRes(int width, int height) {
        this.width  = width;
        this.height = height;
    }
    /**
     * Updates all the screens to the new set width and height
     */
    public void updateRes() {
        for (Screens screen_1 : guiScreens) {
            if (screen_1 != null) {
                screen_1.updateRes(width, height);
            }

            Screen.getNifty().enableAutoScaling(width, height);
        }
        app.restart();
    }
    /**
     * Allows the game settings to be updated from the manager.
     * @param settings the game settings to be updated.
     */
    public void setSettings(AppSettings settings) {
        this.settings = settings;
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

    /**
     * Creates the leaderboard screen
     */    
    public void leaderBoard() {
        guiScreens[5] = new Leaderboard(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        guiScreens[5].build();
        guiScreens[5].updateRes(width, height);
    }

    /**
     * Updates the state of the game so that settings menu can be called after login only
     */
    public static void changeState() {
        loginScreen = !loginScreen;
        inputManager.setCursorVisible(loginScreen);
    }

    /**
     * Adds the mapping of the ESC_KEY to the settings screen, so that it will be called
     * when the ESC_KEY is pressed
     */
    private void settingsScreen() {
        action = new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (!loginScreen) {
                    if (isPressed == true) {
                        menuOpen = !menuOpen;
                    }

                    if (menuOpen) {
                        if (guiScreens[3] == null) {
                            optionScreen();
                            leaderBoard();
                        }

                        inputManager.setCursorVisible(true);
                        guiNode.detachAllChildren();
                        guiScreens[3].goTo("Option_Screen");
                        guiScreens[3].updateRes(width, height);
                    } else {

                        // state.setEnabled(true);
                        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
                        guiNode.attachChild(healthBorder);
                        guiNode.attachChild(health);
                        inputManager.setCursorVisible(false);
                    }
                }
            }
        };
        inputManager.addMapping("ESCAPE", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addListener(action, "ESCAPE");
    }

    /**
     * Creates a new settings screen
     */
    private void optionScreen() {
        guiScreens[3] = new SettingsScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app,
                                           Screen, this, scene);
    }

    /**
     * Creates a new loading screen object
     */
    public void loadingScreen() {
        guiScreens[4] = new LoadingScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app,
                                          Screen);
        guiScreens[4].updateRes(width, height);
    }

    /**
     * Returns the loading screen object to be updated as the game is being loaded    
     * @return the loading screen object
     */
    public LoadingScreen getLoadingScreen() {
        return (LoadingScreen) guiScreens[4];
    }

    /**
     * Changes to the screen ID coming in
     * @param _screen the ID of the screen to be changed to
     */
    public void goTo(String _screen) {
        guiScreens[3].goTo(_screen);
    }
    /**
     * Closes all the login screens
     */
    public void destroyLogin() {
        guiScreens[0].screen.getNifty().exit();
        guiScreens[1].screen.getNifty().exit();
        guiScreens[2].screen.getNifty().exit();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
