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
import de.lessvoid.nifty.controls.imageselect.builder.ImageSelectBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.radiobutton.builder.RadioButtonBuilder;
import de.lessvoid.nifty.controls.radiobutton.builder.RadioGroupBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.effects.impl.Hide;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;

public class LoginScreen {
    private int             selection;
    private AssetManager    assetManger;
    private InputManager    inputManager;
    private AudioRenderer   audioRenderer;
    private ViewPort        guiViewPort;
    private NiftyJmeDisplay loginScreen;
    private AppStateManager appState;
    private Application     app;

    /**
     *
     * @param assetManager
     * @param inputManager
     * @param audioRenderer
     * @param guiViewPort
     */
    public LoginScreen(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer,
                       ViewPort guiViewPort, AppStateManager appState, Application app) {
        this.assetManger   = assetManager;
        this.inputManager  = inputManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort   = guiViewPort;
        this.selection     = 0;
        this.appState      = appState;
        this.app           = app;
        loginScreen        = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        build();
    }

    /**
     * Buiilds the NiftyGui
     */
    private void build() {
        Nifty nifty = loginScreen.getNifty();

        guiViewPort.addProcessor(loginScreen);
        buildGui(nifty);
        nifty.gotoScreen("Login_Screen");
    }

    /**
     * helper function for build
     * @param nifty display object from which the panels and buttons are created
     */
    private void buildGui(Nifty nifty) {
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("Login_Screen", new ScreenBuilder("Login To Connect") {
            {
                controller((ScreenController) app);
                layer(new LayerBuilder("background") {
                    {
                        childLayoutCenter();
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }
                });
                layer(new LayerBuilder("foreground") {
                    {
                        visibleToMouse(true);
                        childLayoutVertical();
                        backgroundColor(new Color(.3f, .3f, .3f, .5f));
                        panel(new PanelBuilder("Main_Login_Panel") {
                            {
                                childLayoutCenter();
                                control(new LabelBuilder("Username_ID", "Username :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("54%");
                                        height("5%");
                                        width("15%");
                                        visibleToMouse(true);
                                    }
                                });
                                control(new TextFieldBuilder("Username_Input_ID", "Enter Username") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("54%");
                                        height("5%");
                                        width("15%");
                                        visibleToMouse(true);
                                    }
                                });
                                control(new LabelBuilder("Password_ID", "Password :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("62%");
                                        height("5%");
                                        width("15%");
                                        visibleToMouse(true);
                                    }
                                });
                                control(new TextFieldBuilder("Password_Input_ID", "Enter Password") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("62%");
                                        height("5%");
                                        width("15%");
                                        visibleToMouse(true);
                                    }
                                });
                                control(new RadioGroupBuilder("Selections"));
                                panel(new PanelBuilder("Radio_Button_Panel") {
                                    {
                                        childLayoutHorizontal();
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("44.4%");
                                        marginTop("70%");
                                        height("5%");
                                        width("15%");
                                        paddingLeft("7px");
                                        paddingRight("7px");
                                        paddingTop("4px");
                                        paddingBottom("4px");
                                        visibleToMouse(true);
                                        panel(new PanelBuilder() {
                                            {
                                                childLayoutHorizontal();
                                                control(new RadioButtonBuilder("Custom_ID") {
                                                    {
                                                        width("40px");
                                                        //backgroundImage("Icons/google+.jpg");
                                                        group("Selections");
                                                    }
                                                });
                                            }
                                        });
                                        panel(new PanelBuilder() {
                                            {
                                                childLayoutHorizontal();
                                                control(new RadioButtonBuilder("Facebook_ID") {
                                                    {
                                                        width("40px");
                                                        //backgroundImage("Icons/fb.jpg");
                                                        group("Selections");
                                                    }
                                                });
                                            }
                                        });
                                        panel(new PanelBuilder() {
                                            {
                                                childLayoutHorizontal();
                                                control(new RadioButtonBuilder("Twitter_ID") {
                                                    {
                                                        width("40px");
                                                        //backgroundImage("Icons/twitter.jpg");
                                                        group("Selections");
                                                    }
                                                });
                                            }
                                        });
                                        panel(new PanelBuilder() {
                                            {
                                                childLayoutHorizontal();
                                                control(new RadioButtonBuilder("Google+_ID") {
                                                    {
                                                        width("40px");
                                                        //backgroundImage("Icons/google+.jpg");
                                                        group("Selections");
                                                        
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                control(new ButtonBuilder("Connect_ID", "Login") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Bottom);
                                        marginBottom("13%");
                                        height("5%");
                                        width("15%");
                                        interactOnClick("loadingScreen()");
                                    }
                                });
                                control(new LabelBuilder("New_Account_ID", "Create New Account") {
                                    {
                                        marginLeft("-5%");
                                        marginBottom("-44%");
                                        height("5%");
                                        width("15%");
                                        interactOnClick("newAccount()");
                                    }
                                });
                                control(new LabelBuilder("Retrieve_Password_ID", "Retrieve Password") {
                                    {
                                        marginLeft("5%");
                                        marginBottom("-44%");
                                        height("5%");
                                        width("15%");
                                        interactOnClick("retrievePassword()");
                                    }
                                });
                            }
                            ;
                        });
                        panel(new PanelBuilder("Button_Panel") {
                            {
                                childLayoutHorizontal();
                                align(Align.Left);
                                valign(VAlign.Top);
                                marginLeft("44.4%");
                                marginTop("-25%");
                                height("5%");
                                width("15%");
                                paddingLeft("7px");
                                paddingRight("7px");
                                paddingTop("4px");
                                paddingBottom("4px");
                                visibleToMouse(true);
                                panel(new PanelBuilder() {
                                    {
                                        childLayoutHorizontal();
                                        backgroundImage("Icons/google+.jpg");
                                        width("40px");
                                    }
                                });
                                panel(new PanelBuilder() {
                                    {
                                        childLayoutHorizontal();
                                        backgroundImage("Icons/fb.jpg");
                                        width("40px");
                                    }
                                });
                                panel(new PanelBuilder() {
                                    {
                                        childLayoutHorizontal();
                                        backgroundImage("Icons/twitter.jpg");
                                        width("40px");
                                    }
                                });
                                panel(new PanelBuilder() {
                                    {
                                        childLayoutHorizontal();
                                        backgroundImage("Icons/google+.jpg");
                                        width("40px");
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
