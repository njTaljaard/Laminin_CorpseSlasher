package CorpseSlasherServer;

//import com.sun.istack.internal.logging.Logger;
import java.sql.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;

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
     * @param name - client's name.
     * @param surname - client's surname.
     * @param email - client's email.
     * @return returns true if user details was successfully stored in database
     * or false if it failed.
     */
    public boolean addUser(String username, String password, String name, String surname, String email) {
        try {
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO user (username,password,name,surname,email,zombieKills,experiencePoints) VALUES ('" + username + "',AES_ENCRYPT('" + password + "', SHA1('9876543210')),'" + name + "','" + surname + "','" + email + "',0,0)";
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
            String query = "SELECT username FROM user WHERE password = AES_ENCRYPT('" + password + "', SHA1('9876543210'))";
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
            String query = "UPDATE user SET password = AES_ENCRYPT('" + password + "'SHA1('9876543210')) WHERE username = '" + username + "'";
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
            String query = "SELECT AES_DECRYPT(password, SHA1('9876543210')) FROM user WHERE username = '" + username + "'";
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
     * getUsername retrieves the client's username from the database.
     *
     * @param email - client's email.
     * @return returns the client's username.
     */
    public String getUsername(String email) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT username FROM user WHERE email = '" + email + "'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String result = rs.getString("username");
            return result;
        } catch (Exception exc) {
            System.out.println("Get username error:" + exc);
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

    /**
     * availableUsername - check if the username does not already exist in the
     * database.
     *
     * @param username - client's username.
     *
     * @return - returns true if the database does not contain the username and
     * false if the database does contain the username.
     */
    public boolean availableUsername(String username) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT username FROM user WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String result = rs.getString("username");
            if (result.compareTo(username) == 0) {
                return false;
            }
            return true;

        } catch (Exception exc) {
            System.out.println("available username error:" + exc);
            return true;
            //TODO: Send exception to exception handler class to process. 
        }
    }
    
    /**
     * getLeaderBoard - Retrieves the needed data from tables user and oauth_user
     * and converts it to a two dimensional array representation of the leader board.
     * 
     * @return - returns a two dimensional string array, containing the leader board.
     */
    
    public String[][] getLeaderBoard()
    {
        try
        {
            Statement stmt = conn.createStatement();
            
            String queryNumUsers = "SELECT COUNT(*) FROM user";
            ResultSet rsNumUsers = stmt.executeQuery(queryNumUsers);
            rsNumUsers.next();
            int numRowsUser = Integer.parseInt(rsNumUsers.getString("COUNT(*)"));
            String[][] arrUser = new String[3][numRowsUser];
            
            String query = "SELECT * FROM user ORDER BY zombieKills DESC";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            for (int i = 0; i < numRowsUser; i++)
            {
                arrUser[0][i] = rs.getString("name") + " " + rs.getString("surname");
                arrUser[1][i] = rs.getString("zombieKills");
                arrUser[2][i] = rs.getString("experiencePoints");
                rs.next();
            }
            
            String queryNumOAuthUsers = "SELECT COUNT(*) FROM oauth_user";
            ResultSet rsNumOAuthUsers = stmt.executeQuery(queryNumOAuthUsers);
            rsNumOAuthUsers.next();
            int numRowsOAuthUser = Integer.parseInt(rsNumOAuthUsers.getString("COUNT(*)"));
            String[][] arrOAuthUser = new String[3][numRowsOAuthUser];
            
            String query1 = "SELECT * FROM oauth_user ORDER BY zombieKills DESC";
            ResultSet rs1 = stmt.executeQuery(query1);
            rs1.next();
            for (int i = 0; i < numRowsOAuthUser; i++)
            {
                arrOAuthUser[0][i] = rs1.getString("name");
                arrOAuthUser[1][i] = rs1.getString("zombieKills");
                arrOAuthUser[2][i] = rs1.getString("experiencePoints");
                rs1.next();
            }
            
            int arrFinalLength = arrUser[0].length + arrOAuthUser[0].length;
            
            String[][] arrFinal = new String[3][arrFinalLength];
            int user = 0;
            int oauth = 0;
            for (int i = 0; i < arrFinalLength; i++)
            {
                
                if (user == numRowsUser)
                {
                    arrFinal[0][i] = arrOAuthUser[0][oauth];
                    arrFinal[1][i] = arrOAuthUser[1][oauth];
                    arrFinal[2][i] = arrOAuthUser[2][oauth];
                    oauth++;
                    
                }
                else if (oauth == numRowsOAuthUser)
                {
                    arrFinal[0][i] = arrUser[0][user];
                    arrFinal[1][i] = arrUser[1][user];
                    arrFinal[2][i] = arrUser[2][user];
                    user++;
                }
                else if (Integer.parseInt(arrUser[2][user]) > Integer.parseInt(arrOAuthUser[2][oauth]))
                {
                    arrFinal[0][i] = arrUser[0][user];
                    arrFinal[1][i] = arrUser[1][user];
                    arrFinal[2][i] = arrUser[2][user];
                    user++;
                }
                else
                {
                    arrFinal[0][i] = arrOAuthUser[0][oauth];
                    arrFinal[1][i] = arrOAuthUser[1][oauth];
                    arrFinal[2][i] = arrOAuthUser[2][oauth];
                    oauth++;
                }
                
            }
            
            return arrFinal;
        }
        catch (Exception exc)
        {
            System.out.println("Leader board error:" + exc);
            //Logger.getLogger(Database.class).log(Level.SEVERE, null,exc);
            return null;
            //TODO: Send exception to exception handler class to process. 
        }
        
    }
}
