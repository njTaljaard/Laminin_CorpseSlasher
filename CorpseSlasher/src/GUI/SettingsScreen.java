
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
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;
import de.lessvoid.nifty.screen.ScreenController;
import java.awt.Toolkit;

/**
 *
 * @author Gerhard An extension of the screens class building a colelction of
 * different setting screens
 */
public class SettingsScreen extends Screens {

    private Nifty nifty;
    private int sHeight;
    private int sWidth;
    private boolean isWide;

    SettingsScreen(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer,
            ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay Screen) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        sHeight = (int) toolkit.getScreenSize().getHeight();
        sWidth = (int) toolkit.getScreenSize().getWidth();
        if ((sWidth / sHeight) == (16 / 9)) {
            isWide = true;
        } else {
            isWide = false;
        }
        buildGui();
    }

    /**
     * Builds all the different screens related to any settings such as auido,
     * graphics and difficulty
     */
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
                        font("Interface/Fonts/zombie.fnt");
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }
                });
                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutOverlay();
                        font("Interface/Fonts/zombie.fnt");

                        panel(new PanelBuilder("Switch_Options") {
                            {
                                childLayoutVertical();
                                control(new ButtonBuilder("", "Display Settings") {
                                    {
                                        marginTop("48%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        interactOnClick("goTo(Display_Settings)");
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Difficulty Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Graphics Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                        interactOnClick("goTo(Graphics_Extension)");
                                    }
                                });
                                control(new ButtonBuilder("", "Sound Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Leaderboard") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                        interactOnClick("goTo(Leader_Board)");
                                    }
                                });
                                control(new ButtonBuilder("", "Quit Game") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
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
                controller((ScreenController) app);
                layer(new LayerBuilder("background") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutCenter();;
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }
                });
                layer(new LayerBuilder("foreground") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutOverlay();
                        panel(new PanelBuilder("Settings") {
                            {
                                childLayoutVertical();
                                control(new ButtonBuilder("", "Display Settings") {
                                    {
                                        marginTop("48%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                        interactOnClick("goTo(Display_Settings)");
                                    }
                                });
                                control(new ButtonBuilder("", "Difficulty Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Graphics Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Sound Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Leaderboard") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                        interactOnClick("goTo(Leader_Board)");
                                    }
                                });
                                control(new ButtonBuilder("", "Quit Game") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                        interactOnClick("quitGame()");
                                    }
                                });
                            }
                        });
                    }
                });
                layer(new LayerBuilder("Settings") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutVertical();
                        panel(new PanelBuilder("Post_Water_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("15%");
                                control(new LabelBuilder("", "Post Water") {
                                    {
                                        color("881188");
                                        font("Interface/Fonts/zombie.fnt");
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
                                        font("Interface/Fonts/zombie.fnt");
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
                                        font("Interface/Fonts/zombie.fnt");
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
                                        font("Interface/Fonts/zombie.fnt");
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
                                        font("Interface/Fonts/zombie.fnt");
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
                                        font("Interface/Fonts/zombie.fnt");
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
                                        font("Interface/Fonts/zombie.fnt");
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
                                        font("Interface/Fonts/zombie.fnt");
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
                                        font("Interface/Fonts/zombie.fnt");
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
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new CheckboxBuilder("Light_Scatter_Button") {
                                    {
                                        marginLeft("90%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Apply_Panel") {
                            {
                                        font("Interface/Fonts/zombie.fnt");
                                childLayoutHorizontal();
                                marginLeft("43%");
                                marginTop("5%");
                                control(new ButtonBuilder("Apply_Button", "Apply Settings") {
                                    {
                                        interactOnClick("applySettings()");
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.build(nifty));
        nifty.addScreen("Display_Screen", new ScreenBuilder("Display_Settings") {
            {
                controller(new SettingsController());
                layer(new LayerBuilder("background") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutCenter();;
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }
                });
                layer(new LayerBuilder("foreground") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutOverlay();
                        panel(new PanelBuilder("Settings") {
                            {
                                        font("Interface/Fonts/zombie.fnt");
                                childLayoutVertical();
                                control(new ButtonBuilder("", "Display Settings") {
                                    {
                                        marginTop("48%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Difficulty Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Graphics Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                        interactOnClick("goTo(Graphics_Extension)");
                                    }
                                });
                                control(new ButtonBuilder("", "Sound Settings") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Leaderboard") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                        interactOnClick("goTo(Leader_Board)");
                                    }
                                });
                                control(new ButtonBuilder("", "Quit Game") {
                                    {
                                        marginTop("2%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
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
                        panel(new PanelBuilder("Resolution_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("40%");
                                marginTop("15%");
                                control(new LabelBuilder("", "Set Resolution") {
                                    {
                                        color("881188");
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ListBoxBuilder("Resolution_Opts") {
                                    {
                                        marginLeft("4%");
                                        marginBottom("10%");
                                        displayItems(1);
                                        showVerticalScrollbar();
                                        hideHorizontalScrollbar();
                                        selectionModeSingle();
                                        width("10%");
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("Apply_Panel") {
                            {
                                childLayoutHorizontal();
                                marginLeft("45%");
                                marginTop("5%");
                                control(new ButtonBuilder("Apply_Button", "Apply Settings") {
                                    {
                                        interactOnClick("displayApply()");
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.build(nifty));
    }

    /**
     *
     * @param _screen the ID of the settings screen to be changed to Changes to
     * the appropriate screen based on the selection, in the options screen
     */
    public void goTo(String _screen) {
        nifty = screen.getNifty();
        guiViewPort.getProcessors().removeAll(guiViewPort.getProcessors());
        guiViewPort.addProcessor(screen);
        nifty.gotoScreen(_screen);
    }
}
//~ Formatted by Jindent --- http://www.jindent.com
