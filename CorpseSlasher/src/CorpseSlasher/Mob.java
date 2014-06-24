package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionGroupListener;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.HullCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Mob
 */
public class Mob implements PhysicsCollisionGroupListener {
    
    private Node mob;
    private GhostControl ghost;
    //private Vector3f walkDirection;
    private CharacterControl characterControl;
    
    public Mob(Vector3f position, InputManager inMan, BulletAppState bullet,
            AssetManager assMan) {
        bullet.getPhysicsSpace().addCollisionGroupListener(this, 2);
        //walkDirection = new Vector3f();
        initGhost(position, bullet, assMan);
    }
    
    private void initGhost(Vector3f position, BulletAppState bullet,
            AssetManager assMan) {
        //HullCollisionShape col = new HullCollisionShape((Mesh)mob.getChild("Cube-ogremesh"));
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(2.5f, 6.0f, 1);
        characterControl = new CharacterControl(capsuleShape, 1.5f);
        characterControl.setGravity(10.0f);
        characterControl.setJumpSpeed(50.0f);
        
        ghost = new GhostControl(new SphereCollisionShape(15f));
        ghost.setCollisionGroup(6);
        ghost.setCollideWithGroups(5);
        
        mob = (Node) assMan.loadModel("Models/zombie/cyborg.j3o");
        mob.setLocalTranslation(position);
        mob.addControl(ghost);
        mob.addControl(characterControl);
                
        bullet.getPhysicsSpace().add(ghost);
        bullet.getPhysicsSpace().add(characterControl);
        bullet.getPhysicsSpace().enableDebug(assMan);
    }
    
    public void updateMob() {
        
    }
    
    public Node retrieveMob() {
        return mob;
    }

    @Override
    public boolean collide(PhysicsCollisionObject nodeA, PhysicsCollisionObject nodeB) {
        System.out.println(nodeA.getCollisionGroup() + "\t" + nodeA.getCollideWithGroups() 
                + "\n\t" + nodeB.getCollisionGroup() + "\t" + nodeB.getCollideWithGroups());
        
        
        if (nodeA.getCollisionGroup() == 5 && nodeB.getCollisionGroup() == 6) {
            System.out.println("YOu touched me");
            //walkDirection = event.getNodeB().getLocalTranslation();
            return true;
        } else if (nodeB.getCollisionGroup() == 5 && nodeA.getCollisionGroup() == 6) {
            System.out.println("YOu touched me twice");
            //walkDirection = event.getNodeA().getLocalTranslation();
            return true;
        } else {}
        
        return false;
    }
    
}
