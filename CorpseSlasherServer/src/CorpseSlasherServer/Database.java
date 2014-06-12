package CorpseSlasherServer;

import java.sql.*;

/**
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301
 *
 * Database connects to the mysql database and sends and receives all the data
 * between the server and database.
 */
public class Database {

    Connection conn = null;

    /**
     *
     * connect connects the server to the mysql database.
     *
     * @return returns true if the connection succeeded and false if the
     * connection failed.
     */
    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();//load driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/corpseslasher", "root", "");//establish connection
            //System.out.println("connected to database");
            return true;
        } catch (Exception exc) {
            System.out.println("Database connection error:" + exc);
            return false;
            //TODO: Send exception to exception handler class to process. 
        }
    }

    /**
     *
     * addUser adds a new user's details to the database.
     *
     * @param username - client's username.
     * @param password - client's password.
     * @param screenName - client's screenName.
     * @param name - client's name.
     * @param surname - client's surname.
     * @param dateOfBirth - client's dateOfBirth.
     * @param gender - client's gender.
     * @param email - client's email.
     * @return returns true if user details was successfully stored in database
     * or false if it failed.
     */
    public boolean addUser(String username, String password, String screenName, String name, String surname, String dateOfBirth, boolean gender, String email) {
        try {
            Statement stmt = conn.createStatement();
            int intGender = gender ? 1 : 0;//convert boolean to integer, true = 1 and false = 0
            String query = "INSERT INTO user (username,password,screenName,name,surname,dateOfBirth,gender,email,zombieKills) VALUES ('" + username + "','" + password + "','" + screenName + "','" + name + "','" + surname + "','" + dateOfBirth + "'," + intGender + ",'" + email + "',0)";
            stmt.executeUpdate(query);
            return true;
        } catch (Exception exc) {
            System.out.println("Add user error:" + exc);
            return false;
            //TODO: Send exception to exception handler class to process. 
        }

    }

    /**
     *
     * login checks if the username and password is correct according to the
     * database.
     *
     * @param username - client's username.
     * @param password - client's password.
     * @return returns true if the username and password is correct and false if
     * they are not.
     */
    public boolean login(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT username FROM user WHERE password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String result = rs.getString("username");
            if (result.compareTo(username) == 0) {
                return true;
            }
            return false;

        } catch (Exception exc) {
            System.out.println("Login error:" + exc);
            return false;
            //TODO: Send exception to exception handler class to process. 
        }
    }

    /**
     *
     * getZombieKills returns the number of zombie kills of a client.
     *
     * @param username - client's username.
     * @return returns the client's number of zombie kills.
     */
    public int getZombieKills(String username) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT zombieKills FROM user WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String result = rs.getString("zombieKills");
            return Integer.parseInt(result);

        } catch (Exception exc) {
            System.out.println("Get zombie kills error:" + exc);
            return -1;
            //TODO: Send exception to exception handler class to process. 
        }
    }

    /**
     *
     * setZombieKills stores the client's number of zombie kills in the
     * database.
     *
     * @param username - client's username.
     * @param zombieKills - client's number of zombie kills.
     * @return - returns true if the client's number of zombie kills was stored
     * in the database or false if it failed.
     */
    public boolean setZombieKills(String username, int zombieKills) {
        try {
            Statement stmt = conn.createStatement();
            String query = "UPDATE user SET zombieKills = " + zombieKills + " WHERE username = '" + username + "'";
            stmt.executeUpdate(query);
            return true;
        } catch (Exception exc) {
            System.out.println("Set zombie kills error:" + exc);
            return false;
            //TODO: Send exception to exception handler class to process. 
        }
    }

    /**
     *
     * increaseZombieKillsByOne increases a client's number of zombie kills by
     * one.
     *
     * @param username - client's username.
     * @return returns true if the client's number of zombie kills is increase
     * with one in the database or false if it fails.
     */
    public boolean increaseZombieKillsByOne(String username) {
        try {
            Statement stmt = conn.createStatement();
            String query = "UPDATE user SET zombieKills = zombieKills + 1 WHERE username = '" + username + "'";
            stmt.executeUpdate(query);
            return true;
        } catch (Exception exc) {
            System.out.println("Increase zombie kills by one error:" + exc);
            return false;
            //TODO: Send exception to exception handler class to process. 
        }
    }

    /**
     *
     * changePassword changes the client's password in the database.
     *
     * @param username - client's username.
     * @param password - client's password.
     * @return returns true if the password is stored in the database or false
     * if it fails.
     */
    public boolean changePassword(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            String query = "UPDATE user SET password = '" + password + "' WHERE username = '" + username + "'";
            stmt.executeUpdate(query);
            return true;
        } catch (Exception exc) {
            System.out.println("Change password error:" + exc);
            return false;
            //TODO: Send exception to exception handler class to process. 
        }
    }

    /**
     *
     * getPassword retrieves the client's password from the database.
     *
     * @param username - client's username.
     * @return returns the client's password.
     */
    public String getPassword(String username) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT password FROM user WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String result = rs.getString("password");
            return result;
        } catch (Exception exc) {
            System.out.println("Get password error:" + exc);
            return "";
            //TODO: Send exception to exception handler class to process. 
        }

    }

    /**
     *
     * getMail retrieves the client's email address.
     *
     * @param username - client's username.
     * @return returns the client's email.
     */
    public String getMail(String username) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT email FROM user WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String result = rs.getString("email");
            return result;
        } catch (Exception exc) {
            System.out.println("Get email error:" + exc);
            return "";
            //TODO: Send exception to exception handler class to process. 
        }
    }
}
