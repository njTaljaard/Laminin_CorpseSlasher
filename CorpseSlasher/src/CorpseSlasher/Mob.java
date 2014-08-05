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
        
        animControl = new MobAnimControl();
        collControl = new MobCollisionControl();
        
        initMob(assMan);
        initControl();
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
    }
    
    /**
     * assembleMob add the controllers to the mob and to the physics handler.
     * @param bullet - BulletAppState physics controller. 
     */
    private void assembleMob(BulletAppState bullet) {
        mob.addControl(collControl.getAggroGhost());
        mob.addControl(characterControl);
        bullet.getPhysicsSpace().addCollisionListener(collControl);
        bullet.getPhysicsSpace().add(collControl.getAggroGhost());
        bullet.getPhysicsSpace().add(collControl.getAttackGhost());
        bullet.getPhysicsSpace().add(characterControl);
        bullet.getPhysicsSpace().addAll(mob);         
        mob.getChild("bennettzombie_body.001-ogremesh").getControl(SkeletonControl.class)
                .getAttachmentsNode("hand.R").addControl(collControl.getAttackGhost());
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
    public String updateMob(Vector3f point, boolean hit) {
        collControl.updateMobPhase(point, mob, characterControl, passivePosition);
        animControl.updateMobAnimations(channel, collControl.aggro,
                collControl.walkAttack, collControl.attack, collControl.passive);
        
        if (hit) {
            System.out.println(mobName + " i have been hit!!!!");
        }
        
        if (animControl.attacking && collControl.attackLanded) {
            animControl.attacking = collControl.attackLanded = false;
            return mobName;
        } else {
            return "";
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
