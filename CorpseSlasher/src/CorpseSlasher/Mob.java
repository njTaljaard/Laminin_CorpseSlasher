package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Mob
 */
public class Mob implements PhysicsCollisionListener  {
    
    private Node mob;
    private String mobName;
    private boolean attack;
    private GhostControl ghost;
    private BetterCharacterControl characterControl;
    
    public Mob(Vector3f position, InputManager inMan, BulletAppState bullet,
            AssetManager assMan, String mName) {
        bullet.getPhysicsSpace().addCollisionListener(this);
        mobName = mName;
        attack = false;
        initGhost(position, bullet, assMan);
    }
    
    private void initGhost(Vector3f position, BulletAppState bullet,
            AssetManager assMan) {
        ghost = new GhostControl(new SphereCollisionShape(15f));
        ghost.setCollisionGroup(6);
        ghost.setCollideWithGroups(5);
        
        mob = (Node) assMan.loadModel("Models/zom/zom.j3o");
        mob.setLocalTranslation(position);
        mob.setName(mobName);
        
        characterControl = new BetterCharacterControl(1.0f,4, 1);
        characterControl.setGravity(new Vector3f(0, -8, 0));
        characterControl.setJumpForce(new Vector3f(0, 4, 0));
        
        mob.addControl(ghost);
        mob.addControl(characterControl);
        bullet.getPhysicsSpace().add(ghost);
        bullet.getPhysicsSpace().add(characterControl);
        bullet.getPhysicsSpace().addAll(mob);
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        if (event.getNodeA().getName().equals("Player") && event.getNodeB().getName().equals(mobName)) {
            //System.out.println("Touch A");
            if (!attack)
                attack = true;
        } else if (event.getNodeB().getName().equals("Player") && event.getNodeA().getName().equals(mobName)) {
            //System.out.println("Touch B");
            if (!attack)
                attack = true;
        }
    }
    
    public void updateMob(Spatial player) {
        if (attack) {
            System.out.println("Trigger attack!!!");
            //characterControl.setViewDirection(player.getControl(BetterCharacterControl.class).getViewDirection().negate());
            //characterControl.setWalkDirection(player.getControl(BetterCharacterControl.class).getWalkDirection().negate());
            
        }
    }
    
    public Node retrieveMob() {
        return mob;
    }
}
