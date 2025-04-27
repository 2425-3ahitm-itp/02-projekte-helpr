package at.htl.helpr;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.home.HomeView;
import at.htl.helpr.sql.SqlRunner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        SqlRunner.main();

        SqlRunner.runSchema();
        SqlRunner.runInserts();

        var view = new HomeView();
        var presenter = new HomePresenter(view);

        var scene = new Scene(view, 920, 550);

        stage.setTitle("Helpr");
        stage.setScene(scene);
        stage.show();
    }
}
