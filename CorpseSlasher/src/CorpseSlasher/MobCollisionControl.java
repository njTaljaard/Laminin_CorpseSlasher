package CorpseSlasher;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * MobMotionControl the control of motion for the mods character control.
 */
public class MobCollisionControl implements PhysicsCollisionListener {
    
    protected boolean aggro, walkAttack, attack, passive;
    private final float runSpeed = 8.0f;
    private final float walkSpeed = 6.0f;
    private Vector3f motionDirection;
    private String mobName;
    
    public MobCollisionControl(String mob) {
        mobName = mob;
        passive = true;
        aggro = false;
        walkAttack = false;
        attack = false;
        motionDirection = new Vector3f();
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
        if (event.getNodeA().getName().equals("Player") & event.getNodeB().getName().equals(mobName)) {
            if (passive) {
                aggro = true;
                passive = false;
            }
        } else if (event.getNodeB().getName().equals("Player") & event.getNodeA().getName().equals(mobName)) {
            if (passive) {
                aggro = true;
                passive = false;
            }
        }
    }
    
    /**
     * updateMobPhase will determine in which phase the mob is: attack, return, passive.
     * Update the mobs actions to its position and animation.
     * @param point - Vector3f the direction of the player required in 
     * the attack phase to move the mobs towards the player.
     * @param  mob - Mob physical object.
     * @param  characterControl - BetterCharacterControl, model controller of mob.
     * @param  passivePosition - Vector3f postion where mob spawn point is.
     */
    protected void updateMobPhase(Vector3f point, Spatial mob, BetterCharacterControl
            characterControl, Vector3f passivePosition) {
        if (aggro) {
            point.subtract(mob.getLocalTranslation(), motionDirection);
            motionDirection.y = 0.0f;
            
            if (mob.getLocalTranslation().distance(point) > 4.0f) {
                characterControl.setViewDirection(motionDirection.normalize().multLocal(runSpeed));
                characterControl.setWalkDirection(motionDirection.normalize().multLocal(runSpeed)); 
            }
            
            if (mob.getLocalTranslation().distance(point) < 4.0f) {
                attack = true;
                walkAttack = false;
            } else if (mob.getLocalTranslation().distance(point) < 8.0f) {
                walkAttack = true;
            } else if (passivePosition.distance(mob.getLocalTranslation()) > 25.0f) {
                aggro = false;
                attack = false;
                walkAttack = false;
            } else {
                attack = false;
                walkAttack = false;
            }
        } else { 
            if (!passive) {
                if (passivePosition.distance(mob.getLocalTranslation()) > 3.0f) {
                    passivePosition.subtract(mob.getLocalTranslation(), motionDirection);
                    motionDirection.y = 0.0f;
                    
                    characterControl.setViewDirection(motionDirection.normalize().multLocal(walkSpeed));
                    characterControl.setWalkDirection(motionDirection.normalize().multLocal(walkSpeed));
                } else {
                    passive = true;
                    characterControl.setViewDirection(new Vector3f(0, 0, 0));
                    characterControl.setWalkDirection(new Vector3f(0, 0, 0));
                    //set animation channel to passive animation.
                }
            } 
        }
    }
}
