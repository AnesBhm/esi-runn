package transport;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene mainScene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/MainMenu.fxml"));
        Parent root = loader.load();
        mainScene = new Scene(root, 800, 600);
        stage.setScene(mainScene);
        stage.setTitle("ESI-RUN System");
        stage.show();
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}