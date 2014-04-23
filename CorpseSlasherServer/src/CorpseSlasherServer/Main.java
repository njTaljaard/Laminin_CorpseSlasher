package CorpseSlasherServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            System.out.println("Im ready for action!!!");
            System.out.println("Waiting for new meat on 32323...");
            ServerSocket serverSocket = new ServerSocket(32323);
            
            while(true) {
                Socket clientSocket = serverSocket.accept();
                threadExecutors.execute(new ClientConnection(clientSocket));
                System.out.println(clientSocket.getLocalAddress().toString() 
                        + " wants try and play with us!!! Let the games begin"
                        + " muhahaha...");
            }
            
        } catch (IOException ex) {
            //TODO: Send exception to exception handler class to process. 
        }
    }
}
