package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
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
    
    public MobsHandler(InputManager inMan, BulletAppState bullet,
            AssetManager assMan) {
        mobNode = new Node("Mobs");
        mobs = new ArrayList<>();
        positions = new ArrayList<>();
        initPositions();
        createMobs(inMan, bullet, assMan);
    }
    
    private void initPositions() {
        positions.add(new Vector3f(200.0f, 48.0f, -220.0f));
    }
    
    private void createMobs(InputManager inMan, BulletAppState bullet, AssetManager assMan) {    
        Mob newMob;
        
        for (Vector3f pos : positions) {
            newMob = new Mob(pos, inMan, bullet, assMan);
            mobs.add(newMob);
            mobNode.attachChild(newMob.retrieveMob());
        }
    }
    
    public void updateMobs() {
        for (Mob mob : mobs) {
            mob.updateMob();
        }
    }
    
    public Node retrieveMobs() {
        return mobNode;
    }
}
