package CorpseSlasher;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Mob is a single instance of a mob to be added to the scene.
 */
public class Mob extends Thread {
    
    protected String mobName, attackLanded;
    protected int handColliosionGroup;
    protected boolean swapControllers, attackAudio, walkAudio, damageAudio;
    private Node mob;
    private AnimChannel channel;
    private AnimControl control;
    private Vector3f passivePosition;
    private MobAnimControl animControl;
    private MobMotionControl motionControl;
    private BetterCharacterControl characterControl;
    private ModelRagdoll ragdoll;
    private GhostControl attackGhost;
    private float health, tpf;
    private boolean alive, mobHit, playerHit;
    private int deathTime, regenTime;
    
    /**
     * Mob - creates a basic mob the required functionality.
     * @param position - Vector3f the position to place the mob at.
     * @param mName - String that defines the name assosiated to this mob required
     * for collision detection.
     */
    public Mob(Vector3f position, String mName) {
        mobName = mName;
        handColliosionGroup = Integer.parseInt(mobName.substring(3));
        passivePosition = position;
        alive = true;
        health = 100;
        attackLanded = "";
        
        createMob();
    }
    
    /**
     * createMob - will create all the sub sections of the mob and assemble it.
     */
    private void createMob() {
        animControl = new MobAnimControl();
        motionControl = new MobMotionControl();
        
        initMob();
        initControl();
        initRagdoll();
        initAttackGhost();
        assembleMob();
        initAnim();
    }
    
    /**
     * initMob - will load the required asset, set it to the required position and
     * name it acordingly.
     */
    private void initMob() {
        mob = (Node) GameWorld.assetManager.loadModel("Models/Zombie/bunnett.j3o");
        
        if (mob != null) {
            mob.setLocalTranslation(passivePosition);
            mob.setName(mobName);
        } else {
            ExceptionHandler.throwError("Model was not loaded succesfully.", "Mob - Mob");
        }
    }
    
    /**
     * initControl - creates the character controller responsible for collision,
     * motion and forces control.
     */
    private void initControl() {
        characterControl = new BetterCharacterControl(0.45f, 5.0f, 1);
        
        if (characterControl != null) {
            characterControl.setGravity(new Vector3f(0, -800, 0));
            characterControl.setJumpForce(new Vector3f(0, 4, 0));
            characterControl.setApplyPhysicsLocal(true);
            characterControl.setEnabled(true);
            characterControl.setViewDirection(GameWorld.getLookAt());
        } else {
            ExceptionHandler.throwError("BetterCharacterControl not initialized succesfully.", "Mob - Control");
        }
    }
    
    /**
     * initRagdoll - will create the ragdoll required for death animation and assign
     * the required limbs.
     */
    private void initRagdoll() {
        ragdoll = new ModelRagdoll(0.01f, "bennettzombie_body.001-ogremesh");
        
        if (ragdoll != null) {
            ragdoll.addBoneName("hips");
            ragdoll.addBoneName("spine");
            ragdoll.addBoneName("chest");
            ragdoll.addBoneName("neck");
            ragdoll.addBoneName("head");
            ragdoll.addBoneName("shoulder.L");
            ragdoll.addBoneName("shoulder.R");
            ragdoll.addBoneName("upper_arm.L");
            ragdoll.addBoneName("upper_arm.R");
            ragdoll.addBoneName("forearm.L");
            ragdoll.addBoneName("forearm.R");
            ragdoll.addBoneName("hand.L");
            ragdoll.addBoneName("hand.R");
            ragdoll.addBoneName("thigh.L");
            ragdoll.addBoneName("thigh.R");
            ragdoll.addBoneName("shin.L");
            ragdoll.addBoneName("shin.R");
            ragdoll.addBoneName("foot.L");
            ragdoll.addBoneName("foot.R");
            ragdoll.setEnabled(false);
            ragdoll.addCollisionListener(ragdoll);
        } else {
            ExceptionHandler.throwError("Ragdoll not initialized succesfully.", "Mob - Ragdoll");
        }
    }
    
    /**
     * initHandGhost - sets up the collision box that will be bound to the mobs
     * arm in order to determine if any collision has occured with the hand
     * and the player.
     */
    private void initAttackGhost() {
        attackGhost = new GhostControl(new BoxCollisionShape(new Vector3f(0.15f, 0.45f, 0.05f)));
        
        if (attackGhost != null) {
            attackGhost.setCollisionGroup(handColliosionGroup);
            attackGhost.setCollideWithGroups(1);
        } else {
            ExceptionHandler.throwError("Attack ghost not created succesfully.", "Mob - AttackGhost");
        }
    }

    /**
     * assembleMob - add the controllers to the mob and to the physics handler.
     */
    private void assembleMob() {
        mob.addControl(motionControl.getAggroGhost());
        mob.addControl(characterControl);
        GameWorld.getSkeletonControl(mob).getAttachmentsNode("hand.R").addControl(attackGhost);
        GameWorld.bullet.getPhysicsSpace().add(characterControl);
        GameWorld.bullet.getPhysicsSpace().add(attackGhost);
        GameWorld.bullet.getPhysicsSpace().add(motionControl.getAggroGhost());
        GameWorld.bullet.getPhysicsSpace().addAll(mob);  
    }
    
