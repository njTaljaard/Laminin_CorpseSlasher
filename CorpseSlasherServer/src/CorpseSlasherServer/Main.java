package CorpseSlasherServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.*;

/**
 * @author Laminin
 * @parm Derivco
 * Main will be the running server for the back-end of the game. It will
 * handle all client connection and data trasfers for experiace updates as well
 * as retrieving the leaderboard.
 */
public class Main implements Runnable {

    /**
     * Handler of all incoming threads to this server.
     */
    private static ExecutorService threadExecutors;
    
    /**
     * Create thread handler and run the server to wait for incomming client
     * connections.
     */
    public static void main(String[] args) {
        threadExecutors = Executors.newCachedThreadPool();
        new Main().run();
    }

    /**
     * Run creates a server socket listining on port: 32323, when an incoming
     * client connects to this port it executes a new thread that will be
     * give access to all the server opperations.
     * @exception Server has run into a problem that will be sent to the Exception
     * Handler class, if error can not be resolved the server will sever all
     * client connections and close the server socket.
     */
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(32323);
            System.out.println("Socket create on port 32323");
            while(true) {
                Socket clientSocket = serverSocket.accept();
                threadExecutors.execute(new ClientConnection(clientSocket));
                System.out.println("Client: " + clientSocket.getLocalAddress().toString() + " connected to server");
                
                //test
                JSONObject obj = new JSONObject();
                try {
                obj.put("username", "usern");
                obj.put("password", "pass");
                obj.put("screenName", "sn");
                obj.put("name", "n");
                obj.put("surname", "s");
                obj.put("dateOfBirth", "2000/05/20");
                obj.put("gender", true);
                obj.put("email", "m2g.com");
                DatabaseUpdate dbu = new DatabaseUpdate();
                dbu.setNewUser(obj);
                }
                catch (Exception exc)
                {
                    System.out.println("User json obj creation error:"+exc);
                }
            }
            
        } catch (IOException ex) {
            //TODO: Send exception to exception handler class to process. 
        }
    }
}
