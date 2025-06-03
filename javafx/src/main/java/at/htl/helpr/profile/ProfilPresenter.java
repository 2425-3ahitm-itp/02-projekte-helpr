package at.htl.helpr.profile;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.scenemanager.Presenter;
import at.htl.helpr.scenemanager.SceneManager;
import at.htl.helpr.taskform.TaskFormPresenter;
import at.htl.helpr.usermanager.UserManager;
import at.htl.helpr.util.I18n;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfilPresenter implements Presenter {

    private final ProfilView view;
    private final SceneManager sceneManager;
    private Scene scene;

    public ProfilPresenter(ProfilView view, SceneManager sceneManager) {
        this.view = view;
        this.sceneManager = sceneManager;
        attachEvents();
        applyTranslations();
    }

    private void attachEvents() {
        view.getNewTaskButton().setOnAction(e -> openTaskView());
        view.getHomeButton().setOnAction(e -> openHomeView());
        view.getLogoutButton().setOnAction(e -> {
            UserManager.getInstance().logout();
            SceneManager.getInstance().setScene(HomePresenter.class);
        });
    }

    private void applyTranslations() {

        I18n.get().bind(view.getNewTaskButton(), "profile.buttons.create-new-task");
        I18n.get().bind(view.getHomeButton(), "profile.buttons.navigate-home");
    }

    private void openHomeView() {
        Stage currentStage = (Stage) getView().getProfileCircle().getScene().getWindow();

        sceneManager.setScene(HomePresenter.class);

        currentStage.setTitle(I18n.get().rawTranslate("application.window-title.default"));
        currentStage.show();
    }

    private void openTaskView() {
        Stage currentStage = (Stage) getView().getProfileCircle().getScene().getWindow();

        sceneManager.setScene(TaskFormPresenter.class);

        currentStage.setTitle(I18n.get().rawTranslate("task.creation.window-title"));
        currentStage.show();
    }

    public ProfilView getView() {
        return view;
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            scene = new Scene(view, 1080, 750);
        }
        return scene;
    }

    @Override
    public void onShow() {
        view.getUsernameLabel().setText(UserManager.getInstance().getUser().getUsername());
        view.getCreatedTasks().rerender();
        view.getAppliedTasks().rerender();
    }

}
