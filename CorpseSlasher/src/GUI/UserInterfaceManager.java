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

public final class UserInterfaceManager {
    private AssetManager    assetManager;
    private InputManager    inputManager;
    private AudioRenderer   audioRenderer;
    private ViewPort        guiViewPort;
    private AppStateManager appState;
    private Application     app;
    private NiftyJmeDisplay Screen;
    private ActionListener  action;
    private LoadingScreen   loading;

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
    }
    public void loginScreen() {
        LoginScreen login = new LoginScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app,
                                Screen);
    }

    public void newAccount() {
        NewAccount newAcc = new NewAccount(assetManager, inputManager, audioRenderer, guiViewPort, appState, app,
                                           Screen);
    }

    public void retrievePassword() {
        RetrievePassword password = new RetrievePassword(assetManager, inputManager, audioRenderer, guiViewPort,
                                        appState, app, Screen);
    }

    public ActionListener action() {
        return action;
    }

    private void settingsScreen() {
        action = new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                System.out.println("hellooooo");
                
            }
        };
        inputManager.addMapping("ESCAPE", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addListener(action, "ESCAPE");
    }

    public void settings(String selection) {
       
    }

    public void loadingScreen() {
         loading = new LoadingScreen(assetManager, inputManager, audioRenderer, guiViewPort,
                                        appState, app, Screen);
    }
    public LoadingScreen getLoadingScreen(){
        return loading;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
