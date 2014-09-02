package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private AssetManager assetManager;
    private BulletAppState bullet;
    private ArrayList<Mob> mobs;
    private ArrayList<Vector3f> positions;
    private ArrayList<String> landedAttacks;
    private ThreadPoolExecutor  pool;
    private BlockingQueue<Runnable> blockingQueue;
    
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
        landedAttacks = new ArrayList<>();
        blockingQueue  = new ArrayBlockingQueue<Runnable>(50);
        pool = new ThreadPoolExecutor(8, 12, Long.MAX_VALUE, TimeUnit.SECONDS, blockingQueue);
        this.assetManager = assMan;
        this.bullet = bullet;
        
        initPositions();
        createMobs();
    }
    
    /**
     * createMobs creates each mob and adds it to the list of mobs as well as the
     * scene node.
     */
    private void createMobs() {    
        int size = positions.size();
        
        for (int i = 0; i < size; i++) {
            mobs.add(new Mob(positions.get(i), bullet, assetManager, "mob"+(i+10)));
            mobNode.attachChild(mobs.get(i).retrieveMob());
        }
    }
    
    /**
     * updateMobs will update each of the mobs individually.
     * @param point - Vector3f with is the direction towards the player.
     * @param playerHits - ArrayList containing the mob name that player has hit.
     * @param mobHits - Collision groups of mobs that have hit player.
     * @param tpf - Time per frame to update ragdoll.
     */
    public ArrayList<String> updateMobs(Vector3f point, ArrayList<String> playerHits, 
            ArrayList<Integer> mobHits, float tpf) {
        //pool = Executors.newFixedThreadPool(12);
        landedAttacks.clear();
        
        for (Mob mob : mobs) {  
            if (mob.getPosition().distance(point) < 50) {
                if (mob.alive()) {
                    if (playerHits.contains(mob.mobName)) {
                        if (mobHits.contains(mob.handColliosionGroup)) {
                            mob.set(point, true, true, tpf);
                        } else {
                            mob.set(point, true, false, tpf);
                        }
                    } else {
                        if (mobHits.contains(mob.handColliosionGroup)) {
                            mob.set(point, false, true, tpf);
                        } else {
                            mob.set(point, false, false, tpf);
                        }
                    }

                }

                pool.execute(mob);
            }
        }
        
        while (pool.getActiveCount() > 0){}
        
        for (Mob mob : mobs) {
                landedAttacks.add(mob.getAttackLanded());
        }
        
        return landedAttacks;
    }
    
    public boolean getAggroState() {
        for (Mob mob : mobs) {
            if (mob.getAggro())
                return true;
        }
        
        return false;
    }
    
    /**
     * retrieveMobs to attach all mobs to rootNode.
     * @return mobNode - Node contains all the mobs in the scene.
     */
    public Node retrieveMobs() {
        return mobNode;
    }
    
    /**
     * initPositions initialize a list of positions where mobs are to be spawned.
     */
    private void initPositions() {
        //Quad 1 ++
        positions.add(new Vector3f(180.0f, 28.0f, 200.0f)); //
        //positions.add(new Vector3f(160.0f, 27.0f, 240.0f)); //
        positions.add(new Vector3f(235.0f, 21.0f, 222.0f)); //
        //positions.add(new Vector3f(180.0f, 9.0f, 310.0f)); //
        positions.add(new Vector3f(145.0f, 14.0f, 280.0f)); //
        //positions.add(new Vector3f(195.0f, 28.0f, 165.0f)); //
        positions.add(new Vector3f(140.0f, 40.0f, 175.0f)); //
        //positions.add(new Vector3f(165.0f, 43.0f, 150.0f)); //
        positions.add(new Vector3f(115.0f, 40.0f, 215.0f)); //
        //positions.add(new Vector3f(100.0f, 45.0f, 185.0f)); //
        positions.add(new Vector3f(110.0f, 64.0f, 85.0f)); //
        //positions.add(new Vector3f(180.0f, 37.0f, 125.0f)); //
        positions.add(new Vector3f(210.0f, 34.0f, 55.0f)); //
        //positions.add(new Vector3f(165.0f, 48.0f, 75.0f)); //
        positions.add(new Vector3f(90.0f, 58.0f, 125.0f)); //
        //positions.add(new Vector3f(65.0f, 60.0f, 95.0f)); //
        positions.add(new Vector3f(95.0f, 72.0f, 25.0f)); //
        //positions.add(new Vector3f(40.0f, 52.0f, 145.0f)); //
        positions.add(new Vector3f(175.0f, 54.0f, 45.0f)); //
        //positions.add(new Vector3f(215.0f, 38.0f, 25.0f)); //
        positions.add(new Vector3f(75.0f, 72.0f, 55.0f)); //
        //positions.add(new Vector3f(125.0f, 58.0f, 95.0f)); //
        positions.add(new Vector3f(25.0f, 74.0f, 15.0f)); //
        //positions.add(new Vector3f(35.0f, 70.0f, 65.0f)); //
        positions.add(new Vector3f(15.0f, 27.0f, 265.0f)); //
        //positions.add(new Vector3f(75.0f, 11.0f, 290.0f)); //
        positions.add(new Vector3f(50.0f, 34.0f, 235.0f)); //
        //positions.add(new Vector3f(37.0f, 42.0f, 215.0f)); //
        positions.add(new Vector3f(22.0f, 55.0f, 145.0f)); //
        //positions.add(new Vector3f(41.0f, 64.0f, 95.0f)); // 
        //Quad 2 +-
        //positions.add(new Vector3f(180.0f, 33.0f, -200.0f)); //
        positions.add(new Vector3f(160.0f, 27.0f, -240.0f)); //
        //positions.add(new Vector3f(235.0f, 29.0f, -222.0f)); //
        positions.add(new Vector3f(180.0f, 12.0f, -310.0f)); //
        //positions.add(new Vector3f(145.0f, 25.0f, -280.0f)); //
        positions.add(new Vector3f(245.0f, 18.0f, -310.0f)); //
        //positions.add(new Vector3f(195.0f, 50.0f, -165.0f)); //
        positions.add(new Vector3f(140.0f, 45.0f, -175.0f)); //
        //positions.add(new Vector3f(165.0f, 48.0f, -150.0f)); //
        positions.add(new Vector3f(115.0f, 40.0f, -215.0f)); //
        //positions.add(new Vector3f(100.0f, 45.0f, -185.0f)); //
        positions.add(new Vector3f(110.0f, 62.0f, -85.0f)); //
        //positions.add(new Vector3f(180.0f, 60.0f, -125.0f)); //
        positions.add(new Vector3f(210.0f, 48.0f, -55.0f)); //
        //positions.add(new Vector3f(165.0f, 65.0f, -75.0f)); //
        positions.add(new Vector3f(90.0f, 58.0f, -125.0f)); // 
        //positions.add(new Vector3f(65.0f, 58.0f, -95.0f)); //
        positions.add(new Vector3f(95.0f, 65.0f, -25.0f)); //
        //positions.add(new Vector3f(40.0f, 55.0f, -145.0f)); // 
        positions.add(new Vector3f(175.0f, 62.0f, -45.0f)); //
        //positions.add(new Vector3f(215.0f, 47.0f, -25.0f)); //
        positions.add(new Vector3f(75.0f, 68.0f, -55.0f)); //
        //positions.add(new Vector3f(125.0f, 58.0f, -95.0f)); //
        positions.add(new Vector3f(25.0f, 70.0f, -15.0f)); //
        //positions.add(new Vector3f(35.0f, 70.0f, -65.0f)); //
        positions.add(new Vector3f(15.0f, 32.0f, -265.0f)); //
        //positions.add(new Vector3f(75.0f, 18.0f, -290.0f)); //
        positions.add(new Vector3f(50.0f, 40.0f, -235.0f)); //
        //positions.add(new Vector3f(37.0f, 40.0f, -215.0f)); //
        positions.add(new Vector3f(22.0f, 60.0f, -145.0f)); //
        //positions.add(new Vector3f(41.0f, 67.0f, -95.0f)); //
        //Quad 3 -+
        positions.add(new Vector3f(-180.0f, 44.0f, 200.0f)); //
        //positions.add(new Vector3f(-160.0f, 39.0f, 240.0f)); //
        positions.add(new Vector3f(-235.0f, 35.0f, 222.0f)); //
        //positions.add(new Vector3f(-180.0f, 25.0f, 310.0f)); //
        positions.add(new Vector3f(-145.0f, 25.0f, 280.0f)); //
        //positions.add(new Vector3f(-245.0f, 28.0f, 310.0f)); //
        positions.add(new Vector3f(-195.0f, 47.0f, 165.0f)); //
        //positions.add(new Vector3f(-140.0f, 48.0f, 175.0f)); //
        positions.add(new Vector3f(-165.0f, 48.0f, 150.0f)); //
        //positions.add(new Vector3f(-115.0f, 43.0f, 215.0f)); //
        positions.add(new Vector3f(-100.0f, 45.0f, 185.0f)); //
        //positions.add(new Vector3f(-110.0f, 65.0f, 85.0f)); //
        positions.add(new Vector3f(-180.0f, 51.0f, 125.0f)); //
        //positions.add(new Vector3f(-210.0f, 48.0f, 55.0f)); //
        positions.add(new Vector3f(-165.0f, 60.0f, 75.0f)); //
        //positions.add(new Vector3f(-90.0f, 58.0f, 125.0f)); // 
        positions.add(new Vector3f(-65.0f, 65.0f, 95.0f)); //
        //positions.add(new Vector3f(-95.0f, 78.0f, 25.0f)); //
        positions.add(new Vector3f(-40.0f, 50.0f, 145.0f)); //
        //positions.add(new Vector3f(-175.0f, 62.0f, 45.0f)); //
        positions.add(new Vector3f(-215.0f, 55.0f, 25.0f)); //
        //positions.add(new Vector3f(-75.0f, 76.0f, 55.0f)); //
        positions.add(new Vector3f(-125.0f, 65.0f, 95.0f)); //
        //positions.add(new Vector3f(-25.0f, 76.0f, 15.0f)); //
        positions.add(new Vector3f(-35.0f, 73.0f, 65.0f)); //
        //positions.add(new Vector3f(-15.0f, 32.0f, 265.0f)); //
        positions.add(new Vector3f(-75.0f, 18.0f, 290.0f)); //
        //positions.add(new Vector3f(-50.0f, 36.0f, 235.0f)); //
        positions.add(new Vector3f(-37.0f, 37.0f, 215.0f)); //
        //positions.add(new Vector3f(-22.0f, 52.0f, 145.0f)); //
        positions.add(new Vector3f(-41.0f, 64.0f, 95.0f)); // 
        //Quad 4 --
        //positions.add(new Vector3f(-180.0f, 45.0f, -200.0f)); //
        positions.add(new Vector3f(-160.0f, 43.0f, -240.0f)); //
        //positions.add(new Vector3f(-235.0f, 43.0f, -222.0f)); //
        positions.add(new Vector3f(-180.0f, 30.0f, -310.0f)); //
        //positions.add(new Vector3f(-145.0f, 37.0f, -280.0f)); //
        positions.add(new Vector3f(-245.0f, 30.0f, -310.0f)); //
        //positions.add(new Vector3f(-195.0f, 58.0f, -165.0f)); //
        positions.add(new Vector3f(-140.0f, 50.0f, -175.0f)); //
        //positions.add(new Vector3f(-165.0f, 57.0f, -150.0f)); //
        positions.add(new Vector3f(-115.0f, 40.0f, -215.0f)); //
        //positions.add(new Vector3f(-100.0f, 41.0f, -185.0f)); //
        positions.add(new Vector3f(-110.0f, 65.0f, -85.0f)); //
        //positions.add(new Vector3f(-180.0f, 62.0f, -125.0f)); //
        positions.add(new Vector3f(-210.0f, 53.0f, -55.0f)); //
        //positions.add(new Vector3f(-165.0f, 63.0f, -75.0f)); //
        positions.add(new Vector3f(-90.0f, 54.0f, -125.0f)); //
        //positions.add(new Vector3f(-65.0f, 60.0f, -95.0f)); //
        positions.add(new Vector3f(-95.0f, 76.0f, -25.0f)); //
        //positions.add(new Vector3f(-40.0f, 53.0f, -145.0f)); //
        positions.add(new Vector3f(-175.0f, 60.0f, -45.0f)); //
        //positions.add(new Vector3f(-215.0f, 49.0f, -25.0f)); //
        positions.add(new Vector3f(-75.0f, 70.0f, -55.0f)); //
        //positions.add(new Vector3f(-125.0f, 61.0f, -95.0f)); //
        positions.add(new Vector3f(-25.0f, 78.0f, -15.0f)); //
        //positions.add(new Vector3f(-35.0f, 71.0f, -65.0f)); //
        positions.add(new Vector3f(-15.0f, 34.0f, -265.0f)); //
        //positions.add(new Vector3f(-75.0f, 30.0f, -290.0f)); //
        positions.add(new Vector3f(-50.0f, 41.0f, -235.0f)); //
        //positions.add(new Vector3f(-37.0f, 43.0f, -215.0f)); //
        positions.add(new Vector3f(-22.0f, 57.0f, -145.0f)); //
        //positions.add(new Vector3f(-41.0f, 63.0f, -95.0f)); //
    }
}
