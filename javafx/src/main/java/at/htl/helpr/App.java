package at.htl.helpr;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.home.HomeView;
import at.htl.helpr.login.LoginPresenter;
import at.htl.helpr.login.LoginView;
import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.scenemanager.SceneManager;
import at.htl.helpr.signup.SignupPresenter;
import at.htl.helpr.signup.SignupView;
import at.htl.helpr.sql.SqlRunner;
import at.htl.helpr.taskform.TaskFormPresenter;
import at.htl.helpr.taskform.TaskFormView;
import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.usermanager.UserManager;
import at.htl.helpr.usermanager.repository.exceptions.LoginFailedException;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        SqlRunner.main();

        SceneManager.initialize(stage);
        SceneManager sceneManager = SceneManager.getInstance();

        try {
            UserManager.getInstance().login("jakki", "123");
        } catch (LoginFailedException e) {
            throw new RuntimeException(e);
        }

        // Presenter registrieren
        addAllPresenters(sceneManager);

        // Erste Szene setzen
        sceneManager.setScene(HomePresenter.class);

        stage.setTitle("Helpr");
        stage.show();
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

        var loginView = new LoginView();
        var loginPresenter = new LoginPresenter(loginView, sceneManager);
        sceneManager.addPresenter(LoginPresenter.class, loginPresenter);

        var signupView = new SignupView();
        var signupPresenter = new SignupPresenter(signupView, sceneManager);
        sceneManager.addPresenter(SignupPresenter.class, signupPresenter);
    }
}
