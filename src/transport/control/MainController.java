package transport.control;

import transport.core.Personne;
import transport.core.Reclamation;
import transport.core.Suspendable;
import transport.core.TitreTransport;
import transport.core.Station;
import transport.core.DataManager;
import transport.core.MoyenTransport;
import transport.core.ServiceReclamation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private List<Personne> users = new ArrayList<>();
    private List<TitreTransport> fareMedia = new ArrayList<>();
    private List<Suspendable> suspendables = new ArrayList<>();
    private List<Reclamation> complaints = new ArrayList<>();
    private ServiceReclamation service = new ServiceReclamation();
    private Stage primaryStage;
    @FXML private Button btnAddUser;
    @FXML private Button btnPurchaseFare;
    @FXML private Button btnValidateFare;
    @FXML private Button btnRegisterComplaint;
    @FXML private Button btnListComplaints;

    @FXML
    public void initialize() {
        loadData();
        suspendables.addAll(List.of(
            new Station("Central"),
            new Station("Nord"),
            new MoyenTransport("Bus-42"),
            new MoyenTransport("Tram-3")
        ));
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/transport/ui/AddUser.fxml"));
            Parent root = loader.load();

            AddUserController controller = loader.getController();
            controller.setUsers(users);

            // ▶️ Inject your own controller so AddUserController can call saveData()
            controller.setMainController(this);

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert("Error loading Add User form: " + e.getMessage());
        }
    }

    @FXML
    private void handlePurchaseFare(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/transport/ui/PurchaseFare.fxml"));
            Parent root = loader.load();

            PurchaseFareController controller = loader.getController();
            controller.setUsers(users);
            controller.setFareMedia(fareMedia);
            controller.setMainController(this); // inject MainController

            // Get the same window you’re already on:
            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            // Simply replace its scene with the purchase form:
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            showAlert("Error loading Purchase Fare form: " + e.getMessage());
        }
    }

    @FXML
    private void handleValidateFare(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transport/ui/ValidateFare.fxml"));
            Parent root = loader.load();

            ValidateController controller = loader.getController();
            controller.setFareMedia(fareMedia);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert("Error loading Validation form: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegisterComplaint(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/transport/ui/Complaints.fxml")
            );
            Parent root = loader.load();

            ComplaintsController controller = loader.getController();
            controller.setData(service, users, suspendables);

            Stage stage = (Stage) btnRegisterComplaint.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleComplaints(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transport/ui/Complaints.fxml"));
            Parent root = loader.load();

            ComplaintsController controller = loader.getController();
            controller.setComplaints(complaints);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert("Error loading Complaints form: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            DataManager.loadState(users, fareMedia, complaints);
        } catch (Exception e) {
            showAlert("Error loading data: " + e.getMessage());
        }
    }

    public void saveData() {
        try {
            DataManager.saveState(users, fareMedia, complaints);
        } catch (IOException e) {
            showAlert("Error saving data: " + e.getMessage());
        }
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setOnCloseRequest(event -> saveData());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleListComplaints(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/transport/ui/ComplaintsList.fxml")
        );
        Parent root = loader.load();

        ComplaintsListController controller = loader.getController();
        controller.setService(service);

        Stage stage = (Stage) btnListComplaints.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}