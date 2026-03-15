package ui.gui;

import service.ComandaService;
import service.TortService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private static TortService tortService;
    private static ComandaService comandaService;

    public static void setServices(TortService ts, ComandaService cs) {
        tortService = ts;
        comandaService = cs;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        // Dependency Injection for Controller
        fxmlLoader.setControllerFactory(param -> new HelloController(tortService, comandaService));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Cofetarie Manager");
        stage.setScene(scene);
        stage.show();
    }
}