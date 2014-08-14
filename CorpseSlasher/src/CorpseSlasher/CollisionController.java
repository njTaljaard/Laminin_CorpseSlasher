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
 * CollisionController
 */
public class CollisionController implements PhysicsCollisionListener {
    
    private ArrayList<String> playerHits;
    private ArrayList<Integer> mobHits;

    public CollisionController() {
        playerHits = new ArrayList<>();
        mobHits = new ArrayList<>();
    }
    
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
    
    public int getMobHitSize() {
        return mobHits.size();
    }
    
    public int getPlayerHitSize() {
        return playerHits.size();
    }
    
    public void attacksProcessed() {
        playerHits.clear();
        mobHits.clear();
    }
    
    public ArrayList<Integer> getMobHits() {
        return mobHits;
    }
    
    public ArrayList<String> getPlayerHits() {
        return playerHits;
    }
}
