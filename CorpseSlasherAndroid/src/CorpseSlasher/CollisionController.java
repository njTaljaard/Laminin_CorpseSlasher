/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CorpseSlasher;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import java.util.ArrayList;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * CollisionController process the collision events regarding to attacks landed
 * by the player and mobs.
 */
public class CollisionController implements PhysicsCollisionListener {
    
    private ArrayList<String> playerHits;
    private ArrayList<Integer> mobHits;

    public CollisionController() {
        playerHits = new ArrayList<>();
        mobHits = new ArrayList<>();
    }
    
    /**
     * collisio processes every collision that occures throughout the scene.
     * @param event - PhysicsCollisionEvent
     */
    @Override
    public void collision(PhysicsCollisionEvent event) {
        
        if (event.getNodeA() != null && event.getNodeB() != null) {
            
            if (event.getNodeA().getName().contains("Sword") &&
                    event.getNodeB().getName().contains("mob")) {
                
                playerHits.add(event.getNodeB().getName());
                
            } else if (event.getNodeB().getName().contains("hand.R") &&
                    event.getNodeA().getName().equals("Player")) {
                
                mobHits.add(event.getObjectB().getCollisionGroup());
                
            }
        }
    }
    
    /**
     * getMobHitSize accessor of mob hits size.
     * @return size.
     */
    public int getMobHitSize() {
        return mobHits.size();
    }
    
    /**
     * getPlayerHitSize accessor of player hits size.
     * @return zize.
     */
    public int getPlayerHitSize() {
        return playerHits.size();
    }
    
    /**
     * attacksProcessed clears the list of mob & player hits.
     */
    public void attacksProcessed() {
        playerHits.clear();
        mobHits.clear();
    }
    
    /**
     * getMobHits accessor to which mobs has hit the player.
     * @return mobHits.
     */
    public ArrayList<Integer> getMobHits() {
        return mobHits;
    }
    
    /**
     * getPlayerHits accessor to which mobs the player has hit.
     * @return playerHits.
     */
    public ArrayList<String> getPlayerHits() {
        return playerHits;
    }
}
