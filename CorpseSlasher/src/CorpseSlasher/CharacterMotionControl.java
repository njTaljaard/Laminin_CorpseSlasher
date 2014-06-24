package CorpseSlasher;

import com.jme3.input.controls.ActionListener;
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
    
    private ActionListener actionListener;
    private Vector3f walkDirection;
    protected boolean slash, walk, jump;
    private boolean left, right, up, down;
    
    /**
     * CharacterMotionControl will set all the values and initialize the Action
     * Listener for the key bindings.
     */
    public CharacterMotionControl() {        
        walkDirection = new Vector3f();
        slash = left = right = up = down = jump = false;
        
        initMotionController();
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
                        /*if (value) {
                            jump = true;
                        } else {
                            jump = false;
                        }*/
                        break;
                }
            }
        };
    }
    
    /**
     * updateCharacterMotion will calulate the resulting directional vector from
     * the keys pressed.
     * @param cam - Camera required for the camera directional and left vector.
     * @return walkDirection - The vector containing the new direction and 
     * magnatude of the motion.
     */
    public Vector3f updateCharacterMotion(Camera cam) {
        Vector3f camDir = cam.getDirection().clone().multLocal(0.3f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.2f);
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
}