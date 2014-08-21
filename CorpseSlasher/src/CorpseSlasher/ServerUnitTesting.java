/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CorpseSlasher;

/**
 *
 * @author Martin
 */
public class ServerUnitTesting {

    
    public static boolean test()
    {
        ClientConnection.StartClientConnection();
        //if (createUser() && login())
        if (login() && usernameAvailble() && addKill() && getLeaderBoard() && logout())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static boolean createUser()
    {
        try
        {
            return ClientConnection.AddUser("test1", "tesrPass9876543210", "Martin", "Schoeman", "firstnamemartins@yahoo.com");
            
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest create user error: " + exc.toString());
            return false;
        }
    }
    
    public static boolean login()
    {
        try
        {
            return ClientConnection.Login("test1", "tesrPass9876543210");
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest login error: " + exc.toString());
            return false;
        }
    }
    
    public static boolean usernameAvailble()
    {
        try
        {
            return !ClientConnection.CheckUsernameAvailable("test1");
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest usernameAvailble error: " + exc.toString());
            return false;
        }
    }
    
    public static boolean logout()
    {
        try
        {
            return ClientConnection.Logout();
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest logout error: " + exc.toString());
            return false;
        }
    }
    
    public static boolean getLeaderBoard()
    {
        try
        {
            
            String board = ClientConnection.retrieveLeaderBoard();
            System.out.println(board);
            if (board == "")
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest get leader board error: " + exc.toString());
            return false;
        }
    }
    
    public static boolean addKill()
    {
        try
        {
            return ClientConnection.AddOneKill("test1");
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest add a kill error: " + exc.toString());
            return false;
        }
    }
    
}
