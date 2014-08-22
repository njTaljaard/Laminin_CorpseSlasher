package CorpseSlasher;

/**
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301
 *
 * ServerUnitTesting runs all the functions that the server offers the client, 
 * testing if they work.
 */
public class ServerUnitTesting {

    /**
     * test - runs all the other functions in the ServerUnitTesting class,
     * so the user only have to run this function to do the unit testing.
     * 
     * @return true if all the tests was successful and false if not.
     */
    public static boolean test()
    {
        ClientConnection.StartClientConnection();
        if (/*createUser() &&*/ login() && usernameAvailble() && addKill() && getLeaderBoard() && setKills() && setPassword() /*&& getPassword() && getPasswordEmailInput()*/)
        {
            System.out.println("User kills from ServerUnitTest: " + getKills());
            logout();
            return true;
        }
        else
        {
            logout();
            return false;
        }
    }
    
    /**
     * createUser test's the addUser function.
     * 
     * @return true if it was successful and false if not.
     */
    
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
    
    /**
     * login test's the Login function.
     * 
     * @return true if it was successful and false if not.
     */
    
    public static boolean login()
    {
        try
        {
            return ClientConnection.Login("test1", "newPassw0rd");
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest login error: " + exc.toString());
            return false;
        }
    }
    
    /**
     * usernameAvailble test's the CheckUsernameAvailable function.
     * 
     * @return true if it was successful and false if not.
     */
    
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
    
    /**
     * logout test's the Logout function.
     * 
     * @return true if it was successful and false if not.
     */
    
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
    
    /**
     * getLeaderBoard test's the retrieveLeaderBoard function.
     * 
     * @return true if it was successful and false if not.
     */
    
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
    
    /**
     * addKill test's the AddOneKill function.
     * 
     * @return true if it was successful and false if not.
     */
    
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
    
    /**
     * setKills test's the SetKills function.
     * 
     * @return true if it was successful and false if not.
     */
    
    public static boolean setKills()
    {
        try
        {
            return ClientConnection.SetKills("test1","111");
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest set kills error: " + exc.toString());
            return false;
        }
    }
    
    /**
     * getKills test's the GetKills function.
     * 
     * @return true if it was successful and false if not.
     */
    
    public static int getKills()
    {
         try
        {
            return ClientConnection.GetKills("test1");
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest get kills error: " + exc.toString());
            return -1;
        }
    }
    
    /**
     * setPassword test's the SetPassword function.
     * 
     * @return true if it was successful and false if not.
     */
    
    public static boolean setPassword()
    {
        try
        {
            return ClientConnection.SetPassword("test1","newPassw0rd");
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest set password error: " + exc.toString());
            return false;
        }
        
    }
    
    /**
     * getPassword test's the RetrievePassword function.
     * 
     * @return true if it was successful and false if not.
     */
    
    public static boolean getPassword()
    {
        try
        {
            return ClientConnection.RetrievePassword("test1");
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest get password error: " + exc.toString());
            return false;
        }
    }
    
    /**
     * getPasswordEmailInput test's the getPasswordEmailInput function.
     * 
     * @return true if it was successful and false if not.
     */
    
    public static boolean getPasswordEmailInput()
    {
        try
        {
            return ClientConnection.RetrievePasswordInputEmail("firstnamemartins@yahoo.com");
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest get password with email as input error: " + exc.toString());
            return false;
        }
    }
    
}
