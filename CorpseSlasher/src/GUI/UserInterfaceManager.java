package GUI;

//~--- non-JDK imports --------------------------------------------------------

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
import de.lessvoid.nifty.controls.ListBox;

/**
 * 
 * @author Gerhard
 * Controls which screen must be displayed at what time.
 */
public final class UserInterfaceManager {
    private boolean         menuOpen    = false;
    private boolean         loginScreen = true;
    private AssetManager    assetManager;
    private InputManager    inputManager;
    private AudioRenderer   audioRenderer;
    private ViewPort        guiViewPort;
    private AppStateManager appState;
    private Application     app;
    private NiftyJmeDisplay Screen;
    private ActionListener  action;
    private Screens[] guiScreens;
    private int       height;
    private int       width;

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
                     ViewPort guiViewPort, AppStateManager appState, Application app) {
        this.assetManager  = assetManager;
        this.inputManager  = inputManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort   = guiViewPort;
        this.app           = app;
        this.appState      = appState;
        settingsScreen();
        Screen = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        guiScreens = new Screens[5];
    }
    public void updateRes(int width, int height){
        this.width = width;
        this.height = height;
        for(Screens screen_1 : guiScreens){
            if(screen_1 != null)
            screen_1.updateRes(width,height);
        }
    }
    /**
     * Creates the login screen
     */
    public void loginScreen() {
        guiScreens[0] = new LoginScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app,
                                Screen);
        guiScreens[0].build();
        guiScreens[0].updateRes(width, height);
    }
    /**
     * Creates the new account account screen
     */
    public void newAccount() {
         guiScreens[1] = new NewAccount(assetManager, inputManager, audioRenderer, guiViewPort, appState, app,
                                           Screen);
        guiScreens[1].build();
        guiScreens[1].updateRes(width, height);
    }
    /**
     * Creates the retrieve password screen
     */
    public void retrievePassword() {
        guiScreens[2] = new RetrievePassword(assetManager, inputManager, audioRenderer, guiViewPort,
                                        appState, app, Screen);
        guiScreens[2].build();
        guiScreens[2].updateRes(width, height);
    }
    /**
     * Updates the state of the game so that settings menu can be called after login only
     */
    public void changeState() {
        loginScreen = !loginScreen;
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
                        inputManager.setCursorVisible(true);
                        guiScreens[3].goTo("Option_Screen");
                        guiScreens[3].updateRes(width, height);
                    } else {
                        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
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
    public void optionScreen() {
        guiScreens[3] = new SettingsScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        ListBox listBox;
    }

    public void settings(String selection) {}
    /**
     * Creates a new loading screen object
     */
    public void loadingScreen() {
        guiScreens[4] = new LoadingScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        guiScreens[4].updateRes(width, height);
    }
    /**
     * 
     * @return the loading screen object
     * Returns the loading screen object to be updated as the game is being loaded
     */
    public LoadingScreen getLoadingScreen() {
        return (LoadingScreen)guiScreens[4];
    }
    /**
     * 
     * @param _screen the ID of the screen to be changed to
     * Changes to the screen ID coming in
     */
    public void goTo(String _screen) {
        guiScreens[3].goTo(_screen);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
