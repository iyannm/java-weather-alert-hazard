package com.example.emailsender.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class DashboardController {

    // Runs when "Send Email" button is clicked
    @FXML
    private void handleSendEmail() {
        try {
            // Load the email GUI page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gui.fxml"));
            Parent root = loader.load();

            // Create a new window for the email sender
            Stage stage = new Stage();
            stage.setTitle("Send Email");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to load Email page");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Runs when "Contacts" button is clicked
    @FXML
    private void handleContacts() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contacts");
        alert.setHeaderText(null);
        alert.setContentText("Contacts button clicked!");
        alert.showAndWait();
    }
}