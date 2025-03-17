package at.htl.helpr;

import at.htl.helpr.homeform.HomePresenter;
import at.htl.helpr.homeform.HomeView;
import at.htl.helpr.sql.SqlRunner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        SqlRunner.runSchema();

        var view = new HomeView();
        var presenter = new HomePresenter(view);

        var scene = new Scene(view, 750, 450);

        stage.setTitle("Helpr");
        stage.setScene(scene);
        stage.show();
    }
}
