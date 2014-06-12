/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CorpseSlasherServer;

import org.json.*;
//import org.json.simple.JSONValue;
/**
 *
 * @author Martin
 */
public class DatabaseUpdate {
    public boolean setNewUser(JSONObject JSONObj) {
       try {
            Database db = new Database();
            db.connect();
            db.addUser(JSONObj.get("username").toString(), JSONObj.get("password").toString(), JSONObj.get("screenName").toString(), JSONObj.get("name").toString(), JSONObj.get("surname").toString(), JSONObj.get("dateOfBirth").toString(), (boolean)JSONObj.get("gender"), JSONObj.get("email").toString());
            return true;
       }
       catch (Exception exc) {
           
           return false;
       }
        
    }
}
