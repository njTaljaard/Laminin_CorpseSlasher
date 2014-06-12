package CorpseSlasherServer;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301
 *
 * Email - sends out an email. Email sends out an email.
 */
public class Email {

    /**
     * Variables that contains the server email details.
     */
    private static String USER_NAME = "corpseslasher.laminin";
    private static String PASSWORD = "GMN3CS2L1";

    /**
     * sendMail sends out an email.
     *
     * @param to - email address of client.
     * @param subject - subject of email.
     * @param body - body of email.
     * @return - returns true if email is sent and false if not sent.
     */
    public boolean sendMail(String to, String subject, String body) {
        String from = USER_NAME + "@gmail.com";
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);

            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (AddressException ae) {
            ae.printStackTrace();
            return false;
        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
    }
}
