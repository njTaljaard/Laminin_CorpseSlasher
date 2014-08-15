package CorpseSlasher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.json.*;

/**
 * @author Laminin
 * @param Derivco ClientConnection is a public final class that will be used as
 * a connection to an exterior server. All methods within must be static and
 * global variables need to be created as private static or final. It will
 * contain default server connection information that will be used to create the
 * initial connect at run time, from there it will maintain the session for
 * further use. Any communication between the client and server will be done
 * through this for login, experience updates and retrieving the leaderboard.
 */
public final class ClientConnection {

    /**
     * Variables used to define client connection and I/O details.
     */
    private static Socket clientSocket;
    private static BufferedReader inReader;
    private static PrintWriter outWriter;
    private static final String hostAddress = "localhost";
    private static final int hostPortNumber = 32323;

    public ClientConnection() {
        StartClientConnection();
    }

    /**
     *
     * @return - return true if the client connection was successful and false
     * if it failed.
     */
    public static boolean StartClientConnection() {
        try {
            clientSocket = new Socket(hostAddress, hostPortNumber);
            inReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            return true;
        } catch (Exception ex) {
            //TODO: Raise exceptions through the ExceptionHandler class
        }
        return false;
    }

    /**
     * Login is used to attempt login on server side through AOuth or private
     * detail.
     *
     * @return if login was successful or not.
     */
    public static boolean Login(String username, String password) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "login");
            obj.put("username", username);
            obj.put("password", password);
            outWriter.println(obj);
            return Boolean.parseBoolean(inReader.readLine().toString());
        } catch (Exception exc) {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection login error: " + exc.toString());
        }
        return false;
    }

    /**
     * AddUser - sends a JSON object with all the clients details to the server,
     * to add new user to database.
     *
     * @param username - client's username
     * @param password - client's password
     * @param name - client's name
     * @param surname - client's surname
     * @param email - client's email
     *
     * @return returns what ever boolean value the server returns, true if the
     * user was added to database and false if it failed.
     */
    public static boolean AddUser(String username, String password, String name, String surname, String email) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "addUser");
            obj.put("username", username);
            obj.put("password", password);
            obj.put("name", name);
            obj.put("surname", surname);
            obj.put("email", email);
            outWriter.println(obj);
            return Boolean.parseBoolean(inReader.readLine().toString());
        } catch (Exception exc) {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection Add User error: " + exc.toString());
        }
        return false;
    }

    /**
     * RetrievePassword - sends the client's username in a JSON object to the
     * server, so that the client's password can be sent to hin via email.
     *
     * @param username - client's username
     *
     * @return - returns true or false, depending on if the email was sent to
     * the client, containing his/hers password.
     */
    public static boolean RetrievePassword(String username) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "retrievePassword");
            obj.put("username", username);
            outWriter.println(obj);
            return Boolean.parseBoolean(inReader.readLine().toString());
        } catch (Exception exc) {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection retrieve password error: " + exc.toString());
        }
        return false;
    }
    
    /**
     * RetrievePasswordInputEmail - sends the client's email in a JSON object to the
     * server, so that the client's password can be sent to hin via email.
     *
     * @param email - client's email
     *
     * @return - returns true or false, depending on if the email was sent to
     * the client, containing his/hers password.
     */
    public static boolean RetrievePasswordInputEmail(String email) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "retrievePasswordInputEmail");
            obj.put("email", email);
            outWriter.println(obj);
            return Boolean.parseBoolean(inReader.readLine().toString());
        } catch (Exception exc) {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection retrieve password input email error: " + exc.toString());
        }
        return false;
    }

    /**
     * SetPassword - sends a JSON object to the server, containing the client's
     * username and password, so that his/hers password can be changed in the
     * database.
     *
     * @param username - client's username.
     * @param password - client's password
     *
     * @return returns true or false, depending on if the client's password was
     * changed successfully(then true) or not(then false).
     */
    public static boolean SetPassword(String username, String password) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "setPassword");
            obj.put("username", username);
            obj.put("password", password);
            outWriter.println(obj);
            return Boolean.parseBoolean(inReader.readLine().toString());
        } catch (Exception exc) {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection set password error: " + exc.toString());
        }
        return false;
    }

    /**
     * GetKills - get the number of client's zombie kills from the server.
     *
     * @param username - client's username.
     *
     * @return - return the number of client's zombie kills or -1 if it failed.
     */
    public static int GetKills(String username) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "getKills");
            obj.put("username", username);
            outWriter.println(obj);
            return Integer.parseInt(inReader.readLine().toString());
        } catch (Exception exc) {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection get kills error: " + exc.toString());
        }
        return -1;
    }

    /**
     * SetKills - send the number of client's zombie kills to the server to be
     * saved
     *
     * @param username - client's username
     * @param zombieKills - client's number of zombie kills.
     *
     * @return - returns true if the client's zombie kills was saved on the
     * server or false if it failed.
     */
    public static boolean SetKills(String username, String zombieKills) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "setKills");
            obj.put("username", username);
            obj.put("zombieKills", zombieKills);
            outWriter.println(obj);
            return Boolean.parseBoolean(inReader.readLine().toString());
        } catch (Exception exc) {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection set kills error: " + exc.toString());
        }
        return false;
    }

    /**
     * AddOneKill - adds one kill to the client's total number of kills.
     *
     * @param username - client's username.
     *
     * @return - returns true if the one kill was added server side and false if
     * it failed.
     */
    public static boolean AddOneKill(String username) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "addOneKill");
            obj.put("username", username);
            outWriter.println(obj);
            return Boolean.parseBoolean(inReader.readLine().toString());
        } catch (Exception exc) {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection add one kill error: " + exc.toString());
        }
        return false;
    }

    /**
     * CheckUsernameAvailable - checks is the username is available or already
     * taken.
     *
     * @param username - client's username.
     *
     * @return - return true if username is available and false if not.
     */
    public static boolean CheckUsernameAvailable(String username) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "checkUsernameAvailable");
            obj.put("username", username);
            outWriter.println(obj);
            return Boolean.parseBoolean(inReader.readLine().toString());
        } catch (Exception exc) {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection check username available error: " + exc.toString());
        }
        return false;
    }

    /**
     * Logout is used to safely close the connection between client and server.
     *
     * @return if logout was successful or not.
     */
    public static boolean Logout() {
        try {
            clientSocket.close();
            return true;
        } catch (Exception exc) {
            System.out.println("Logout error: " + exc.toString());
        }
        return false;
    }

    /**
     * Retrieves the leader board from the server through the existing session
     * connection.
     *
     * @return an comma separated string containing the information of all players and
     * scores.
     */
    public static String retrieveLeaderBoard() {
        JSONObject obj = new JSONObject();
        try
        {
            obj.put("type", "retrieveLeaderBoard");
            outWriter.println(obj);
            return inReader.readLine().toString();
        }
        catch (Exception exc)
        {
            //TODO: Raise exceptions through the ExceptionHandler class.
            System.out.println("Connection check username available error: " + exc.toString());
            return "";
        }
    }
}
