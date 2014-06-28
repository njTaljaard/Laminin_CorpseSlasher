package CorpseSlasher;

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
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
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
    private WaterFilter water;
        
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
     * @param bullet - BulletAppState which controls the physics of the scene.
     * @param  app - SimpleApplication to retrieve camera positions.
     */
    public void createScene(AssetManager assMan, ViewPort vp, Camera cam, 
            BulletAppState bullet) {
        fpp = new FilterPostProcessor(assMan);
        initAmbientLight();
        initSunLight();
        initTerrain(assMan, bullet);
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
     * @param cam - Camera required to create a day night skybox system.
     * @param bullet - BulletAppState which controls the physics of the scene.
     */
    private void initTerrain(AssetManager assMan, BulletAppState bullet) {
        sceneModel = (Node) assMan.loadModel("Scenes/" + sceneName + ".j3o");
        
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
     * initSkyBox will create a day night system skybox consisting of a moving 
     * sun and moon, a rotating star system and a changing day night sky.
     * @param assMan - Assetmanager passed through from main game.
     * @param cam - Camera required to create a day night skybox system.
     */
    private void initSkyBox(AssetManager assMan, Camera cam) {
        /**
         * @param AssetManager, Camera, Cloud Flattening, Star motion, Bottom dome
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
        skyControl.update(tpf);
        skyControl.getSunAndStars().setHour(tod.getHour());
        sun.setDirection(skyControl.getUpdater().getDirection());
        water.setLightDirection(skyControl.getUpdater().getDirection());
    }
    
    /**
     * @return the basic scene node to be added to the game node.
     */
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
