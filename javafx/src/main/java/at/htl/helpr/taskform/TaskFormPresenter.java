package at.htl.helpr.taskform;

import at.htl.helpr.controller.TaskRepository;
import at.htl.helpr.model.Task;

public class TaskFormPresenter {

    private TaskFormView view;
    private Task model;

    private final TaskRepository repository = new TaskRepository();


    public TaskFormPresenter( Task model, TaskFormView view) {
        this.model = model;
        this.view = view;

        bindViewToModel();
        attachEvents();
    }

    private void attachEvents() {
        view.getCreateButton().setOnAction(e -> saveTask());
    }

    private void bindViewToModel() {
        view.getTitleField().textProperty().bindBidirectional(model.titleProperty());
        view.getDescriptionArea().textProperty().bindBidirectional(model.descriptionProperty());
        view.getEstimatedEffortSpinner().getValueFactory().valueProperty()
                .bindBidirectional(model.estimatedEffortProperty().asObject());
    }

    private void saveTask() {
        Task newTask = new Task(model);

        if (validateData()) {
            repository.create(newTask);
        }

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

}