    /**
     * initAnim - creates the controller and animations channel required to
     * access all available animations, set the current animation and the type
     * of trigger at the end of a animations cycle.
     */
    private void initAnim() {
        control = GameWorld.getAnimationControl(mob);
        
        if (control != null) {
            control.addListener(animControl.getAnimationListener());
        } else {
            ExceptionHandler.throwInformation("AnimControl could not be found within model.", "Mob - Anim");
        }
        
        channel = control.createChannel();
        
        if (channel != null) {
            channel.setAnim("Stand");
            channel.setLoopMode(LoopMode.Cycle); 
        } else {
            ExceptionHandler.throwError("AnimChannel could not be created from AnimControl.", "Mob - Anim");
        }
    }
    
    /**
     * set - From MobsHandler these values are set before the mob thread is sent
     * of for update.
     * @param point - Vector3f the direction of the player required in 
     * the attack phase to move the mobs towards the player.
     * @param playerHit - boolean if the player hit this mob.
     * @param mobHit - boolean if mob has attacked player.
     * @param tpf - Time per frame required for updating ragdoll. 
     */
    public void set(boolean playerHit, boolean mobHit, float tpf) {
        this.playerHit = playerHit;
        this.mobHit = mobHit;
        this.tpf = tpf;
    }
      
    /**
     * updateMob will update the mobs phase according to if aggro was triggers 
     * and animations that is required for that phase.
     */
    @Override
    public void run() {
        if (alive) {
            if (!GameWorld.alive) {
                motionControl.aggro = false;
            }
            
            GameWorld.updateAggro(motionControl.aggro);
            characterControl.update(tpf);
            motionControl.updateMobPhase(characterControl, passivePosition, mob.getLocalTranslation());
            
            if (motionControl.walk) {
                walkAudio = true;
            } else {
                walkAudio = false;
            }
            
            attackAudio = animControl.updateMobAnimations(channel, motionControl.aggro,
                    motionControl.walkAttack, motionControl.attack, 
                    motionControl.passive, mob.getLocalTranslation());
            
            if (playerHit) {
                health -= 15;
                damageAudio = true;
                
                if (health <= 0) {
                    health = 0;
                    alive = false;
                    deathTime = GameWorld.systemTime;
                    motionControl.death(characterControl);
                    swapControllers = true;
                    
                    if (ClientConnection.getUserType().equals("oauth")) {
                        ClientConnection.AddOAuthUser();
                    } else if (ClientConnection.getUserType().equals("costume")) {
                        ClientConnection.AddOneKill();
                    }
                    
                    return;
                }
            } else {
                damageAudio = false;
            }
            
            if (!motionControl.aggro && regenTime == 0) {
                regenTime = GameWorld.systemTime;
            } else if (GameWorld.systemTime - regenTime > GameWorld.mobRegenInterval && health != 100 && !motionControl.aggro) {
                health += 5;
                regenTime = 0;
            }

            if (animControl.attacking && mobHit) {
                animControl.attacking = false;
                attackLanded = mobName;
            }
        } else {
            ragdoll.update(tpf);
            if (GameWorld.systemTime - deathTime > GameWorld.mobRespawnTimer) {
                alive = true;
                health = 100;
                swapControllers = true;
            }
        }
    }
    
    /**
     * swapControllers will swap between controllers from BetterCharacterControl
     * will ghost boxes for alive and ragdoll for death.
     */
    protected void swapControllers() {
        try {
            walkAudio = false;
            attackAudio = false;
            damageAudio = false;
            swapControllers = false;

            if (alive) { //Swap to character control
                mob.setLocalTranslation(passivePosition);
                ragdoll.setKinematicMode();
                GameWorld.bullet.getPhysicsSpace().removeAll(mob);

                ragdoll.setEnabled(false);
                mob.removeControl(ModelRagdoll.class);

                characterControl.setEnabled(true);
                characterControl.setViewDirection(GameWorld.getLookAt());
                motionControl.getAggroGhost().setEnabled(true);
                attackGhost.setEnabled(true);

                assembleMob();
            } else { //Swap to ragdoll control
                GUI.UserInterfaceManager.guiNode.detachChildNamed("aggroImage");
                characterControl.setEnabled(false);
                motionControl.getAggroGhost().setEnabled(false);
                attackGhost.setEnabled(false);
                ragdoll.setEnabled(true);

                mob.removeControl(BetterCharacterControl.class);
                mob.removeControl(GhostControl.class);
                mob.addControl(ragdoll);

                ragdoll.setJointLimit("hips", GameWorld.eighth_pi, GameWorld.eighth_pi, GameWorld.eighth_pi, GameWorld.eighth_pi, GameWorld.eighth_pi, GameWorld.eighth_pi);
                ragdoll.setJointLimit("spine", GameWorld.eighth_pi, GameWorld.eighth_pi, GameWorld.eighth_pi, GameWorld.eighth_pi, GameWorld.eighth_pi, GameWorld.eighth_pi);
                ragdoll.setJointLimit("chest", GameWorld.eighth_pi, GameWorld.eighth_pi, 0, 0, GameWorld.eighth_pi, GameWorld.eighth_pi); 
                GameWorld.bullet.getPhysicsSpace().add(ragdoll);
                ragdoll.setRagdollMode();            
            }
        } catch (Exception e) {
            ExceptionHandler.throwInformation("An unknown problem existed while swapping controllers.", "Mob - SwapControllers");
        }
    }
    
    /**
     * getPosition - Accessor to mobs current position.
     * @return Vector3f mob position.
     */
    public Vector3f getPosition() {
        return mob.getLocalTranslation();
    }
    
    /**
     * alive - Accessor to mobs alive state.
     * @return boolean of mobs alive state.
     */
    public boolean alive() {
        return alive;
    }
        
    /**
     * retrieveMob to access the node containing the single mob.
     * @return mob - Node this mob.
     */
    public Node retrieveMob() {
        return mob;
    }
}
