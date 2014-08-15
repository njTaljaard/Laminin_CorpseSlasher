package CorpseSlasher;

import GUI.LoadingScreen;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import jme3utilities.sky.SkyControl;
import com.jme3.water.WaterFilter;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.util.SkyFactory;
import com.jme3.water.SimpleWaterProcessor;
import java.util.List;
import jme3utilities.Misc;
import jme3utilities.TimeOfDay;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * BasicScene will setup the terrain, water, lighting and skybox.
 */
public class BasicScene {
    
    /**
     * All global variables required to initialize each scene element.
     */
    private Node sceneModel;
    private Vector3f lightDir;
    private Node sceneNode;
    private SkyControl skyControl;
    private String sceneName;
    private FilterPostProcessor fpp;
    private AmbientLight ambient;
    private DirectionalLight sun;
    private WaterFilter postWater;
    private SimpleWaterProcessor simpleWater;
    private GameSettings settings;
        
    /**
     * BasicScene will create the scene attached to sceneNode.
     * @param mapName - Map that you desire to load.
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
     * @param bullet - BulletAppState which controls the physics of the scene.
     * @param ui - SimpleApplication to retrieve camera positions.
     */
    public void createScene(AssetManager assMan, ViewPort vp, Camera cam, 
            BulletAppState bullet, LoadingScreen ui,GameSettings settings) {
        this.settings = settings;
        fpp = new FilterPostProcessor(assMan);
        initAmbientLight();
        //ui.update(0.1f);
        initSunLight();
        //ui.update(0.2f);
        initWater(assMan, vp);
        //ui.update(0.45f);
        initSkyBox(assMan, cam);
        //ui.update(0.6f);
        initTerrain(assMan, bullet);
        //ui.update(0.85f);
    }
    
    public void reloadScene(AssetManager assMan, ViewPort vp, Camera cam) {
        initWater(assMan, vp);
        initSkyBox(assMan, cam);
    }
    
    /**
     * initAmbientLight will crreate the basic ambient light to be able to see 
     * within the scene.
     */
    private void initAmbientLight() { 
        ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.clone().multLocal(1.5f));
        ambient.setName("Ambient");
        
