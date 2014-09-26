package CorpseSlasherServer;

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
 * AuditLog saves all the types of server request a client makes to a text file
 * and also saves the the user's username that made the request. The time and 
 * date is also saved.
 */

public class AuditLog {
    
    /**
     * writeAudit saves all the types of server request a client makes to a text file
     * and also saves the the user's username that made the request. The time and 
     * date is also saved.
     * 
     * @param username - the client's username.
     * @param queryType - the type of request the client made to the server.
     */
    
    static public void writeAudit(String username, String queryType)
    {
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("auditLog.txt", true)));
            out.println("Date: " + dateFormat.format(date) + ", Username: " + username + ", Query: " + queryType);
            out.flush();
            out.close();
        }catch (IOException e) {
            ExceptionHandler.catchException("AuditLog", "writeAudit", e.toString());
        }
    }
}

