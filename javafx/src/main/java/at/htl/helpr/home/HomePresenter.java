package at.htl.helpr.home;

import at.htl.helpr.components.TaskList;
import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class HomePresenter {

    private final HomeView view;
    private final TaskRepository repository = new TaskRepositoryImpl();
    private final TaskList taskList = new TaskList(false, repository::findAll);

    public HomePresenter(HomeView view) {
        this.view = view;
        view.setCenter(taskList);
        attachEvents();
    }

    private void attachEvents() {
        getView().getProfilePicture().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getUsernameLabel().setOnMouseClicked(mouseEvent -> openProfilView());

    }



    private void openProfilView() {
        Stage currentStage = (Stage) getView().getProfilePicture().getScene().getWindow();

        var view = new ProfilView();
        var presenter = new ProfilPresenter(view);

        var scene = new Scene(view, 750, 650);

        currentStage.setTitle("Helpr Profil");
        currentStage.setScene(scene);
        currentStage.show();
    }

    public HomeView getView() {
        return view;
    }
}
