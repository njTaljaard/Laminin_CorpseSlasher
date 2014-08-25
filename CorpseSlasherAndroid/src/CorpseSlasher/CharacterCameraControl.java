package CorpseSlasher;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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
 * CharacterCameraControl will control the pivot around the camera when looking 
 * up or down.
 */
public class CharacterCameraControl {
    private Node pivot;
    private CameraNode cameraNode;
    private AnalogListener analogListener;
    private BetterCharacterControl characterControl;
    private float xMovementSpeed = 0.5f;
    private float yMovementSpeed = 0.5f;
    private float verticalAngle = 5 * FastMath.DEG_TO_RAD;
    private float maxVerticalAngle = 85 * FastMath.DEG_TO_RAD;
    private float minVerticalAngle = -25 * FastMath.DEG_TO_RAD;
 
    /**
     * CharacterCameraControl will create the functionality that will enable the
     * user to move the camera left and right.
     * @param name - Name of camera node.
     * @param cam - Camera as to add to the camera node to control the camera.
     * @param player - Spatial node containing the model and all its controllers.
     * @param cc - BetterCharacterControl to change player turning direction.
     */
    public CharacterCameraControl(String name, Camera cam, Node player, BetterCharacterControl cc)
    {
	pivot = new Node("CamTrack");	
	player.attachChild(pivot);
        
	cameraNode = new CameraNode(name, cam);
        cameraNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
	pivot.attachChild(cameraNode);
        cameraNode.setLocalTranslation(new Vector3f(-0.8f, 5.5f, -6.2f));
	cameraNode.lookAt(pivot.getLocalTranslation(), Vector3f.UNIT_Y);
 
	pivot.getLocalRotation().fromAngleAxis(verticalAngle, Vector3f.UNIT_X);
        this.characterControl = cc;
        initActionListener();
    }
    
    /**
     * initActionListener to update the mouse motion.
     */
    private void initActionListener() {
        analogListener = new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {
                Quaternion turn = new Quaternion();
                switch(name) {
                    case "TurnLeft":
                        turn.fromAngleAxis(xMovementSpeed * value, Vector3f.UNIT_Y);
                        characterControl.setViewDirection(turn.mult(characterControl.getViewDirection()));
                        break;
                    case "TurnRight":
                        turn.fromAngleAxis(-xMovementSpeed * value, Vector3f.UNIT_Y);
                        characterControl.setViewDirection(turn.mult(characterControl.getViewDirection()));
                        break;
                    case "LookUp":
                        verticalRotate(yMovementSpeed*value);
                        break;
                    case "LookDown":
                        verticalRotate(-yMovementSpeed*value);
                        break;
                }
            }
        };
    }
 
    /**
     * verticalRotate will update the vertical rotation of the camera according
     * to the maximum and minimum rotation specified.
     * @param angle - Mouse motion converted to an angle to update the direction.
     */
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
    
    /**
     * getAnalogListener is the accessor for the mouse motion Analog Listener.
     * @return analogListener.
     */
    public AnalogListener getAnalogListener() {
        return analogListener;
    }
}
