package com.example.emailsender.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class startpage {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblWrong;

    @FXML
    private void handleBtnPlayClick() {
        String enteredEmail = txtEmail.getText();
        String enteredPassword = txtPassword.getText();

        // 🔍 Check if either email or password is wrong
        if (!enteredEmail.equalsIgnoreCase("uogwah2025@gmail.com") || !enteredPassword.equals("1234")) {
            lblWrong.setText("Email or password is incorrect");
            return;
        }

        try {
            // Load the GUI FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            // Create new stage for the main GUI
            Stage stage = new Stage();
            stage.setTitle("WAH Email Project - Dashboard");

            // Attach the CSS stylesheet
            String css = getClass().getResource("/css/style.css").toExternalForm();
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(css);

            stage.setScene(scene);
            stage.setMinWidth(800);  // Prevent too small window
            stage.setMinHeight(600);
            stage.show();

            // Close the login page
            Stage loginStage = (Stage) btnLogin.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            // Show an alert dialog on error instead of just printing
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to open the email GUI");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
}
