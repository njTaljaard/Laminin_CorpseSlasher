package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Audio has the responsibility of loading audio files, updating volumes, playing 
 * and pausing when required.
 */
public final class Audio {
    
    protected static AudioRenderer audioRenderer;
    protected static AssetManager assetManager;
    
    private static AudioNode wave;
    
    private static int playerAttackCount;
    private static AudioNode playerAttack1;
    private static AudioNode playerAttack2;
    private static AudioNode playerAttack3;
    private static AudioNode playerDamage;
    private static AudioNode playerWalk;
    private static AudioNode lowHealth;
    public static boolean played = false;
    private static ArrayList<AudioNode> dialog;
    
    private static int mobAttackCount;
    private static AudioNode mobAttack1;
    private static AudioNode mobAttack2;
    private static AudioNode mobAttack3;
    private static AudioNode mobAttack4;
    private static AudioNode mobAttack5;
    private static AudioNode mobDamage;
    private static AudioNode mobWalk;
    
    /**
     * updateVolume - After new sound settings is applied each audio node needs
     * to be updated to the new volume.
     */
    public static void updateVolume() {
        wave.setVolume(GameSettings.mVol / 100 * (GameSettings.aVol / 100));
        playerAttack1.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        playerAttack2.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        playerAttack3.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        playerDamage.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        playerWalk.setVolume(GameSettings.mVol / 100 * (GameSettings.fVol / 100));
        lowHealth.setVolume(GameSettings.mVol / 100 * ((GameSettings.dVol / 100)*4));
        
        for (AudioNode node : dialog) {
            node.setVolume(GameSettings.mVol / 100 * (GameSettings.dVol / 100));
        }
        
        mobAttack1.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        mobAttack2.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        mobAttack3.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        mobAttack4.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        mobAttack5.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        mobDamage.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
        mobWalk.setVolume(GameSettings.mVol / 100 * (GameSettings.fVol / 100));
    }
    
    /**
     * loadOcean - Creates wave audio node, loading file and assigns settings.
     */
    public static void loadOcean() {
        try {
            wave = new AudioNode(assetManager, "Audio/waves_sound.ogg", false);

            if (wave != null) {
                wave.setLocalTranslation(1000, 10, 1000);
                wave.setPositional(true);
                wave.setLooping(true);
                wave.setDirectional(true);
                wave.setMaxDistance(200.0f);
                wave.setRefDistance(100f);
                wave.setVolume(GameSettings.mVol / 100 * (GameSettings.aVol / 100));
                wave.setDirection(new Vector3f(0,90,0));
            } else {
                ExceptionHandler.throwError("AudioNode not created.", "Audio - Ocean");
            }
        } catch (Exception e) {
            ExceptionHandler.throwError("Could not find/load audio file.", "Audio - Ocean");
        }
    }
    
    /**
     * playAmbient - Starts playing wave audio node.
     */
    public static void playAmbient() {
        wave.play();
    }
    
    /**
     * pauseAmbient - Pauses wave audio node.
     */
    public static void pauseAmbient() {
        wave.pause();
    }
    
