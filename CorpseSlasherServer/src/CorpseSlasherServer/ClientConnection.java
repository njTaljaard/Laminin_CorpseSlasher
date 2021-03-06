package CorpseSlasherServer;

import java.io.*;
import java.net.*;

/**
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301
 *
 * ClientConnection handles all the client's traffic to and from the server.
 */
public class ClientConnection implements Runnable {

    private BufferedReader in;
    private Socket client;
    private PrintWriter out;
    
    /**
     *
     * @param clientSocket - receives the client socket
     */
    public ClientConnection(Socket clientSocket) {
        client = clientSocket;
    }

    /**
     * run handles all the client's traffic to and from the server.
     */
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        
            
            Input data = new Input();
            while (true) {
                String recievedValue = in.readLine();
                if (recievedValue == null) {
                    client.close();
                    break;
                } else {
                    
                    out.println(data.getInput(recievedValue));
                }

            }
        } catch (Exception exc) {
            ExceptionHandler.catchException("ClientConnection", "run", exc.toString());
        }
    }
}
