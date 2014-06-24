package CorpseSlasher;

import com.jme3.system.AppSettings;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * GameSettings will be used to load and store the users custom game settings
 * at launch and during game changes. Read Setttings form ./GameSettings.txt
 */
public class GameSettings {
    protected boolean postWater;
    protected boolean waterReflections;
    protected boolean waterRippels;
    protected boolean waterSpecular;
    protected boolean waterFoam;
    
    protected boolean skyDome;
    protected boolean starMotion;
    protected boolean cloudMotion;
    
    protected boolean bloomLight;
    protected boolean lightScatter;
    
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