    /**
     * loadCharacterAudio - Loads and assigns required setting for the following
     * audio nodes : Attacks, damage, footsteps, dialog and lowhealth.
     */
    public static void loadCharacterAudio() {
        try {
            playerAttack1 = new AudioNode(assetManager, "Audio/playerAttack1.ogg", false);
            
            if (playerAttack1 != null) {
                playerAttack1.setTimeOffset(125.0f);
                playerAttack1.setMaxDistance(10);
                playerAttack1.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                playerAttack1.setLooping(false);
                playerAttack1.setPositional(false);
            } else {
                ExceptionHandler.throwError("playerAttack1 not created succefully.", "Audio - loadPlayer");
            }
                
            playerAttack2 = new AudioNode(assetManager, "Audio/playerAttack2.ogg", false);
            
            if (playerAttack2 != null) {
                playerAttack2.setTimeOffset(125.0f);
                playerAttack2.setMaxDistance(10);
                playerAttack2.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                playerAttack2.setLooping(false);   
                playerAttack2.setPositional(false);     
            } else {
                ExceptionHandler.throwError("playerAttack2 not created succefully.", "Audio - loadPlayer");
            }
            
            playerAttack3 = new AudioNode(assetManager, "Audio/playerAttack3.ogg", false);
            
            if (playerAttack3 != null) {
                playerAttack3.setTimeOffset(125.0f);
                playerAttack3.setMaxDistance(10);
                playerAttack3.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                playerAttack3.setLooping(false);
                playerAttack3.setPositional(false);
            } else {
                ExceptionHandler.throwError("playerAttack3 not created succefully.", "Audio - loadPlayer");
            }
                
            playerDamage = new AudioNode(assetManager, "Audio/mobHitPlayer.ogg", false);
            
            if (playerDamage != null) {
                playerDamage.setMaxDistance(10);
                playerDamage.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                playerDamage.setLooping(false);
                playerDamage.setPositional(false);
            } else {
                ExceptionHandler.throwError("playerDamage not created succefully.", "Audio - loadPlayer");
            }

            playerWalk = new AudioNode(assetManager, "Audio/walk_l.ogg", false);
            
            if (playerWalk != null) {
                playerWalk.setMaxDistance(10);
                playerWalk.setVolume(GameSettings.mVol / 100 * (GameSettings.fVol / 100));
                playerWalk.setLooping(false);
                playerWalk.setPositional(false);
            } else {
                ExceptionHandler.throwError("playerWalk not created succefully.", "Audio - loadPlayer");
            }
            
            dialog = new ArrayList<>();
            
            if (dialog != null) {
                AudioNode dio;
                for (int i = 0; i < 13; i++) {
                    dio = new AudioNode(assetManager, "Audio/dialog_" + i + ".ogg");
                    
                    dio.setMaxDistance(10);
                    dio.setVolume(GameSettings.mVol / 100 * (GameSettings.dVol / 100));
                    dio.setLooping(false);
                    dio.setPositional(false);
                    dialog.add(dio);
                }
            } else {
                ExceptionHandler.throwError("Dialog arraylist not initialized.", "Audio - loadPlayer");
            }
            
            lowHealth = new AudioNode(assetManager, "Audio/lowhealth.ogg");
            
            if (lowHealth != null) {
                lowHealth.setMaxDistance(10);
                lowHealth.setVolume(GameSettings.mVol / 100 * ((GameSettings.dVol / 100)*5));
                lowHealth.setLooping(false);
                lowHealth.setPositional(false);
            } else {
                ExceptionHandler.throwError("lowHealth not created succefully.", "Audio - loadPlayer");
            }
        } catch (Exception e) {
            ExceptionHandler.throwError("Could not find/load audio file. " + e.getMessage(), "Audio - Ocean");
        }
    }
    
    /**
     * playerCharacterDialog - Select a random audio node from the list through 
     * gaussian and plays the selected audio.
     */
    public static void playerCharacterDialog() {
        int rand = Math.abs((int) GameWorld.getNextGaussian(dialog.size()-1));
        
        while (rand > dialog.size()-1) {
            rand = Math.abs((int) GameWorld.getNextGaussian(dialog.size())-1);
        }
        
        dialog.get(rand).play();
    }
    
    /**
     * playLowHealt - Will play low health audio when the players health hits 30%
     * and will be playable after it has regenerated to 50% when out of combat.
     */
    public static void playLowHealth() {
        if (!played) {
            lowHealth.play();
            played = true;
        }
    }
    
    /**
     * playCharacterAttack - Select the next attack audio node it.
     */
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
    
    /**
     * playCharacterDamage - While be played when the player takes damage from
     * a mob.
     */
    public static void playCharacterDamage() {
       playerDamage.play();
    }
    
    /**
     * playCharacterWalk - Starts playing audio when charater is in movement.
     */
    public static void playCharacterWalk() {
        playerWalk.play();
    }

    /**
     * pauseCharacterWalk - Pauses walk audio once player stops moving.
     */
    public static void pauseCharacterWalk() {
        playerWalk.pause();
    }
    
