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

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Mob is a single instance of a mob to be added to the scene.
 */
public class Mob implements PhysicsCollisionListener  {
    
    private Node mob;
    private String mobName;
    private GhostControl ghost;
    private Vector3f passivePosition;
    private BetterCharacterControl characterControl;
    private float runSpeed;
    private float walkSpeed;
    private boolean attack;
    private boolean passive;
    
    /**
     * Mob creates a basic mob the required functionality.
     * @param position - Vector3f the position to place the mob at.
     * @param bullet - BulletAppState to add controllers to physics space.
     * @param assMan - AssetManager to load required model.
     * @param mName - String that defines the name assosiated to this mob required
     * for collision detection.
     */
    public Mob(Vector3f position, BulletAppState bullet, AssetManager assMan, 
            String mName) {
        bullet.getPhysicsSpace().addCollisionListener(this);
        mobName = mName;
        attack = false;
        passive = true;
        runSpeed = 8.0f;
        walkSpeed = 4.0f;
        passivePosition = position;
        initMob(assMan);
        initControl();
        initGhost();
        assembleMob(bullet);
    }
    
    /**
     * initMob will load the required asset, set it to the required position and
     * name it acordingly.
     * @param assMan - AssetManager required to load the model.
     */
    private void initMob(AssetManager assMan) {
        mob = (Node) assMan.loadModel("Models/zom/zom.j3o");
        mob.setLocalTranslation(passivePosition);
        mob.setName(mobName);
        passivePosition.y = 0;
    }
    
    /**
     * initControl creates the character controller responsible for collision,
     * motion and forces control.
     */
    private void initControl() {
        characterControl = new BetterCharacterControl(1.0f,4, 1);
        characterControl.setGravity(new Vector3f(0, -8, 0));
        characterControl.setJumpForce(new Vector3f(0, 4, 0));
        
    }
    
    /**
     * initGhost creates the GhostControl collision sphere that will double as
     * the player detection bounds for changing to attack phase. Also sets the
     * collision group and the group it does collid with.
     */
    private void initGhost() {
        ghost = new GhostControl(new SphereCollisionShape(15f));
        ghost.setCollisionGroup(6);
        ghost.setCollideWithGroups(5);        
    }
    
    /**
     * assembleMob add the controllers to the mob and to the physics handler.
     * @param bullet - BulletAppState physics controller. 
     */
    private void assembleMob(BulletAppState bullet) {
        mob.addControl(ghost);
        mob.addControl(characterControl);
        bullet.getPhysicsSpace().add(ghost);
        bullet.getPhysicsSpace().add(characterControl);
        bullet.getPhysicsSpace().addAll(mob);        
    }

    /**
     * collision handles when a collision occurs between the ghost controller and
     * another collidable object in the scene. It will determine if the player
     * was detected and trigger to approach and attack the player.
     * @param event - PhysicsCollisionEvent containing all the data in relation
     * to the collision that has occured.
     */
    @Override
    public void collision(PhysicsCollisionEvent event) {
        if (event.getNodeA().getName().equals("Player") && event.getNodeB().getName().equals(mobName)) {
            //System.out.println("Touch A");
            if (passive) {
                attack = true;
                passive = false;
            }
        } else if (event.getNodeB().getName().equals("Player") && event.getNodeA().getName().equals(mobName)) {
            //System.out.println("Touch B");
            if (passive) {
                attack = true;
                passive = false;
            }
        }
    }
    
    /**
     * updateMob will determine in which phase the mob is: attack, return, passive.
     * Update the mobs actions to its position and animation.
     * @param attackDirection - Vector3f the direction of the player required in 
     * the attack phase to move the mobs towards the player.
     */
    public void updateMob(Vector3f attackDirection) {
        if (attack) {
            characterControl.setViewDirection(attackDirection.normalize().multLocal(runSpeed));
            characterControl.setWalkDirection(attackDirection.normalize().multLocal(runSpeed));
            
            if (Math.abs((attackDirection.x - passivePosition.x)) > 20 || Math.abs((attackDirection.z - passivePosition.z)) > 20) {
                attack = false;
            }
        } else if (!passive & characterControl.getWalkDirection().add(passivePosition.negate()) != Vector3f.ZERO) {
            System.out.println("Lose aggro go back");
            characterControl.setViewDirection(passivePosition.normalize().multLocal(walkSpeed));
            characterControl.setWalkDirection(passivePosition.normalize().multLocal(walkSpeed));
        } else {
            passive = true;
            //set animation channel to passive animation.
        }
    }
    
    /**
     * retrieveMob to access the node containing the single mob.
     * @return mob - Node this mob.
     */
    public Node retrieveMob() {
        return mob;
    }
}
