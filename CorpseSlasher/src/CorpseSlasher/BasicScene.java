package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture2D;
import com.jme3.util.SkyFactory;
import jme3utilities.sky.SkyControl;
import com.jme3.water.SimpleWaterProcessor;
import com.jme3.water.WaterFilter;
import com.jme3.math.FastMath;
import com.jme3.post.filters.BloomFilter;
import jme3utilities.Misc;

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
    private Spatial sceneModel;
    private Vector3f lightDir;
    private Node sceneNode;
    private SkyControl skyControl;
    private String sceneName;
    
    /**
     * BasicScene will create the scene attached to sceneNode.
     * @param mapName - Map that you desire to load.
     * @param assMan - Assetmanager passed through from main game.
     * @param vp - ViewPort required for water, contains position of camara.
     * @param cam - Camera required to create a day night skybox system.
     */
    public BasicScene(String mapName) {
        sceneName = mapName;
        sceneNode = new Node("BasicScene");
        lightDir = new Vector3f(2.9236743f, -3.27054665f, 5.896916f);
    }
    
    /**
     * createScene will call the appopriate functions to create the scene and
     * attached it to sceneNode, which will be added to rootNode higher up to
     * be able to draw.
     * @param assMan - Assetmanager passed through from main game.
     * @param vp - ViewPort required for water, contains position of camara.
     * @param cam - Camera required to create a day night skybox system.
     */
    public void createScene(AssetManager assMan, ViewPort vp, Camera cam) {
        initAmbientLight();
        initSunLight();
        initSkyBox(assMan, vp, cam);
        initTerrain(assMan);
        initWater(assMan, vp);
        //initDepthOfField(assMan, vp);
    }
    
    /**
     * initAmbientLight will crreate the basic ambient light to be able to see 
     * within the scene.
     */
    private void initAmbientLight() { 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.clone().multLocal(1.5f));
        ambient.setName("Ambient");
        
        sceneNode.addLight(ambient); 
    }
    
    /**
     * initSunLight will create the scenes sunlight with a given direction and
     * color.
     */
    private void initSunLight() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(lightDir);
        sun.setColor(ColorRGBA.White.clone().multLocal(2.5f));
        sun.setName("Sun");
        
        sceneNode.addLight(sun);
    }
    
    /**
     * initTerrain will load the Scene j3o for the appropriate scene and attach
     * it to the basic scene node.
     */
    private void initTerrain(AssetManager assMan) {
        sceneModel = assMan.loadModel("Scenes/" + sceneName + ".j3o");
        sceneModel.setName("Terrian");
        
        if (sceneModel != null) {
            sceneNode.attachChild(sceneModel);
        } else {
            System.out.println("I am not loaded - terrain");
            /**
             * @TODO throw exception to exception handler.
             */
        }
    }
    
    /**
     * initWater will load the water into the scene with the appropriate values.
     */
    private void initWater(AssetManager assMan, ViewPort vp) {
        /**
         * @TODO determine if simpleWater or PPcWater should be used.
         */
        //initSimpleWater(assMan, vp);
        initPPcWater(assMan, vp);
    }
    
    /**
     * initSimpleWater will create a basic water quad and add it to the scene.
     * To be used by the lower end system.
     */
    /* LOOKS HORRIBLE DONT USE!!!!
     private void initSimpleWater(AssetManager assMan, ViewPort vp) { 
        SimpleWaterProcessor waterProcessor = new SimpleWaterProcessor(assMan);
        waterProcessor.setReflectionScene(sceneModel); 
        waterProcessor.setDebug(true);
        vp.addProcessor(waterProcessor);
        
        Vector3f waterLocation = new Vector3f(0,45,0); 
        waterProcessor.setLightPosition(lightDir);
        waterProcessor.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y)));  
        waterProcessor.setWaterDepth(50f); 
        waterProcessor.setDistortionScale(0.05f); 
        waterProcessor.setWaveSpeed(0.02f); 
        Quad quad = new Quad(1000,1000); 
        quad.scaleTextureCoordinates(new Vector2f(6f,6f)); 
        Geometry water = new Geometry("water", quad); 
        water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X)); 
        water.setLocalTranslation(-200, -6, 250); 
        water.setMaterial(waterProcessor.getMaterial()); 
        
        water.setName("SimpleWater");
        sceneNode.attachChild(water);
    }     */
    
    /**
     * initPPcWater will create a per pixel motion water quad and add it to the scene.
     * To be used by the higher end system.
     */
    private void initPPcWater(AssetManager assMan, ViewPort vp) { 
        FilterPostProcessor fpp = new FilterPostProcessor(assMan);
        WaterFilter water;
        
        water = new WaterFilter(sceneNode, lightDir); 
        //water.setUseHQShoreline(true); //to high resource usage. //see setting if high end build running.
        water.setWaveScale(0.003f);
        water.setMaxAmplitude(3.0f);
        water.setDeepWaterColor(new ColorRGBA(0.064f,0.329f,0.398f,0.8f));
        water.setUseRefraction(true);
        water.setUseRipples(true);
        water.setUseSpecular(true);
        water.setShoreHardness(0.005f);
        water.setUnderWaterFogDistance(20);
        water.setWindDirection(new Vector2f(0.35f, 0.35f));
        water.setCenter(Vector3f.ZERO); 
        water.setRadius(2600); 
        water.setWaveScale(0.005f); 
        water.setFoamExistence(new Vector3f(2.5f, 2.0f, 3.0f)); 
        water.setFoamTexture((Texture2D) assMan.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg")); 
        water.setRefractionStrength(0.2f); 
        water.setWaterHeight(10.0f); 
        
        fpp.addFilter(water); 
        vp.addProcessor(fpp); 
    }
    
    /**
     * initSkyBox will determine if the user would like to create a day night 
     * system skybox or for low performance setting a simple skybox from a dds
     * texture.
     */
    private void initSkyBox(AssetManager assMan, ViewPort vp, Camera cam) {
        /**
         * @TODO determine if textureSkyBox or DayNightSkyBox should be used.
         */
        //initTextureSkyBox(assMan);
        initDayNightSkyBox(assMan, vp, cam);
    }
    
    /**
     * initDayNightSkyBox creates a day-night system with a sun, moon, stars &
     * clouds.
     */
    private void initDayNightSkyBox(AssetManager assMan, ViewPort vp, Camera cam) {
        /**
         * @param AssetManager, Camera, Cloud Flattening, Star motion, Bottom dome )
         */
        skyControl = new SkyControl(assMan, cam, 0.7f, true, true);
        skyControl.setCloudModulation(true);
        skyControl.setCloudiness(0.6f);
        skyControl.setCloudYOffset(0.5f);
        skyControl.setTopVerticalAngle(1.65f);
        skyControl.getSunAndStars().setObserverLatitude(37.4046f * FastMath.DEG_TO_RAD);
        
        //Add scene light to skycontrol
        for (Light light : sceneNode.getLocalLightList()) {
            switch (light.getName()) {
                case "Ambient":
                    skyControl.getUpdater().setAmbientLight((AmbientLight) light);
                    break;
                case "Sun":
                    skyControl.getUpdater().setMainLight((DirectionalLight) light);
                    break;
            }
        }
        
        /**
         * @TODO Test setting if bloom is activated
         */
        //skyControl.getUpdater().addBloomFilter(initBloomLight(assMan, vp));
        
        sceneNode.addControl(skyControl);
    }
    
    /**
     * initTextureSkyBox will create a simple skybox from a dds texture 
     * corrisponding to the scene name
     */
    private void initTextureSkyBox(AssetManager assMan) {
        Spatial skyFactory = SkyFactory.createSky(assMan, "Textures/Skybox/" 
                + sceneName + ".dds", false);
        skyFactory.setName("Skybox");
        
        if (skyFactory != null) {
            sceneNode.attachChild(skyFactory);
        }            
        
        /**
         * @TODO If not a successfull load through exception and exit.
         */
    }
    
    private BloomFilter initBloomLight(AssetManager assMan, ViewPort vp) {
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
        bloom.setBlurScale(2.5f);
        bloom.setExposurePower(1f);
        Misc.getFpp(vp, assMan).addFilter(bloom);
        
        return bloom;
    }
    
    /**
     * @return the basic scene node to be added to the game node.
     */
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
