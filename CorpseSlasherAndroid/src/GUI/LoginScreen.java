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
 * @author gerhard An extension of the screen class to create a Login Screen
 * where the user has to enter a Username and password, or chose which social
 * media it wants to login with or create a new custom account or retrieve his
 * password
 */
public class LoginScreen extends Screens {

    private Nifty nifty;

    public LoginScreen(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer,
            ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay screen) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, screen);

    }

    /**
     * Buiilds the NiftyGui
     */
    public void build() {
        nifty = screen.getNifty();
        nifty.enableAutoScaling(800, 600);
        nifty.setIgnoreKeyboardEvents(true);
        guiViewPort.addProcessor(screen);
        buildGui(nifty);
        nifty.gotoScreen("#Login_Screen");
    }

    /**
     *
     * @param nifty the nifty object that has to be designed Helper function to
     * build, adding buttons and labels
     */
    private void buildGui(Nifty nifty) {
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("#Login_Screen", new ScreenBuilder("Login To Connect") {
            {
                controller((ScreenController) app);
                layer(new LayerBuilder("#background") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        childLayoutCenter();
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }
                });
                layer(new LayerBuilder("#foreground") {
                    {
                        font("Interface/Fonts/zombie.fnt");
                        visibleToMouse(true);
                        childLayoutVertical();
                        panel(new PanelBuilder("#Main_Login_Panel") {
                            {
                                childLayoutCenter();
                                font("Interface/Fonts/zombie.fnt");
                                control(new LabelBuilder("#Username_ID", "Username :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("21%");
                                        marginTop("26%");
                                        height("15%");
                                        width("15%");
                                        visibleToMouse(true);
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new TextFieldBuilder("#Username_Input_ID", "Enter Username") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("26%");
                                        height("10%");
                                        width("25%");
                                        visibleToMouse(true);
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                        interactOnClick("erase(#Username_Input_ID)");
                                    }
                                });
                                control(new LabelBuilder("#Password_ID", "Password :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("21%");
                                        marginTop("44%");
                                        height("15%");
                                        width("15%");
                                        visibleToMouse(true);
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new TextFieldBuilder("#Password_Input_ID", "Enter Password") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("44%");
                                        height("10%");
                                        width("25%");
                                        visibleToMouse(true);
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                        passwordChar('*');
                                        interactOnClick("erase(#Password_Input_ID)");
                                    }
                                });
                                control(new ButtonBuilder("#Connect_ID", "Login") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Bottom);
                                        marginBottom("11%");
                                        height("10%");
                                        width("25%");
                                        interactOnClick("loadingScreen()");
                                        font("Interface/Fonts/zombie.fnt");
                                    }
                                });
                                control(new LabelBuilder("#New_Account_ID", "Create New Account") {
                                    {
                                        marginLeft("-25%");
                                        marginBottom("-50%");
                                        height("10%");
                                        width("25%");
                                        interactOnClick("newAccount()");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                control(new LabelBuilder("#Retrieve_Password_ID", "Retrieve Password") {
                                    {
                                        marginLeft("25%");
                                        marginBottom("-50%");
                                        height("10%");
                                        width("25%");
                                        interactOnClick("retrievePassword()");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                            }
                        ;
                        });
                        panel(new PanelBuilder("#Button_Panel") {
                            {
                                childLayoutHorizontal();
                                align(Align.Left);
                                valign(VAlign.Top);
                                marginLeft("34%");
                                marginTop("-33%");
                                height("10%");
                                width("15%");
                                paddingLeft("7px");
                                paddingRight("7px");
                                paddingTop("4px");
                                paddingBottom("4px");
                                visibleToMouse(true);

                                font("Interface/Fonts/zombie.fnt");
                                panel(new PanelBuilder() {
                                    {
                                        childLayoutHorizontal();
                                        backgroundImage("Icons/fb.jpg");
                                        width("70px");
                                        interactOnClick("socialLogin(1)");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                                panel(new PanelBuilder() {
                                    {
                                        childLayoutHorizontal();
                                        marginLeft("94%");
                                        backgroundImage("Icons/google+.jpg");
                                        width("70px");
                                        interactOnClick("socialLogin(2)");
                                        font("Interface/Fonts/zombie.fnt");
                                        color("#ff0000");
                                    }
                                });
                            }
                        });
                        control(new ButtonBuilder("", "Quit Game") {
                            {
                                width("100px");
                                height("50px");
                                interactOnClick("quitGame()");
                                alignCenter();
                                marginTop("23%");
                            }
                        });
                    }
                });
            }
        }.build(nifty));
    }
}



//~ Formatted by Jindent --- http://www.jindent.com
