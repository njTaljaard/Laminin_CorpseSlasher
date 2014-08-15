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

    protected boolean postWater = false;
    protected boolean waterReflections = true;
    protected boolean waterRippels = true;
    protected boolean waterSpecular = true;
    protected boolean waterFoam = true;
    protected boolean skyDome = true;
    protected boolean starMotion = true;
    protected boolean cloudMotion = true;
    protected boolean bloomLight = true;
    protected boolean lightScatter = true;
    private AppSettings storedSettings;
    private ArrayList setting = new ArrayList();

    public GameSettings() {
        Scanner contents = null;
        try {
            contents = new Scanner(new FileReader("GameSettings.txt"));
            while (contents.hasNextLine()) {
                String line = contents.nextLine();
                setting.add(line);
            }
        } catch (FileNotFoundException ex) {
        } finally {
            contents.close();
        }
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

    public GameSettings(boolean x1, boolean x2, boolean x3, boolean x4, boolean x5, boolean x6, boolean x7, boolean x8,
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

    public boolean isWaterReflections() {
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
        File savedSettings = new File("GameSettings.txt");
        return storedSettings;
    }

    public void apply() {

        FileWriter savedGame = null;
        try {
            savedGame = new FileWriter("GameSettings.txt");
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

    public void updateSettings(String name, String settings) {
        if (setting.contains(name + "=" + settings + "=")) {
            setting.remove(name + "=" + settings + "=");
        }
        setting.add(name + "=" + settings + "=");
    }
    public void updateRes(int width, int height){
        UserInterfaceManager UI = new UserInterfaceManager();
        UI.init(null, null, null, null, null, null);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
