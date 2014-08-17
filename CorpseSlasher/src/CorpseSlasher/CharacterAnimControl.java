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
 * CharacterAnimControl handles the player character animation updates and cycle
 * loops.
 */
public class CharacterAnimControl {
    
    private AnimEventListener animationListener;
    protected boolean attacking;
    
    /**
     * CharacterAnimControl will initailize the animation event listener.
     */
    public CharacterAnimControl() {
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
                        if (channel.getLoopMode().equals(LoopMode.Loop)) {
                            channel.setAnim("Walk", 0.0f);
                            channel.setLoopMode(LoopMode.Loop);
                            channel.setSpeed(1.5f);
                        } else {
                            channel.setAnim("Stand", 0.0f);
                            channel.setLoopMode(LoopMode.Loop);
                        }
                        break;                    
                    case "Stand":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1f);
                        break;                        
                    case "Slash":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1f);
                        attacking = false;
                        break;
                }
            }

            @Override
            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {}
        };
    }
    
    /**
     * handleAnimations updates animations where required.
     * @param channel - AnimChannel controls the animation currently being used.
     * @param slash - If player is attacking.
     * @param walk - If player is currently walking.
     * @param alive - If the player is alive to allow motion.
     */
    public boolean updateCharacterAnimations(AnimChannel channel, boolean slash, 
            boolean walk, boolean alive) {        
        if (alive) {
            if (slash) {
                if (channel.getAnimationName().equals("Stand")) {
                    channel.setAnim("Slash");
                    channel.setLoopMode(LoopMode.DontLoop);
                    channel.setSpeed(1.15f);
                    attacking = true;
                } else {
                    if (!channel.getAnimationName().equals("Slash")) {
                        channel.setAnim("Slash", 1.0f);
                        channel.setLoopMode(LoopMode.DontLoop);
                        channel.setSpeed(1.15f);
                    }
                }
                slash = false;
            }

            if (walk) {
                if (channel.getAnimationName().equals("Stand")) {
                    channel.setAnim("Walk");
                    channel.setLoopMode(LoopMode.Loop);
                    channel.setSpeed(1.5f);
                }
            } else {
                if (!channel.getAnimationName().equals("Slash")) {
                    channel.setAnim("Stand");
                    channel.setLoopMode(LoopMode.Cycle);                    
                }
            }
        } else {
            channel.setAnim("Stand");
            channel.setLoopMode(LoopMode.Loop);
        }
        
        return slash;
    }
    
    /**
     * getAnimationListener accessor of the animation handler.
     * @return AnimEventListener
     */
    public AnimEventListener getAnimationListener() {
        return animationListener;
    }
}
