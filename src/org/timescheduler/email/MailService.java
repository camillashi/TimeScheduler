package org.timescheduler.email;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Sends emails
 *
 * @author nikoa
 */
public class MailService {
    
    private static final String EMAIL_ADRESS = "javatimescheduler@gmail.com";
    private static final String EMAIL_PASSWORD = "javajava";
    
    Session session;
    

    public MailService() {
        // Configuration of email client
        Properties properties = getSmtpProperties();
        Authenticator authenticator = getAuthenticator();

        // Create a session for sending emails
        session = Session.getInstance(properties, authenticator);
    }

    /**
     * Send an email to a list of recipients with a subject and a message using a gmail account as sender
     * 
     * @param recipients of the email
     * @param subject of the email
     * @param message of the email
     */
    public void sendEmail(List<String> recipients, String subject, String message) {
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(EMAIL_ADRESS));
            for (Iterator<String> iterator = recipients.iterator(); iterator.hasNext();) {
                String recipient = iterator.next();
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get Authenticator for gmail email adress based on adress and password
     *
     * @return Authenticator with password
     */
    private Authenticator getAuthenticator() {
        return new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_ADRESS, EMAIL_PASSWORD);
            }
        };
    }

    /**
     * Get Configuration for gmail SMTP server.
     * Sets host and port based on gmail documentation.
     * Also enables SSL and authentification because this is required on gmail.
     * 
     * @return Properties for Gmail SMTP Server
     */
    private Properties getSmtpProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }
}
