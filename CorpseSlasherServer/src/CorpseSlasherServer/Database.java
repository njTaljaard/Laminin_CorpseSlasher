
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CorpseSlasherServer;
import java.sql.*;
/**
 *
 * @author Martin
 */
public class Database {
    Connection conn = null;
   
    public boolean connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();//load driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/corpseslasher","root","");//establish connection
            System.out.println("connected to database");
            return true;
        }
        catch (Exception exc)
        {
            System.out.println("Database connection error:"+exc);
            return false;
            //TODO: Send exception to exception handler class to process. 
        }
    }
    
    public boolean addUser(String username,String password,String screenName,String name, String surname, String dateOfBirth, boolean gender, String email)
    {
        try {
            Statement stmt = conn.createStatement();
            int intGender = gender?1:0;//convert boolean to integer, true = 1 and false = 0
            String query = "INSERT INTO user (username,password,screenName,name,surname,dateOfBirth,gender,email,zombieKills) VALUES ('"+username+"','"+password+"','"+screenName+"','"+name+"','"+surname+"','"+dateOfBirth+"',"+intGender+",'"+email+"',0)";
            //ResultSet rs = stmt.executeQuery(query);
            stmt.executeUpdate(query);
            return true;
        }
        catch (Exception exc)
        {
            System.out.println("Add user error:"+exc);
            return false;
            //TODO: Send exception to exception handler class to process. 
        }
        
    }
    
}
