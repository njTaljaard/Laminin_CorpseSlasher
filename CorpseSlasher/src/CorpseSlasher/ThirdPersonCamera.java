package CorpseSlasher;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * ThirdPersonCamera will control the pivot around the camera when looking up or down.
 */
public class ThirdPersonCamera {
    private Node pivot;
    private CameraNode cameraNode;
    public float followDistance = 7;
    public float verticalAngle = 5 * FastMath.DEG_TO_RAD;
    public float maxVerticalAngle = 85 * FastMath.DEG_TO_RAD;
    public float minVerticalAngle = -25 * FastMath.DEG_TO_RAD;
 
    public ThirdPersonCamera(String name, Camera cam, Node player)
    {
	pivot = new Node("CamTrack");	
	player.attachChild(pivot);
 
	cameraNode = new CameraNode(name, cam);
        cameraNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
	pivot.attachChild(cameraNode);
        cameraNode.setLocalTranslation(new Vector3f(-0.8f, 5.5f, -4.2f));
	cameraNode.lookAt(pivot.getLocalTranslation(), Vector3f.UNIT_Y);
 
	pivot.getLocalRotation().fromAngleAxis(verticalAngle, Vector3f.UNIT_X);
    }
 
    public void verticalRotate(float angle)
    {
	verticalAngle += angle;
 
	if(verticalAngle > maxVerticalAngle)
	{
	    verticalAngle = maxVerticalAngle;
	}
	else if(verticalAngle < minVerticalAngle)
	{
	    verticalAngle = minVerticalAngle;
	}
 
	pivot.getLocalRotation().fromAngleAxis(-verticalAngle, Vector3f.UNIT_X);
    }
 
    public CameraNode getCameraNode()
    {
	return cameraNode;
    }
 
    public Node getCameraTrack()
    {
	return pivot;
    }
}
