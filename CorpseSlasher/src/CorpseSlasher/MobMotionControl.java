package CorpseSlasher;

import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * MobMotionControl - the control of motion for the mods character control.
 */
public class MobMotionControl {
    
    protected boolean aggro, walkAttack, attack, passive, walk;
    private Vector3f motionDirection;
    private GhostControl aggroGhost;
    protected boolean alive = true;
    private float distanceFromPlayer;
    private float distanceToPassive;
    
    /**
     * MobMotionControl - initializes all required variables.
     */
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
     * initGhost - creates the GhostControl collision sphere that will double as
     * the player detection bounds for changing to attack phase. Also sets the
     * collision group and the group it does collid with.
     */
    private void initAggroGhost() {
        aggroGhost = new GhostControl(new SphereCollisionShape(15f));
        
        if (aggroGhost != null) {
            aggroGhost.setCollisionGroup(6);
            aggroGhost.setCollideWithGroups(8);
        } else {
            ExceptionHandler.throwError("Aggro ghost not initialized succesfully.", "MobMotionControl - AggroGhost");
        }
    }
    
    /**
     * updateMobPhase - will determine in which phase the mob is: attack, return, passive.
     * Update the mobs actions to its position and animation
     * @param  characterControl - BetterCharacterControl, model controller of mob.
     * @param  passivePosition - Vector3f postion where mob spawn point is.
     * @param  mobPosition - Vector3f of mobs current position.
     */
    protected void updateMobPhase(BetterCharacterControl characterControl, 
            Vector3f passivePosition, Vector3f mobPosition) {
        distanceFromPlayer = mobPosition.distance(GameWorld.playerPosition);
        distanceToPassive = mobPosition.distance(passivePosition);

        if (aggro) {
            GameWorld.playerPosition.subtract(mobPosition, motionDirection);
            motionDirection.y = 0.0f;
        
            if (distanceFromPlayer > 5.0f) {
                characterControl.setViewDirection(motionDirection.normalize().multLocal(GameWorld.mobRunSpeed).negate());
                characterControl.setWalkDirection(motionDirection.normalize().multLocal(GameWorld.mobRunSpeed)); 
            }

            if (distanceFromPlayer < 4.0f) {
                attack = true;
                walkAttack = false;
                walk = false;
            } else if (distanceFromPlayer < 8.0f) {
                walkAttack = true;
                walk = true;
            } else if (distanceToPassive > 25.0f) {
                aggro = false;
                attack = false;
                walkAttack = false;
                walk = false;
                GUI.UserInterfaceManager.guiNode.detachChildNamed("aggroImage");
            } else {
                attack = false;
                walkAttack = false;
                walk = true;
            }
        } else { 
            if (!passive) {
                for (int i = 0; i < aggroGhost.getOverlappingObjects().size(); i++) {
                    if (aggroGhost.getOverlapping(i).getCollisionGroup() == 8 &&
                            distanceToPassive < 20.0f) {
                        if (!GUI.UserInterfaceManager.guiNode.hasChild(GUI.UserInterfaceManager.aggro)) {
                            GUI.UserInterfaceManager.guiNode.attachChild(GUI.UserInterfaceManager.aggro);
                        }
                        
                        aggro = true;
                        passive = false;
                        return;
                    }
                }
                
                if (distanceToPassive > 2.0f) {
                  passivePosition.subtract(mobPosition, motionDirection);
                  motionDirection.y = 0.0f;
                  walk = true;

                  characterControl.setViewDirection(motionDirection.normalize().multLocal(GameWorld.mobWalkSpeed).negate());
                  characterControl.setWalkDirection(motionDirection.normalize().multLocal(GameWorld.mobWalkSpeed));
                } else {
                    passive = true;
                    walk = false;
                    characterControl.setViewDirection(GameWorld.getLookAt());
                    characterControl.setWalkDirection(new Vector3f(0, 0, 0));
                    //set animation channel to passive animation.
                }
            } else {
                for (int i = 0; i < aggroGhost.getOverlappingObjects().size(); i++) {
                    if (aggroGhost.getOverlappingObjects().get(i).getCollisionGroup() == 8) {
                        if (!GUI.UserInterfaceManager.guiNode.hasChild(GUI.UserInterfaceManager.aggro)) {
                            GUI.UserInterfaceManager.guiNode.attachChild(GUI.UserInterfaceManager.aggro);
                        }
                        
                        aggro = true;
                        passive = false;
                    }
                }
            } 
        }
    }
    
    /**
     * death - will turn of motion control so that mob will remain dead.
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
