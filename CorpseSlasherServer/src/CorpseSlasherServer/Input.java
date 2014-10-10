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
            String type = clientObj.get("type").toString();
            if (type.equals("retrieveLeaderBoard") || type.equals("retrievePasswordInputEmail") || type.equals("getOAuthKills")|| type.equals("setOAuthKills")|| type.equals("addOAuthOneKill")|| type.equals("checkOauthIdAvailable"))
            {
                AuditLog.writeAudit("unknown", type);
            }
            else
            {
                AuditLog.writeAudit(clientObj.get("username").toString(), type);
            }
            switch (type) {
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
                case "addOAuthUser": {
                    obj.put("id", clientObj.get("id").toString());
                    obj.put("username", clientObj.get("username").toString());
                    if (dbu.setOAuthNewUser(obj)) {
                        return "true";
                    } else {
                        return "false";
                    }
                }
                case "getKills": {
                    obj.put("username", clientObj.get("username").toString());
                    return Integer.toString(dbu.getKills(obj));
                }
                case "getOAuthKills": {
                    obj.put("id", clientObj.get("id").toString());
                    return Integer.toString(dbu.getOAuthKills(obj));
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
                case "setOAuthKills": {
                    obj.put("id", clientObj.get("id").toString());
                    obj.put("zombieKills", clientObj.get("zombieKills").toString());
                    if (dbu.setOAuthKills(obj)) {
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
                case "addOAuthOneKill": {
                    obj.put("id", clientObj.get("id").toString());
                    if (dbu.increaseOAuthKillsByOne(obj)) {
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
                case "retrievePasswordInputEmail": {
                    obj.put("email", clientObj.get("email").toString());

                    if (dbu.retrievePasswordInputEmail(obj)) {
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
                case "checkOauthIdAvailable": {
                    obj.put("id", clientObj.get("id").toString());

                    if (dbu.checkOauthIdAvailable(obj)) {
                        return "true";
                    } else {
                        return "false";
                    }
                }
                case "retrieveLeaderBoard": {
                    return dbu.retriveLeaderBoard();
                }  
                default:
                    return "";
            }
        } catch (Exception exc) {
            //ExceptionHandler.catchException("Input", "getInput", exc.toString());
        }
        return "";
    }
}
