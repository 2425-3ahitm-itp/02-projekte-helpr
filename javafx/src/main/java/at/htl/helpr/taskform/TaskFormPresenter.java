package at.htl.helpr.taskform;

import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.scenemanager.Presenter;
import at.htl.helpr.scenemanager.SceneManager;
import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import at.htl.helpr.usermanager.UserManager;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.util.converter.NumberStringConverter;

public class TaskFormPresenter implements Presenter {

    private final TaskRepository repository = new TaskRepositoryImpl();
    private final TaskFormView view;
    private Task model;
    private Scene scene;

    public TaskFormPresenter(Task model, TaskFormView view) {
        this.model = model;
        this.view = view;
        bindViewToModel();
        attachEvents();
    }

    private void attachEvents() {
        view.getCreateButton().setOnAction(e -> saveTask());
        view.getCancelButton().setOnAction(e -> goToProfilView());
    }

    private void bindViewToModel() {
        view.getTitleField().textProperty().bindBidirectional(model.titleProperty());
        view.getDescriptionArea().textProperty().bindBidirectional(model.descriptionProperty());
        view.getEstimatedEffortSpinner().getValueFactory().valueProperty()
                .bindBidirectional(model.effortProperty().asObject());
        model.locationProperty().bind(
                view.getZipField().textProperty().concat(" ")
                        .concat(view.getCityField().textProperty())
        );
        Bindings.bindBidirectional(view.getRewardField().textProperty(), model.rewardProperty(),
                new NumberStringConverter());
    }

    private void unbindModelFromView() {
        view.getTitleField().textProperty().unbindBidirectional(model.titleProperty());
        view.getDescriptionArea().textProperty().unbindBidirectional(model.descriptionProperty());
        view.getEstimatedEffortSpinner().getValueFactory().valueProperty()
                .unbindBidirectional(model.effortProperty().asObject());
        model.locationProperty().unbind();
        Bindings.unbindBidirectional(view.getRewardField().textProperty(), model.rewardProperty());
    }

    private void resetFields() {
        unbindModelFromView();
        model = new Task(); // neues Model
        bindViewToModel();

        view.getTitleField().clear();
        view.getDescriptionArea().clear();
        view.getZipField().clear();
        view.getCityField().clear();
        view.getRewardField().clear();
        view.getEstimatedEffortSpinner().getValueFactory().setValue(1);
    }

    private void saveTask() {
        model.setAuthorId(UserManager.getInstance().getUser().getId());

        if (validateData()) {
            repository.create(new Task(model));
            goToProfilView();
        }
    }

    private void goToProfilView() {
        SceneManager.getInstance().setScene(ProfilPresenter.class);
    }

    private boolean validateData() {
        // TODO: Validierung implementieren
        return true;
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
        // reset all fields
        resetFields();
    }
}
