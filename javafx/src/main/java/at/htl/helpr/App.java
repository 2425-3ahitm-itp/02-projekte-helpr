package at.htl.helpr;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.home.HomeView;
import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.sql.SqlRunner;
import at.htl.helpr.scenemanager.SceneManager;
import at.htl.helpr.taskform.TaskFormPresenter;
import at.htl.helpr.taskform.model.Task;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        SqlRunner.runSchema();
        SqlRunner.runInserts();

        SceneManager sceneManager = new SceneManager(stage);


        addAllPresenters(sceneManager);
        sceneManager.setScene(HomePresenter.class);

        stage.setTitle("Helpr");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void addAllPresenters(SceneManager sceneManager) {
        var view = new HomeView();
        var presenter = new HomePresenter(view, sceneManager);
        sceneManager.addPresenter(HomePresenter.class, presenter);

        var profilView = new ProfilView();
        var profilPresenter = new ProfilPresenter(profilView, sceneManager);
        sceneManager.addPresenter(at.htl.helpr.profile.ProfilPresenter.class, profilPresenter);

        var taskFormView = new at.htl.helpr.taskform.TaskFormView();
        var taskFormPresenter = new TaskFormPresenter(new Task(), taskFormView, sceneManager);
        sceneManager.addPresenter(TaskFormPresenter.class, taskFormPresenter);
    }
}
