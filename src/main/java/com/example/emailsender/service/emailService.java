package com.example.emailsender.service;

import com.example.emailsender.model.SentEmail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class emailService {


    private static final String JSON_FILE = "src/main/resources/sent_emails.json";


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

        // Log email to JSON file
        logSentEmail(recipients, subject, body, "Sent");
    }

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());  // support LocalDateTime

    private static void logSentEmail(String recipients, String subject, String body, String status) {
        try {
            List<SentEmail> emails;
            File file = new File(JSON_FILE);
            if (file.exists()) {
                emails = objectMapper.readValue(file, new com.fasterxml.jackson.core.type.TypeReference<List<SentEmail>>() {});
            } else {
                emails = new ArrayList<>();
            }

            // Add new email with body included
            emails.add(new SentEmail(recipients, subject, body, LocalDateTime.now(), status));

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, emails);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
