package at.htl.helpr.home;

import at.htl.helpr.components.TaskList;
import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import at.htl.helpr.taskform.repository.filter.EffortFilter;
import at.htl.helpr.taskform.repository.filter.EffortMinMaxFilter;
import at.htl.helpr.taskform.repository.filter.PostalCodeFilter;
import at.htl.helpr.taskform.repository.filter.TaskFilter;
import at.htl.helpr.taskform.repository.filter.TaskQueryBuilder;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import javax.swing.JToggleButton;
import java.util.List;

public class HomePresenter {

    private final HomeView view;
    private final TaskRepository repository = new TaskRepositoryImpl();
    private final TaskList taskList = new TaskList(false, repository::findAll,
            "Keine Aufgaben gefunden");
    private TaskQueryBuilder filterTasks = new TaskQueryBuilder();

    public HomePresenter(HomeView view) {
        this.view = view;
        view.setCenter(taskList);
        attachEvents();
        toggledButtonSetOnAction();
    }

    private void attachEvents() {
        getView().getProfilePicture().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getUsernameLabel().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getSearchButton().setOnAction(mouseEvent -> updateCardsBySearch());
        getView().getFilterButton().setOnAction(mouseEvent -> useFilter());
    }

    private void toggledButtonSetOnAction() {
        getView().getMinPaymentField().disableProperty()
                .bind(getView().getPaymentToggle().selectedProperty().not());
        getView().getMaxPaymentField().disableProperty()
                .bind(getView().getPaymentToggle().selectedProperty().not());

        getView().getEffortField().disableProperty()
                .bind(getView().getEffortToggle().selectedProperty().not());
        getView().getPostalCodeField().disableProperty()
                .bind(getView().getPostalToggle().selectedProperty().not());
        getView().getCityField().disableProperty()
                .bind(getView().getCityToggle().selectedProperty().not());
        getView().getDateFields().disableProperty()
                .bind(getView().getDateToggle().selectedProperty().not());

        // Nummernfelder nur Integer erlauben
        onlyAceptInt(getView().getEffortField());
        onlyAceptInt(getView().getPostalCodeField());


    }

    private void onlyAceptInt(TextField textField) {
        textField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                textField.setText(oldText);
            }
        });
    }

    private void useFilter() {
        filterTasks = new TaskQueryBuilder();
        handleEffortFilter();
        handlePlzFilter();
        taskList.rerender();


    }

    private void handlePaymentFilter() {
    }

    private void handleEffortFilter() {
        if (getView().getEffortToggle().isSelected()) {
            if (getView().getEffortField().getText() != null) {

                filterTasks.addFilter(
                        new EffortFilter(Integer.parseInt(getView().getEffortField().getText())));
                taskList.setTaskSupplier(() -> repository.getTasksWithFilter(filterTasks));
            }
        }
    }

    private void handlePlzFilter() {
        if (getView().getPostalToggle().isSelected()) {
            filterTasks.addFilter(new PostalCodeFilter(getView().getPostalCodeField().getText()+"%"));
            taskList.setTaskSupplier(() -> repository.getTasksWithFilter(filterTasks));
        }
    }

    private void handlePlaceFilter() {
    }

    private void handleDateFilter() {
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
