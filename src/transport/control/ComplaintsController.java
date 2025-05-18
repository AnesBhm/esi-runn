package transport.control;

import transport.core.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import transport.Main;
import java.time.LocalDate;
import java.util.List;

public class ComplaintsController {
    @FXML private ComboBox<String> typeCombo;
    @FXML private TextArea descriptionField;
    private List<Reclamation> complaints;
    private Personne currentUser;
    private Suspendable currentTarget; // Replace Object with the actual type if known

    public void setComplaints(List<Reclamation> complaints) {
        this.complaints = complaints;
    }

    public void setCurrentUser(Personne user) {
        this.currentUser = user;
    }

    public void setCurrentTarget(Suspendable target) { // Replace Object with the actual type if known
        this.currentTarget = target;
    }

    @FXML
    private void handleSubmit() {
        TypeReclamation type = TypeReclamation.valueOf(typeCombo.getValue());
        String desc = descriptionField.getText();
        
        complaints.add(new Reclamation(
            currentUser, // You'll need to pass logged-in user
            type,
            currentTarget, // Need to implement target selection
            desc,
            LocalDate.now()
        ));
        
        showAlert("Complaint submitted!");
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