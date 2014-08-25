package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.Environment;
import com.jme3.math.Vector3f;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Audio
 */
public final class Audio {
    
    protected static AudioRenderer audioRenderer;
    protected static AssetManager assetManager;
    private static AudioNode wave1;
    private static AudioNode wave2;
    private static AudioNode wave3;
    private static AudioNode wave4;
    private static Environment env;
    
    public static void initAmbient() {
        /*
         * density, diffusion, gain, gainHF, decayTime, decayHF, reflGain, reflDelay, lateGain, lateDelay
         */
        float[] eax = new float[]{5, 38.0f, 0.300f, -1000, -3300, 0,
            1.49f, 0.54f, 1.00f, -2560, 0.162f, 0.00f, 0.00f,
            0.00f, -229, 0.088f, 0.00f, 0.00f, 0.00f, 0.125f, 1.000f,
            0.250f, 0.000f, -5.0f, 5000.0f, 250.0f, 0.00f, 0x3f};
        env = new Environment(eax);
        audioRenderer.setEnvironment(env);
    }
    
    public static void loadOcean() {
        wave1 = new AudioNode(assetManager, "Audio/waves_sound.ogg", false);
        wave1.setLocalTranslation(1000, 10, 1000);
        wave1.setMaxDistance(700.0f);
        wave1.setPositional(true);
        wave1.setRefDistance(400f);
        wave1.setVolume(0.1f);
        wave1.setDirection(Vector3f.ZERO);
        wave1.setLooping(true);
        
        wave2 = new AudioNode(assetManager, "Audio/waves_sound.ogg", false);
        wave2.setLocalTranslation(-1000, 10, 1000);
        wave2.setMaxDistance(700.0f);
        wave2.setPositional(true);
        wave2.setRefDistance(400f);
        wave2.setVolume(0.1f);
        wave2.setDirection(Vector3f.ZERO);
        wave2.setLooping(true);
        
        wave3 = new AudioNode(assetManager, "Audio/waves_sound.ogg", false);
        wave3.setLocalTranslation(1000, 10, -1000);
        wave3.setMaxDistance(700.0f);
        wave3.setPositional(true);
        wave3.setRefDistance(400f);
        wave3.setVolume(0.1f);
        wave3.setDirection(Vector3f.ZERO);
        wave3.setLooping(true);
        
        wave4 = new AudioNode(assetManager, "Audio/waves_sound.ogg", false);
        wave4.setLocalTranslation(-1000, 10, -1000);
        wave4.setMaxDistance(700.0f);
        wave4.setPositional(true);
        wave4.setRefDistance(400f);
        wave4.setVolume(0.1f);
        wave4.setDirection(Vector3f.ZERO);
        wave4.setLooping(true);
        
        wave1.play();
        wave2.play();
        wave3.play();
        wave4.play();
    }
}
