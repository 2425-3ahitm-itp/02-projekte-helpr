package at.htl.helpr;

import at.htl.helpr.taskform.FormView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        var view = new FormView();

        var scene = new Scene(view, 600, 400);

        stage.setTitle("Helpr");
        stage.setScene(scene);
        stage.show();
    }
}
