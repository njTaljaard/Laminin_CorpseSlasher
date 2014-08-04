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
    
    protected ArrayList<String> hitMobs;

    public CharacterAttackControl() {
        hitMobs = new ArrayList<>();
    }
    
    @Override
    public void collision(PhysicsCollisionEvent event) {
        System.out.println("bow chicky brown cow");
        if (event.getNodeA().getName().contains("mob")) {
            hitMobs.add(event.getNodeA().getName());
        } else if (event.getNodeB().getName().contains("mob")) {
            hitMobs.add(event.getNodeB().getName());
        }
    }
    
    public void attacksProcessed() {
        hitMobs.clear();
    }
    
    public ArrayList<String> mobsHit() {
        return hitMobs;
    }
}
