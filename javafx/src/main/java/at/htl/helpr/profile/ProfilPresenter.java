package at.htl.helpr.profile;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.home.HomeView;
import at.htl.helpr.scenemanager.Presenter;
import at.htl.helpr.scenemanager.SceneManager;
import at.htl.helpr.taskform.TaskFormPresenter;
import at.htl.helpr.taskform.TaskFormView;
import at.htl.helpr.taskform.model.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfilPresenter implements Presenter {

    private ProfilView view;
    private SceneManager sceneManager;
    private Scene scene;

    public ProfilPresenter(ProfilView view, SceneManager sceneManager) {
        this.view = view;
        this.sceneManager = sceneManager;
        attachEvents();
    }

    private void attachEvents() {

        view.getNewTaskButton().setOnAction(e -> openTaskView());
        view.getHomeButton().setOnAction(e -> openHomeView());
    }

    private void openHomeView() {
        Stage currentStage = (Stage) getView().getProfileCircle().getScene().getWindow();

        sceneManager.setScene(HomePresenter.class);

        currentStage.setTitle("Helpr");
        currentStage.show();
    }

    private void openTaskView() {
        Stage currentStage = (Stage) getView().getProfileCircle().getScene().getWindow();

        sceneManager.setScene(TaskFormPresenter.class);

        currentStage.setTitle("Helpr-Aufgabe erstellen");
        currentStage.show();
    }

    public ProfilView getView() {
        return view;
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            scene = new Scene(view, 1080, 648);
        }
        return scene;
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }
}
