package GUI;

//~--- non-JDK imports --------------------------------------------------------

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.system.AppSettings;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.SizeValue;

/**
 *
 * @author Gerhard
 * An extension of the screen class that creates a loading screen for the game
 */
public class LoadingScreen extends Screens {
    private Nifty nifty;

    LoadingScreen(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer,
                  ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay screen) {
        super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, screen);
        build(0.0f);
    }
    /**
     * 
     * @param value starting value of progress bar
     * Just instantiates the builing of the GUI 
     */
    private void build(float value) {
        nifty = screen.getNifty();
        guiViewPort.addProcessor(screen);
        buildGui(nifty, value);
        nifty.gotoScreen("Loading");
    }
    /**
     * 
     * @param nifty the nifty object that has to be designed
     * @param value the starting value of the progress bar
     * Helper function to build, adding buttons and labels
     */
    private void buildGui(Nifty nifty, float value) {
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("Loading", new ScreenBuilder("Loading_Screen") {
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
                        childLayoutCenter();
                        panel(new PanelBuilder("Progress_Bar") {
                            {
                                childLayoutCenter();
                                marginBottom("-30%");
                                image(new ImageBuilder("Border_Progress") {
                                    {
                                        childLayoutAbsolute();
                                        filename("LoadingBar/border.png");
                                        imageMode("resize:15,2,15,15,15,2,15,2,15,2,15,15");
                                        width("400px");
                                        height("32px");
                                        image(new ImageBuilder("Inner_Progress") {
                                            {
                                                x("0");
                                                y("0");
                                                filename("LoadingBar/inner.png");
                                                width("32px");
                                                height("100%");
                                                imageMode("resize:15,2,15,15,15,2,15,2,15,2,15,15");
                                                color(new Color(1.0f, 0.0f, 0.0f, 0.4f));
                                            }
                                        });
                                    }
                                });
                                control(new LabelBuilder("Progress_Text") {
                                    {
                                        label("Loading...");
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
     * @param value updates the the loading bar to the value
     * Updates the loading bar as the graphics and game is being loaded into memory
     */
    public void update(float value) {
        Element progressBarElement = nifty.getScreen("Loading").findElementByName("Inner_Progress");
        int     MIN_WIDTH          = 32;
        int     pixelWidth         = (int) ((int) (MIN_WIDTH + progressBarElement.getParent().getWidth() - MIN_WIDTH)
                                         * value);

        progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
        progressBarElement.getParent().layoutElements();
        System.out.println(pixelWidth + "px");
        nifty.gotoScreen("Loading");
    }
    @Override
    public void updateRes(int width, int height){
        AppSettings settings = new AppSettings(true);
        settings.setResolution(width, height);
        nifty.render(true);
        screen.reshape(guiViewPort, width, width);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