        sceneNode.addLight(ambient); 
    }
    
    /**
     * initSunLight will create the scenes sunlight with a given direction and
     * color.
     */
    private void initSunLight() {
        sun = new DirectionalLight();
        sun.setDirection(lightDir);
        sun.setColor(ColorRGBA.White.clone().multLocal(2.5f));
        sun.setName("Sun");
        
        sceneNode.addLight(sun);
    }
    
    /**
     * initTerrain will load the Scene j3o for the appropriate scene and attach
     * it to the basic scene node. Add collision detection to the terrain.
     * @param assMan - Assetmanager passed through from main game.
     * @param bullet - BulletAppState which controls the physics of the scene.
     */
    private void initTerrain(AssetManager assMan, BulletAppState bullet) {
        sceneModel = (Node) assMan.loadModel("Scenes/" + sceneName + ".j3o");
        sceneModel.setName("Terrian");
        
        if (sceneModel != null) {
            sceneModel.setName("Terrian");        
            Spatial terrain = sceneModel.getChild("terrain-ZombieScene1");
            terrain.addControl(new RigidBodyControl(0));
            terrain.getControl(RigidBodyControl.class).setCollisionGroup(1);
            bullet.getPhysicsSpace().add(terrain);
            
            Node treeNode = (Node) sceneModel.getChild("Tree");
            List<Spatial> treeList = treeNode.getChildren();
            
            for (int i = 0; i < treeList.size(); i++) {
                BoxCollisionShape treeCol = new BoxCollisionShape(new Vector3f(3.5f, 15, 3.5f));
                RigidBodyControl rig = new RigidBodyControl(treeCol,0);
                rig.setCollisionGroup(1);
                
                treeList.get(i).addControl(rig);
                bullet.getPhysicsSpace().add(treeList.get(i));
            }
            sceneNode.attachChild(sceneModel);
        } else {
            System.out.println("I am not loaded - terrain");
            /**
             * @TODO throw exception to exception handler.
             */
        }
    }
    
    /**
     * initWater will create a per pixel motion water quad and add it to the scene.
     * To be used by the higher end system.     * 
     * @param assMan - Assetmanager passed through from main game.
     * @param vp - ViewPort required for water, contains position of camara.
     */
    private void initWater(AssetManager assMan, ViewPort vp) {
        if (settings.postWater) {
             initPostProcessWater(assMan, vp);
         } else {
             initBasicWater(assMan, vp);
         }
    }
    
    private void initBasicWater(AssetManager assMan, ViewPort vp) {
        simpleWater = new SimpleWaterProcessor(assMan);
        simpleWater.setReflectionScene(sceneNode);
        simpleWater.setWaterDepth(80);         // transparency of water
        simpleWater.setDistortionScale(0.05f); // strength of waves
        simpleWater.setWaveSpeed(0.05f);       // speed of waves
 
        // we set the water plane
        Vector3f waterLocation=new Vector3f(0,20,0);
        simpleWater.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y)));
        vp.addProcessor(simpleWater);
        
        // we define the wave size by setting the size of the texture coordinates
        Quad quad = new Quad(2000,2000);
        quad.scaleTextureCoordinates(new Vector2f(10f,10f));
        
        // we create the water geometry from the quad
        Geometry waterPlane = new Geometry("water", quad);
        waterPlane.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        waterPlane.setLocalTranslation(-1000, 20, 1000);
        waterPlane.setShadowMode(ShadowMode.Receive);
        waterPlane.setMaterial(simpleWater.getMaterial());
        /*Geometry waterPlane = simpleWater.createWaterGeometry(10, 10);
        waterPlane.setLocalTranslation(-5, 0, 5);
        waterPlane.setMaterial(simpleWater.getMaterial());*/
        
        sceneNode.attachChild(waterPlane);
    }
    
    private void initPostProcessWater(AssetManager assMan, ViewPort vp) {
        postWater = new WaterFilter(sceneNode, lightDir); 
        //water.setUseHQShoreline(true); //to high resource usage. //see setting if high end build running.
        postWater.setWaveScale(0.003f);
        postWater.setMaxAmplitude(3.0f);
        postWater.setDeepWaterColor(new ColorRGBA(0.064f,0.329f,0.398f,0.8f));
        if (settings.waterReflections) {
            postWater.setUseRefraction(true);
            postWater.setRefractionStrength(0.2f);
        }
        postWater.setUseRipples(settings.waterRippels);
        postWater.setUseSpecular(settings.waterSpecular);
        postWater.setShoreHardness(0.005f);
        postWater.setUnderWaterFogDistance(20);
        postWater.setWindDirection(new Vector2f(0.35f, 0.35f));
        postWater.setCenter(Vector3f.ZERO); 
        postWater.setRadius(2600); 
        postWater.setWaveScale(0.005f); 
        if (settings.waterFoam) {
            postWater.setFoamExistence(new Vector3f(2.5f, 2.0f, 3.0f)); 
            postWater.setFoamTexture((Texture2D) assMan.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));  
        }
        postWater.setWaterHeight(10.0f); 
        
        fpp.addFilter(postWater); 
        vp.addProcessor(fpp);
    }
    
    /**
     * initSkyBox will create a day night system skybox consisting of a moving 
     * sun and moon, a rotating star system and a changing day night sky.
     * @param assMan - Assetmanager passed through from main game.
     * @param cam - Camera required to create a day night skybox system.
     */
    private void initSkyBox(AssetManager assMan, Camera cam) {
        if (settings.skyDome) {
            initSkyControl(assMan, cam);
        } else {
            initBasicSky(assMan);
        }
    }
     
    private void initBasicSky(AssetManager assMan) {
        sceneNode.attachChild(SkyFactory.createSky(assMan, "Textures/Sky/Bright/BrightSky.dds", false));
    }

    private void initSkyControl(AssetManager assMan, Camera cam) {
        /*
         *  AssetManager, Camera, Cloud Flattening, Star motion, Bottom dome
         */
        skyControl = new SkyControl(assMan, cam, 0.7f, settings.starMotion, true);
        skyControl.setCloudModulation(settings.cloudMotion);
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
     * initBloomLight will create bloom lighting effect and add it to the skybox
     * day night system controler.
     * @param assMan - Assetmanager passed through from main game.
     * @param vp - ViewPort required for water, contains position of camara.
     */
    private BloomFilter initBloomLight(AssetManager assMan, ViewPort vp) {
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
        bloom.setBlurScale(2.5f);
        bloom.setExposurePower(1f);
        Misc.getFpp(vp, assMan).addFilter(bloom);
        
        return bloom;
    }
    
    private void initDepthOfField(ViewPort vp) {
        DepthOfFieldFilter dofFilter = new DepthOfFieldFilter();
        dofFilter.setFocusDistance(50);
        dofFilter.setFocusRange(100);
        dofFilter.setBlurScale(1.15f);
        fpp.addFilter(dofFilter);
        vp.addProcessor(fpp);
    }
    
    public void update(TimeOfDay tod, float tpf) {
        if (settings.skyDome) {
            skyControl.update(tpf);
            skyControl.getSunAndStars().setHour(tod.getHour());
            sun.setDirection(skyControl.getUpdater().getDirection());
            if (settings.postWater) {
                postWater.setLightDirection(sun.getDirection());
            } else {
                simpleWater.setLightPosition(sun.getDirection());
            }
        }
    }
    
    /**
     * @return the basic scene node to be added to the game node.
     */
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
