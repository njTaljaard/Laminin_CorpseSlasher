/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CorpseSlasher;

import com.jme3.animation.AnimControl;
import com.jme3.animation.SkeletonControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

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
    
    public static synchronized void updateAggro(boolean agg) {
        if (!agg) {
            aggro = agg;
        }
    }
    
    public static void setAlive(boolean alv) {
        alive = alv;
    }
    
    public static void setPlayerPosition(Vector3f vec) {
        playerPosition = vec;
    }
}
