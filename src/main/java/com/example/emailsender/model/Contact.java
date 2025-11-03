package com.example.emailsender.model;

public class Contact {
    private String name;
    private String email;

    // Default constructor needed by Jackson
    public Contact() {}

    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
