package CorpseSlasher;

import com.jme3.animation.AnimControl;
import com.jme3.animation.SkeletonControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * GameWorld is used to containt the game state and variable of objects.
 */
public final class GameWorld {
    protected static AssetManager assetManager;
    protected static BulletAppState bullet;
    protected static boolean aggro = false;
    protected static boolean alive = true;    
    protected static Vector3f playerPosition = new Vector3f();
    
    public static final int mobRespawnTimer = 100000;
    public static final int mobRegenInterval = 20000;
    public static final int playerRespwanTime = 50000;
    public static final int playerRegenInterval = 20000;
    
    public static final float playerWalkSpeed = 15.0f;
    public static final float playerCameraXSpeed = 0.5f;
    public static final float playerCameraYSpeed = 0.5f;
    public static final float mobWalkSpeed = 6.0f;
    public static final float mobRunSpeed = 8.0f;
    
    public static final float eighth_pi = FastMath.PI * 0.125f;
    public static final Random rand = new Random();
    
    public static final int dialogInterval = 90000;
    public static int dialogPlayed;
    public static int systemTime;
    
    public static int hitSplahsInterval = 5000;
    public static int hitSplash = 0;
    
    /**
     * getSkeletonControl - Recursive search to find a skeleton controller of the
     * required model.
     * @param model - Model to be searched.
     * @return SkeletonControl of model if found.
     */
    public static SkeletonControl getSkeletonControl(Node model) {
        SkeletonControl control = null;
        
        if ((control = ((Spatial) model).getControl(SkeletonControl.class)) != null) {
            return control;
        }
        
        for (int i = 0; i < model.getChildren().size(); i++) {
            if ((control = model.getChild(i).getControl(SkeletonControl.class)) != null) {
                return control;
            } else {
                control = GameWorld.getSkeletonControl((Node) model.getChild(i));
                
                if (control != null) {
                    return control;
                }
            }
        }
        
        return control;
    }
    
    /**
     * getAnimCOntrol - Recursive search to find an animation controller of the 
     * required model.
     * @param model - Model to be searched.
     * @return AnimControl of model if found.
     */
    public static AnimControl getAnimationControl(Node model) {
        AnimControl control = null;
        
        if ((control = ((Spatial) model).getControl(AnimControl.class)) != null) {
            return control;
        }
        
        for (int i = 0; i < model.getChildren().size(); i++) {
            if ((control = model.getChild(i).getControl(AnimControl.class)) != null) {
                return control;
            } else {
                control = GameWorld.getAnimationControl((Node) model.getChild(i));
                
                if (control != null) {
                    return control;
                }
            }
        }
        
        return control;
    }
    
    /**
     * getLookAt - Generates a random direction for a mob to look at, at spawn,
     * respawn and aggro loss.
     * @return lookAt - Vector3f to set lookAt of model.
     */
    public static Vector3f getLookAt() {
        Vector3f lookAt = new Vector3f();
        
        lookAt.y = 0.0f;
        lookAt.x = getNextGaussian(1000);
        lookAt.z = getNextGaussian(1000);
        
        return lookAt;
    }
    
    /**
     * getNextGaussian - Generates a random value from Random.
     * @param range - The range to create a value within.
     * @return random number - float.
     */
    public static float getNextGaussian(int range) {
        return (float) rand.nextGaussian() * range;
    }
    
    /**
     * updateAggro - During mob concurrent update each mob updates its own aggro
     * state to set overall aggro to true is so.
     * @param agg - Mob aggro state.
     */
    public static synchronized void updateAggro(boolean agg) {
        if (agg) {
            aggro = agg;
        }
    }
    
    /**
     * removeAggro - Player removes aggro after update, mobs update after.
     */
    public static void removeAggro(){
        aggro = false;
    }
    
    /**
     * setAlive - Player updates alive or death at each update.
     * @param alv - Players alive.
     */
    public static void setAlive(boolean alv) {
        alive = alv;
    }
    
    /**
     * setPlayerPosition - Player updates its current position at each udpate.
     * @param vec - Player position.
     */
    public static void setPlayerPosition(Vector3f vec) {
        playerPosition = vec;
    }
}
