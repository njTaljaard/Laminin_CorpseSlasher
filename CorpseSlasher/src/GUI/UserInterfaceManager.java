package GUI;

//~--- non-JDK imports --------------------------------------------------------

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;

public final class UserInterfaceManager {
    private AssetManager    assetManager;
    private InputManager    inputManager;
    private AudioRenderer   audioRenderer;
    private ViewPort        guiViewPort;
    private AppStateManager appState;
    private Application     app;

    public void init(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer,
                     ViewPort guiViewPort, AppStateManager appState, Application app) {
        this.assetManager  = assetManager;
        this.inputManager  = inputManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort   = guiViewPort;
        this.app           = app;
        this.appState      = appState;
    }

    public AssetManager getAssetManger() {
        return assetManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public AudioRenderer getAudioRenderer() {
        return audioRenderer;
    }

    public ViewPort getGuiViewPort() {
        return guiViewPort;
    }

    public void loginScreen() {
        LoginScreen login = new LoginScreen(assetManager, inputManager, audioRenderer, guiViewPort, appState, app);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
