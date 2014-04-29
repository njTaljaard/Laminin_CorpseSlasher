package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture2D;
import com.jme3.water.SimpleWaterProcessor;
import com.jme3.water.WaterFilter;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * BasicScene will setup the terrain as well as water.
 */
public class BasicScene {
    
    /**
     * All global variables required to initialize each scene element.
     */
    private AssetManager assetManager;
    private ViewPort viewPort;
    private Spatial sceneModel;
    private String sceneName;
    private Node sceneNode;
    
    public BasicScene(String mapName) {
        sceneName = mapName;
        initTerrain();
        initWater();
    }
    
    /**
     * initTerrain will load the Scene j3o for the appropriate scene and attach
     * it to the basic scene node.
     */
    private void initTerrain() {
        sceneModel = assetManager.loadModel("Scenes/ZombieScene1.j3o");
        if (sceneModel != null) {
            sceneNode.attachChild(sceneModel);
        } else {
            /**
             * @TODO throw exception to exception handler.
             */
        }
    }
    
    /**
     * initWater will load the water into the scene with the appropriate values.
     */
    private void initWater() {
        /**
         * @TODO determine if simpleWater or PPcWater should be used.
         */
        initPPcWater();
    }
    
    /**
     * initSimpleWater will create a basic water quad and add it to the scene.
     * To be used by the lower end system.
     */
    public void initSimpleWater() { 
        SimpleWaterProcessor waterProcessor = new SimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(sceneModel); 
        Vector3f waterLocation = new Vector3f(0,-6,0); 
        waterProcessor.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y))); 
        viewPort.addProcessor(waterProcessor); 
        waterProcessor.setWaterDepth(10); 
        // transparency of water 
        waterProcessor.setDistortionScale(0.05f); 
        // strength of waves 
        waterProcessor.setWaveSpeed(0.05f); 
        // speed of waves 
        Quad quad = new Quad(800,800); 
        quad.scaleTextureCoordinates(new Vector2f(6f,6f)); 
        Geometry water = new Geometry("water", quad); 
        water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X)); 
        water.setLocalTranslation(-400, 0.32f, 400); 
        water.setShadowMode(RenderQueue.ShadowMode.Receive); 
        water.setMaterial(waterProcessor.getMaterial()); 
        sceneNode.attachChild(water);
    }     
    
    /**
     * initPPcWater will create a per pixel motion water quad and add it to the scene.
     * To be used by the higher end system.
     */
    public void initPPcWater() { 
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        Vector3f lightDir = new Vector3f(-4.0f, -1.0f, -5.0f);
        WaterFilter water = new WaterFilter(sceneNode, lightDir); 
        water.setUseHQShoreline(true);
        water.setUseRefraction(true);
        water.setUseRipples(true);
        water.setUseSpecular(true);
        water.setShoreHardness(0.05f);
        water.setUnderWaterFogDistance(80);
        water.setWindDirection(new Vector2f(0.7f, 0.2f));
        water.setCenter(Vector3f.ZERO); 
        water.setRadius(2600); 
        water.setWaveScale(0.003f); 
        water.setMaxAmplitude(6.0f); 
        water.setFoamExistence(new Vector3f(1.0f, 4.0f, 0.5f)); 
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg")); 
        water.setRefractionStrength(0.2f); water.setWaterHeight(5.0f); 
        fpp.addFilter(water); 
        viewPort.addProcessor(fpp); 
    }
    
    /**
     * @return the basic scene node to be added to the game node.
     */
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
