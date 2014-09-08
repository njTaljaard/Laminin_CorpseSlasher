package CorpseSlasher;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
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
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import com.jme3.water.SimpleWaterProcessor;
import java.util.List;
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
    private Spatial skybox;
    private SkyControl skyControl;
    private String sceneName;
    private List<Spatial> treeList;
    private FilterPostProcessor fpp;
    private AmbientLight ambient;
    private WaterFilter postWater;
    private SimpleWaterProcessor simpleWater;
    private GameSettings settings;
    private AssetManager assetManager;
    private BulletAppState bullet;
    private ViewPort vp;
    private Camera cam;
        
    /**
     * BasicScene will create the scene attached to sceneNode.
     * @param mapName - Map that you desire to load.
     */
    public BasicScene(String mapName) {
        sceneName = mapName;
        sceneNode = new Node("BasicScene");
        //lightDir = new Vector3f(30.0f, -25.0f, 30.0f);
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
     * @param settings - GameSettings.
     */
    public void createScene(AssetManager assMan, ViewPort vp, Camera cam, 
            BulletAppState bullet, /*LoadingScreen ui,*/GameSettings settings) {
        this.settings = settings;
        this.assetManager = assMan;
        this.cam = cam;
        this.vp = vp;
        this.bullet = bullet;
        this.simpleWater = null;
        this.postWater = null;
        this.skyControl = null;
        this.skybox = null;
        fpp = new FilterPostProcessor(assMan);
        initAmbientLight();
        initSunLight();
        initSkybox();
        //initWater();
        initTerrain();
    }
    
    /**
     * 
     */
    public void reloadScene() {
        if (simpleWater == null) {
            fpp.removeFilter(postWater);
            postWater = null;
        } else {
            sceneNode.detachChildAt(1);
            simpleWater = null;
        }
        initWater();
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
        ColorRGBA col = new ColorRGBA(0.99215f, 0.72156f, 0.074509f, 1.0f);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(30.0f, -25.0f, 30.0f));
        sun.setColor(ColorRGBA.White.clone());
        sun.setName("Sun");
        sceneNode.addLight(sun);
        
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setDirection(new Vector3f(-30.f, -15.0f, 30.0f));
        sun2.setColor(col);
        sceneNode.addLight(sun2);
        
        DirectionalLight sun4 = new DirectionalLight();
        sun4.setDirection(new Vector3f(30.f, -15.0f, -30.0f));
        sun4.setColor(col);
        sceneNode.addLight(sun4);
        
        DirectionalLight sun3 = new DirectionalLight();
        sun3.setDirection(new Vector3f(-30.f, -15.0f, -30.0f));
        sun3.setColor(col);
        sceneNode.addLight(sun3);
    }
    
    /**
     * initTerrain will load the Scene j3o for the appropriate scene and attach
     * it to the basic scene node. Add collision detection to the terrain.
     */
    private void initTerrain() {
        sceneModel = (Node) assetManager.loadModel("Scenes/ZombieAndroidScene.j3o");
        sceneModel.setName("Terrian");
        
        Spatial trrn = sceneModel.getChild("terrain-ZombieAndroidScene");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/Terrian/Terrain.png"));
        trrn.setMaterial(mat);
                            
        if (sceneModel != null) {
            /*trrn.addControl(new RigidBodyControl(0));
            trrn.getControl(RigidBodyControl.class).setCollisionGroup(1);
            bullet.getPhysicsSpace().add(trrn);*/
            
            Node treeNode = (Node) sceneModel.getChild("Tree");
            treeList = treeNode.getChildren();
            
            Material treeMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            Texture treeTex = assetManager.loadTexture("Textures/treeTexture.png");
            
            treeMat.setTexture("ColorMap", treeTex);
            treeMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
            
            for (int i = 0; i < treeList.size(); i++) {
                System.out.println("Tree  :  " + i);
                ((Geometry) ((Node) treeList.get(i)).getChild("Tree-3d-geom-0")).setMaterial(treeMat);                
                /*BoxCollisionShape treeCol = new BoxCollisionShape(new Vector3f(0.875f, 3.75f, 0.875f));
                RigidBodyControl rig = new RigidBodyControl(treeCol,0);
                rig.setCollisionGroup(1);
                
                treeList.get(i).addControl(rig);
                bullet.getPhysicsSpace().add(treeList.get(i));*/
            }
            
            sceneNode.attachChild(sceneModel);
        } else {
            System.out.println("I am not loaded - terrain");
        }
    }
    
    private void initSkybox() {
        Texture north = assetManager.loadTexture("Textures/Skybox/negative_z.png");
        Texture south = assetManager.loadTexture("Textures/Skybox/positive_z.png");
        Texture east  = assetManager.loadTexture("Textures/Skybox/positive_x.png");
        Texture west  = assetManager.loadTexture("Textures/Skybox/negative_x.png");
        Texture top   = assetManager.loadTexture("Textures/Skybox/positive_y.png");
        Texture down  = assetManager.loadTexture("Textures/Skybox/negative_y.png");
        Spatial sky   = SkyFactory.createSky(assetManager, west, east, north, south, top, down);
        sceneNode.attachChild(sky);
    }
    
    /**
     * initWater determine if post processing water or simple water should be used.
     */
    private void initWater() {
        /* if (settings.postWater) {
             initPostProcessWater();
         } else { */
             initBasicWater();
         // }
    }
    
    /**
     * initBasicWater will create a basic water system that does not require 
     * any post processing.
     */
    private void initBasicWater() {
        simpleWater = new SimpleWaterProcessor(assetManager);
        //simpleWater.setReflectionScene(sceneNode);
        simpleWater.setWaterDepth(80);         // transparency of water
        simpleWater.setDistortionScale(0.02f); // strength of waves
        simpleWater.setWaveSpeed(0.02f);       // speed of waves
        simpleWater.setDebug(false);
        simpleWater.setWaterTransparency(0.3f);
        Vector3f waterLocation=new Vector3f(0,20,0);
        simpleWater.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y)));
        vp.addProcessor(simpleWater);
        
        Quad quad = new Quad(2000,2000);
        quad.scaleTextureCoordinates(new Vector2f(16f,16f));
        
        Geometry waterPlane = new Geometry("water", quad);
        waterPlane.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        waterPlane.setLocalTranslation(-1000, 20, 1000);
        waterPlane.setShadowMode(ShadowMode.Receive);
        waterPlane.setMaterial(simpleWater.getMaterial());
        
        sceneNode.attachChild(waterPlane);
    }
    
    /**
     * initPostProcessWater will create post processing water for a more realistic
     * viewing effect.
     */
    private void initPostProcessWater() {
        postWater = new WaterFilter(sceneNode, lightDir); 
        //water.setUseHQShoreline(true); //to high resource usage. //see setting if high end build running.
        postWater.setWaveScale(0.003f);
        postWater.setMaxAmplitude(3.0f);
        postWater.setDeepWaterColor(new ColorRGBA(0.064f,0.329f,0.398f,0.8f));
        postWater.setUseRipples(settings.waterRippels);
        postWater.setUseSpecular(settings.waterSpecular);
        postWater.setShoreHardness(0.005f);
        postWater.setUnderWaterFogDistance(20);
        postWater.setWindDirection(new Vector2f(0.35f, 0.35f));
        postWater.setCenter(Vector3f.ZERO); 
        postWater.setRadius(2600); 
        postWater.setWaveScale(0.005f); 
        postWater.setWaterHeight(10.0f); 
        
        if (settings.waterReflections) {
            postWater.setUseRefraction(true);
            postWater.setRefractionStrength(0.2f);
        }
        
        if (settings.waterFoam) {
            postWater.setFoamExistence(new Vector3f(2.5f, 2.0f, 3.0f)); 
            postWater.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));  
        }
        
        fpp.addFilter(postWater); 
        vp.addProcessor(fpp);
    }
            
    /**
     * update runs tests is skycontrol and post water is rendering. Updates of 
     * sky control. Sets the new position of the sun and star system with light.
     * @param tod - Time of day.
     * @param tpf - Time per frame.
     */
    public void update(TimeOfDay tod, float tpf, Vector3f position) {
        for (Spatial tree : treeList) {
            tree.lookAt(position, Vector3f.UNIT_Y);
        }
    }
    
    /**
     * @return the basic scene node to be added to the game node.
     */
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
