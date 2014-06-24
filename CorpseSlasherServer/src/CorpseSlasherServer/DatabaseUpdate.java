package CorpseSlasherServer;

import org.json.*;

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

    /**
     * setNewUser sends a new user's details to the database class.
     *
     * @param JSONObj - has all the new user's details
     * @return - returns true if user details was sent to the Database class or
     * returns false if failed.
     */
    public boolean setNewUser(JSONObject JSONObj) {
        try {
            Database db = new Database();
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
            Database db = new Database();
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
            Database db = new Database();
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
            Database db = new Database();
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
            Database db = new Database();
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
            Database db = new Database();
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
            Database db = new Database();
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
            Database db = new Database();
            db.connect();
            String username = JSONObj.get("username").toString();
            Email mail = new Email();
            mail.sendMail(db.getMail(username), "Corpse Slasher Password", "Your Corpse Slasher Password: " + db.getPassword(username));
            return true;
        } catch (Exception exc) {
            return false;
        }
    }
}
