package at.htl.helpr.taskform;

import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class TaskFormPresenter {

    private final TaskFormView view;
    private final Task model;

    private final TaskRepository repository = new TaskRepositoryImpl();

    public TaskFormPresenter(Task model, TaskFormView view) {
        this.model = model;
        this.view = view;

        bindViewToModel();
        attachEvents();
    }

    private void attachEvents() {
        view.getCreateButton().setOnAction(e -> saveTask());
        view.getCancelButton().setOnAction(e -> openProfilView());
    }

    private void bindViewToModel() {
        view.getTitleField().textProperty().bindBidirectional(model.titleProperty());
        view.getDescriptionArea().textProperty().bindBidirectional(model.descriptionProperty());
        view.getEstimatedEffortSpinner().getValueFactory().valueProperty()
                .bindBidirectional(model.effortProperty().asObject());
        model.locationProperty().bind(view.getZipField().textProperty().concat(" ")
                .concat(view.getCityField().textProperty()));
        Bindings.bindBidirectional(view.getRewardField().textProperty(), model.rewardProperty(),
                new NumberStringConverter());
    }

    private void saveTask() {

        //Delete it when adding users
        model.setAuthorId(1);

        Task newTask = new Task(model);
        if (validateData()) {
            getRepository().create(newTask);
        }

        Stage currentStage = (Stage) getView().getTitleField().getScene().getWindow();

        var view = new ProfilView();
        var presenter = new ProfilPresenter(view);

        var scene = new Scene(view, 750, 650);

        currentStage.setTitle("Helpr Profil");
        currentStage.setScene(scene);
        currentStage.show();

    }

    private void openProfilView() {
        Stage currentStage = (Stage) getView().getTitleField().getScene().getWindow();

        var view = new ProfilView();
        var presenter = new ProfilPresenter(view);

        var scene = new Scene(view, 750, 650);

        currentStage.setTitle("Helpr Profil");
        currentStage.setScene(scene);
        currentStage.show();
    }

    private void updateTask() {
        repository.update(model);
    }

    private void deleteTask() {
        repository.delete(model.getId());
    }


    private boolean validateData() {
        return true;
    }


    public TaskFormView getView() {
        return view;
    }

    public Task getModel() {
        return model;
    }

    public TaskRepository getRepository() {
        return repository;
    }
}
