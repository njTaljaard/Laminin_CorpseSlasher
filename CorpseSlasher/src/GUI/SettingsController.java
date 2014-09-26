package GUI;

import CorpseSlasher.Audio;
import CorpseSlasher.ClientConnection;
import CorpseSlasher.GameScene;
import CorpseSlasher.GameSettings;
import com.jme3.app.Application;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gerhard
 * The controller for the graphics settings screen
 */
public class SettingsController implements ScreenController {
    private GameSettings settings = new GameSettings();
    private UserInterfaceManager UI;
    private boolean x1,x2,x3,x4,x5,x6,x7,x8,x9,x10;
    public static float mVol, aVol, cVol, dVol, fVol;
    private Nifty nifty;
    private Screen screen;
    private GameScene scene;
    private Application app;
    
    public SettingsController(UserInterfaceManager UI,GameScene scene, Application app){
        this.UI = UI;
        this.scene = scene;
        this.app = app;
    }
    
    /**
     * Checks the correct settings based on previous settings
     * @param nifty
     * @param screen 
     */
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        ListBox listBox = screen.findNiftyControl("Resolution_Opts", ListBox.class);
        listBox.addItem("1920 X 1080");
        listBox.addItem("1600 X 900");
        listBox.addItem("1280 X 800");
        listBox.addItem("1366 X 768");
        listBox.addItem("1024 X 768");
        listBox.addItem("1280 X 720");
        listBox.addItem("800 X 600");       
        CheckBox checkbox = null; 
        try {
            try (Scanner scFile = new Scanner(new FileReader("GameSettings.ini"))) {
                while(scFile.hasNextLine()){
                String line = scFile.nextLine();
                String[] parts = line.split("=");
                switch(parts[0]){
                    case "PostWater":
                        checkbox = screen.findNiftyControl("Post_Water_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x1 = Boolean.parseBoolean(parts[1]);
                        break;
                    case "WaterReflections":
                        checkbox = screen.findNiftyControl("Water_Reflections_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x2 = Boolean.parseBoolean(parts[1]);
                        break;
                    case "WaterRipples":
                        checkbox = screen.findNiftyControl("Water_Ripples_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x3 = Boolean.parseBoolean(parts[1]);
                        break;
                    case "WaterSpecular":
                        checkbox = screen.findNiftyControl("Water_Specular_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x4 = Boolean.parseBoolean(parts[1]);
                        break;
                    case "WaterFoam":
                        checkbox = screen.findNiftyControl("Water_Foam_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x5 = Boolean.parseBoolean(parts[1]);
                        break;
                    case "SkyDome":
                        checkbox = screen.findNiftyControl("Sky_Dome_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x6 = Boolean.parseBoolean(parts[1]);
                        break;
                    case "StarMotion":
                        checkbox = screen.findNiftyControl("Star_Motion_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x7 = Boolean.parseBoolean(parts[1]);
                        break;
                    case "CloudMotion":
                        checkbox = screen.findNiftyControl("Cloud_Motion_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x8 = Boolean.parseBoolean(parts[1]);
                        break;
                    case "BloomLight":
                        checkbox = screen.findNiftyControl("Bloom_Light_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x9 = Boolean.parseBoolean(parts[1]);
                        break;
                    case "LightScatter":
                        checkbox = screen.findNiftyControl("Light_Scatter_Button", CheckBox.class); 
                        checkbox.setChecked(Boolean.parseBoolean(parts[1]));
                        x10 = Boolean.parseBoolean(parts[1]);
                        break;
                }
                }
            }
            Scanner soundScanner = new Scanner(new FileReader("SoundSettings.ini"));
            Slider slider = null;
            while(soundScanner.hasNextLine()){
                String line = soundScanner.nextLine();
                String[] parts = line.split("=");
                switch(parts[0]){
                    case "Master":
                        slider = screen.findNiftyControl("#Master_Volume", Slider.class);                        
                        mVol = Float.parseFloat(parts[1]);
                        slider.setValue(mVol);
                        break;
                    case "Ambient":
                        slider = screen.findNiftyControl("#Ambient_Volume", Slider.class);
                        aVol = Float.parseFloat(parts[1]);
                        slider.setValue(aVol);
                        break;
                    case "Combat":
                        slider = screen.findNiftyControl("#Combat_Volume", Slider.class);
                        cVol = Float.parseFloat(parts[1]);
                        slider.setValue(cVol);
                        break;
                    case "Dialog" :
                        slider = screen.findNiftyControl("#Dialog_Volume", Slider.class);
                        dVol = Float.parseFloat(parts[1]);
                        slider.setValue(dVol);
                        break;
                    case "Footsteps" :
                        slider = screen.findNiftyControl("#Footsteps_Volume", Slider.class);
                        fVol = Float.parseFloat(parts[1]);
                        slider.setValue(fVol);
                        break;
                }
            }
            soundScanner.close();
        } catch (FileNotFoundException ex) {
        }
        
    
    }

    @Override
    public void onStartScreen() {
       nifty.setIgnoreKeyboardEvents(false);
    }

    @Override
    public void onEndScreen() { 
       nifty.setIgnoreKeyboardEvents(true);      
    }
    /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Post_Water_Button")
    public void checkBoxPW(final String id, CheckBoxStateChangedEvent event){
        x1 = event.isChecked();
    }
         /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Water_Reflections_Button")
    public void checkBoxWR(final String id, CheckBoxStateChangedEvent event){
        x2 = event.isChecked();
    }
         /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Water_Ripples_Button")
    public void checkBoxWRIP(final String id, CheckBoxStateChangedEvent event){
        x3 = event.isChecked();
    }
         /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Water_Specular_Button")
    public void checkBox(final String id, CheckBoxStateChangedEvent event){
        x4 = event.isChecked();
    }
         /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Water_Foam_Button")
    public void checkBoxWF(final String id, CheckBoxStateChangedEvent event){
       x5 = event.isChecked();
    }
         /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Sky_Dome_Button")
    public void checkBoxSD(final String id, CheckBoxStateChangedEvent event){
        x6 = event.isChecked();
    }
         /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Star_Motion_Button")
    public void checkBoxSM(final String id, CheckBoxStateChangedEvent event){
      x7 = event.isChecked();
    }
         /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Cloud_Motion_Button")
    public void checkBoxCM(final String id, CheckBoxStateChangedEvent event){
        x8 = event.isChecked();
    }
         /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Bloom_Light_Button")
    public void checkBoxBL(final String id, CheckBoxStateChangedEvent event){
        x9 = event.isChecked();
    }
         /**
     * 
     * @param id the ID of the check box being selected
     * @param event the new state of the check box
     * Checks the state of the the check box to update the setting to be activated or deactivated
     */
     @NiftyEventSubscriber(id = "Light_Scatter_Button")
    public void checkBoxLS(final String id, CheckBoxStateChangedEvent event){
       x10 = event.isChecked();
    }
     /**
      * Applys the settings according to the selected and deselected check boxes
      */
     public void applySettings(){
         if(settings == null){
        settings = new GameSettings();   
         }    
        settings.updateSettings(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10);
        settings.apply();
        //Still needs to be added
        //scene.reload();
     }
     /**
      * updates the display setting
      */
     public void displayApply(){
         ListBox listBox = screen.findNiftyControl("Resolution_Opts", ListBox.class);
         String res[] = listBox.getFocusItem().toString().split(" X ");
         String width = res[0];
         String height = res[1];
         settings.updateSettings("width", width);         
         settings.updateSettings("height", height);
         settings.apply();
         UI.updateRes(Integer.parseInt(width),Integer.parseInt(height));
     }
     /**
      * Quits the game
      */
     public void quitGame(){
         app.stop(false);
        System.exit(0);
     }
    public void goTo(String screen) {
         if(screen.equals("#Login_Screen")){
             System.out.println("back to login screen");
         }
        nifty.gotoScreen(screen);
        ClientConnection.loggedIn = false;
        ClientConnection.relog = true;
        Audio.pauseAmbient();
    }
     @NiftyEventSubscriber(id = "#Master_Volume")
     public void volumeSliderM(final String id,SliderChangedEvent event){
         mVol  = event.getValue();
     }
     @NiftyEventSubscriber(id = "#Ambient_Volume")
     public void volumeSliderA(final String id,SliderChangedEvent event){
         aVol  = event.getValue();
     }
     @NiftyEventSubscriber(id = "#Combat_Volume")
     public void volumeSliderC(final String id,SliderChangedEvent event){
         cVol  = event.getValue();
     }
     @NiftyEventSubscriber(id = "#Dialog_Volume")
     public void volumeSliderD(final String id,SliderChangedEvent event){
         dVol  = event.getValue();
     }
     @NiftyEventSubscriber(id = "#Footsteps_Volume")
     public void volumeSliderF(final String id,SliderChangedEvent event){
         fVol  = event.getValue();
     }  
     public void soundApply(){
         if(settings == null){
            settings = new GameSettings();   
         }   
        try {
            settings.updateSound(mVol,aVol,cVol,dVol,fVol);
        } catch (IOException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
