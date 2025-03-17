package at.htl.helpr;

import at.htl.helpr.homeform.HomeFormPresenter;
import at.htl.helpr.homeform.HomeFormView;
import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.sql.SqlRunner;
import at.htl.helpr.taskform.TaskFormPresenter;
import at.htl.helpr.taskform.TaskFormView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        SqlRunner.runSchema();

        var view = new HomeFormView();
        var presenter = new HomeFormPresenter(view);

        var scene = new Scene(view, 750, 450);

        stage.setTitle("Helpr");
        stage.setScene(scene);
        stage.show();
    }
}
