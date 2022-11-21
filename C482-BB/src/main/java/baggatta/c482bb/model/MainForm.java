package baggatta.c482bb.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Starts the application
 * javadoc folder found at C482.zip\software1\javadoc
 * @author Brandon Baggatta
 */
public class MainForm extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainForm.class.getResource("/baggatta/c482bb/view/mainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {






        launch();
    }
}