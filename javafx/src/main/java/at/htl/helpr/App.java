package at.htl.helpr;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        var scene = new Scene(new HBox(), 600, 400);
        stage.setTitle("Helpr");
        stage.setScene(scene);
        stage.show();
    }
}
