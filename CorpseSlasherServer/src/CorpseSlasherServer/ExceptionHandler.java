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
    
    
    /**
     * catchException - takes an error and calls the writeError function in main,
     * so that the error can be written to the error log.
     * 
     * 
     * @param class_ - Class name of the class that contains the error.
     * @param func - Function name of the function that contains the error.
     * @param error - the error details.
     */
    
    public static void catchException(String class_, String func, String error)
    {
        //Error log
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            Main.writeError("Date: " + dateFormat.format(date) + ", Class: " + class_ + ", Function: " + func + ", Error: " + error);
        }catch (Exception e) {
            System.out.println("Error in ExceptionHandler when writing to error log: " + e.toString());
        }
    }
}
