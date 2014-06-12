package CorpseSlasher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Laminin
 * @param Derivco
 * ClientConnection is a public final class that will be used as a connection 
 * to an exterior server. All methods within must be static and global variables
 * need to be created as private static or final. It will contain default server
 * connection information that will be used to create the initial connect at run
 * time, from there it will maintain the session for further use. Any 
 * communication between the client and server will be done through this for 
 * login, experiance updates and retrieving the leaderboard.
 */
public final class ClientConnection {
    
    /** 
     * Variables used to define client connection and I/O details.
     */
    private static Socket clientSocket;
    private static BufferedReader inReader;
    private static PrintWriter outWriter;
    private final String hostAddress = "localhost";
    private final int hostPortNumber = 32323;
    
    public ClientConnection() {
        try {
            clientSocket = new Socket(hostAddress, hostPortNumber);
            inReader = new BufferedReader(new 
                    InputStreamReader(clientSocket.getInputStream()));
            outWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception ex) {
            //TODO: Raise exceptions through the ExceptionHandler class 
        }
    }
    
    /**
     * Login is used to attempt login on server side through AOuth or private 
     * detail.
     * @return if login was successfull or not.
     */
    public boolean Login() {
        outWriter.println("login");
        return true;
    }
    
    /**
     * Logout is used to safely close the connection between client and server.
     * @return if logout was successfull or not.
     */
    public boolean Logout() {
        
        return true;
    }
    
    /**
     * Retrieves the leaderboard from the server through the existing session 
     * connection.
     * @return an arraylist containing the information of all players and scores.
     */
    public static ArrayList<Object> retrieveLeaderBoard() {
        
        return null;
    }
}
