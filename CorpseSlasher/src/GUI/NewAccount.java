/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class NewAccount extends Screens 
{
    private NiftyJmeDisplay newAccount;
    NewAccount(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort, AppStateManager appState, Application app) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app);
        newAccount        = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        build();
    }

    private void build() {
        Nifty nifty = newAccount.getNifty();
        guiViewPort.addProcessor(newAccount);
        buildGui(nifty);
        nifty.gotoScreen("New_Account_Screen");
    }

    private void buildGui(Nifty nifty) {
        
    }
    
}
