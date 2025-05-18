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
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private DatePicker birthDatePicker;
    @FXML private CheckBox handicapCheck;
    @FXML private ComboBox<String> userTypeCombo;
    @FXML private ComboBox<TypeFonction> functionCombo;

    private List<Personne> users;

    public void setUsers(List<Personne> users) {
        this.users = users;
        initialize();
    }

    @FXML
    private void initialize() {
        userTypeCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean isEmployee = "Employee".equals(newVal);
            functionCombo.setVisible(isEmployee);
            functionCombo.getItems().setAll(TypeFonction.values());
        });
    }

    @FXML
    private void handleSaveUser() {
        try {
            Personne user = createUser();
            users.add(user);
            showAlert("User created: " + user.getNom());
        } catch (Exception e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    private Personne createUser() {
        String type = userTypeCombo.getValue();
        LocalDate birthDate = birthDatePicker.getValue();
        
        if("Employee".equals(type)) {
            return new Employe(
                lastNameField.getText(),
                firstNameField.getText(),
                birthDate,
                handicapCheck.isSelected(),
                "EMP-" + (users.size()+1),
                functionCombo.getValue()
            );
        } else {
            return new Usager(
                lastNameField.getText(),
                firstNameField.getText(),
                birthDate,
                handicapCheck.isSelected()
            );
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