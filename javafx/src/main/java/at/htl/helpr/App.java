package at.htl.helpr;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.home.HomeView;
import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.sql.SqlRunner;
import at.htl.helpr.scenemanager.SceneManager;
import at.htl.helpr.taskform.TaskFormPresenter;
import at.htl.helpr.taskform.TaskFormView;
import at.htl.helpr.taskform.model.Task;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        SqlRunner.runSchema();
        SqlRunner.runInserts();

        // SceneManager als Singleton initialisieren
        SceneManager.initialize(stage);
        SceneManager sceneManager = SceneManager.getInstance();

        // Presenter registrieren
        addAllPresenters(sceneManager);

        // Erste Szene setzen
        sceneManager.setScene(HomePresenter.class);

        stage.setTitle("Helpr");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void addAllPresenters(SceneManager sceneManager) {
        var homeView = new HomeView();
        var homePresenter = new HomePresenter(homeView, sceneManager);
        sceneManager.addPresenter(HomePresenter.class, homePresenter);

        var profilView = new ProfilView();
        var profilPresenter = new ProfilPresenter(profilView, sceneManager);
        sceneManager.addPresenter(ProfilPresenter.class, profilPresenter);

        var taskFormView = new TaskFormView();
        var taskFormPresenter = new TaskFormPresenter(new Task(), taskFormView);
        sceneManager.addPresenter(TaskFormPresenter.class, taskFormPresenter);
    }
}
