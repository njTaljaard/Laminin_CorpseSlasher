/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Gerhard
 */

public class LeaderBoardController implements ScreenController {

    private Nifty nifty;
    private Screen screen;
    private static boolean init = true;
    ArrayList content = new ArrayList<String>();
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        if(init)
        {
       content.add("Zombie King # 1500 # 50000");
       content.add("Zombie Noob # 15 # 500");
       init = false;
        }
    }

    @Override
    public void onStartScreen() {        
       nifty.setIgnoreKeyboardEvents(false);
        ListBox listBox = screen.findNiftyControl("#scorebar", ListBox.class);
       // TextField text = screen.findNiftyControl("", TextField.class);
       for(Object details : content){
           StringTokenizer tokens = new StringTokenizer(details.toString(),"#");
           String name = tokens.nextToken();
           String kills = tokens.nextToken();
           String exp = tokens.nextToken();
           listBox.addItem(name+"\t\t\t\t\t"+kills+"\t\t\t"+exp);
       }
    }

    @Override
    public void onEndScreen() {
       nifty.setIgnoreKeyboardEvents(true);        
    }
    public void goTo(String _screen){
        nifty.gotoScreen(_screen);
    }
    public void quitGame(){
        System.exit(1);
    }
    
}
