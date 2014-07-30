package GUI;

import CorpseSlasher.GameSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Gerhard
 * The controller for the graphics settings screen
 */
public class SettingsController implements ScreenController {
    private GameSettings settings;
    private boolean x1,x2,x3,x4,x5,x6,x7,x8,x9,x10;
    @Override
    public void bind(Nifty nifty, Screen screen) {
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
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
        settings = new GameSettings(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10);        
    }
     /**
      * Quits the game
      */
     public void quitGame(){
         System.exit(1);
     }
    
}
