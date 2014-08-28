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
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;

/**
 *
 * @author Gerhard An extension of the screens class, allowing the user to
 * retrieve lost or forgotten passwords via the use of his account username.
 */
public class RetrievePassword extends Screens {

    RetrievePassword(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay screen) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, screen);
    }

    /**
     * Builds the retrieve password screen
     */
    @Override
    public void build() {
        Nifty nifty = screen.getNifty();
        nifty.setIgnoreKeyboardEvents(true);

        guiViewPort.addProcessor(screen);
        buildGui(nifty);
        nifty.gotoScreen("Retrieve_Password");
    }

    /**
     *
     * @param nifty the nifty object that has to be designed Helper function to
     * build, adding buttons and labels
     */
    private void buildGui(Nifty nifty) {
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("Retrieve_Password", new ScreenBuilder("Retrieve Password") {
            {
                controller((ScreenController) app);
                layer(new LayerBuilder("background") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutCenter();
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                    }
                });
                layer(new LayerBuilder("foreground") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutVertical();
                        backgroundColor(new Color(.3f, .3f, .3f, .5f));
                        panel(new PanelBuilder("Main_Retrieve_Password") {
                            {
                                childLayoutCenter();
                                control(new LabelBuilder("Username", "Username/Email :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("50%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new TextFieldBuilder("#Username_Input_ID_3", "Enter Username/Email") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("50%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                        interactOnClick("erase(#Username_Input_ID_3)");
                                    }
                                });
                                control(new ButtonBuilder("#Get_Password", "Retrieve Password") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Bottom);
                                        marginBottom("25%");
                                        height("5%");
                                        width("15%");
                                        interactOnClick("retrievePasswordAndGoBack()");
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new ButtonBuilder("Back", "Back") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Bottom);
                                        marginBottom("15%");
                                        height("5%");
                                        width("15%");
                                        interactOnClick("goTo(#Login_Screen)");
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
