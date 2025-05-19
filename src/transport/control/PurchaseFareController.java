package transport.control;

import transport.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import transport.Main;
import javafx.scene.Node;
import java.util.List;
import java.util.Comparator;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class PurchaseFareController {
    @FXML
    private ComboBox<String> fareTypeCombo;
    @FXML
    private ComboBox<String> paymentMethodCombo;
    @FXML
    private ComboBox<Personne> userCombo;
    @FXML
    private Label priceLabel;
    @FXML
    private ListView<TitreTransport> soldListView;

    private List<Personne> users;
    private List<TitreTransport> fareMedia;
    private MainController mainController;

    public void setUsers(List<Personne> users) {
        this.users = users;
        userCombo.getItems().setAll(users);
    }

    public void setFareMedia(List<TitreTransport> fareMedia) {
        this.fareMedia = fareMedia;
        refreshSoldList();
    }

    public void setMainController(MainController mc) {
        this.mainController = mc;
    }

    @FXML
    private void initialize() {
        fareTypeCombo.getItems().setAll("Ticket", "Personal Card");
        paymentMethodCombo.getItems().setAll("Cash", "Dahabia", "BaridiMob");
    }

    @FXML
    private void handlePurchase() {
        Personne user = userCombo.getValue();
        String type = fareTypeCombo.getValue();
        String payment = paymentMethodCombo.getValue();

        // create the fare
        TitreTransport fare = "Personal Card".equals(type)
                ? new CartePersonnelle(user)
                : new Ticket(user);

        fare.setPaymentMethod(payment); // purchase date already set in ctor
        fareMedia.add(fare);

        // persist immediately
        mainController.saveData();

        // 1) Show confirmation alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Purchase Complete");
        alert.setHeaderText(null);
        alert.setContentText(
                String.format("Purchased successfully!\nID : %d\nPrice: %.2f DA", fare.getId(), fare.getPrix()));
        alert.showAndWait(); // <-- wait for user to click OK

        // 2) Go back to main menu
        Stage stage = (Stage) priceLabel.getScene().getWindow();
        stage.setScene(Main.getMainScene());
    }

    private void refreshSoldList() {
        // sort fareMedia descending by date
        List<TitreTransport> sorted = fareMedia.stream()
                .sorted(Comparator.comparing(TitreTransport::getDateAchat).reversed())
                .collect(Collectors.toList());

        soldListView.getItems().setAll(sorted);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getMainScene());
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
    }
}
