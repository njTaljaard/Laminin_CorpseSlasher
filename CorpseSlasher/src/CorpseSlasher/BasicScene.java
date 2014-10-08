package CorpseSlasher;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.material.Material;
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
import com.jme3.renderer.queue.RenderQueue.Bucket;
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
    private Spatial skybox;
    private SkyControl skyControl;
    private String sceneName;
    private FilterPostProcessor fpp;
    private AmbientLight ambient;
    private DirectionalLight sun;
    private WaterFilter postWater;
    private SimpleWaterProcessor simpleWater;
    private ViewPort vp;
    private Camera cam;
        
    /**
     * BasicScene - will create the scene attached to sceneNode and light direction.
     * @param mapName - Map that you desire to load.
     */
    public BasicScene(String mapName) {
        sceneName = mapName;
        sceneNode = new Node("BasicScene");
        
        if (sceneNode == null) {
            ExceptionHandler.throwError("SceneNode could not be created.", "BasicScene");
        }
        
        lightDir = new Vector3f(2.9236743f, -3.27054665f, 5.896916f);
        
        if (lightDir == null) {
            ExceptionHandler.throwError("Light direction not initialized succesfully.", "BasicScene");
        }
    }
    
    /**
     * createScene - will call the appopriate functions to assemble the scene and
     * attached it to sceneNode, which will be added to rootNode higher up to
     * be able to draw.
     * @param vp - ViewPort required for water, contains position of camara.
     * @param cam - Camera required to create a day night skybox system.
     */
    public void createScene(ViewPort vp, Camera cam) {
        this.cam = cam;
        this.vp = vp;
        this.simpleWater = null;
        this.postWater = null;
        this.skyControl = null;
        this.skybox = null;
        fpp = new FilterPostProcessor(GameWorld.assetManager);
        
        initAmbientLight();
        initSunLight();
        initWater();
        initSkyBox();
        initTerrain();
    }
    
    /**
     * reloadScene - Called after graphics settings have been changed and applied.
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
        
        if (skybox == null) {
            sceneNode.removeControl(SkyControl.class);
            skyControl = null;
        } else {
            sceneNode.detachChildNamed("skybox");
            skybox = null;
        }
        sceneNode.detachChildAt(2);
        initSkyBox();
    }
    
    /**
     * initAmbientLight - will crreate the basic ambient light to be able to see 
     * within the scene.
     */
    private void initAmbientLight() {
        ColorRGBA col = new ColorRGBA(0.99215f, 0.72156f, 0.074509f, 1.0f); 
        ambient = new AmbientLight();
        
        if (ambient != null) {
            ambient.setColor(col);
            ambient.setName("Ambient");

            sceneNode.addLight(ambient); 
        } else {
            ExceptionHandler.throwError("Ambient light was not succesfully initialized.", "BasiceScene - AmbientLight");
        }
    }
    
    /**
     * initSunLight - will create the scenes sunlight with a given direction and
     * color.
     */
    private void initSunLight() {
        ColorRGBA col = new ColorRGBA(0.99215f, 0.72156f, 0.074509f, 1.0f);
        sun = new DirectionalLight();
        
        if (sun != null) {
            sun.setDirection(lightDir);
            sun.setColor(col);
            sun.setName("Sun");

            sceneNode.addLight(sun);
        } else {
            ExceptionHandler.throwError("Sun light was not succesfully initialized.", "BasicScene - SunLight");
        }
    }
    
    /**
     * initTerrain - will load the Scene j3o for the appropriate scene and attach
     * it to the basic scene node, which contains the terrain, trees and camp site. 
     * Add collision detection to all object to create a realistic scene.
     */
    private void initTerrain() {
        try {
            sceneModel = (Node) GameWorld.assetManager.loadModel("Scenes/" + sceneName + ".j3o");
            
            if (sceneModel != null) {
                sceneModel.setName("Terrian");     
                Spatial terrain = sceneModel.getChild("terrain-ZombieScene1");
                
                if (terrain != null) {
                    terrain.addControl(new RigidBodyControl(0));
                    terrain.getControl(RigidBodyControl.class).setCollisionGroup(1);
                    GameWorld.bullet.getPhysicsSpace().add(terrain);
                } else {
                    ExceptionHandler.throwError("Could not retrieve terrain node from scene", "BasicScene - Terrain");
                }

                Node treeNode = (Node) sceneModel.getChild("Tree");
                
                if (treeNode != null) {
                    List<Spatial> treeList = treeNode.getChildren();
                    BoxCollisionShape treeCol;
                    
                    for (int i = 0; i < treeList.size(); i++) {
                        treeCol = new BoxCollisionShape(new Vector3f(3.5f, 15, 3.5f));
                        RigidBodyControl rig = new RigidBodyControl(treeCol,0);
                        rig.setCollisionGroup(1);

                        treeList.get(i).addControl(rig);
                        GameWorld.bullet.getPhysicsSpace().add(treeList.get(i));
                    }
                } else {
                    ExceptionHandler.throwError("Could not retrieve tree node from scene.", "BasiceScene - Terrain");
                }
                
                Spatial fire = sceneModel.getChild("Models/campfire/campfire.j3o");
                if (fire != null) {
                    fire.addControl(new RigidBodyControl(0));
                    fire.getControl(RigidBodyControl.class).setCollisionGroup(1);
                    GameWorld.bullet.getPhysicsSpace().add(fire);
                    
                    ParticleEmitter particle = 
                            new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 90);
                    Material mat_red = new Material(GameWorld.assetManager, 
                            "Common/MatDefs/Misc/Particle.j3md");
                    mat_red.setTexture("Texture", GameWorld.assetManager.loadTexture(
                            "Effects/effect-fire.png"));
                    particle.setLocalTranslation(fire.getLocalTranslation().add(0.0f, 1.0f,0.0f));
                    particle.setMaterial(mat_red);
                    particle.setImagesX(1); 
                    particle.setImagesY(1);
                    particle.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
                    particle.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
                    particle.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
                    particle.setStartSize(1.5f);
                    particle.setEndSize(0.05f);
                    particle.setGravity(0, 0, 0);
                    particle.setLowLife(1.2f);
                    particle.setHighLife(1.8f);
                    particle.getParticleInfluencer().setVelocityVariation(0.1f);
                    sceneNode.attachChild(particle);
                } else {
                    ExceptionHandler.throwError("Could not retrieve fire model from scene.", "BasicScene - Terrain");
                }
                
                Spatial tent = sceneModel.getChild("Models/Tent/Tent_1.j3o");
                if (tent != null) {
                    tent.addControl(new RigidBodyControl(0));
                    tent.getControl(RigidBodyControl.class).setCollisionGroup(1);
                    GameWorld.bullet.getPhysicsSpace().add(tent);
                } else {
                    ExceptionHandler.throwError("Could not retrieve tent model from scene.", "BasicScene - Terrain");
                }
                
                sceneNode.attachChild(sceneModel);
            } else {
                ExceptionHandler.throwError("Scene was not loaded succesfully.", "BasicScene - Terrain");
            }
        } catch (Exception e) {
            ExceptionHandler.throwInformation("An unknown problem occured while loading terrain.", "BasicScene - Terrain");
        }
    }
    
    /**
     * initWater - determine if post processing water or simple water should be used.
     */
    private void initWater() {
        if (GameSettings.postWater) {
             initPostProcessWater();
         } else {
             initBasicWater();
         }
    }
    
    /**
     * initBasicWater - will create a basic water system that does not require 
     * any post processing.
     */
    private void initBasicWater() {
        try {
            simpleWater = new SimpleWaterProcessor(GameWorld.assetManager);

            if (simpleWater != null) {
                simpleWater.setReflectionScene(sceneNode);
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
            } else {
                ExceptionHandler.throwError("SimpleWaterProcessor not created succesfully.", "BasicScene - BasicWater");
            }
        } catch (Exception e) {
            ExceptionHandler.throwInformation("Could not load required assets for SimpleWaterProcessor.", "BasicScene - BasicWater");
        }
    }
    
    /**
     * initPostProcessWater - will create post processing water for a more realistic
     * viewing effect.
     */
    private void initPostProcessWater() {
        try {
            postWater = new WaterFilter(sceneNode, lightDir); 

            if (postWater != null) {
                //water.setUseHQShoreline(true); //to high resource usage. //see setting if high end build running.
                postWater.setWaveScale(0.003f);
                postWater.setMaxAmplitude(3.0f);
                postWater.setDeepWaterColor(new ColorRGBA(0.064f,0.329f,0.398f,0.8f));
                postWater.setUseRipples(GameSettings.waterRippels);
                postWater.setUseSpecular(GameSettings.waterSpecular);
                postWater.setShoreHardness(0.005f);
                postWater.setUnderWaterFogDistance(20);
                postWater.setWindDirection(new Vector2f(0.35f, 0.35f));
                postWater.setCenter(Vector3f.ZERO); 
                postWater.setRadius(2600); 
                postWater.setWaveScale(0.005f); 
                postWater.setWaterHeight(10.0f); 

                if (GameSettings.waterReflections) {
                    postWater.setUseRefraction(true);
                    postWater.setRefractionStrength(0.2f);
                }

                if (GameSettings.waterFoam) {
                    postWater.setFoamExistence(new Vector3f(2.5f, 2.0f, 3.0f)); 
                    postWater.setFoamTexture((Texture2D) GameWorld.assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));  
                }

                fpp.addFilter(postWater); 
                vp.addProcessor(fpp);
            } else {
                ExceptionHandler.throwError("WaterFilter not created succesfully.", "BasicScene - PostProcessWater");
            }
        } catch (Exception e) {
            ExceptionHandler.throwInformation("Could not load required assets for WaterFilter.", "BasicScene - PostProcessWater");
        }
    }
    
    /**
     * initSkyBox - determine if a basic skybox with textures should be create or
     * a day night system with a moving sun and moon.
     */
    private void initSkyBox() {
        if (GameSettings.skyDome) {
            initSkyControl();
        } else {
            initBasicSky();
        }
    }
     
    /**
     * initBasicSky - creates a basic sky box for lower performance. A cube with static textures.
     */
    private void initBasicSky() {
        try {
            skybox = SkyFactory.createSky(GameWorld.assetManager, "Textures/Skybox/ZombieScene1.dds", true);

            if (skybox != null) {
                skybox.setName("skybox");
                skybox.setCullHint(Spatial.CullHint.Never);
                skybox.setQueueBucket(Bucket.Sky);
                skybox.setLocalTranslation(0.0f, 2000.0f, 0.0f);
                sceneNode.attachChild(skybox);
            } else {
                ExceptionHandler.throwError("SkyFactory could not create skybox.", "BasicScene - BasicSkybox");
            }
        } catch (Exception e) {
            ExceptionHandler.throwInformation("Could not load dds for skybox.", "BasicScene - BasicSkybox");
        }
    }

    /**
     * initSkyControl - will create a day night system skybox consisting of a moving 
     * sun and moon, a rotating star system and a changing day night sky.
     */
    private void initSkyControl() {
        try { 
            /*
             *  AssetManager, Camera, Cloud Flattening, Star motion, Bottom dome
             */
            skyControl = new SkyControl(GameWorld.assetManager, cam, 0.7f, GameSettings.starMotion, true);
            skyControl.setCloudModulation(GameSettings.cloudMotion);
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
        } catch (Exception e) {
            ExceptionHandler.throwInformation("SkyControl could not load required assests.", "BasicScene - SkyControl");
        }
    }
    
    /**
     * initBloomLight - will create bloom lighting effect and add it to the skybox
     * day night system controler.
     */
    private BloomFilter initBloomLight() {
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
        bloom.setBlurScale(2.5f);
        bloom.setExposurePower(1f);
        Misc.getFpp(vp, GameWorld.assetManager).addFilter(bloom);
        
        return bloom;
    }
    
    /**
     * 
     */
    private void initDepthOfField() {
        DepthOfFieldFilter dofFilter = new DepthOfFieldFilter();
        dofFilter.setFocusDistance(50);
        dofFilter.setFocusRange(100);
        dofFilter.setBlurScale(1.15f);
        fpp.addFilter(dofFilter);
        vp.addProcessor(fpp);
    }
        
    /**
     * update - Tests if skycontrol and post water is rendering. Updates of 
     * sky control. Sets the new position of the sun and star system with light.
     * @param tod - Time of day.
     * @param tpf - Time per frame.
     */
    public void update(TimeOfDay tod, float tpf) {
        if (GameSettings.skyDome) {
            skyControl.update(tpf);
            skyControl.getSunAndStars().setHour(tod.getHour());
            sun.setDirection(skyControl.getUpdater().getDirection());
            if (GameSettings.postWater) {
                postWater.setLightDirection(sun.getDirection());
            } else {
                simpleWater.setLightPosition(sun.getDirection());
            }
        }
    }
    
    /**
     * retrieveSceneNode - used to retrieve the scene node to be set as root node.
     * @return the basic scene node to be added to the game node.
     */
    public Node retrieveSceneNode() {
        return sceneNode;
    }
}
