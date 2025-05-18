package transport.control;

import transport.core.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import transport.Main;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.time.LocalDate;
import java.util.List;
public class ValidateController {
    @FXML private TextField fareIdField;
    @FXML private Label resultLabel;
    private List<TitreTransport> fareMedia;

    public void setFareMedia(List<TitreTransport> fareMedia) {
        this.fareMedia = fareMedia;
    }

    @FXML
    private void handleValidate() {
        try {
            int id = Integer.parseInt(fareIdField.getText());
            TitreTransport fare = fareMedia.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElseThrow();

            boolean valid = fare.estValide(LocalDate.now());
            resultLabel.setText(valid ? "Valid Fare Medium" : "Invalid");
        } catch (Exception e) {
            resultLabel.setText("Error: " + e.getMessage());
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