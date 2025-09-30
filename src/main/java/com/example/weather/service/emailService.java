package com.example.weather.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class emailService {
    public static void main(String[] args) {
        try {
            // Load config.properties
            Properties config = new Properties();
            try (InputStream input = new FileInputStream("src/main/resources/config.properties/config.properties")) {
                config.load(input);
            }


            // Make sure these match the keys in config.properties
            final String username = config.getProperty("mail.smtp.user");
            final String password = config.getProperty("mail.smtp.password");

            if (username == null || password == null) {
                System.out.println(" Missing username or password in config.properties");
                return;
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

            // Take input from console
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter recipient email(s) (comma separated): ");
            String recipients = scanner.nextLine().trim();

            System.out.print("Enter subject: ");
            String subject = scanner.nextLine().trim();

            System.out.print("Enter message body: ");
            String body = scanner.nextLine().trim();

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
            System.out.println("Email sent successfully to: " + recipients);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
