package at.htl.helpr.home;

import at.htl.helpr.components.TaskList;
import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.scenemanager.Presenter;
import at.htl.helpr.scenemanager.SceneManager;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePresenter implements Presenter {

    private  HomeView view;
    private SceneManager sceneManager;
    private Scene scene;

    private final TaskRepository repository = new TaskRepositoryImpl();
    private final TaskList taskList = new TaskList(false, repository::findAll,
            "Keine Aufgaben gefunden");

    public HomePresenter(HomeView view, SceneManager sceneManager) {
        this.view = view;
        this.sceneManager = sceneManager;
        view.setCenter(taskList);
        attachEvents();
    }

    private void attachEvents() {
        getView().getProfilePicture().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getUsernameLabel().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getSearchButton().setOnAction(mouseEvent -> updateCardsBySearch());

    }


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

        sceneManager.setScene(ProfilPresenter.class);

        currentStage.setTitle("Helpr-Profil");
        currentStage.show();
    }

    public HomeView getView() {
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
