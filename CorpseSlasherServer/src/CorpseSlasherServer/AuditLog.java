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
 * AuditLog audits all the client's server request, by sending it to the function 
 * writeAudit in main where it gets written to the audit log.
 */

public class AuditLog {
    
    /**
     * writeAudit audits all the client's server request, by sending it to the function 
     * writeAudit in main where it gets written to the audit log.
     * 
     * @param username - the client's username.
     * @param queryType - the type of request the client made to the server.
     */
    
    static public void writeAudit(String username, String queryType)
    {
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            Main.writeAudit("Date: " + dateFormat.format(date) + ", Username: " + username + ", Query: " + queryType);
        }catch (Exception e) {
            ExceptionHandler.catchException("AuditLog", "writeAudit", e.toString());
        }
    }
}

