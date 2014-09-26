package CorpseSlasher;

//~--- non-JDK imports --------------------------------------------------------
import GUI.UserInterfaceManager;
import com.jme3.system.AppSettings;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301 GameSettings will be used to load and store the users custom
 * game settings at launch and during game changes. Read Setttings form
 * ./GameSettings.txt
 */
public class GameSettings {

    static boolean postWater = true;
    static boolean waterReflections = true;
    static boolean waterRippels = true;
    static boolean waterSpecular = true;
    static boolean waterFoam = true;
    static boolean skyDome  = true;
    static boolean starMotion = true;
    static boolean cloudMotion = true;
    static boolean bloomLight = true;
    static boolean lightScatter = true;
    static float mVol,aVol,cVol,dVol,fVol;
    private AppSettings storedSettings;
    private ArrayList setting = new ArrayList();
/**
 * Creates the object from the stored settings file
 */
    public GameSettings() {
        Scanner contents = null;
        try {
            contents = new Scanner(new FileReader("GameSettings.ini"));
            while (contents.hasNextLine()) {
                String line = contents.nextLine();
                setting.add(line);
                String parts[] = line.split("=");
                switch (parts[0]) {
                    case "PostWater":
                        postWater = Boolean.parseBoolean(parts[1]);
                        break;
                    case "WaterReflections":
                        waterReflections = Boolean.parseBoolean(parts[1]);
                        break;
                    case "WaterRipples":
                        waterRippels = Boolean.parseBoolean(parts[1]);
                        break;
                    case "WaterSpecular":
                        waterSpecular = Boolean.parseBoolean(parts[1]);
                        break;
                    case "WaterFoam":
                        waterFoam = Boolean.parseBoolean(parts[1]);
                        break;
                    case "SkyDome":
                        skyDome = Boolean.parseBoolean(parts[1]);
                        break;
                    case "StarMotion":
                        starMotion = Boolean.parseBoolean(parts[1]);
                        break;
                    case "CloudMotion":
                        cloudMotion = Boolean.parseBoolean(parts[1]);
                        break;
                    case "BloomLight":
                        bloomLight = Boolean.parseBoolean(parts[1]);
                        break;
                    case "LightScatter":
                        lightScatter = Boolean.parseBoolean(parts[1]);
                        break;
                }

            }
            contents.close();
            contents = new Scanner(new FileReader("SoundSettings.ini"));
            while (contents.hasNextLine()) {
                String line = contents.nextLine();
                setting.add(line);
                String parts[] = line.split("=");
                switch (parts[0]) {
                    case "Master":
                        mVol = Float.parseFloat(parts[1]);
                        break;
                    case "Ambient":
                        aVol = Float.parseFloat(parts[1]);
                        break;
                    case "Combat":
                        cVol = Float.parseFloat(parts[1]);
                        break;
                    case "Dialog":
                        dVol = Float.parseFloat(parts[1]);
                        break;
                    case "Footsteps":
                        fVol = Float.parseFloat(parts[1]);
                        break;
                }

            }
            
        } catch (FileNotFoundException ex) {
        } finally {
 //           contents.close();
        }
    }
    public void updateSound(float mVol,float aVol,float cVol,float dVol,float fVol) throws IOException{
        GameSettings.mVol = mVol;
        GameSettings.aVol = aVol;
        GameSettings.cVol = cVol;
        GameSettings.dVol = dVol;
        GameSettings.fVol = fVol;
        
        FileWriter soundFile = new FileWriter("SoundSettings.ini");
        soundFile.append("Master="+mVol+"\n");
        soundFile.append("Ambient="+aVol+"\n");
        soundFile.append("Combat="+cVol+"\n");
        soundFile.append("Dialog="+dVol+"\n");
        soundFile.append("Footsteps="+fVol+"\n");
        soundFile.close();
        Audio.updateVolume();
    }

    public GameSettings(boolean[] settings) {
        int x = 0;
        postWater = settings[x++];
        waterReflections = settings[x++];
        waterRippels = settings[x++];
        waterSpecular = settings[x++];
        waterFoam = settings[x++];
        skyDome = settings[x++];
        starMotion = settings[x++];
        cloudMotion = settings[x++];
        bloomLight = settings[x++];
        lightScatter = settings[x++];
    }
    /**
     * @param x1 the new postWater value
     * @param x2 the new waterReflections value
     * @param x3 the new waterRippels value
     * @param x4 the new waterSpecular value
     * @param x5 the new waterFoam value
     * @param x6 the new skyDome value
     * @param x7 the new starMotion value
     * @param x8 the new cloudMotion value
     * @param x9 the new bloomLight value
     * @param x10 the new lightScatter value
     */
    public void updateSettings(boolean x1, boolean x2, boolean x3, boolean x4, boolean x5, boolean x6, boolean x7, boolean x8,
            boolean x9, boolean x10) {
        postWater = x1;
        waterReflections = x2;
        waterRippels = x3;
        waterSpecular = x4;
        waterFoam = x5;
        skyDome = x6;
        starMotion = x7;
        cloudMotion = x8;
        bloomLight = x9;
        lightScatter = x10;
        updateSettings("PostWater", "" + x1);
        updateSettings("WaterReflections", "" + x2);
        updateSettings("WaterRipples", "" + x3);
        updateSettings("WaterSpecular", "" + x4);
        updateSettings("WaterFoam", "" + x5);
        updateSettings("SkyDome", "" + x6);
        updateSettings("StarMotion", "" + x7);
        updateSettings("CloudMotion", "" + x8);
        updateSettings("BloomLight", "" + x9);
        updateSettings("LightScatter", "" + x10);
    }

    public boolean isPostWater() {
        return postWater;
    }

    public  boolean isWaterReflections() {
        return waterReflections;
    }

    public boolean isWaterRippels() {
        return waterRippels;
    }

    public boolean isWaterSpecular() {
        return waterSpecular;
    }

    public boolean isWaterFoam() {
        return waterFoam;
    }

    public boolean isSkyDome() {
        return skyDome;
    }

    public boolean isStarMotion() {
        return starMotion;
    }

    public boolean isCloudMotion() {
        return cloudMotion;
    }

    public boolean isBloomLight() {
        return bloomLight;
    }

    public boolean isLightScatter() {
        return lightScatter;
    }

    /**
     * LoadSettings will read & parse GameSettings.txt into AppSettings.
     *
     * @return the settings to be used.
     */
    public AppSettings LoadSettings() {
        File savedSettings = new File("GameSettings.ini");
        return storedSettings;
    }
    /**
     * Applys the new settings from the graphics settings screen
     */
    public void apply() {

        FileWriter savedGame = null;
        try {
            savedGame = new FileWriter("GameSettings.ini");
            for (Object line : setting) {
                savedGame.append(line.toString() + "\n");
            }
        } catch (IOException ex) {
        } finally {
            try {
                savedGame.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Store settings will store the current game settings to be used later.
     *
     * @param settings - The current game settings
     */
    public void StoreSettings(AppSettings settings) {
        storedSettings = settings;
    }
    /**
     * Checks to see if the value has changed and then removes the old setting and then updates
     * the new setting
     * @param name the settings name to be updated
     * @param settings the value of the setting
     */
    public void updateSettings(String name, String settings) {
        for (int x = 0; x < setting.size(); x++) {
            String set = setting.get(x).toString();
            if (set.contains(name)) {
                setting.remove(x);
                break;
            }
        }
        setting.add(name + "=" + settings);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
