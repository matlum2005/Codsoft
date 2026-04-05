package com.studentmgmt.controller;

import java.io.IOException;

import com.studentmgmt.model.Student;
import com.studentmgmt.service.HttpService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Long> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> rollNumberColumn;
    @FXML private TableColumn<Student, String> emailColumn;
    @FXML private TableColumn<Student, Integer> ageColumn;
    @FXML private TextField searchField;
    @FXML private Button addButton, updateButton, deleteButton, refreshButton, searchButton;

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();
    private Student selectedStudent;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        rollNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rollNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        studentTable.setItems(studentList);
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newValue) -> {
            selectedStudent = newValue;
            updateButton.setDisable(newValue == null);
            deleteButton.setDisable(newValue == null);
        });

        loadStudents();
    }

@FXML
    public void loadStudents() {
        HttpService.getAllStudents().thenAccept(students -> Platform.runLater(() -> {
            System.out.println("Loaded " + students.size() + " students: " + students);
            studentList.setAll(students);
        })).exceptionally(ex -> {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load students: " + ex.getMessage());
            return null;
        });
    }

    @FXML
    private void addStudent() {
        showStudentDialog(null);
    }

    @FXML
    private void updateStudent() {
        if (selectedStudent != null) {
            showStudentDialog(selectedStudent);
        }
    }

    @FXML
    private void deleteStudent() {
        if (selectedStudent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Student");
            alert.setContentText("Are you sure you want to delete " + selectedStudent.getName() + "?");

            if (alert.showAndWait().orElse(null) == ButtonType.OK) {
                HttpService.deleteStudent(selectedStudent.getId()).thenRun(() -> Platform.runLater(this::loadStudents))
                        .exceptionally(ex -> {
                            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete: " + ex.getMessage()));
                            return null;
                        });
            }
        }
    }

    @FXML
    private void searchStudents() {
        String keyword = searchField.getText().trim();
        if (!keyword.isEmpty()) {
            HttpService.searchStudents(keyword).thenAccept(students -> Platform.runLater(() -> {
                studentList.setAll(students);
            })).exceptionally(ex -> {
                showAlert(Alert.AlertType.ERROR, "Error", "Search failed: " + ex.getMessage());
                return null;
            });
        } else {
            loadStudents();
        }
    }

    private void showStudentDialog(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/student-dialog.fxml"));
            VBox dialogContent = loader.load();
            StudentDialogController controller = loader.getController();
            controller.setStudent(student);
            controller.setMainController(this);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle(student == null ? "Add Student" : "Update Student");
            dialog.initModality(Modality.APPLICATION_MODAL);

            // Set custom content
            dialog.getDialogPane().setContent(dialogContent);

            // Add OK/Cancel buttons
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Pass stage to controller
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            controller.setDialogStage(stage);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    loadStudents();
                }
            });
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load dialog: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
