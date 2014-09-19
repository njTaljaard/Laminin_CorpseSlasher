package CorpseSlasherServer;

import org.json.*;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
            /*if (type == "retrieveLeaderBoard")
            {
                auditLog(type,"unknown");
            }
            else
            {
                auditLog(type,clientObj.get("username").toString());
            }*/
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
                    obj.put("username", clientObj.get("username").toString());
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
                    obj.put("username", clientObj.get("username").toString());
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
                    obj.put("username", clientObj.get("username").toString());
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
                    case "retrieveLeaderBoard": {
                    return dbu.retriveLeaderBoard();
                }  
                default:
                    return "";
            }
        } catch (Exception exc) {
            System.out.println("Input error: " + exc);
        }
        return "";
    }
    
    public void auditLog(String username, String queryType)
    {
        
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(); //2014/08/06 15:59:48
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("auditLog.txt", true)));
            out.println(dateFormat.format(date) + " , Username : " + username + " , Query : " + queryType);
        }catch (IOException e) {
            //TODO: Send exception to exception handler class to process.
        }
    }
}
