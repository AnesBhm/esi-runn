package transport.control;

import transport.Main;
import transport.core.Reclamation;
import transport.core.ServiceReclamation;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import javafx.beans.property.ReadOnlyStringWrapper;

public class ComplaintsListController {
    @FXML private TableView<Reclamation> table;
    @FXML private TableColumn<Reclamation, Integer> colNumero;
    @FXML private TableColumn<Reclamation, LocalDate> colDate;
    @FXML private TableColumn<Reclamation, String> colType;
    @FXML private TableColumn<Reclamation, String> colCible;
    @FXML private TableColumn<Reclamation, String> colPersonne;
    @FXML private TableColumn<Reclamation, String> colDesc;
    @FXML private Button btnBack;

    private ServiceReclamation service;

    public void setService(ServiceReclamation service) {
        this.service = service;
        table.getItems().setAll(service.getAllReclamations());
    }

    @FXML
    public void initialize() {
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(cell ->
            new ReadOnlyStringWrapper(cell.getValue().getType().name())
        );
        colCible.setCellValueFactory(cell ->
            new ReadOnlyStringWrapper(cell.getValue().getCible().toString())
        );
        colPersonne.setCellValueFactory(cell ->
            new ReadOnlyStringWrapper(cell.getValue().getPersonne().toString())
        );
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getMainScene());
    }
}