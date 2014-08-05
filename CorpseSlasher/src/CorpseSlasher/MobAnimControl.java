package CorpseSlasher;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * MobAnimControl the control for when animations is currently bound and what
 * happens at the end of each cycle.
 */
public class MobAnimControl {
    
    private AnimEventListener animationListener;
    public boolean attacking;
    
    public MobAnimControl() {
        initAnimEventListener();
        attacking = false;
    }
    
    /**
     * initAminEventListener will trigger on animation change as well as when a 
     * cycle of an animations if completed to run it again.
     */
    private void initAnimEventListener() {
        animationListener = new AnimEventListener() {
            @Override
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                switch (animName) {
                    case "Walk":
                        channel.setAnim("Walk", 1.5f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1.5f);
                        break;                    
                    case "Stand":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1.0f);
                        break;                        
                    case "Attack":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1.5f);
                        attacking = false;
                        break;                        
                    case "WalkAttack":
                        channel.setAnim("Walk", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1.5f);
                        attacking = false;
                        break;
                    case "Passive":
                        
                        break;
                }
            }

            @Override
            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {}
        };
    }
    
    /**
     * updateCharacterAnimations updates animations when required.
     * @param channel - AnimChannel to change and read current animations.
     * @param aggro - Boolean if mob has been aggroed by the player.
     * @param walkAttack - Boolean if mob should attack while walking.
     * @param attack - Boolean if mob can be stationary and attack.
     * @param passive - Boolean if mob is not aggroed and at spawn position.
     */
    public void updateMobAnimations(AnimChannel channel, boolean aggro, 
            boolean walkAttack, boolean attack, boolean passive) {
        if (aggro) {                
            if (walkAttack) {
                if (!channel.getAnimationName().equals("WalkAttack")) {
                    channel.setAnim("WalkAttack", 0.05f);
                    channel.setLoopMode(LoopMode.DontLoop);
                    channel.setSpeed(1.0f);
                    attacking = true;
                }
            } else if (attack) {
                if (!channel.getAnimationName().equals("Attack")) {
                    channel.setAnim("Attack", 0.05f);
                    channel.setLoopMode(LoopMode.DontLoop);
                    channel.setSpeed(1.0f);
                    attacking = true;
                }
            } else {
                if (!channel.getAnimationName().equals("Walk")){
                    channel.setAnim("Walk");
                    channel.setLoopMode(LoopMode.Loop);
                    channel.setSpeed(1.5f);
                }
            }
        } else {
            if (!passive) {
                if (!channel.getAnimationName().equals("Walk")){
                    channel.setAnim("Walk");
                    channel.setLoopMode(LoopMode.Loop);
                    channel.setSpeed(1.5f);
                }
            } else {
                channel.setAnim("Stand"); /** @TODO: Create passive anim and set*/
                channel.setLoopMode(LoopMode.Cycle);                    
            }
        }
    }
    
    /**
     * getAnimationListener accessor of the animation handler.
     * @return AnimEventListener
     */
    public AnimEventListener getAnimationListener() {
        return animationListener;
    }
}
