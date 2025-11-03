package com.example.emailsender.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    // Buttons from FXML
    @FXML
    private Button sendEmailButton;

    @FXML
    private Button contactsButton;

    @FXML
    private Button sentHistoryButton;

    @FXML
    private Button logoutButton;

    // Utility method to open a new FXML window
    private void openWindow(String fxmlPath, String title, Button sourceButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);

            Scene scene = new Scene(root, 800, 600); // Resolution 800x600
            String css = getClass().getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.show();

            // Close the current window (dashboard)
            Stage currentStage = (Stage) sourceButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to open window");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSendEmail() {
        openWindow("/fxml/gui.fxml", "WAH Email Project - Send Email", sendEmailButton);
    }

    @FXML
    private void handleContacts() {
        openWindow("/fxml/contacts.fxml", "WAH Email Project - Contacts", contactsButton);
    }



    @FXML
    private void handleSentHistory() {
        openWindow("/fxml/sent_history.fxml", "WAH Email Project - Sent History", sentHistoryButton);
    }

    @FXML
    private void handleLogout() {
        openWindow("/fxml/startpage.fxml", "WAH Email Project - Login", logoutButton);
    }

}
