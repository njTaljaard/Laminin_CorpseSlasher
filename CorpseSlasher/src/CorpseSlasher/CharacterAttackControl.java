/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CorpseSlasher;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import java.util.ArrayList;

/**
 *
 * @author Nico
 */
public class CharacterAttackControl implements PhysicsCollisionListener {
    
    private ArrayList<String> hitMobs;

    public CharacterAttackControl() {
        hitMobs = new ArrayList<>();
    }
    
    @Override
    public void collision(PhysicsCollisionEvent event) {
        if (event.getNodeA() != null && event.getNodeB() != null) {
            if (event.getNodeB().getName().contains("mob") && 
                    event.getNodeA().getName().contains("Sword")) {
                hitMobs.add(event.getNodeB().getName());
            }
        }
    }
    
    public int getHitsSize() {
        return hitMobs.size();
    }
    
    public void attacksProcessed() {
        hitMobs.clear();
    }
    
    public ArrayList<String> mobsHit() {
        return hitMobs;
    }
}
