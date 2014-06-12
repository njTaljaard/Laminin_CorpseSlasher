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

/**
 *
 * @author Gerhard
 */
public class Screens 
{
    protected AssetManager    assetManger;
    protected InputManager    inputManager;
    protected AudioRenderer   audioRenderer;
    protected ViewPort        guiViewPort;
    protected NiftyJmeDisplay loginScreen;
    protected AppStateManager appState;
    protected Application     app;
    Screens(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort, AppStateManager appState, Application app)
    {
        this.assetManger   = assetManager;
        this.inputManager  = inputManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort   = guiViewPort;
        this.appState      = appState;
        this.app           = app;
    }
}
