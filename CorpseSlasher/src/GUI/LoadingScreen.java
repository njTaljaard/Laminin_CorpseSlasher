package GUI;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author Gerhard
 */
public class LoadingScreen extends Screens
{

    LoadingScreen(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay screen) {
       super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, screen);
       build();
    }
    private void build()
    {
        Nifty nifty = screen.getNifty();
        guiViewPort.addProcessor(screen);
        buildGui(nifty);
        nifty.gotoScreen("Loading");
    }

    private void buildGui(Nifty nifty) {
        
    }
    public void update(float value){
        
    }
}
