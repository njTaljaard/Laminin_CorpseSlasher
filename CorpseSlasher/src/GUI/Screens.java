package GUI;

//~--- non-JDK imports --------------------------------------------------------

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.system.AppSettings;

/**
 *
 * @author Gerhard
 * The super class of all screens
 */
abstract class Screens {
    protected AssetManager    assetManager;
    protected InputManager    inputManager;
    protected AudioRenderer   audioRenderer;
    protected ViewPort        guiViewPort;
    protected NiftyJmeDisplay screen;
    protected AppStateManager appState;
    protected Application     app;

    /**
     *
     * @param assetManager the main application's asset manager
     * @param inputManager the main application's input manager to change any input functions or maps
     * @param audioRenderer the main applications audio renderer to change or add audio to the GUIs
     * @param guiViewPort allows the display of the GUI screens to be added to the view processor
     * @param appState the current state of the main application at the time of the screen being called
     * @param app a copy of the application
     * @param screen the main Nifty display objec shared between all the screens
     */
    Screens(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort,
            AppStateManager appState, Application app, NiftyJmeDisplay screen) {
        this.assetManager  = assetManager;
        this.inputManager  = inputManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort   = guiViewPort;
        this.appState      = appState;
        this.app           = app;
        this.screen        = screen;
    }

    void goTo(String _screen) {
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        screen.getNifty().gotoScreen(_screen);
    }

    void build() {}

    ;
    public void updateRes(int width, int height) {
        AppSettings settings = new AppSettings(true);

        settings.setResolution(width, height);
        screen.reshape(guiViewPort, width, height);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
