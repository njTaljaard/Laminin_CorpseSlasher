package CorpseSlasher;

import javax.swing.JDialog;
import javax.swing.JOptionPane;


/**
 * @author Laminin
 * @author Derivco
 * ExceptionHandler will be used to raise exceptions throughout the game. All 
 * methods within must be static and global variables need to be created as 
 * private static or final.
 */
public final class ExceptionHandler {
    
    private static JDialog dialog;
    
    private ExceptionHandler() {}
    
    public static void throwInformation(String message, String title) {
        dialog = new JOptionPane(message, JOptionPane.ERROR_MESSAGE,JOptionPane.DEFAULT_OPTION).createDialog(title);
        dialog.setAlwaysOnTop(true); 
        dialog.setVisible(true); 
        dialog.dispose();
    }
    
    public static void throwError(String message, String title) {
        dialog = new JOptionPane(message, JOptionPane.ERROR_MESSAGE,JOptionPane.DEFAULT_OPTION).createDialog(title);
        dialog.setAlwaysOnTop(true); 
        dialog.setVisible(true); 
        dialog.dispose();
    }
}
