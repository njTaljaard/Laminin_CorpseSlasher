package CorpseSlasher;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * MobMotionControl the control of motion for the mods character control.
 */
public class MobCollisionControl {
    
    protected boolean aggro, walkAttack, attack, passive;
    private final float runSpeed = 8.0f;
    private final float walkSpeed = 6.0f;
    private Vector3f motionDirection;
    private GhostControl aggroGhost;
    private GhostControl attackGhost;
    private boolean attackLanded;
    
    public MobCollisionControl() {
        passive = true;
        aggro = false;
        walkAttack = false;
        attack = false;
        motionDirection = new Vector3f();
        
        initAggroGhost();
        initAttackGhost();
    }
    
    /**
     * initGhost creates the GhostControl collision sphere that will double as
     * the player detection bounds for changing to attack phase. Also sets the
     * collision group and the group it does collid with.
     */
    private void initAggroGhost() {
        aggroGhost = new GhostControl(new SphereCollisionShape(15f));
        aggroGhost.setCollisionGroup(6);
        aggroGhost.setCollideWithGroups(8);
    }
    
    /**
     * initHandGhost sets up the collision box that will be bound to the mobs
     * arm in order to determine if any collision has occured with the hand
     * and the player.
     */
    private void initAttackGhost() {
        attackGhost = new GhostControl(new BoxCollisionShape(new Vector3f(0.15f, 0.45f, 0.05f)));
        attackGhost.setCollisionGroup(7);
        attackGhost.setCollideWithGroups(8);
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
            
            if (mob.getLocalTranslation().distance(point) > 4.5f) {
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
                for (int i = 0; i < aggroGhost.getOverlappingObjects().size(); i++) {
                    if (aggroGhost.getOverlapping(i).getCollisionGroup() == 8 &&
                            passivePosition.distance(mob.getLocalTranslation()) < 20.0f) {
                        aggro = true;
                        passive = false;
                        return;
                    }
                }
                
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
            } else {
                for (int i = 0; i < aggroGhost.getOverlappingObjects().size(); i++) {
                    if (aggroGhost.getOverlappingObjects().get(i).getCollisionGroup() == 8) {
                        aggro = true;
                        passive = false;
                    }
                }
            } 
        }
    }
    
    public boolean attackLanded() {
        return attackLanded;
    }
    
    public GhostControl getAggroGhost() {
        return aggroGhost;
    }
    
    public GhostControl getAttackGhost() {
        return attackGhost;
    }
}
