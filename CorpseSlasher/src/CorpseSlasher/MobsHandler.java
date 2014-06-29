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
 * Mob
 */
public class MobsHandler {
    
    private Node mobNode;
    private ArrayList<Mob> mobs;
    private ArrayList<Vector3f> positions;
    
    public MobsHandler(InputManager inMan, BulletAppState bullet, AssetManager assMan) {
        mobNode = new Node("Mobs");
        mobs = new ArrayList<>();
        positions = new ArrayList<>();
        initPositions();
        createMobs(inMan, bullet, assMan);
    }
    
    private void initPositions() {
        positions.add(new Vector3f(180.0f, 55.0f, -200.0f));
    }
    
    private void createMobs(InputManager inMan, BulletAppState bullet, AssetManager assMan) {    
        Mob newMob;
        
        int size = positions.size();
        for (int i = 0; i < size; i++) {
            newMob = new Mob(positions.get(i), inMan, bullet, assMan, "mob"+i);
            mobs.add(newMob);
            mobNode.attachChild(newMob.retrieveMob());
        }
    }
    
    public void updateMobs(Vector3f attackDirection) {
        attackDirection = attackDirection.negate();
        attackDirection.y = 0.0f;
        for (Mob mob : mobs) {
            mob.updateMob(attackDirection);
        }
    }
    
    public Node retrieveMobs() {
        return mobNode;
    }
}
