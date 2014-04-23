package CorpseSlasher;

import com.jme3.system.AppSettings;

/**
 * @author Laminin
 * @param  Derivco
 * GameSettings will be used to load and store the users custom game settings
 * at launch and during game changes. Read Setttings form ./GameSettings.txt
 */
public class GameSettings {
    
    
    public GameSettings() {
        
    }
    
    /**
     * LoadSettings will read & parse GameSettings.txt into AppSettings.
     * @return the settings to be used.
     */
    public AppSettings LoadSettings() {
        AppSettings settings = new AppSettings(false);
        
        return settings;
    }
    
    /**
     * Store settings will store the current game settings to be used later.
     * @param settings - The current game settings
     */
    public void StoreSettings(AppSettings settings) {
        
    }
}
