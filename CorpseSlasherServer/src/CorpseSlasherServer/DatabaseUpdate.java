package CorpseSlasherServer;

import org.json.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301
 *
 * DatabaseUpdate receives JSON objects from the Input class and sends and
 * request the data from the Database class.
 */
public class DatabaseUpdate {
    
    public Database db = new Database();
    
    /**
     * key - is the secret key used for the encryption.
     */
    private static byte[] key = {
            0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
    };//"thisIsASecretKey";

    /**
     * setNewUser sends a new user's details to the database class.
     *
     * @param JSONObj - has all the new user's details
     * @return - returns true if user details was sent to the Database class or
     * returns false if failed.
     */
    public boolean setNewUser(JSONObject JSONObj) {
        try {
            db.connect();
            db.addUser(JSONObj.get("username").toString(), JSONObj.get("password").toString(), JSONObj.get("name").toString(), JSONObj.get("surname").toString(), JSONObj.get("email").toString());
            return true;
        } catch (Exception exc) {
            System.out.println("Set new user error: " + exc.toString());
            return false;
        }
    }

    /**
     *
     * checkLogin sends the client username and password to the Database class.
     *
     * @param JSONObj - JSON object containing client username and password.
     * @return - return true if login succeeded and false if failed.
     */
    public boolean checkLogin(JSONObject JSONObj) {
        try {
            db.connect();
            if (db.login(JSONObj.get("username").toString(), JSONObj.get("password").toString())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exc) {

            return false;
        }
    }

    /**
     * checkUsernameAvailable - sends to username to the database in a JSON
     * object to check for availability.
     *
     * @param JSONObj - JSON object containing the client's username.
     *
     * @return - returns true is username is available and false if not.
     */
    public boolean checkUsernameAvailable(JSONObject JSONObj) {
        try {
            db.connect();
            if (db.availableUsername(JSONObj.get("username").toString())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exc) {
            return false;
        }
    }

    /**
     *
     * getKills returns the zombie kills of the client by receiving it from the
     * Database class.
     *
     * @param JSONObj - JSON object containing the client username.
     * @return - return the client zombie kills.
     */
    public int getKills(JSONObject JSONObj) {
        try {
            db.connect();
            return db.getZombieKills(JSONObj.get("username").toString());
        } catch (Exception exc) {
            return -1;
        }
    }

    /**
     *
     * setZombies sends the zombie kills of a client to the Database class.
     *
     * @param JSONObj - JSON object containing the client username and zombie
     * kills.
     * @return - return true if the zombie kills where sent to the Database
     * class or false if it failed.
     */
    public boolean setKills(JSONObject JSONObj) {
        try {
            db.connect();
            if (db.setZombieKills(JSONObj.get("username").toString(), Integer.parseInt(JSONObj.get("zombieKills").toString()))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exc) {

            return false;
        }
    }

    /**
     *
     * increaseKillsByOne sends the client username to the Database class.
     *
     * @param JSONObj - JSON object containing the client username.
     * @return returns true if client username was sent successfully to the
     * Database class or false if it failed.
     */
    public boolean increaseKillsByOne(JSONObject JSONObj) {
        try {
            db.connect();
            if (db.increaseZombieKillsByOne(JSONObj.get("username").toString())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exc) {

            return false;
        }
    }

    /**
     *
     * setPassword sends the client username and password to the Database class.
     *
     * @param JSONObj - JSON object containing the client username and password.
     * @return returns true if the client username and password sent to the
     * Database class successfully or false if it failed.
     */
    public boolean setPassword(JSONObject JSONObj) {
        try {
            db.connect();
            if (db.changePassword(JSONObj.get("username").toString(), JSONObj.get("password").toString())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exc) {
            return false;
        }
    }

    /**
     *
     * retrievePassword sends an email the the client containing his or her
     * password.
     *
     * @param JSONObj - JSON object containing the client's username.
     * @return - returns true if email is sent or false if the sent email false.
     */
    public boolean retrievePassword(JSONObject JSONObj) {
        try {
            db.connect();
            String username = JSONObj.get("username").toString();
            String pass = decrypt(db.getPassword(username));
            Email mail = new Email();
            mail.sendMail(db.getMail(username), "Corpse Slasher Password", "Your Corpse Slasher Password: " + pass.substring(pass.indexOf('/') + 1));
            return true;
        } catch (Exception exc) {
            return false;
        }
    }
    
    /**
     *
     * retrievePasswordInputEmail sends an email the the client containing his or her
     * password.
     *
     * @param JSONObj - JSON object containing the client's email address.
     * @return - returns true if email is sent or false if the sent email false.
     */
    public boolean retrievePasswordInputEmail(JSONObject JSONObj) {
        try {
            db.connect();
            String email = JSONObj.get("email").toString();
            String username = db.getUsername(email);
            String pass = decrypt(db.getPassword(username));
            Email mail = new Email();
            mail.sendMail(email, "Corpse Slasher Password", "Your Corpse Slasher Password: " + pass.substring(pass.indexOf('/') + 1));
            return true;
        } catch (Exception exc) {
            return false;
        }
    }
    
    /**
     * retriveLeaderBoard - converts the two dimensional string array representation
     * of the board, to a comma separated string.
     * 
     * @return - returns a comma separated string representation of the leader board.
     */
    
    public String retriveLeaderBoard()
    {
        try
        {
            String leaderBoard = "";
            db.connect();
            String[][] tmp = db.getLeaderBoard();
            for (int i = 0; i < tmp[0].length; i++)
            {
                leaderBoard += tmp[0][i] + "," + tmp[1][i] + "," + tmp[2][i] + ",";
            }
            return leaderBoard.substring(0, leaderBoard.length() - 1);
        }
        catch (Exception exc)
        {
            return "";
        }
    }
    
    /**
     * 
     * encrypt - takes a string as input and return the encrypted string.
     * 
     * @param strToEncrypt  string that needs to be encrypted.
     * @return returns the encrypted string.
     */
     public static String encrypt(String strToEncrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            final String encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
            return encryptedString;
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        return null;

    }

     /**
      * 
      * decrypt - takes an encrypted string as input and returns the decrypted version of the input.
      * 
      * @param strToDecrypt string that needs to be decrypted.
      * @return returns the decrypted string of the input string.
      */
    public static String decrypt(String strToDecrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
            return decryptedString;
        }
        catch (Exception e)
        {
          e.printStackTrace();

        }
        return null;
    }
    
}
