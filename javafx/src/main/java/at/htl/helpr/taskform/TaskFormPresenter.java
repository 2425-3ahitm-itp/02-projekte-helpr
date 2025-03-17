package at.htl.helpr.taskform;

import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;

public class TaskFormPresenter {

    private TaskFormView view;
    private Task model;

    private final TaskRepository repository = new TaskRepositoryImpl();


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
                .bindBidirectional(model.effortProperty().asObject());
    }

    private void saveTask() {
        Task newTask = new Task(model);

        if (validateData()) {
            repository.create(newTask, 0);
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
