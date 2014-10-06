
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
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;

/**
 *
 * @author Gerhard An extension of the screen class allowing the creatation of
 * new custom accounts
 */
public class NewAccount extends Screens {

    NewAccount(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort,
            AppStateManager appState, Application app, NiftyJmeDisplay screen) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, screen);
    }

    /**
     * builds the new account screen
     */
    public void build() {
        Nifty nifty = screen.getNifty();
        nifty.setIgnoreKeyboardEvents(true);

        guiViewPort.addProcessor(screen);
        buildGui(nifty);
        nifty.gotoScreen("New_Account_Screen");
    }

    /**
     *
     * @param nifty the nifty object that has to be designed Helper function to
     * build, adding buttons and labels
     */
    private void buildGui(Nifty nifty) {
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("New_Account_Screen", new ScreenBuilder("Create a new account") {
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
                        panel(new PanelBuilder("Main_Account_Panel") {
                            {
                                childLayoutCenter();
                                control(new LabelBuilder("Email", "Email :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("22%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new TextFieldBuilder("#Email_Input_ID", "Enter Email") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("22%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                        interactOnClick("erase(#Email_Input_ID)");
                                    }
                                });
                                control(new LabelBuilder("Name", "Name :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("30%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new TextFieldBuilder("#Name_Input_ID", "Enter Name") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("30%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                        interactOnClick("erase(#Name_Input_ID)");
                                    }
                                });
                                control(new TextFieldBuilder("#Surname_Input_ID", "Enter Surname") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("38%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                        interactOnClick("erase(#Surname_Input_ID)");
                                    }
                                });
                                control(new LabelBuilder("Surname", "Surname :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("38%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new LabelBuilder("Username_ID_2", "Username :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("46%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new TextFieldBuilder("#Username_Input_ID_2", "Enter Username") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("46%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                        interactOnClick("erase(#Username_Input_ID_2)");
                                    }
                                });
                                control(new LabelBuilder("Password_ID_2", "Password :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("54%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new TextFieldBuilder("#Password_Input_ID_2", "Enter Password") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("54%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                        passwordChar('*');
                                        interactOnClick("erase(#Password_Input_ID_2)");
                                    }
                                });
                                control(new LabelBuilder("Password_ID_2_2", "RE-Password :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("62%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new TextFieldBuilder("#Password_Input_ID_2_2", "RE-Enter Password") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("62%");
                                        height("5%");
                                        width("15%");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                        passwordChar('*');
                                        interactOnClick("erase(#Password_Input_ID_2_2)");
                                    }
                                });
                                control(new ButtonBuilder("Create_Account", "Create Account") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Bottom);
                                        marginBottom("25%");
                                        height("5%");
                                        width("15%");
                                        interactOnClick("createNewAccount()");
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


//~ Formatted by Jindent --- http://www.jindent.com
