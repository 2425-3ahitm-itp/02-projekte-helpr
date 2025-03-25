package at.htl.helpr.profile;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.home.HomeView;
import at.htl.helpr.taskform.TaskFormPresenter;
import at.htl.helpr.taskform.TaskFormView;
import at.htl.helpr.taskform.model.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfilPresenter {

    private final ProfilView view;

    public ProfilPresenter(ProfilView view) {
        this.view = view;
        attachEvents();
    }

    private void attachEvents() {

        view.getNewTaskButton().setOnAction(e -> openTaskView());
        view.getHomeButton().setOnAction(e -> openHomeView());
    }

    private void openHomeView() {
        Stage currentStage = (Stage) getView().getProfileCircle().getScene().getWindow();

        var view = new HomeView();
        var presenter = new HomePresenter(view);

        var scene = new Scene(view, 750, 450);

        currentStage.setTitle("Helpr");
        currentStage.setScene(scene);
        currentStage.show();
    }

    private void openTaskView() {
        Stage currentStage = (Stage) getView().getProfileCircle().getScene().getWindow();

        var view = new TaskFormView();
        var task = new Task();
        var presenter = new TaskFormPresenter(task, view);

        var scene = new Scene(view, 750, 650);

        currentStage.setTitle("Helpr Aufgabe erstellen");
        currentStage.setScene(scene);
        currentStage.show();
    }

    public ProfilView getView() {
        return view;
    }

}
