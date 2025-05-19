package transport.control;

import transport.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import transport.Main;

import java.time.LocalDate;
import java.util.List;

public class AddUserController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private CheckBox handicapCheck;
    @FXML
    private ComboBox<String> userTypeCombo;
    @FXML
    private ComboBox<TypeFonction> functionCombo;

    private List<Personne> users;
    private MainController mainController;

    public void setMainController(MainController mc) {
        this.mainController = mc;
    }

    public void setUsers(List<Personne> users) {
        this.users = users;
        // populate immediately
        userTypeCombo.getItems().setAll("Passenger", "Employee");
        initialize(); // wire up listener
    }

    @FXML
    private void initialize() {
        functionCombo.setVisible(false);
        userTypeCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, newVal) -> {
            boolean isEmployee = "Employee".equals(newVal);
            functionCombo.setVisible(isEmployee);
            if (isEmployee) {
                functionCombo.getItems().setAll(TypeFonction.values());
            } else {
                functionCombo.getItems().clear();
            }
        });
    }

    @FXML
    private void handleSaveUser() {
        // 1) Validate first & last names
        String first = firstNameField.getText().trim();
        String last = lastNameField.getText().trim();
        if (first.isEmpty() || last.isEmpty()) {
            showAlert("First name and last name must not be empty.");
            return;
        }
        // Only letters and spaces allowed
        if (!first.matches("[A-Za-z ]+") || !last.matches("[A-Za-z ]+")) {
            showAlert("Names can only contain letters and spaces.");
            return;
        }

        // 2) Validate birth date
        LocalDate dob = birthDatePicker.getValue();
        if (dob == null) {
            showAlert("Please enter a birth date.");
            return;
        }
        if (!dob.isBefore(LocalDate.now())) {
            showAlert("Birth date must be before today.");
            return;
        }

        // 3) Validate user type selection
        String type = userTypeCombo.getValue();
        if (type == null) {
            showAlert("Please select User Type (Passenger or Employee).");
            return;
        }

        // 4) If Employee, ensure a function is chosen
        if ("Employee".equals(type) && functionCombo.getValue() == null) {
            showAlert("Please select an Employee Function.");
            return;
        }

        // All checks passed → proceed to creation
        try {
            Personne user = createUser();
            users.add(user);

            // Persist immediately:
            mainController.saveData();

            showAlert("User created: " + user.getNom());

            // go back to Main Menu
            Stage stage = (Stage) firstNameField.getScene().getWindow();
            stage.setScene(Main.getMainScene());

        } catch (Exception e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    private Personne createUser() {
        String type = userTypeCombo.getValue();
        LocalDate birthDate = birthDatePicker.getValue();

        if ("Employee".equals(type)) {
            return new Employe(
                    lastNameField.getText(),
                    firstNameField.getText(),
                    birthDate,
                    handicapCheck.isSelected(),
                    "EMP-" + (users.size() + 1),
                    functionCombo.getValue());
        } else {
            return new Usager(
                    lastNameField.getText(),
                    firstNameField.getText(),
                    birthDate,
                    handicapCheck.isSelected());
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getMainScene());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}