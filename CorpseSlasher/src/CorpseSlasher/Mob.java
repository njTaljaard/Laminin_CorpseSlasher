package CorpseSlasher;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SkeletonControl;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.FastMath;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Mob is a single instance of a mob to be added to the scene.
 */
public class Mob {
    
    protected String mobName;
    private Node mob;
    private AnimChannel channel;
    private AnimControl control;
    private Vector3f passivePosition;
    private MobAnimControl animControl;
    private MobCollisionControl collControl;
    private BetterCharacterControl characterControl;
    private ModelRagdoll ragdoll;
    private float health;
    private boolean alive;
    private long deathTime, spawnTime;
    
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
        mobName = mName;
        passivePosition = position;
        alive = true;
        health = 100;
        spawnTime = new Long("10000000000");
        
        animControl = new MobAnimControl();
        collControl = new MobCollisionControl();
        
        initMob(assMan);
        initControl();
        initRagdoll();
        assembleMob(bullet);
        initAnim();
    }
    
    /**
     * initMob will load the required asset, set it to the required position and
     * name it acordingly.
     * @param assMan - AssetManager required to load the model.
     */
    private void initMob(AssetManager assMan) {
        mob = (Node) assMan.loadModel("Models/Zombie/bunnett.j3o");
        mob.setLocalTranslation(passivePosition);
        mob.setName(mobName);
    }
    
    /**
     * initControl creates the character controller responsible for collision,
     * motion and forces control.
     */
    private void initControl() {
        characterControl = new BetterCharacterControl(0.45f, 5.0f, 1);
        characterControl.setGravity(new Vector3f(0, -800, 0));
        characterControl.setJumpForce(new Vector3f(0, 4, 0));
        characterControl.setApplyPhysicsLocal(true);
        characterControl.setEnabled(true);
    }
    
    private void initRagdoll() {
        float eighth_pi = FastMath.PI * 0.125f;
        ragdoll = new ModelRagdoll(0.5f, "bennettzombie_body.001-ogremesh");
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
        ragdoll.addBoneName("toe.L");
        ragdoll.addBoneName("toe.R"); 
        ragdoll.setJointLimit("hips", eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi, eighth_pi);
        ragdoll.setJointLimit("chest", eighth_pi, eighth_pi, 0, 0, eighth_pi, eighth_pi);
        ragdoll.setCcdMotionThreshold(1.0f);
        ragdoll.setCcdSweptSphereRadius(1.0f);
        ragdoll.setEnabled(false);
    }
    
    /**
     * assembleMob add the controllers to the mob and to the physics handler.
     * @param bullet - BulletAppState physics controller. 
     */
    private void assembleMob(BulletAppState bullet) {
        mob.addControl(collControl.getAggroGhost());
        mob.addControl(characterControl);
        mob.getChild("bennettzombie_body.001-ogremesh").getControl(SkeletonControl.class)
                .getAttachmentsNode("hand.R").addControl(collControl.getAttackGhost());
        bullet.getPhysicsSpace().addCollisionListener(collControl);
        bullet.getPhysicsSpace().add(characterControl);
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
     * updateMob will update the mobs phase according to if aggro was triggers 
     * and animations that is required for that phase.
     * @param point - Vector3f the direction of the player required in 
     * the attack phase to move the mobs towards the player.
     */
    public String updateMob(BulletAppState bullet, Vector3f point, boolean hit, 
            float tpf) {
        if (alive) {
            characterControl.update(tpf);
            collControl.updateMobPhase(point, mob, characterControl, passivePosition);
            animControl.updateMobAnimations(channel, collControl.aggro,
                    collControl.walkAttack, collControl.attack, collControl.passive);

            if (hit) {
                health -= 10;
                System.out.println(mobName + " i have been hit!!!! My health is " + health);
                if (health <= 0) {
                    health = 0;
                    alive = false;
                    deathTime = System.nanoTime();
                    collControl.death(characterControl);
                    System.out.println("You killed : " + mobName);
                    swapControllers(bullet);
                    return "";
                }
            }

            if (animControl.attacking && collControl.attackLanded) {
                animControl.attacking = collControl.attackLanded = false;
                return mobName;
            } else {
                return "";
            }
        } else {
            ragdoll.update(tpf);
            if (System.nanoTime() - deathTime > spawnTime) {
                respawn(bullet);
            }
            return "";
        }
    }
    
    private void swapControllers(BulletAppState bullet) {
        if (alive) { //Swap to character control
            ragdoll.setEnabled(false);
            characterControl.setEnabled(true);
            collControl.getAggroGhost().setEnabled(true);
            collControl.getAttackGhost().setEnabled(true);
            mob.removeControl(ModelRagdoll.class);
            bullet.getPhysicsSpace().remove(ragdoll);
            mob.addControl(collControl.getAggroGhost());
            mob.addControl(characterControl);
            mob.getChild("bennettzombie_body.001-ogremesh").getControl(SkeletonControl.class)
                    .getAttachmentsNode("hand.R").addControl(collControl.getAttackGhost());
            bullet.getPhysicsSpace().add(characterControl);
            bullet.getPhysicsSpace().add(collControl.getAttackGhost());
            bullet.getPhysicsSpace().add(collControl.getAggroGhost());
            bullet.getPhysicsSpace().addCollisionListener(collControl);
        } else { //Swap to ragdoll control
            characterControl.setEnabled(false);
            collControl.getAggroGhost().setEnabled(false);
            collControl.getAttackGhost().setEnabled(false);
            ragdoll.setEnabled(true);
            mob.removeControl(BetterCharacterControl.class);
            mob.removeControl(GhostControl.class);
            bullet.getPhysicsSpace().removeCollisionListener(collControl);
            bullet.getPhysicsSpace().remove(characterControl);
            bullet.getPhysicsSpace().remove(collControl.getAttackGhost());
            bullet.getPhysicsSpace().remove(collControl.getAggroGhost());
            mob.addControl(ragdoll);
            bullet.getPhysicsSpace().add(ragdoll);
            ragdoll.setRagdollMode();
        }
    }
    
    private void respawn(BulletAppState bullet) {
        alive = true;
        health = 100;
        collControl.passive = true;
        collControl.aggro = false;
        mob.setLocalTranslation(passivePosition);
        swapControllers(bullet);
    }
    
    /**
     * retrieveMob to access the node containing the single mob.
     * @return mob - Node this mob.
     */
    public Node retrieveMob() {
        return mob;
    }
}
