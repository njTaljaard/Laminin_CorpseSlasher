package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * MobAudioControl
 */
public class MobAudioControl {
    
    private int mobAttackCount;
    private AudioNode mobAttack1;
    private AudioNode mobAttack2;
    private AudioNode mobAttack3;
    private AudioNode mobAttack4;
    private AudioNode mobAttack5;
    private AudioNode mobDamage;
    private AudioNode mobWalk;
    
    public MobAudioControl(AssetManager assetManager) {
        loadMobAudio(assetManager);
    }
    
    private void loadMobAudio(AssetManager assetManager) {
        mobAttack1 = new AudioNode(assetManager, "Audio/zombieAttack1.ogg", false);
        mobAttack1.setTimeOffset(125.0f);
        mobAttack1.setMaxDistance(10);
        mobAttack1.setVolume(0.15f);
        mobAttack1.setLooping(false);
        mobAttack1.setPositional(false);
        
        mobAttack2 = new AudioNode(assetManager, "Audio/zombieAttack2.ogg", false);
        mobAttack2.setTimeOffset(125.0f);
        mobAttack2.setMaxDistance(10);
        mobAttack2.setVolume(0.15f);
        mobAttack2.setLooping(false);   
        mobAttack2.setPositional(false);     
        
        mobAttack3 = new AudioNode(assetManager, "Audio/zombieAttack3.ogg", false);
        mobAttack3.setTimeOffset(125.0f);
        mobAttack3.setMaxDistance(10);
        mobAttack3.setVolume(0.15f);
        mobAttack3.setLooping(false);
        mobAttack3.setPositional(false);
        
        mobAttack4 = new AudioNode(assetManager, "Audio/zombieAttack4.ogg", false);
        mobAttack4.setTimeOffset(125.0f);
        mobAttack4.setMaxDistance(10);
        mobAttack4.setVolume(0.15f);
        mobAttack4.setLooping(false);   
        mobAttack4.setPositional(false);     
        
        mobAttack5 = new AudioNode(assetManager, "Audio/zombieAttack5.ogg", false);
        mobAttack5.setTimeOffset(125.0f);
        mobAttack5.setMaxDistance(10);
        mobAttack5.setVolume(0.15f);
        mobAttack5.setLooping(false);
        mobAttack5.setPositional(false);
        
        mobDamage = new AudioNode(assetManager, "Audio/playerHitMob.ogg", false);
        mobDamage.setMaxDistance(10);
        mobDamage.setVolume(0.15f);
        mobDamage.setLooping(false);
        mobDamage.setPositional(false);
        
        mobWalk = new AudioNode(assetManager, "Audio/walk_r.ogg", false);
        mobWalk.setMaxDistance(10);
        mobWalk.setVolume(0.05f);
        mobWalk.setLooping(false);
        mobWalk.setPositional(true);
    }
    
    public void playMobAttack(Vector3f position) {
        switch (mobAttackCount) {
            case 1  :
                mobAttack1.setLocalTranslation(position);
                mobAttack1.play();
                mobAttackCount++;
                break;
            case 2  :
                mobAttack2.setLocalTranslation(position);
                mobAttack2.play();
                mobAttackCount++;
                break;
            case 3  :
                mobAttack3.setLocalTranslation(position);
                mobAttack3.play();
                mobAttackCount++;
                break;
            case 4  :
                mobAttack4.setLocalTranslation(position);
                mobAttack4.play();
                mobAttackCount++;
                break;
            default :
                mobAttack5.setLocalTranslation(position);
                mobAttack5.play();
                mobAttackCount = 1;
                break;
        }
    }
    
    public void playMobDamage(Vector3f position) {
        mobDamage.setLocalTranslation(position);
        mobDamage.play();
    }
    
    public void playMobWalk() {
        mobWalk.play();
    }
    
    public void pauseMobWalk() {
        mobWalk.pause();
    }
}
