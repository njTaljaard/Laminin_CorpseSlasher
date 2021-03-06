package CorpseSlasher;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SkeletonControl;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import java.util.List;

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
    private AssetManager assetManager;
    private BulletAppState bullet;
    private AnimChannel channel;
    private AnimControl control;
    private Vector3f passivePosition;
    private MobAnimControl animControl;
    private MobMotionControl motionControl;
    private BetterCharacterControl characterControl;
    private ModelRagdoll ragdoll;
    private GhostControl attackGhost;
    private Vector3f point;
    private float health, eighth_pi, tpf;
    private boolean alive, mobHit, playerHit;
    private long deathTime, spawnTime, regenTime, regenInterval;
    
    /**
     * Mob creates a basic mob the required functionality.
     * @param position - Vector3f the position to place the mob at.
     * @param bullet - BulletAppState to add controllers to physics space.
     * @param assMan - AssetManager to load required model.
     * @param mName - String that defines the name assosiated to this mob required
     * for collision detection.
     */
    public Mob(Vector3f position, BulletAppState bullet, AssetManager assetManager, 
            String mName) {
        mobName = mName;
        this.assetManager = assetManager;
        this.bullet = bullet;
        handColliosionGroup = Integer.parseInt(mobName.substring(3));
        passivePosition = position;
        alive = true;
        health = 100;
        eighth_pi = FastMath.PI * 0.125f;
        attackLanded = "";
        this.spawnTime = new Long("10000000000");
        this.regenInterval = new Long("2000000000");
        
        createMob();
    }
    
    /**
     * createMob will create all the sub sections of the mob and assemble it.
     */
    private void createMob() {
        animControl = new MobAnimControl();
        motionControl = new MobMotionControl();
        
        initMob();
        initControl();
        //initRagdoll();
        initAttackGhost();
        assembleMob();
        initAnim();
    }
    
    /**
     * initMob will load the required asset, set it to the required position and
     * name it acordingly.
     */
    private void initMob() {
        mob = (Node) assetManager.loadModel("Models/ZombieMobile/bunnett.j3o");        
        mob.setLocalTranslation(passivePosition);
        mob.setName(mobName);
    }
    
    /**
     * initControl creates the character controller responsible for collision,
     * motion and forces control.
     */
    private void initControl() {
        characterControl = new BetterCharacterControl(0.2f, 0.85f, 13.5f);
        characterControl.setGravity(new Vector3f(0, -200, 0));
        characterControl.setJumpForce(new Vector3f(0, 4, 0));
        characterControl.setApplyPhysicsLocal(true);
        characterControl.setEnabled(true);
    }
    
    /**
     * initRagdoll will create the ragdoll required for death animation and assign
     * the required limbs.
     */
    private void initRagdoll() {
        ragdoll = new ModelRagdoll(0.01f, "bennettzombie_body.001-ogremesh");
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
    }
    
    /**
     * initHandGhost sets up the collision box that will be bound to the mobs
     * arm in order to determine if any collision has occured with the hand
     * and the player.
     */
    private void initAttackGhost() {
        attackGhost = new GhostControl(new BoxCollisionShape(new Vector3f(0.15f, 0.45f, 0.05f)));
        attackGhost.setCollisionGroup(handColliosionGroup);
        attackGhost.setCollideWithGroups(1);
    }

    /**
     * assembleMob add the controllers to the mob and to the physics handler.
     */
    private void assembleMob() {
        mob.addControl(motionControl.getAggroGhost());
        mob.addControl(characterControl);
        mob.getChild("bennettzombie_body.001-ogremesh").getControl(SkeletonControl.class)
                .getAttachmentsNode("hand.R").addControl(attackGhost);
        bullet.getPhysicsSpace().add(characterControl);
        bullet.getPhysicsSpace().add(attackGhost);
        bullet.getPhysicsSpace().add(motionControl.getAggroGhost());
        bullet.getPhysicsSpace().addAll(mob);  
    }
    
    /**
     * initAnim creates the controller and animations channel required to
     * access all available animations, set the current animation and the type
     * of trigger at the end of a animations cycle.
     */
    private void initAnim() {
        control = mob.getChild("bennettzombie_body.001-ogremesh").getControl(AnimControl.class);
        control.addListener(animControl.getAnimationListener());
        channel = control.createChannel();
        channel.setAnim("Stand");
        channel.setLoopMode(LoopMode.Cycle); 
    }
    
    /**
     * 
     * @param point - Vector3f the direction of the player required in 
     * the attack phase to move the mobs towards the player.
     * @param playerHit - boolean if the player hit this mob.
     * @param mobHit - boolean if mob has attacked player.
     * @param tpf - Time per frame required for updating ragdoll. 
     */
    public void set(Vector3f point, boolean playerHit, boolean mobHit, float tpf) {
        this.point = point;
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
            characterControl.update(tpf);
            motionControl.updateMobPhase(point, mob, characterControl, passivePosition);
            
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
                //System.out.println(mobName + " i have been hit!!!! My health is " + health);
                damageAudio = true;
                
                if (health <= 0) {
                    health = 0;
                    alive = false;
                    deathTime = System.nanoTime();
                    motionControl.death(characterControl);
                    //System.out.println("You killed : " + mobName);
                    swapControllers = true;
                    return;
                }
            } else {
                damageAudio = false;
            }
            
            if (!motionControl.aggro && regenTime == new Long("0")) {
                regenTime = System.nanoTime();
            } else if (System.nanoTime() - regenTime > regenInterval && health != 100 && !motionControl.aggro) {
                health += 5;
                regenTime = new Long("0");
                //System.out.println("Regen time, health is : " + health);
            }

            if (animControl.attacking && mobHit) {
                animControl.attacking = false;
                attackLanded = mobName;
            }
        } else {
            ragdoll.update(tpf);
            if (System.nanoTime() - deathTime > spawnTime) {
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
        walkAudio = false;
        attackAudio = false;
        damageAudio = false;
        swapControllers = false;
        
        if (alive) { //Swap to character control
            mob.setLocalTranslation(passivePosition);
            ragdoll.setKinematicMode();
            bullet.getPhysicsSpace().removeAll(mob);
            
            ragdoll.setEnabled(false);
            mob.removeControl(ModelRagdoll.class);
            
            characterControl.setEnabled(true);
            motionControl.getAggroGhost().setEnabled(true);
            attackGhost.setEnabled(true);
            
            assembleMob();
        } else { //Swap to ragdoll control
            characterControl.setEnabled(false);
            motionControl.getAggroGhost().setEnabled(false);
            attackGhost.setEnabled(false);
            ragdoll.setEnabled(true);
            
            mob.removeControl(BetterCharacterControl.class);
            mob.removeControl(GhostControl.class);
            mob.addControl(ragdoll);
            
            ragdoll.setJointLimit("hips", eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi);
            ragdoll.setJointLimit("spine", eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi);
            ragdoll.setJointLimit("chest", eighth_pi, eighth_pi, 0, 0, eighth_pi, eighth_pi); 
            bullet.getPhysicsSpace().add(ragdoll);
            ragdoll.setRagdollMode();            
        }
    }
    
    /**
     * 
     * @return 
     */
    public Vector3f getPosition() {
        return mob.getLocalTranslation();
    }
    
    /**
     * 
     * @return 
     */
    public boolean getAggro() {
        return motionControl.aggro;
    }
    
    /**
     * isAlive to determine if the mob is alive.
     * @return alive state.
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
