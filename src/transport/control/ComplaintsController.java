package transport.control;

import transport.core.*;
import transport.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComplaintsController {
    @FXML private ComboBox<Personne>   reporterCombo;   // still users
    @FXML private ComboBox<Suspendable> targetCombo;    // now Suspendable
    @FXML private ComboBox<String>     typeCombo;
    @FXML private TextArea             descriptionField;
    @FXML private ListView<Reclamation> complaintsListView;

    private List<Personne>    users;
    private List<Suspendable> targets;      // stations + transports
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
    }

    @FXML
    private void handleSubmit() {
        Personne reporter     = reporterCombo.getValue();
        Suspendable target    = targetCombo.getValue();
        String      typeStr   = typeCombo.getValue();
        String      desc      = descriptionField.getText();

        if (reporter==null || target==null
          || typeStr==null || desc.isBlank()) {
            showAlert("Please select reporter, target, type, and enter a description.");
            return;
        }

        Reclamation rec = new Reclamation(
            reporter,
            TypeReclamation.valueOf(typeStr),
            target,
            desc,
            LocalDate.now()
        );
        complaints.add(rec);

        showAlert("Complaint submitted against: " + target);

        refreshComplaintList();

        // suspend if >3 complaints
        Map<Suspendable, Long> counts = complaints.stream()
            .collect(Collectors.groupingBy(Reclamation::getCible,
                                           Collectors.counting()));

        long c = counts.getOrDefault(target, 0L);
        if (c > 3) {
            showAlert("⚠️ " + target + " has " + c + " complaints → SUSPENDED!");
            target.suspendre();  // now actually suspend
        }

        // clear form
        descriptionField.clear();
        typeCombo.getSelectionModel().clearSelection();
    }

    private void refreshComplaintList() {
        complaintsListView.getItems().setAll(complaints);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(Main.getMainScene());
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
