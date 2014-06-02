/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jme3.app.Application;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MyStartScreen implements ScreenController 
{
    private Application app;
    private ViewPort guiViewPort;
    private NiftyJmeDisplay loginScreen;
    public MyStartScreen(Application app,NiftyJmeDisplay loginScreen) {
        this.app = app;
        this.loginScreen = loginScreen;
    }
        
        @Override
        public void bind(Nifty nifty, Screen screen) {}

        @Override
        public void onStartScreen() {
            }

        @Override
        public void onEndScreen() {}
        
        public void quitGame(String in)
        {          
            app.getGuiViewPort().removeProcessor(loginScreen);
            app.getInputManager().setCursorVisible(false);   
        }
    }
