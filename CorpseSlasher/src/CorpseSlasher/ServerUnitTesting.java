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
        System.out.println("Start of server unit test.");
        System.out.println("/////////////////////////////////////////////");
        ClientConnection.StartClientConnection();
        //user one
        String username1 = "u10651994";
        String password1 = "9876543210";
        String name1 = "Martin";
        String surname1 = "Schoeman";
        String email1 = "firstnamemartins@yahoo.com";
        
        if (usernameAvailable(username1))
        {
            if(!createUser(username1, password1, name1, surname1, email1))
            {
                System.out.println("Unit test failed beacause of create user.");
                System.out.println("End of server unit test.");
                System.out.println("/////////////////////////////////////////////");
                return false;
            }
            else
            {
                System.out.println("Run unit test again, if same message appear then the create user or available username not working.");
                System.out.println("End of server unit test.");
                System.out.println("/////////////////////////////////////////////");
                return true;
            }
        }
        else
        {
            if (login(username1,"notRealPassword"))
            {
                System.out.println("Unit test failed beacause login working with wrong password.");
                System.out.println("End of server unit test.");
                System.out.println("/////////////////////////////////////////////");
                return false;
            }
            else
            {
                if (login(username1, password1))
                {
                    if (getLeaderBoard())
                    {
                        if (setKills(username1, "10"))
                        {
                            if (addKill(username1))
                            {
                                if (getKills(username1) == 11)
                                {
                                    System.out.println("Get password by username: " + getPassword(username1));
                                        System.out.println("Get password by email: " + getPasswordEmailInput(email1));
                                    if (logout())
                                    {
                                        System.out.println("Unit test was successful");
                                        System.out.println("End of server unit test.");
                                        System.out.println("/////////////////////////////////////////////");
                                        return true;
                                    }
                                    else
                                    {
                                        System.out.println("Unit test failed beacause of logout not working.");
                                        System.out.println("End of server unit test.");
                                        System.out.println("/////////////////////////////////////////////");
                                        return false;
                                    }
                                }
                                else
                                {
                                    System.out.println("Unit test failed beacause of set kills or add kill not calculating right.");
                                    System.out.println("End of server unit test.");
                                    System.out.println("/////////////////////////////////////////////");
                                    return false;
                                }
                            }
                            else
                            {
                                System.out.println("Unit test failed beacause of add kill not working.");
                                System.out.println("End of server unit test.");
                                System.out.println("/////////////////////////////////////////////");
                                return false;
                            }
                        }
                        {
                            System.out.println("Unit test failed beacause of set kills not working.");
                            System.out.println("End of server unit test.");
                            System.out.println("/////////////////////////////////////////////");
                            return false;
                        }
                    }
                    else
                    {
                        System.out.println("Unit test failed beacause of getLeaderBoard not working.");
                        System.out.println("End of server unit test.");
                        System.out.println("/////////////////////////////////////////////");
                        return false;
                    }
                }
                else
                {
                    System.out.println("Unit test failed beacause of login not working.");
                    System.out.println("End of server unit test.");
                    System.out.println("/////////////////////////////////////////////");
                    return false;
                }
            }
        }
    }
    
    /**
     * createUser test's the addUser function.
     * 
     * @param username - user's username.
     * @param password - user's password.
     * @param name - user's name.
     * @param surname - user's surname.
     * @param email - user's email.
     * @return true if it was successful and false if not.
     */
    public static boolean createUser(String username, String password, String name, String surname, String email)
    {
        try
        {
            return ClientConnection.AddUser(username, password, name, surname, email);
            
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
     * @param username - user's username.
     * @param password - user's password.
     * @return true if it was successful and false if not.
     */
    
    public static boolean login(String username, String password)
    {
        try
        {
            return ClientConnection.Login(username, password);
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest login error: " + exc.toString());
            return false;
        }
    }
    
    /**
     * usernameAvailable test's the CheckUsernameAvailable function.
     * 
     * @param username - user's username.
     * @return true if it was successful and false if not.
     */
    
    public static boolean usernameAvailable(String username)
    {
        try
        {
            return ClientConnection.CheckUsernameAvailable(username);
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
            System.out.println("Leader board in string format: " + board);
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
     * @param username - user's username.
     * @return true if it was successful and false if not.
     */
    
    public static boolean addKill(String username)
    {
        try
        {
            return ClientConnection.AddOneKill(username);
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
     * @param username - user's username.
     * @param numKills - number of the user's kills.
     * @return true if it was successful and false if not.
     */
    
    public static boolean setKills(String username, String numKills)
    {
        try
        {
            return ClientConnection.SetKills(username, numKills);
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
     * @param username - user's username.
     * @return true if it was successful and false if not.
     */
    
    public static int getKills(String username)
    {
         try
        {
            return ClientConnection.GetKills(username);
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
     * @param username - user's username.
     * @param password - user's password.
     * @return true if it was successful and false if not.
     */
    
    public static boolean setPassword(String username, String password)
    {
        try
        {
            return ClientConnection.SetPassword(username,password);
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
     * @param username - user's username.
     * @return true if it was successful and false if not.
     */
    
    public static boolean getPassword(String username)
    {
        try
        {
            return ClientConnection.RetrievePassword(username);
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
     * @param email - user's email
     * @return true if it was successful and false if not.
     */
    
    public static boolean getPasswordEmailInput(String email)
    {
        try
        {
            return ClientConnection.RetrievePasswordInputEmail(email);
        }
        catch (Exception exc)
        {
            System.out.println("ServerUnitTest get password with email as input error: " + exc.toString());
            return false;
        }
    }
    
}
