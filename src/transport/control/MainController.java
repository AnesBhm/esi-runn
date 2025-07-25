package transport.control;

import transport.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private List<Personne> users = new ArrayList<>();
    private List<TitreTransport> fareMedia = new ArrayList<>();
    private List<Reclamation> complaints = new ArrayList<>();
    private List<Station> stationsList = List.of(
            new Station("Oued Smar"), new Station("Bab Ezzouar"), new Station("El Harrach"));
    private List<MoyenTransport> vehiclesList = List.of(
            new MoyenTransport("Bus1"), new MoyenTransport("Bus2"), new MoyenTransport("Bus3"));

    private Stage primaryStage;

    @FXML
    public void initialize() {
        loadData();
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
    private void handleComplaints(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/transport/ui/Complaints.fxml"));
            Parent root = loader.load();

            ComplaintsController ctrl = loader.getController();
            // supply your existing users
            ctrl.setUsers(users);
            // supply all suspendable entities: for example
            List<Suspendable> allTargets = new ArrayList<>();
            allTargets.addAll(stationsList); // your List<Station>
            allTargets.addAll(vehiclesList); // your List<MoyenTransport>
            ctrl.setTargets(allTargets);

            // supply the shared complaints list
            ctrl.setComplaints(complaints);

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
}