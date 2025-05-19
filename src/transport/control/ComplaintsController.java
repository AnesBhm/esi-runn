package transport.control;

import transport.core.*;
import transport.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComplaintsController {
    @FXML
    private ComboBox<Personne> reporterCombo; // still users
    @FXML
    private ComboBox<Suspendable> targetCombo; // now Suspendable
    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ListView<Reclamation> complaintsListView;
    @FXML
    private Button submitButton;

    private List<Personne> users;
    private List<Suspendable> targets; // stations + transports
    private List<Reclamation> complaints;

    public void setUsers(List<Personne> users) {
        this.users = users;
        reporterCombo.getItems().setAll(users);
    }

    /** Call this to supply the list of Suspendable entities */
    public void setTargets(List<Suspendable> targets) {
        this.targets = targets;
        targetCombo.getItems().setAll(targets);
    }

    public void setComplaints(List<Reclamation> complaints) {
        this.complaints = complaints;
        refreshComplaintList();
    }

    @FXML
    private void initialize() {
        typeCombo.getItems().setAll("TECHNIQUE", "SERVICE", "PAYMENT");
        // disable unless everything is non-null/non-empty
        BooleanBinding formValid = reporterCombo.valueProperty().isNotNull()
                .and(targetCombo.valueProperty().isNotNull())
                .and(typeCombo.valueProperty().isNotNull())
                .and(new BooleanBinding() {
                    {
                        super.bind(descriptionField.textProperty());
                    }
                    @Override
                    protected boolean computeValue() {
                        return !descriptionField.getText().trim().isEmpty();
                    }
                });

        submitButton.disableProperty().bind(formValid.not());

    }

    @FXML
    private void handleSubmit() {
        Personne reporter = reporterCombo.getValue();
        Suspendable target = targetCombo.getValue();
        String typeStr = typeCombo.getValue();
        String desc = descriptionField.getText().trim(); // ← trim spaces

        // 1) Validate inputs
        if (reporter == null ||
                target == null ||
                typeStr == null ||
                desc.isEmpty()) {
            showAlert("Please select reporter, target, type, and enter a description.");
            return;
        }

        // 2) Convert to enum safely
        TypeReclamation type;
        try {
            type = TypeReclamation.valueOf(typeStr);
        } catch (IllegalArgumentException iae) {
            showAlert("Unknown complaint type: " + typeStr);
            return;
        }

        // 3) Record the complaint
        Reclamation rec = new Reclamation(
                reporter,
                type,
                target,
                desc,
                LocalDate.now());
        complaints.add(rec);
        showAlert("Complaint submitted against: " + target);

        refreshComplaintList();

        // 4) Suspend if needed
        long count = complaints.stream()
                .filter(r -> r.getCible().equals(target))
                .count();
        if (count == 3) {
            showAlert("⚠️ " + target + " has " + count + " complaints → SUSPENDED!");
            target.suspendre();
        }

        // 5) Clear the form
        reporterCombo.getSelectionModel().clearSelection();
        targetCombo.getSelectionModel().clearSelection();
        typeCombo.getSelectionModel().clearSelection();
        descriptionField.clear();
    }

    private void refreshComplaintList() {
        complaintsListView.getItems().setAll(complaints);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getMainScene());
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
