package CorpseSlasherServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

/**
 * @author Laminin
 * @parm Derivco Main will be the running server for the back-end of the game.
 * It will handle all client connection and data trasfers for experiace updates
 * as well as retrieving the leaderboard.
 */
public class Main implements Runnable {

    /**
     * Handler of all incoming threads to this server
     * and the logs(error and audit.
     */
    private static ExecutorService threadExecutors;
    private static PrintWriter auditOut;
    private static PrintWriter errorOut;
    /**
     * Create thread handler and run the server to wait for incomming client
     * connections.
     */
    public static void main(String[] args) {
        threadExecutors = Executors.newCachedThreadPool();
        try
        {
        auditOut = new PrintWriter(new BufferedWriter(new FileWriter("auditLog.txt", true)));
        errorOut = new PrintWriter(new BufferedWriter(new FileWriter("errorLog.txt", true)));
        }
        catch (Exception exc)
        {
            ExceptionHandler.catchException("Main", "main", exc.toString());
        }
        new Main().run();
    }

    /**
     * Run creates a server socket listining on port: 32323, when an incoming
     * client connects to this port it executes a new thread that will be give
     * access to all the server opperations.
     *
     * @exception Server has run into a problem that will be sent to the
     * Exception Handler class, if error can not be resolved the server will
     * sever all client connections and close the server socket.
     */
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(32323);
            System.out.println("Socket created on port 32323");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadExecutors.execute(new ClientConnection(clientSocket));
                System.out.println("Client: " + clientSocket.getLocalAddress().toString() + " connected to server");

            }

        } catch (IOException ex) {
            ExceptionHandler.catchException("Main", "run", ex.toString()); 
        } finally {
            auditOut.close();
            errorOut.close();
        }
    }
    
    /**
     * 
     * writeAudit - write audits to the audit log.
     * 
     * @param audit - audit details, like username, function call, time and date.
     */
    
    public synchronized static void writeAudit(String audit)
    {
        try
        {
            auditOut.println(audit);
            auditOut.flush();
        }
        catch (Exception exc)
        {
            ExceptionHandler.catchException("Main", "writeAudit", exc.toString());
        }
    }
    
    /**
     * 
     * writeError - write errors to the error log.
     * 
     * @param error - error details, like class name and function name of the
     * class and function containing the error and error details.
     */
    
    public synchronized static void writeError(String error)
    {
        try
        {
            errorOut.println(error);
            errorOut.flush();
        }
        catch (Exception exc)
        {
            System.out.println("Error in Main when writing to error log: " + exc.toString());
        }
    }
}
