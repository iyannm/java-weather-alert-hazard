package com.example.weather.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            // Load the second window if login is correct
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gui.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("WAH Email Project - Main Window");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

            // Close the login page
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
