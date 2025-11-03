package com.example.emailsender.gui.controllers;

import com.example.emailsender.model.SentEmail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SentHistoryController {

    @FXML private TableView<SentEmail> sentTable;
    @FXML private TableColumn<SentEmail, String> recipientColumn;
    @FXML private TableColumn<SentEmail, String> subjectColumn;
    @FXML private TableColumn<SentEmail, String> bodyColumn;
    @FXML private TableColumn<SentEmail, String> dateColumn;
    @FXML private TableColumn<SentEmail, String> statusColumn;
    @FXML private Button btnBack; // Back button

    private static final String JSON_FILE = "src/main/resources/sent_emails.json";

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");

    @FXML
    public void initialize() {
        recipientColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRecipients())
        );
        subjectColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSubject())
        );
        bodyColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBody())
        );
        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getDateSent() != null
                                ? cellData.getValue().getDateSent().format(formatter)
                                : ""
                )
        );
        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus())
        );

        refreshSentHistory();
    }

    @FXML
    public void refreshSentHistory() {
        List<SentEmail> emails = readSentEmailsFromJson();
        ObservableList<SentEmail> data = FXCollections.observableArrayList(emails);
        sentTable.setItems(data);
    }

    @FXML
    public void clearSentHistory() {
        File file = new File(JSON_FILE);
        if (file.exists()) {
            file.delete();
        }
        sentTable.getItems().clear();
    }

    private List<SentEmail> readSentEmailsFromJson() {
        File file = new File(JSON_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<SentEmail>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Back button handler
    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("WAH Email Project - Dashboard");
            Scene scene = new Scene(root, 800, 600);
            String css = getClass().getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) btnBack.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open Dashboard: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
