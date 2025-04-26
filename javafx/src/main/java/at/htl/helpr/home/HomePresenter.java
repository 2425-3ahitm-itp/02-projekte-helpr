package at.htl.helpr.home;

import at.htl.helpr.components.TaskList;
import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePresenter {

    private final HomeView view;
    private final TaskRepository repository = new TaskRepositoryImpl();
    private final TaskList taskList = new TaskList(false, repository::findAll,
            "Keine Aufgaben gefunden");

    public HomePresenter(HomeView view) {
        this.view = view;
        view.setCenter(taskList);
        attachEvents();
    }

    private void attachEvents() {
        getView().getProfilePicture().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getUsernameLabel().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getSearchButton().setOnAction(mouseEvent -> updateCardsBySearch());

    }

    private void toggledButtonSetOnAction() {

    }
    private void handlePaymentFilter() {}
    private void handleEffortFilter() {}
    private void handlePlzFilter() {}
    private void handlePlaceFilter() {}
    private void handleDateFilter() {}


    private void updateCardsBySearch() {
        String searchQuery = getView().getSearchField().getText();

        if (searchQuery.isBlank()) {
            taskList.setTaskSupplier(repository::findAll);
        } else {
            taskList.setTaskSupplier(() -> repository.getTaskBySearchQueryAndLimit(searchQuery));
        }
        taskList.rerender();

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
