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
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Gerhard
 */
public class SettingsScreen extends Screens
{
    private Nifty nifty;
    SettingsScreen(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay Screen) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        buildGui();
    }
    
    private void buildGui()
    {
        nifty = screen.getNifty();
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");   
        nifty.addScreen("Option_Screen", new ScreenBuilder("Options_Screen"){
            {
                controller((ScreenController) app);
                layer(new LayerBuilder("background"){
                    {
                        childLayoutCenter();;
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }
                });
                layer(new LayerBuilder("foreground"){
                    {
                        childLayoutCenter();
                        panel(new PanelBuilder("Switch_Options"){
                            {
                                childLayoutHorizontal();
                                control(new ButtonBuilder("", "Difficulty Settings"){
                                    {
                                        align(Align.Left);
                                        marginLeft("5%");
                                        marginBottom("5%");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.build(nifty));
    }
    public void gotTo()
    {
        nifty = screen.getNifty();
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        guiViewPort.addProcessor(screen);
        nifty.gotoScreen("Option_Screen");
    }
}
