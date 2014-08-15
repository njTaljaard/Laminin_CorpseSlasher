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
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;

/**
 *
 * @author Gerhard An extension of the screens class, allowing the user to
 * retrieve lost or forgotten passwords via the use of his account username.
 */
public class Leaderboard extends Screens {

    Leaderboard(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay screen) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, screen);
    }

    /**
     * Builds the retrieve password screen
     */
    public void build() {
        Nifty nifty = screen.getNifty();
        nifty.setIgnoreKeyboardEvents(true);

        guiViewPort.addProcessor(screen);
        buildGui(nifty);
        nifty.gotoScreen("Leader_Board");
    }

    /**
     *
     * @param nifty the nifty object that has to be designed Helper function to
     * build, adding buttons and labels
     */
    private void buildGui(Nifty nifty) {
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("Leader_Board", new ScreenBuilder("Leaderboard") {
            {
                controller(new LeaderBoardController());
                layer(new LayerBuilder("background") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutCenter();
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }
                });
                layer(new LayerBuilder("GoTos") {
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
                layer(new LayerBuilder("#LeaderBoard") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutOverlay();
                        panel(new PanelBuilder("#leaderPanel") {
                            {
                                childLayoutHorizontal();
                                control(new LabelBuilder("#nameLabel", "Player Name") {
                                    {
                                        marginTop("10%");
                                        marginLeft("25%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new LabelBuilder("#killsLabel", "Zombie Kills") {
                                    {
                                        marginTop("10%");
                                        marginLeft("10%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new LabelBuilder("#expLabel", "Current Experience") {
                                    {
                                        marginTop("10%");
                                        marginLeft("5%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("#scores") {
                            {
                                childLayoutVertical();
                                control(new ListBoxBuilder("#scorebar") {
                                    {
                                        displayItems(10);
                                        showVerticalScrollbar();
                                        hideHorizontalScrollbar();
                                        selectionModeDisabled();
                                        width("50%");
                                        marginLeft("25%");
                                        height("15%");
                                        marginTop("15%");
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
}
