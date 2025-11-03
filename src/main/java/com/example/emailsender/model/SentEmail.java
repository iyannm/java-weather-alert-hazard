package com.example.emailsender.model;

import java.time.LocalDateTime;

public class SentEmail {
    private String recipients;
    private String subject;
    private String body;          // NEW
    private LocalDateTime dateSent;
    private String status;

    // Constructor
    public SentEmail(String recipients, String subject, String body, LocalDateTime dateSent, String status) {
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;        // NEW
        this.dateSent = dateSent;
        this.status = status;
    }

    // Default constructor needed by Jackson
    public SentEmail() {}

    // Getters & setters
    public String getRecipients() { return recipients; }
    public void setRecipients(String recipients) { this.recipients = recipients; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }           // NEW
    public void setBody(String body) { this.body = body; } // NEW

    public LocalDateTime getDateSent() { return dateSent; }
    public void setDateSent(LocalDateTime dateSent) { this.dateSent = dateSent; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}