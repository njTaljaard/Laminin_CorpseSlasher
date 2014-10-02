/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CorpseSlasher;

import com.jme3.animation.AnimControl;
import com.jme3.animation.SkeletonControl;
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
    private static boolean aggro;
    private static float playerMovementSpeed;
    private static float playerCamaraXSpeed;
    private static float playerCameraYSpeed;
    private static float mobMovementSpeed;
    private static float mobRunSpeed;
    
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
        System.out.println(model.getName());
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
    
    public static synchronized void updateAggro(boolean aggro) {
        GameWorld.aggro = aggro;
    }
    
    
}
