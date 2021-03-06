package CorpseSlasher;

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
public class MobMotionControl {
    
    protected boolean aggro, walkAttack, attack, passive, walk;
    private final float runSpeed = 8.0f;
    private final float walkSpeed = 6.0f;
    private Vector3f motionDirection;
    private GhostControl aggroGhost;
    protected boolean alive = true;
    
    public MobMotionControl() {
        passive = true;
        aggro = false;
        walkAttack = false;
        attack = false;
        walk = false;
        motionDirection = new Vector3f();
        initAggroGhost();
    }
    
    /**
     * initGhost creates the GhostControl collision sphere that will double as
     * the player detection bounds for changing to attack phase. Also sets the
     * collision group and the group it does collid with.
     */
    private void initAggroGhost() {
        aggroGhost = new GhostControl(new SphereCollisionShape(2.75f));
        aggroGhost.setCollisionGroup(6);
        aggroGhost.setCollideWithGroups(8);
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

            if (mob.getLocalTranslation().distance(point) > 1.25f) {
                characterControl.setViewDirection(motionDirection.normalize().multLocal(runSpeed).negate());
                characterControl.setWalkDirection(motionDirection.normalize().multLocal(runSpeed)); 
            }

            if (mob.getLocalTranslation().distance(point) < 1.0f) {
                attack = true;
                walkAttack = false;
                walk = false;
            } else if (mob.getLocalTranslation().distance(point) < 2.0f) {
                walkAttack = true;
                walk = true;
            } else if (passivePosition.distance(mob.getLocalTranslation()) > 6.25f) {
                aggro = false;
                attack = false;
                walkAttack = false;
                walk = false;
            } else {
                attack = false;
                walkAttack = false;
                walk = true;
            }
        } else { 
            if (!passive) {
                for (int i = 0; i < aggroGhost.getOverlappingObjects().size(); i++) {
                    if (aggroGhost.getOverlapping(i).getCollisionGroup() == 8 &&
                            passivePosition.distance(mob.getLocalTranslation()) < 5.0f) {
                        aggro = true;
                        passive = false;
                        return;
                    }
                }

                if (passivePosition.distance(mob.getLocalTranslation()) > 0.75f) {
                  passivePosition.subtract(mob.getLocalTranslation(), motionDirection);
                  motionDirection.y = 0.0f;
                  walk = true;

                  characterControl.setViewDirection(motionDirection.normalize().multLocal(walkSpeed).negate());
                  characterControl.setWalkDirection(motionDirection.normalize().multLocal(walkSpeed));
                } else {
                    passive = true;
                    walk = false;
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
    
    /**
     * death will turn of motion control so that mob will remain dead.
     * @param characterControl - BetterCharacterControl of mob.
     */
    public void death(BetterCharacterControl characterControl) {
        characterControl.setWalkDirection(new Vector3f(0,0,0));
        aggro = false;
        passive = false;
    }
        
    /**
     * getAggroGhost gives access to the aggro ghost controller.
     * @return aggoGhost - GhostCollisionBox.
     */
    public GhostControl getAggroGhost() {
        return aggroGhost;
    }
}
