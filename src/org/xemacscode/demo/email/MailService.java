/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo.email;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 *
 * @author nikoa
 */
public class MailService {
    
    Session session;

    public MailService() {
        Properties properties = getSmtpProperties();

        session = Session.getInstance(properties, getAuthenticator());
    }

    /**
     *
     * @param recipients of the email
     * @param subject of the email
     * @param message of the email
     */
    public void sendEmail(List<String> recipients, String subject, String message) {
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("javatimescheduler@gmail.com"));
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
     * @return Authenticator with password
     */
    private static Authenticator getAuthenticator() {
        return new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("javatimescheduler@gmail.com", "javajava");
            }
        };
    }

    /**
     * @return Properties for Gmail SMTP Server
     */
    private static Properties getSmtpProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }
}
