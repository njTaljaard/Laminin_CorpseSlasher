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
    private boolean attack;
    
    public MobAnimControl() {
        initAnimEventListener();
        attack = false;
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
                        if (channel.getLoopMode().equals(LoopMode.Loop)) {
                            channel.setAnim("Walk", 0.0f);
                            channel.setLoopMode(LoopMode.Loop);
                            channel.setSpeed(1.0f);
                        } else {
                            channel.setAnim("Stand", 0.0f);
                            channel.setLoopMode(LoopMode.Loop);
                        }
                        break;                    
                    case "Stand":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1.0f);
                        break;                        
                    case "Attack":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1.0f);
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
     */
    public boolean updateMobAnimations(AnimChannel channel, boolean walk, boolean passive) {        
        if (walk) {
            if (channel.getAnimationName().equals("Stand")) {
                channel.setAnim("Attack");
                channel.setLoopMode(LoopMode.DontLoop);
                channel.setSpeed(1.15f);
            } else {
                if (!channel.getAnimationName().equals("Attack")) {
                    channel.setAnim("Attack", 1.0f);
                    channel.setLoopMode(LoopMode.DontLoop);
                    channel.setSpeed(1.15f);
                }
            }
        }
        
        if (!passive) {
            if (channel.getAnimationName().equals("Stand")) {
                channel.setAnim("Walk");
                channel.setLoopMode(LoopMode.Loop);
                channel.setSpeed(1.5f);
            }
        } else {
            if (!channel.getAnimationName().equals("Attack")) {
                channel.setAnim("Stand");
                channel.setLoopMode(LoopMode.Cycle);                    
            }
        }
        
        return attack;
    }
    
    /**
     * getAnimationListener accessor of the animation handler.
     * @return AnimEventListener
     */
    public AnimEventListener getAnimationListener() {
        return animationListener;
    }
}
