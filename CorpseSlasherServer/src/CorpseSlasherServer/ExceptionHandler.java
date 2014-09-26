package CorpseSlasherServer;

import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Laminin
 * @param Derivco ExceptionHandler will be used to raise exceptions throughout
 * the game. All methods within must be static and global variables need to be
 * created as private static or final.
 */
public final class ExceptionHandler {

    private ExceptionHandler() {
    }
    
    public static void catchException(String class_, String func, String error)
    {
        //Error log
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("errorLog.txt", true)));
            out.println("Date: " + dateFormat.format(date) + ", Class: " + class_ + ", Function: " + func + ", Error: " + error);
            out.flush();
            out.close();
        }catch (IOException e) {
            System.out.println("Error in ExceptionHandler when writing to error log: " + e.toString());
        }
    }
}
