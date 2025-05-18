package transport.control;

import transport.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import transport.Main;
import javafx.scene.Node;
import java.util.List;

public class PurchaseFareController {
    @FXML private ComboBox<String> fareTypeCombo;
    @FXML private ComboBox<Personne> userCombo;
    @FXML private Label priceLabel;

    private List<Personne> users;
    private List<TitreTransport> fareMedia;

    public void setUsers(List<Personne> users) {
        this.users = users;
        userCombo.getItems().setAll(users);
    }

    public void setFareMedia(List<TitreTransport> fareMedia) {
        this.fareMedia = fareMedia;
    }

    @FXML
    private void handlePurchase() {
        try {
            TitreTransport fare = createFare();
            fareMedia.add(fare);
            priceLabel.setText(String.format("Purchased! Price: %.2f DA", fare.getPrix()));
        } catch (ReductionImpossibleException e) {
            showAlert("Cannot create card: " + e.getMessage());
        }
    }

    private TitreTransport createFare() throws ReductionImpossibleException {
        Personne user = userCombo.getValue();
        if("Personal Card".equals(fareTypeCombo.getValue())) {
            return new CartePersonnelle(user);
        } else {
            return new Ticket();
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