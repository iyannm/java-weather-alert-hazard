package com.example.emailsender.controller;

import com.example.emailsender.service.ContactManager;
import com.example.emailsender.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;

public class ContactsController {

    @FXML private TableView<Contact> contactsTable;
    @FXML private TableColumn<Contact, String> nameColumn;
    @FXML private TableColumn<Contact, String> emailColumn;

    private ObservableList<Contact> contacts;

    @FXML
    public void initialize() {
        contacts = FXCollections.observableArrayList(ContactManager.loadContacts());
        contactsTable.setItems(contacts);

        nameColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        emailColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail()));
    }

    @FXML
    private void onCreateContact() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Create New Contact");
        dialog.setContentText("Enter name and email (comma separated):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            String[] parts = input.split(",");
            if (parts.length == 2) {
                Contact contact = new Contact(parts[0].trim(), parts[1].trim());
                contacts.add(contact);
                ContactManager.saveContacts(contacts);
            } else {
                showError("Invalid input. Use format: Name, Email");
            }
        });
    }

    @FXML
    private void onEditContact() {
        Contact selected = contactsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a contact to edit.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selected.getName() + ", " + selected.getEmail());
        dialog.setHeaderText("Edit Contact");
        dialog.setContentText("Edit name and email (comma separated):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            String[] parts = input.split(",");
            if (parts.length == 2) {
                selected.setName(parts[0].trim());
                selected.setEmail(parts[1].trim());
                contactsTable.refresh();
                ContactManager.saveContacts(contacts);
            } else {
                showError("Invalid input. Use format: Name, Email");
            }
        });
    }

    @FXML
    private void onDeleteContact() {
        Contact selected = contactsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            contacts.remove(selected);
            ContactManager.saveContacts(contacts);
        } else {
            showError("Please select a contact to delete.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
