package com.example.project.mail;


import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class sendMail {

    private String to;
    private String from;
    private String password;
    private String subject;
    public Boolean check = true;

    public sendMail(String to, String from, String password, String subject) {
        this.to = to;
        this.from = from;
        this.password = password;
        this.subject = subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void sendContent(String content) {

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();


        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });


        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
            check = true;
        } catch (AuthenticationFailedException e) {
            System.out.println("Wrong username or password!");
            check = false;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            check = false;
        }

    }


    public void send(File file) {
        // Recipient's email ID needs to be mentioned.

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            MimeMultipart msgMultiPart = new MimeMultipart();
            MimeBodyPart msgBodyPart = new MimeBodyPart();

            // Create a default MimeMessage object.
            msgBodyPart.setFileName(file.getName());
            DataSource source = new FileDataSource(file);
            msgBodyPart.setDataHandler(new DataHandler(source));

            msgMultiPart.addBodyPart(msgBodyPart);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(msgMultiPart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}