package GUI;

import CorpseSlasher.ClientConnection;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Scanner;

/**
 *
 * @author Gerhard
 */

public class LeaderBoardController implements ScreenController {

    private Nifty nifty;
    private Screen screen;
    private static boolean init = true;
    private static ClientConnection client;
    private AssetManager assMan;
    public LeaderBoardController(ClientConnection client,AssetManager assManager){
        LeaderBoardController.client = client;
        assMan = assManager;
    }
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }
    @Override
    public void onStartScreen() { 
       nifty.setIgnoreKeyboardEvents(false);
       ListBox listBox = screen.findNiftyControl("#scorebar", ListBox.class);
       String leaderboard = client.retrieveLeaderBoard();
       Scanner scLine = new Scanner(leaderboard).useDelimiter(",");
       while(scLine.hasNext()){
           String name = scLine.next();
           String kills = scLine.next();
           String exp = scLine.next();
           String finalStr = addSpaces(name,25)+"\t\t\t\t\t"+addSpaces(kills,15)+"\t\t\t"+exp;       
           listBox.addItem(finalStr);
           System.out.println(finalStr);
           listBox.setStyle("");
       }
    }
    private String addSpaces(String str,int spaces){
        str = str.toUpperCase();
        for(int x = str.length();x<spaces;x++){
            str+=" ";
        }
        return str;
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
