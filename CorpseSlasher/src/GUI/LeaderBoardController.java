package GUI;

import CorpseSlasher.ClientConnection;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Scanner;

/**
 * The controller class for the leaderboard class, displaying and adding all
 * scores and usernames
 */
public class LeaderBoardController implements ScreenController {

    private Nifty nifty;
    private Screen screen;
    private static boolean init = true;
    private static ClientConnection client;
    private AssetManager assMan;
    private Application app;
    
    public LeaderBoardController(ClientConnection client,AssetManager assManager, Application app){
        LeaderBoardController.client = client;
        assMan = assManager;
        this.app = app;
    }
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }
    /**
     * Builds the listbox displaying all the values of scoreboard
     */
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
    /**
     * 
     * @param str string to have spaces added
     * @param spaces amount of spaces to strng coming in
     * @return the string incoming with the added spaces
     */
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
    /**
     * 
     * @param _screen screen to change to
     * Changes the nifty gui screen based on the input
     */
    public void goTo(String _screen){
        nifty.gotoScreen(_screen);
    }
    /**
     * Quits the game
     */
    public void quitGame(){
        app.stop(false);
        System.exit(0);
    }
    
}
