package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
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
    private Vector3f lightDir;
    private Node sceneNode;
    
    /**
     * BasicScene will create the scene attached to sceneNode.
     * @param mapName - Map that you desire to load.
     * @param assMan - Assetmanager passed through from main game.
     * @param vp - ViewPort required for water, contains position of camara.
     */
    public BasicScene(String mapName, AssetManager assMan, ViewPort vp) {
        assetManager =  assMan;
        viewPort = vp;
        sceneNode = new Node("BasicScene");
        lightDir = new Vector3f(2.9236743f, -3.27054665f, 5.896916f);
        
        initAmbientLight();
        initSunLight();
        initTerrain(mapName);
        initWater();
    }
    
    /**
     * initAmbientLight will crreate the basic ambient light to be able to see 
     * within the scene.
     */
    private void initAmbientLight() {
            /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        sceneNode.addLight(ambient); 
    }
    
    /**
     * 
     */
    private void initSunLight() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(lightDir);
        sun.setColor(ColorRGBA.White.clone().multLocal(1.7f));
        sceneNode.addLight(sun);
    }
    
    /**
     * initTerrain will load the Scene j3o for the appropriate scene and attach
     * it to the basic scene node.
     */
    private void initTerrain(String sceneName) {
        sceneModel = assetManager.loadModel("Scenes/" + sceneName + ".j3o");
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
        waterProcessor.setWaterDepth(15); 
        // transparency of water 
        waterProcessor.setDistortionScale(0.05f); 
        // strength of waves 
        waterProcessor.setWaveSpeed(0.02f); 
        // speed of waves 
        Quad quad = new Quad(1000,1000); 
        quad.scaleTextureCoordinates(new Vector2f(6f,6f)); 
        Geometry water = new Geometry("water", quad); 
        water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X)); 
        water.setLocalTranslation(-200, -6, 250); 
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
        WaterFilter water = new WaterFilter(sceneNode, lightDir); 
        
        water.setWaveScale(0.003f);
        water.setMaxAmplitude(3.0f);
        //water.setUseHQShoreline(true); //to high resource usage.
        water.setUseRefraction(true);
        water.setUseRipples(true);
        water.setUseSpecular(true);
        water.setShoreHardness(0.005f);
        water.setUnderWaterFogDistance(60);
        water.setWindDirection(new Vector2f(0.35f, 0.35f));
        water.setCenter(Vector3f.ZERO); 
        water.setRadius(2600); 
        water.setWaveScale(0.005f); 
        water.setFoamExistence(new Vector3f(2.5f, 2.0f, 3.0f)); 
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg")); 
        water.setRefractionStrength(0.2f); water.setWaterHeight(10.0f); 
        
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
