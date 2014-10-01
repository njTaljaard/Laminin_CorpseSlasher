
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

//~--- non-JDK imports --------------------------------------------------------
import CorpseSlasher.GameScene;
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
    private UserInterfaceManager UI;
    private GameScene scene;

    SettingsScreen(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer,
            ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay Screen, UserInterfaceManager UI,GameScene scene) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, Screen);
        this.UI = UI;
        this.scene = scene;
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
        nifty.setIgnoreKeyboardEvents(true);
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
                                control(new ButtonBuilder("", "Sound Settings") {
                                    {
                                        marginTop("30%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("", "Leaderboard") {
                                    {
                                        marginTop("5%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                        interactOnClick("goTo(Leader_Board)");
                                    }
                                });
                                control(new ButtonBuilder("", "Logout") {
                                    {
                                        controller((ScreenController) app);
                                        marginTop("5%");
                                        marginLeft("5%");
                                        height("50px");
                                        width("150px");
                                        align(Align.Left);
                                        font("Interface/Fonts/zombie.fnt");
                                        interactOnClick("goTo(#Login_Screen)");
                                    }
                                });
                                control(new ButtonBuilder("", "Quit Game") {
                                    {
                                        marginTop("5%");
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
