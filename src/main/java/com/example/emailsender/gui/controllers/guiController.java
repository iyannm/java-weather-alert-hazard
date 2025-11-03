package com.example.emailsender.gui.controllers;

import com.example.emailsender.service.emailService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class guiController {

    @FXML
    private TextField txtTo;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtBody;

    @FXML
    private Button btnSend;

    @FXML
    private void initialize() {
        btnSend.setOnAction(e -> handleSendEmail());
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
}
