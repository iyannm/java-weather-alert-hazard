package com.example.emailsender.service;

import com.example.emailsender.model.Contact;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContactManager {

    private static final String CONTACTS_FILE = "src/main/resources/contacts.json";

    private static final ObjectMapper mapper = new ObjectMapper();

    // Load contacts from JSON
    public static List<Contact> loadContacts() {
        File file = new File(CONTACTS_FILE);
        if (!file.exists()) {
            return FXCollections.observableArrayList(); // return empty list if file doesn't exist
        }

        try {
            return mapper.readValue(file, new TypeReference<List<Contact>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    // Save contacts to JSON
    public static void saveContacts(List<Contact> contacts) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONTACTS_FILE), contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
