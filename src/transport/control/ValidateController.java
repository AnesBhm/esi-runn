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
    @FXML
    private TextField fareIdField;
    @FXML
    private Label resultLabel;
    private List<TitreTransport> fareMedia;

    public void setFareMedia(List<TitreTransport> fareMedia) {
        this.fareMedia = fareMedia;
    }

    @FXML
    private void handleValidate() {
        String input = fareIdField.getText().trim();
        if (input.isEmpty()) {
            showAlert("Please enter a Fare ID.");
            return;
        }

        try {
            int id = Integer.parseInt(input);

            TitreTransport fare = fareMedia.stream()
                    .filter(f -> f.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No fare medium found with ID " + id));

            // This will throw TitreNonValideException if invalid
            fare.estValide(LocalDate.now());

            // If we get here, it’s valid
            showAlert(String.format(
                    "✅ Fare #%d is VALID for today.\nPrice: %.2f DA",
                    id, fare.getPrix()));

        } catch (NumberFormatException nfe) {
            showAlert("Fare ID must be a number.");
        } catch (TitreNonValideException invalid) {
            showAlert(String.format(
                    "❌ Fare #%s is NOT valid: %s",
                    input, invalid.getMessage()));
        } catch (IllegalArgumentException notFound) {
            showAlert(notFound.getMessage());
        } catch (Exception ex) {
            showAlert("Unexpected error: " + ex.getMessage());
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