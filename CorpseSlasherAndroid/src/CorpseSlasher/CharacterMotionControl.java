package CorpseSlasher;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 * @author Laminin
 * @param  Derivco
 * @param  University of Pretoria
 * @param  COS301
 * CharacterMotionControl will be the controller for the keyboard functionality
 * bounded to the character model.
 */
public class CharacterMotionControl {
    
    private Camera cam;
    private ActionListener actionListener;
    private TouchListener touchListener;
    private Vector3f walkDirection, camDir, camLeft;
    protected boolean slash, walk, jump;
    private boolean left, right, up, down;
    private float x, y, pressure, deltaX, deltaY;
    private int pointer;
    
    /**
     * CharacterMotionControl will set all the values and initialize the Action
     * Listener for the key bindings.
     * @param cam - Camera to retrieve position and direction.
     */
    public CharacterMotionControl(Camera cam) {        
        walkDirection = new Vector3f();
        this.cam = cam;
        slash = left = right = up = down = jump = false;
        
        initMotionController();
        initTouchController();
    }
    
    /**
     * initMotionController will create the Action Listener which will trigger
     * when a butten is pressed and released, updating the corrisponding values
     * to be able to calulate the resulting motion and required animation.
     */
    private void initMotionController() {
        actionListener = new ActionListener() {
            @Override
            public void onAction(String binding, boolean value, float tpf) {
                switch (binding) {
                    case "Slash":
                        if (value) 
                            slash = true;
                        break;
                    case "Left":
                        if (value) { 
                            left = true; 
                        } else { 
                            left = false; 
                        }
                        break;
                    case "Right":
                        if (value) { 
                            right = true; 
                        } else { 
                            right = false; 
                        }
                        break;
                    case "Up":
                        if (value) { 
                            up = true; 
                        } else { 
                            up = false; 
                        }
                        break;
                    case "Down":
                        if (value) {
                            down = true; 
                        } else { 
                            down = false; 
                        }
                        break;
                    case "Jump":
                        break;
                }
            }
        };
    }
    
    /**
     * 
     */
    private void initTouchController() {
        touchListener = new TouchListener() {

            @Override
            public void onTouch(String name, TouchEvent event, float tpf) {
                switch(event.getType())
                {
                    case MOVE:
                        x = event.getX();
                        
                        if (x < 1000) {
                            deltaX = event.getDeltaX();
                            deltaY = event.getDeltaY();
                            
                            if (deltaX > 0.5) { //right
                                right = true;
                                left = false;
                            } else if (deltaX < -1.0) { //left
                                left = true;
                                right = false;
                            }

                            if (deltaY > 1.0) { //forward
                                up = true;
                                down = false;
                            } else if (deltaY < -1.0) { //back
                                down = true;
                                up = false;
                            }
                        } 
                        break;
                    case TAP:
                        slash = true;
                        break;
                    case UP :
                        left = right = up = down = false;
                    default:
                        break;
                }
            }
        };
    }    
    
    /**
     * updateCharacterMotion will calulate the resulting directional vector from
     * the keys pressed.
     */
    public Vector3f updateCharacterMotion() {
        camDir = cam.getDirection().clone();
        camLeft = cam.getLeft().clone();
        camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        
        if (left) { walkDirection.addLocal(camLeft); }
        
        if (right) { walkDirection.addLocal(camLeft.negate()); }
        
        if (up) { walkDirection.addLocal(camDir); }
        
        if (down) { walkDirection.addLocal(camDir.negate()); }
        
        if (left || right || up || down) {
            walk = true;
        } else {
            walk = false;
        }
        
        return walkDirection;
    }
    
    /**
     * getMotionController is the accessor for the motion Action Listener.
     * @return actionListener.
     */
    public ActionListener getMotionController() {
        return actionListener;
    }
    
    /**
     * 
     */
    public TouchListener getTouchListener() {
        return touchListener;
    }
}
