package CorpseSlasher;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;

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
    private AnimChannel channel;
    private AnimControl control;
    private Vector3f motionDirection;
    private Vector3f passivePosition;
    private BetterCharacterControl characterControl;
    private AnimEventListener animationListener;
    private final float runSpeed = 8.0f;
    private final float walkSpeed = 6.0f;
    private boolean walk;
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
        walk = false;
        attack = false;
        passive = true;
        passivePosition = position;
        motionDirection = new Vector3f();
        
        initMob(assMan);
        initControl();
        initGhost();
        assembleMob(bullet);
        
        initAnimEventListener();
        initAnim();
    }
    
    /**
     * initMob will load the required asset, set it to the required position and
     * name it acordingly.
     * @param assMan - AssetManager required to load the model.
     */
    private void initMob(AssetManager assMan) {
        mob = (Node) assMan.loadModel("Models/bunnett/bunnett_backup2.j3o");
        mob.setLocalTranslation(passivePosition);
        mob.setName(mobName);
    }
    
    /**
     * initControl creates the character controller responsible for collision,
     * motion and forces control.
     */
    private void initControl() {
        characterControl = new BetterCharacterControl(1.0f, 4, 50);
        characterControl.setGravity(new Vector3f(0, -800, 0));
        characterControl.setJumpForce(new Vector3f(0, 4, 0));
        characterControl.setApplyPhysicsLocal(true);
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
     * 
     */
    private void initAnim() {
        control = mob.getChild("bennettzombie_body.001").getControl(AnimControl.class);
        control.addListener(animationListener);
        channel = control.createChannel();
        for (String s : control.getAnimationNames())
            System.out.println(s);
        channel.setAnim("metarigAction");
        channel.setLoopMode(LoopMode.Cycle); 
    }
    
    /**
     * initAminEventListener will trigger on animation change as well as when a 
     * cycle of an animations if completed to run it again.
     */
    private void initAnimEventListener() {
        animationListener = new AnimEventListener() {
            @Override
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                /*switch (animName) {
                    case "Walk":
                        if (channel.getLoopMode().equals(LoopMode.Loop)) {
                            channel.setAnim("Walk", 0.0f);
                            channel.setLoopMode(LoopMode.Loop);
                            channel.setSpeed(1.0f);
                        } else {
                            channel.setAnim("Stand", 0.0f);
                            channel.setLoopMode(LoopMode.Loop);
                        }
                        break;                    
                    case "Stand":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1.0f);
                        break;                        
                    case "Attack":
                        channel.setAnim("Stand", 0.0f);
                        channel.setLoopMode(LoopMode.Loop);
                        channel.setSpeed(1.0f);
                        break;
                    case "Passive":
                        
                        break;
                }*/
            }

            @Override
            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {}
        };
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
                walk = true;
                passive = false;
            }
        } else if (event.getNodeB().getName().equals("Player") & event.getNodeA().getName().equals(mobName)) {
            if (passive) {
                walk = true;
                passive = false;
            }
        }
    }
    
    /**
     * updateMob will update the mobs phase according to if aggro was triggers 
     * and animations that is required for that phase.
     * @param attackDirection - Vector3f the direction of the player required in 
     * the attack phase to move the mobs towards the player.
     */
    public void updateMob(Vector3f point) {
        updateMobPhase(point);
        //updateMobAnimations();
    }
    
    /**
     * updateMobPhase will determine in which phase the mob is: attack, return, passive.
     * Update the mobs actions to its position and animation.
     * @param attackDirection - Vector3f the direction of the player required in 
     * the attack phase to move the mobs towards the player.
     */
    private void updateMobPhase(Vector3f point) {
        if (walk) {
            point.subtract(mob.getLocalTranslation(), motionDirection);
            motionDirection.y = 0.0f;
            
            characterControl.setViewDirection(motionDirection.normalize().multLocal(runSpeed));
            characterControl.setWalkDirection(motionDirection.normalize().multLocal(runSpeed)); 
            
            if (passivePosition.distance(mob.getLocalTranslation()) > 25.0f) {
                walk = false;
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
                    //set animation channel to passive animation. */
                }
            }
        }
    }
    
    /**
     * updateCharacterAnimations updates animations where required.
     */
    public boolean updateMobAnimations() {        
        if (attack) {
            if (channel.getAnimationName().equals("Stand")) {
                channel.setAnim("Attack");
                channel.setLoopMode(LoopMode.DontLoop);
                channel.setSpeed(1.15f);
            } else {
                if (!channel.getAnimationName().equals("Attack")) {
                    channel.setAnim("Slash", 1.0f);
                    channel.setLoopMode(LoopMode.DontLoop);
                    channel.setSpeed(1.15f);
                }
            }
            attack = false;
        }
        
        if (walk) {
            if (channel.getAnimationName().equals("Stand")) {
                channel.setAnim("Walk");
                channel.setLoopMode(LoopMode.Loop);
                channel.setSpeed(1.5f);
            }
        } else {
            if (!channel.getAnimationName().equals("Attack")) {
                channel.setAnim("Stand");
                channel.setLoopMode(LoopMode.Cycle);                    
            }
        }
        
        return attack;
    }
    
    /**
     * retrieveMob to access the node containing the single mob.
     * @return mob - Node this mob.
     */
    public Node retrieveMob() {
        return mob;
    }
}