    /**
     * loadMobAudio - Loads and assigns required setting for the following
     * audio nodes : Attacks, damage, footsteps.
     */
    public static void loadMobAudio() {
        try {
            mobAttack1 = new AudioNode(assetManager, "Audio/zombieAttack1.ogg", false);
            
            if (mobAttack1 != null) {
                mobAttack1.setTimeOffset(125.0f);
                mobAttack1.setMaxDistance(10);
                mobAttack1.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                mobAttack1.setLooping(false);
                mobAttack1.setPositional(false);
            } else {
                ExceptionHandler.throwError("AudioNode not created succesfully.", "Audio - Ocean");
            }

            mobAttack2 = new AudioNode(assetManager, "Audio/zombieAttack2.ogg", false);
            
            if (mobAttack2 != null) {
                mobAttack2.setTimeOffset(125.0f);
                mobAttack2.setMaxDistance(10);
                mobAttack2.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                mobAttack2.setLooping(false);   
                mobAttack2.setPositional(false);     
            } else {
                ExceptionHandler.throwError("AudioNode not created succesfully.", "Audio - Ocean");
            }

            mobAttack3 = new AudioNode(assetManager, "Audio/zombieAttack3.ogg", false);
            
            if (mobAttack3 != null) {
                mobAttack3.setTimeOffset(125.0f);
                mobAttack3.setMaxDistance(10);
                mobAttack3.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                mobAttack3.setLooping(false);
                mobAttack3.setPositional(false);
            } else {
                ExceptionHandler.throwError("AudioNode not created succesfully.", "Audio - Ocean");
            }

            mobAttack4 = new AudioNode(assetManager, "Audio/zombieAttack4.ogg", false);
            
            if (mobAttack4 != null) {
                mobAttack4.setTimeOffset(125.0f);
                mobAttack4.setMaxDistance(10);
                mobAttack4.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                mobAttack4.setLooping(false);   
                mobAttack4.setPositional(false);     
            } else {
                ExceptionHandler.throwError("AudioNode not created succesfully.", "Audio - Ocean");
            }

            mobAttack5 = new AudioNode(assetManager, "Audio/zombieAttack5.ogg", false);
            
            if (mobAttack5 != null) {
                mobAttack5.setTimeOffset(125.0f);
                mobAttack5.setMaxDistance(10);
                mobAttack5.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                mobAttack5.setLooping(false);
                mobAttack5.setPositional(false);
            } else {
                ExceptionHandler.throwError("AudioNode not created succesfully.", "Audio - Ocean");
            }

            mobDamage = new AudioNode(assetManager, "Audio/playerHitMob.ogg", false);
            
            if (mobDamage != null) {
                mobDamage.setMaxDistance(10);
                mobDamage.setVolume(GameSettings.mVol / 100 * (GameSettings.cVol / 100));
                mobDamage.setLooping(false);
                mobDamage.setPositional(false);
            } else {
                ExceptionHandler.throwError("AudioNode not created succesfully.", "Audio - Ocean");
            }

            mobWalk = new AudioNode(assetManager, "Audio/walk_r.ogg", false);
            
            if (mobWalk != null) {
                mobWalk.setMaxDistance(10);
                mobWalk.setVolume(GameSettings.mVol / 100 * (GameSettings.fVol / 100));
                mobWalk.setLooping(false);
                mobWalk.setPositional(true);
            } else {
                ExceptionHandler.throwError("AudioNode not created succesfully.", "Audio - Ocean");
            }
        } catch (Exception e) {
            ExceptionHandler.throwError("Could not find/load audio file.", "Audio - Ocean");
        }
    }
    
    /**
     * playMobAttack - Selects the current attack audio and plays and instance
     * at the location of the mob.
     * @param position - Vector3f location of the current mob.
     */
    public static void playMobAttack(Vector3f position) {
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
    
    /**
     * playMobDamage - Plays an instance of player damage audio.
     */
    public static void playMobDamage() {
       mobDamage.playInstance();
    }
    
    /**
     * playMobWalk - Starts playing walk audio when a mob is in motion.
     */
    public static void playMobWalk() {
       mobWalk.play();
    }
    
    /**
     * pauseMobWalk - Pauses walk audio when no mobs are in motion.
     */
    public static void pauseMobWalk() {
       mobWalk.pause();
    }
}
