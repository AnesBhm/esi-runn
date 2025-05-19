package transport.control;

import transport.core.Reclamation;
import transport.Main;
import transport.core.Personne;
import transport.core.Suspendable;
import transport.core.ServiceReclamation;
import transport.core.TypeReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ComplaintsController {
    @FXML private ComboBox<TypeReclamation> typeCombo;
    @FXML private ComboBox<Personne> userCombo;
    @FXML private ComboBox<Suspendable> targetCombo;
    @FXML private TextArea descriptionField;

    private ServiceReclamation service;
    private List<Personne> users;
    private List<Suspendable> targets;
    private List<Reclamation> complaints;

    public void initialize() {
        typeCombo.getItems().setAll(TypeReclamation.values());
    }

    public void setData(ServiceReclamation service,
                        List<Personne> users,
                        List<Suspendable> targets) {
        this.service = service;
        this.users = users;
        this.targets = targets;

        userCombo.getItems().setAll(users);
        targetCombo.getItems().setAll(targets);
    }

    @FXML
    private void handleSubmit() {
        TypeReclamation type = typeCombo.getValue();
        Personne p = userCombo.getValue();
        Suspendable cible = targetCombo.getValue();
        String desc = descriptionField.getText();

        Reclamation rec = new Reclamation(p, type, cible, desc, LocalDate.now());
        Optional<String> suspensionMsg = service.soumettre(rec);

        new Alert(Alert.AlertType.INFORMATION, "Réclamation enregistrée !").showAndWait();
        suspensionMsg.ifPresent(msg ->
            new Alert(Alert.AlertType.WARNING, msg).showAndWait()
        );
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
    public void setComplaints(List<Reclamation> complaints) {
        this.complaints = complaints;
    }
}