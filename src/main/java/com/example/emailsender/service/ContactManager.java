package com.example.emailsender.service;

import com.example.emailsender.model.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private static final String CONTACTS_FILE = "contacts.json";
    private static final Gson gson = new Gson();

    public static List<Contact> loadContacts() {
        try (Reader reader = new FileReader(CONTACTS_FILE)) {
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void saveContacts(List<Contact> contacts) {
        try (Writer writer = new FileWriter(CONTACTS_FILE)) {
            gson.toJson(contacts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

