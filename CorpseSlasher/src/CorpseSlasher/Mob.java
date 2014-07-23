package CorpseSlasher;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.debug.SkeletonDebugger;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * Mob is a single instance of a mob to be added to the scene.
 */
public class Mob {
    
    private Node mob;
    private String mobName;
    private GhostControl ghost;
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
        collControl = new MobCollisionControl(mName);
        bullet.getPhysicsSpace().addCollisionListener(collControl);
        
        initMob(assMan);
        initControl();
        initGhost();
        assembleMob(bullet);
        initAnim(assMan);
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
    private void initAnim(AssetManager assMan) {
        control = mob.getChild("bennettzombie_body.001-ogremesh").getControl(AnimControl.class);
        control.addListener(animControl.getAnimationListener());
        channel = control.createChannel();
        channel.setAnim("Stand");
        channel.setLoopMode(LoopMode.Cycle); 
        
        /*SkeletonDebugger skeletonDebug = new SkeletonDebugger("skeleton1", control.getSkeleton());
        Material mat = new Material(assMan, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", ColorRGBA.Green);
        mat.getAdditionalRenderState().setDepthTest(false);
        skeletonDebug.setMaterial(mat);
        mob.attachChild(skeletonDebug);*/
    }
      
    /**
     * updateMob will update the mobs phase according to if aggro was triggers 
     * and animations that is required for that phase.
     * @param attackDirection - Vector3f the direction of the player required in 
     * the attack phase to move the mobs towards the player.
     */
    public void updateMob(Vector3f point) {
        collControl.updateMobPhase(point, mob, characterControl, passivePosition);
        animControl.updateMobAnimations(channel, collControl.attack, collControl.passive);
    }
    
    /**
     * retrieveMob to access the node containing the single mob.
     * @return mob - Node this mob.
     */
    public Node retrieveMob() {
        return mob;
    }
}
