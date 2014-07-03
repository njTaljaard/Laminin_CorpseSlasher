
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
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
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
                controller(new SettingsController());
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
                        panel(new PanelBuilder("Settings") {
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
                    }
                });
                layer(new LayerBuilder("Settings") {
                    {
                        childLayoutVertical();
                        panel(new PanelBuilder("Post_Water_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("15%");
                                control(new LabelBuilder("", "Post Water") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Post_Water_Button") {
                                    {
                                        marginLeft("110%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Water_Reflections_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("5%");
                                control(new LabelBuilder("", "Water Reflections") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Water_Reflections_Button") {
                                    {
                                        marginLeft("45%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Water_Ripples_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("5%");
                                control(new LabelBuilder("", "Water Ripples") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Water_Ripples_Button") {
                                    {
                                        marginLeft("75%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Water_Specular_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("5%");
                                control(new LabelBuilder("", "Water Specular") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Water_Specular_Button") {
                                    {
                                        marginLeft("59%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Water_Foam_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("5%");
                                control(new LabelBuilder("", "Water Foam") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Water_Foam_Button") {
                                    {
                                        marginLeft("93%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Sky_Dome_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("5%");
                                control(new LabelBuilder("", "Sky Dome") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Sky_Dome_Button") {
                                    {
                                        marginLeft("128%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Star_Motion_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("5%");
                                control(new LabelBuilder("", "Star Motion") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Star_Motion_Button") {
                                    {
                                        marginLeft("106%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Cloud_Motion_Button") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("5%");
                                control(new LabelBuilder("", "Cloud Motion") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Cloud_Motion_Button") {
                                    {
                                        marginLeft("87%");                                       
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Bloom_Light_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("5%");
                                control(new LabelBuilder("", "Bloom Lighting") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Bloom_Light_Button") {
                                    {
                                        marginLeft("70%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Light_Scatter_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("5%");
                                control(new LabelBuilder("", "Light Scatter") {
                                    {
                                        color("881188");
                                    }
                                });
                                control(new CheckboxBuilder("Light_Scatter_Button") {
                                    {
                                        marginLeft("90%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Apply_Panel"){
                            {
                                childLayoutHorizontal();
                                marginLeft("43%");
                                marginTop("5%");
                                control(new ButtonBuilder("Apply_Button", "Apply Settings"){
                                    {
                                        interactOnClick("applySettings()");
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
        nifty = screen.getNifty();
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        guiViewPort.addProcessor(screen);
        nifty.gotoScreen(_screen);
    }
}
//~ Formatted by Jindent --- http://www.jindent.com
