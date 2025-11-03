package com.example.emailsender.gui.controllers;

import com.example.emailsender.service.emailService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class guiController {

    @FXML
    private ComboBox<String> comboContacts;

    @FXML
    private TextField txtTo;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextArea txtBody;

    @FXML
    private Button btnSend;

    private List<Contact> contactsList;

    @FXML
    private void initialize() {
        loadContactsFromJson();

        // Populate ComboBox with names
        if (contactsList != null) {
            comboContacts.getItems().addAll(
                    contactsList.stream()
                            .map(Contact::getName)
                            .collect(Collectors.toList())
            );
        }

        // When a contact is selected, populate txtTo with the corresponding email
        comboContacts.setOnAction(e -> {
            String selectedName = comboContacts.getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                contactsList.stream()
                        .filter(c -> c.getName().equals(selectedName))
                        .findFirst()
                        .ifPresent(c -> txtTo.setText(c.getEmail()));
            }
        });

        btnSend.setOnAction(e -> handleSendEmail());
    }

    private void loadContactsFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            contactsList = mapper.readValue(new File("contacts.json"), new TypeReference<List<Contact>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load contacts: " + e.getMessage());
        }
    }

    private void handleSendEmail() {
        String to = txtTo.getText().trim();
        String subject = txtSubject.getText().trim();
        String body = txtBody.getText().trim();

        if (to.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            emailService.sendEmail(to, subject, body);
            showAlert("Success", "Email sent successfully to: " + to);
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to send email: " + ex.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Inner class to map JSON contacts
    public static class Contact {
        private String name;
        private String email;

        public Contact() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
