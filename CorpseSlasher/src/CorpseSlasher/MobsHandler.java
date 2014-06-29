package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * MobHandler controls create and access all mobs through a single channel to
 * be able to handle the updates required. Including the aggro detection & control,
 * motion and animations.
 */
public class MobsHandler {
    
    private Node mobNode;
    private ArrayList<Mob> mobs;
    private ArrayList<Vector3f> positions;
    
    /**
     * MobHandler will be in control of create mobs at the predefined positions.
     * @param inMan
     * @param bullet
     * @param assMan 
     */
    public MobsHandler(BulletAppState bullet, AssetManager assMan) {
        mobNode = new Node("Mobs");
        mobs = new ArrayList<>();
        positions = new ArrayList<>();
        initPositions();
        createMobs(bullet, assMan);
    }
    
    /**
     * initPositions initialize a list of positions where mobs are to be spawned.
     */
    private void initPositions() {
        positions.add(new Vector3f(180.0f, 45.0f, -200.0f));
    }
    
    /**
     * createMobs creates each mob and adds it to the list of mobs as well as the
     * scene node.
     * @param inMan
     * @param bullet
     * @param assMan 
     */
    private void createMobs(BulletAppState bullet, AssetManager assMan) {    
        int size = positions.size();
        for (int i = 0; i < size; i++) {
            mobs.add(new Mob(positions.get(i), bullet, assMan, "mob"+i));
            mobNode.attachChild(mobs.get(i).retrieveMob());
        }
    }
    
    /**
     * updateMobs will update each of the mobs individually.
     * @param attackDirection - Vector3f with is the direction towards the player.
     */
    public void updateMobs(Vector3f attackDirection) {
        attackDirection = attackDirection.negate();
        attackDirection.y = 0.0f;
        for (Mob mob : mobs) {
            mob.updateMob(attackDirection);
        }
    }
    
    /**
     * retrieveMobs to attach all mobs to rootNode.
     * @return mobNode - Node contains all the mobs in the scene.
     */
    public Node retrieveMobs() {
        return mobNode;
    }
}
