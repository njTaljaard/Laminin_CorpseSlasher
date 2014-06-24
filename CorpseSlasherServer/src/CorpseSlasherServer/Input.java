package CorpseSlasherServer;

import org.json.*;

/**
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301
 *
 * Input will receive all the server calls from the client and send it to the
 * correct functions in DatabaseUdate.
 */
public class Input {

    public Input() {
    }

    /**
     * getInput will call the correct functions from DatabaseUpdate.
     *
     * @param value - The string received from the client.
     */
    public String getInput(String value) {
        try {
            JSONObject clientObj = new JSONObject(value);
            JSONObject obj = new JSONObject();
            DatabaseUpdate dbu = new DatabaseUpdate();
            switch (clientObj.get("type").toString()) {
                case "login": {
                    obj.put("username", clientObj.get("username").toString());
                    obj.put("password", clientObj.get("password").toString());
                    if (dbu.checkLogin(obj)) {
                        return "true";
                    } else {
                        return "false";
                    }
                }
                case "addUser": {
                    obj.put("username", clientObj.get("username").toString());
                    obj.put("password", clientObj.get("password").toString());
                    obj.put("name", clientObj.get("name").toString());
                    obj.put("surname", clientObj.get("surname").toString());
                    obj.put("email", clientObj.get("email").toString());
                    if (dbu.setNewUser(obj)) {
                        return "true";
                    } else {
                        return "false";
                    }
                }
                case "getKills": {
                    obj.put("username", clientObj.get("username").toString());
                    return Integer.toString(dbu.getKills(obj));
                }
                case "setKills": {
                    obj.put("username", clientObj.get("username").toString());
                    obj.put("zombieKills", clientObj.get("zombieKills").toString());
                    if (dbu.setKills(obj)) {
                        return "true";
                    } else {
                        return "false";
                    }
                }
                case "addOneKill": {
                    obj.put("username", clientObj.get("username").toString());
                    if (dbu.increaseKillsByOne(obj)) {
                        return "true";
                    } else {
                        return "false";
                    }
                }
                case "setPassword": {
                    obj.put("username", clientObj.get("username").toString());
                    obj.put("password", clientObj.get("password").toString());
                    if (dbu.setPassword(obj)) {
                        return "true";
                    } else {
                        return "false";
                    }
                }
                case "retrievePassword": {
                    obj.put("username", clientObj.get("username").toString());

                    if (dbu.retrievePassword(obj)) {
                        return "true";
                    } else {
                        return "false";
                    }
                }
                case "checkUsernameAvailable": {
                    obj.put("username", clientObj.get("username").toString());

                    if (dbu.checkUsernameAvailable(obj)) {
                        return "true";
                    } else {
                        return "false";
                    }
                }  
                default:
                    return "";
            }
        } catch (Exception exc) {
            System.out.println("Input error: " + exc);
        }
        return "";
    }
}
