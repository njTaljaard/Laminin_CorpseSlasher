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
 * @author Gerhard
 */
public class RetrievePassword extends Screens {

    RetrievePassword(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay screen) {
             super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, screen);     
        build();   
    }

    private void build() {
        Nifty nifty = screen.getNifty();

        guiViewPort.addProcessor(screen);
        buildGui(nifty);
        nifty.gotoScreen("Retrieve_Password");
    }

    private void buildGui(Nifty nifty) {
       nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("Retrieve_Password", new ScreenBuilder("Retrieve Password") {
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
                        panel(new PanelBuilder("Main_Retrieve_Password"){
                            {
                                childLayoutCenter();
                                control(new LabelBuilder("Email", "Email :") {
                                    {
                                        align(Align.Left);
                                        valign(VAlign.Top);
                                        marginLeft("31%");
                                        marginTop("50%");
                                        height("5%");
                                        width("15%");
                                        visibleToMouse(true);
                                    }
                                });
                                control(new TextFieldBuilder("Email_Input_ID", "Enter Email") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Top);
                                        marginTop("50%");
                                        height("5%");
                                        width("15%");
                                        visibleToMouse(true);
                                    }
                                });
                                control(new ButtonBuilder("Get_Password", "Retrieve Password") {
                                    {
                                        alignCenter();
                                        valign(VAlign.Bottom);
                                        marginBottom("25%");
                                        height("5%");
                                        width("15%");
                                        interactOnClick("loginScreen()");
                                    }
                                });
                            }});
                    }});
            }}.build(nifty));
    }
    
}
