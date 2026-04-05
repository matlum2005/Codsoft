package com.studentmgmt.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.studentmgmt.model.Student;
import com.studentmgmt.service.HttpService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentDialogController implements Initializable {
    @FXML private TextField nameField, rollNumberField, emailField, ageField;
    private MainController mainController;
    private Student student;
    private Stage dialogStage;
    private boolean isNew = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Event handlers already in FXML or handled in code
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setStudent(Student student) {
        this.student = student;
        if (student != null) {
            isNew = false;
            nameField.setText(student.getName());
            rollNumberField.setText(student.getRollNumber());
            emailField.setText(student.getEmail());
            ageField.setText(student.getAge() != null ? student.getAge().toString() : "");
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void saveStudent() {
        try {
            Student newStudent = new Student();
            newStudent.setName(nameField.getText().trim());
            newStudent.setRollNumber(rollNumberField.getText().trim());
            newStudent.setEmail(emailField.getText().trim());
            newStudent.setAge(Integer.parseInt(ageField.getText().trim()));

            if (newStudent.getName().isEmpty() || newStudent.getRollNumber().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Name and Roll Number are required.");
                return;
            }

            if (isNew) {
                HttpService.createStudent(newStudent).thenRun(() -> {
                    Platform.runLater(() -> {
                        dialogStage.close();
                        mainController.loadStudents();
                    });
                }).exceptionally(ex -> {
                    Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage()));
                    return null;
                });
            } else {
                HttpService.updateStudent(student.getId(), newStudent).thenRun(() -> {
                    Platform.runLater(() -> {
                        dialogStage.close();
                        mainController.loadStudents();
                    });
                }).exceptionally(ex -> {
                    Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage()));
                    return null;
                });
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Age must be a valid number.");
        }
    }

    @FXML
    private void cancel() {
        dialogStage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initOwner(dialogStage);
        alert.showAndWait();
    }
}
