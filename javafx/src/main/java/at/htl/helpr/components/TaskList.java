package at.htl.helpr.components;

import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

import java.util.List;

public class TaskList extends ScrollPane {

    private final TaskRepository taskRepository = new TaskRepositoryImpl();
    private final IntegerProperty columns = new SimpleIntegerProperty(4);
    private final boolean singleRow;

    public TaskList(boolean singleRow) {
        this.singleRow = singleRow;
        initialize();
    }

    private void initialize() {
        List<Task> tasks = taskRepository.findAll();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        this.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            if (singleRow) {
                columns.set(tasks.size());
            } else {
                double availableWidth = newWidth.doubleValue() - gridPane.getPadding().getLeft()
                        - gridPane.getPadding().getRight();
                int newColumns = Math.max(1, (int) (availableWidth / (200 + gridPane.getHgap())));
                columns.set(newColumns);
            }
            updateGrid(gridPane, tasks, columns.get());
        });

        updateGrid(gridPane, tasks, columns.get());
        this.setContent(gridPane);
    }

    private void updateGrid(GridPane gridPane, List<Task> tasks, int columns) {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;

        for (Task task : tasks) {
            VBox card = createTaskCard(task);
            gridPane.add(card, column, row);

            column++;
            if (column == columns) {
                column = 0;
                row++;
            }
        }
    }

    private VBox createTaskCard(Task task) {
        VBox card = new VBox();
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        Label titleLabel = new Label("Title: " + task.getTitle());
        Label priceLabel = new Label("Price: " + task.getReward());
        Label locationLabel = new Label("Location: " + task.getLocation());
        Label effortLabel = new Label("Effort: " + task.getEffort());

        card.getChildren().addAll(titleLabel, priceLabel, locationLabel, effortLabel);
        return card;
    }
}