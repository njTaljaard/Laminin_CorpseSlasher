package GUI;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.SizeValue;

/**
 *
 * @author Gerhard
 */
public class LoadingScreen extends Screens
{
    private Nifty nifty;
    LoadingScreen(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort, AppStateManager appState, Application app, NiftyJmeDisplay screen) {
       super(assetManager, inputManager, audioRenderer, guiViewPort, appState, app, screen);
       build(0.0f);
    }
    private void build(float value)
    {
        nifty = screen.getNifty();
        guiViewPort.addProcessor(screen);
        buildGui(nifty,value);
        nifty.gotoScreen("Loading");
    }

    private void buildGui(Nifty nifty,float value) {
        //System.out.println("attempting");
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("Loading", new ScreenBuilder("Loading_Screen"){
            {
                controller((ScreenController) app);
                layer(new LayerBuilder("background"){
                    {
                        childLayoutCenter();
                        backgroundImage("Backgrounds/ZOMBIE1.jpg");
                        visibleToMouse(true);
                    }});
                layer(new LayerBuilder("foreground"){
                    {
                        childLayoutCenter();
                        panel(new PanelBuilder("Progress_Bar"){
                            {
                                childLayoutCenter();
                                marginBottom("-30%");
                              image(new ImageBuilder("Border_Progress"){
                                  {
                                      childLayoutAbsolute(); 
                                      filename("LoadingBar/border.png");
                                      imageMode("resize:15,2,15,15,15,2,15,2,15,2,15,15");
                                      width("400px");
                                      height("32px");
                                      image(new ImageBuilder("Inner_Progress"){
                                          {
                                              x("0");
                                              y("0");
                                              filename("LoadingBar/inner.png");
                                              width("32px");
                                              height("100%");
                                              imageMode("resize:15,2,15,15,15,2,15,2,15,2,15,15"); 
                                              color(new Color(1.0f, 0.0f, 0.0f, 0.4f));
                                          }});
                                  }});
                              control(new LabelBuilder("Progress_Text"){
                                  {
                                      label("Loading...");                                    
                                  }});
                            }});
                    }});
            }}.build(nifty));
  
    }
    public void update(float value){
        Element progressBarElement = nifty.getScreen("Loading").findElementByName("Inner_Progress");
        int MIN_WIDTH = 32;
        int pixelWidth = (int) ((int)(MIN_WIDTH + progressBarElement.getParent().getWidth() - MIN_WIDTH) * value);
        progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
        progressBarElement.getParent().layoutElements();
        System.out.println(pixelWidth+"px");
        nifty.gotoScreen("Loading");
    }

}
