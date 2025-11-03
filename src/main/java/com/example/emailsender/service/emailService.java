package com.example.emailsender.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class emailService {

    public static void sendEmail(String recipients, String subject, String body) throws Exception {
        // Load config.properties
        Properties config = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/config.properties/config.properties")) {
            config.load(input);
        }

        final String username = config.getProperty("mail.smtp.user");
        final String password = config.getProperty("mail.smtp.password");

        if (username == null || password == null) {
            throw new Exception("Missing username or password in config.properties");
        }

        // Setup SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", config.getProperty("mail.smtp.auth", "true"));
        props.put("mail.smtp.starttls.enable", config.getProperty("mail.smtp.starttls.enable", "true"));
        props.put("mail.smtp.host", config.getProperty("mail.smtp.host", "smtp.gmail.com"));
        props.put("mail.smtp.port", config.getProperty("mail.smtp.port", "587"));

        // Authenticate session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Build email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipients)
        );
        message.setSubject(subject);
        message.setText(body);

        // Send email
        Transport.send(message);
    }
}
