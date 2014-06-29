
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

//~--- non-JDK imports --------------------------------------------------------

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.Size;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.scrollbar.builder.ScrollbarBuilder;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Gerhard
 */
public class SettingsScreen extends Screens {
    private Nifty nifty;

    SettingsScreen(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer,
                   ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay Screen) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        buildGui();
    }

    private void buildGui() {
        nifty = screen.getNifty();
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("Option_Screen", new ScreenBuilder("Options_Screen") {
            {
                controller((ScreenController) app);
                layer(new LayerBuilder("background") {
                    {
                        childLayoutCenter();;
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }
                });
                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutOverlay();
                        panel(new PanelBuilder("Switch_Options") {
                            {
                                childLayoutVertical();
                                control(new ButtonBuilder("", "Difficulty Settings") {
                                    {
                                        marginTop("70%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                    }
                                });
                                control(new ButtonBuilder("", "Graphics Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        interactOnClick("graphicsScreen()");
                                    }
                                });
                                control(new ButtonBuilder("", "Sound Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                    }
                                });
                                control(new ButtonBuilder("", "Quit Game") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        interactOnClick("quitGame()");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.build(nifty));
        nifty.addScreen("Graphics_Settings", new ScreenBuilder("Graphics_Extension") {
            {
                controller((ScreenController) app);
                layer(new LayerBuilder("background") {
                    {
                        childLayoutCenter();;
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }
                });
                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutOverlay();
                        panel(new PanelBuilder("Switch_Options") {
                            {
                                childLayoutVertical();
                                control(new ButtonBuilder("", "Difficulty Settings") {
                                    {
                                        marginTop("70%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                    }
                                });
                                control(new ButtonBuilder("", "Graphics Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                    }
                                });
                                control(new ButtonBuilder("", "Sound Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                    }
                                });
                                control(new ButtonBuilder("", "Quit Game") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        interactOnClick("quitGame()");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Graphics_Panel"){
                            {
                                childLayoutVertical();
                                control(new ScrollbarBuilder("Selections",true){
                                    {
                                       marginLeft("48%");
                                    }
                                });
                                control(new LabelBuilder("Label_1", "Post Water"){
                                    {
                                        marginLeft("8%");
                                        marginBottom("80%");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.build(nifty));
    }

    public void gotTo(String _screen) {
        System.out.println(_screen);
        nifty = screen.getNifty();
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        guiViewPort.addProcessor(screen);
        nifty.gotoScreen(_screen);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
