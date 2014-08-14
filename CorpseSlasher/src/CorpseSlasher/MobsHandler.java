package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
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
     * @param bullet - BulletAppState so be able to add model and collision to 
     * the physics domain.
     * @param assMan - AssetManager to load model into engine.
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
        positions.add(new Vector3f(180.0f, 33.0f, -200.0f));
    }
    
    /**
     * createMobs creates each mob and adds it to the list of mobs as well as the
     * scene node.
     * @param bullet - BulletAppState so be able to add model and collision to 
     * the physics domain.
     * @param assMan - AssetManager to load model into engine.
     */
    private void createMobs(BulletAppState bullet, AssetManager assMan) {    
        int size = positions.size();
        
        for (int i = 0; i < size; i++) {
            mobs.add(new Mob(positions.get(i), bullet, assMan, "mob"+(i+10)));
            mobNode.attachChild(mobs.get(i).retrieveMob());
        }
    }
    
    /**
     * updateMobs will update each of the mobs individually.
     * @param point - Vector3f with is the direction towards the player.
     */
    public ArrayList<String> updateMobs(BulletAppState bullet, Vector3f point, 
            ArrayList<String> playerHits, ArrayList<Integer> mobHits, float tpf) {
        ArrayList<String> landedAttacks = new ArrayList<>();
        String name;
        boolean playerHit, mobHit = false;
        
        for (Mob mob : mobs) {            
            if (playerHits.contains(mob.mobName)) {
                playerHit = true;
            } else {
                playerHit = false;
            }
            
            if (mobHits.contains(mob.handColliosionGroup)) {
                mobHit = true;
            } else {
                mobHit = false;
            }
            
            name = mob.updateMob(bullet, point, playerHit, mobHit, tpf);
                
            if (!name.equals("")) 
                landedAttacks.add(name);
        }
        
        return landedAttacks;
    }
    
    /**
     * retrieveMobs to attach all mobs to rootNode.
     * @return mobNode - Node contains all the mobs in the scene.
     */
    public Node retrieveMobs() {
        return mobNode;
    }
}
