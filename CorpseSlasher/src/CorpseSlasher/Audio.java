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
    
    private static int playerAttackCount;
    private static AudioNode playerAttack1;
    private static AudioNode playerAttack2;
    private static AudioNode playerAttack3;
    private static AudioNode playerDamage;
    private static AudioNode playerWalk;
    
    private static int mobAttackCount;
    private static AudioNode mobAttack1;
    private static AudioNode mobAttack2;
    private static AudioNode mobAttack3;
    private static AudioNode mobAttack4;
    private static AudioNode mobAttack5;
    private static AudioNode mobDamage;
    private static AudioNode mobWalk;
    
    private static Environment env;
    
    public static void updateVolume() {
        wave1.setVolume(GameSettings.mVol / 200 + GameSettings.aVol / 200);
        playerAttack1.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        playerAttack2.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        playerAttack3.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        playerDamage.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        playerWalk.setVolume(GameSettings.mVol / 200 + GameSettings.fVol / 200);
        mobAttack1.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobAttack2.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobAttack3.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobAttack4.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobAttack5.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobDamage.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobWalk.setVolume(GameSettings.mVol / 200 + GameSettings.fVol / 200);
    }
    
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
        playerAttackCount = 1;
    }
    
    public static void loadOcean() {
       wave1 = new AudioNode(assetManager, "Audio/waves_sound.ogg", false);
        wave1.setLocalTranslation(1000, 10, 1000);
        wave1.setPositional(true);
        wave1.setLooping(true);
        wave1.setDirectional(true);
        wave1.setMaxDistance(200.0f);
        wave1.setRefDistance(100f);
        wave1.setVolume(GameSettings.mVol / 200 + GameSettings.aVol / 200);
        wave1.setDirection(new Vector3f(0,90,0));
        
        /*wave2 = new AudioNode(assetManager, "Audio/waves_sound.ogg", false);
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
        wave4.setLooping(true);*/
        
        /*wave2.play();
        wave3.play();
        wave4.play();*/
    }
    
    public static void playAmbient() {
        wave1.play();
    }
    
    public static void pauseAmbient() {
        wave1.pause();
    }
    
    public static void loadCharacterAudio() {
        playerAttack1 = new AudioNode(assetManager, "Audio/playerAttack1.ogg", false);
        playerAttack1.setTimeOffset(125.0f);
        playerAttack1.setMaxDistance(10);
        playerAttack1.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        playerAttack1.setLooping(false);
        playerAttack1.setPositional(false);
        
        playerAttack2 = new AudioNode(assetManager, "Audio/playerAttack2.ogg", false);
        playerAttack2.setTimeOffset(125.0f);
        playerAttack2.setMaxDistance(10);
        playerAttack2.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        playerAttack2.setLooping(false);   
        playerAttack2.setPositional(false);     
        
        playerAttack3 = new AudioNode(assetManager, "Audio/playerAttack3.ogg", false);
        playerAttack3.setTimeOffset(125.0f);
        playerAttack3.setMaxDistance(10);
        playerAttack3.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        playerAttack3.setLooping(false);
        playerAttack3.setPositional(false);
        
        playerDamage = new AudioNode(assetManager, "Audio/mobHitPlayer.ogg", false);
        playerDamage.setMaxDistance(10);
        playerDamage.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        playerDamage.setLooping(false);
        playerDamage.setPositional(false);
        
        playerWalk = new AudioNode(assetManager, "Audio/walk_l.ogg", false);
        playerWalk.setMaxDistance(10);
        playerWalk.setVolume(GameSettings.mVol / 200 + GameSettings.fVol / 200);
        playerWalk.setLooping(false);
        playerWalk.setPositional(false);
    }
    
    public static void playCharacterAttack() {
        switch (playerAttackCount) {
            case 1  :
                playerAttack1.play();
                playerAttackCount++;
                break;
            case 2  :
                playerAttack2.play();
                playerAttackCount++;
                break;
            default :
                playerAttack3.play();
                playerAttackCount = 1;
                break;
        }
    }
    
    public static void playCharacterDamage() {
       playerDamage.play();
    }
    
    public static void playCharacterWalk() {
        playerWalk.play();
    }

    public static void pauseCharacterWalk() {
        playerWalk.pause();
    }
    
    public static void loadMobAudio() {
        mobAttack1 = new AudioNode(assetManager, "Audio/zombieAttack1.ogg", false);
        mobAttack1.setTimeOffset(125.0f);
        mobAttack1.setMaxDistance(10);
        mobAttack1.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobAttack1.setLooping(false);
        mobAttack1.setPositional(false);
        
        mobAttack2 = new AudioNode(assetManager, "Audio/zombieAttack2.ogg", false);
        mobAttack2.setTimeOffset(125.0f);
        mobAttack2.setMaxDistance(10);
        mobAttack2.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobAttack2.setLooping(false);   
        mobAttack2.setPositional(false);     
        
        mobAttack3 = new AudioNode(assetManager, "Audio/zombieAttack3.ogg", false);
        mobAttack3.setTimeOffset(125.0f);
        mobAttack3.setMaxDistance(10);
        mobAttack3.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobAttack3.setLooping(false);
        mobAttack3.setPositional(false);
        
        mobAttack4 = new AudioNode(assetManager, "Audio/zombieAttack4.ogg", false);
        mobAttack4.setTimeOffset(125.0f);
        mobAttack4.setMaxDistance(10);
        mobAttack4.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobAttack4.setLooping(false);   
        mobAttack4.setPositional(false);     
        
        mobAttack5 = new AudioNode(assetManager, "Audio/zombieAttack5.ogg", false);
        mobAttack5.setTimeOffset(125.0f);
        mobAttack5.setMaxDistance(10);
        mobAttack5.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobAttack5.setLooping(false);
        mobAttack5.setPositional(false);
        
        mobDamage = new AudioNode(assetManager, "Audio/playerHitMob.ogg", false);
        mobDamage.setMaxDistance(10);
        mobDamage.setVolume(GameSettings.mVol / 200 + GameSettings.cVol / 200);
        mobDamage.setLooping(false);
        mobDamage.setPositional(false);
        
        mobWalk = new AudioNode(assetManager, "Audio/walk_r.ogg", false);
        mobWalk.setMaxDistance(10);
        mobWalk.setVolume(GameSettings.mVol / 200 + GameSettings.fVol / 200);
        mobWalk.setLooping(false);
        mobWalk.setPositional(true);
    }
    
    public synchronized static void playMobAttack(Vector3f position) {
        switch (mobAttackCount) {
            case 1  :
                mobAttack1.setLocalTranslation(position);
                mobAttack1.playInstance();
                mobAttackCount++;
                break;
            case 2  :
                mobAttack2.setLocalTranslation(position);
                mobAttack2.playInstance();
                mobAttackCount++;
                break;
            case 3  :
                mobAttack3.setLocalTranslation(position);
                mobAttack3.playInstance();
                mobAttackCount++;
                break;
            case 4  :
                mobAttack4.setLocalTranslation(position);
                mobAttack4.playInstance();
                mobAttackCount++;
                break;
            default :
                mobAttack5.setLocalTranslation(position);
                mobAttack5.playInstance();
                mobAttackCount = 1;
                break;
        } 
    }
    
    public synchronized static void playMobDamage(Vector3f position) {
       mobDamage.setLocalTranslation(position);
       mobDamage.playInstance();
    }
    
    public synchronized static void playMobWalk() {
       mobWalk.play();
    }
    
    public synchronized static void pauseMobWalk() {
       mobWalk.pause();
    }
}
