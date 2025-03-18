package at.htl.helpr.components;

import at.htl.helpr.taskform.model.Task;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

import java.util.List;
import java.util.function.Supplier;

public class TaskList extends ScrollPane {

    private Supplier<List<Task>> taskSupplier;
    private final IntegerProperty columns = new SimpleIntegerProperty(4);
    private final boolean singleRow;

    public TaskList(boolean singleRow) {
        this.singleRow = singleRow;
        initialize();
    }

    public TaskList(boolean singleRow, Supplier<List<Task>> taskSupplier) {
        this(singleRow);
        this.taskSupplier = taskSupplier;
        rerender();
    }

    public void setTaskSupplier(Supplier<List<Task>> taskSupplier) {
        this.taskSupplier = taskSupplier;
    }

    private void initialize() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        final int[] previousColumns = {0};

        this.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeAndUpdateGrid(gridPane, previousColumns, newWidth.doubleValue());
        });

        this.setContent(gridPane);
        this.setFitToWidth(true); // Ensure the ScrollPane fits its content to width

        // Initial resize and update
        resizeAndUpdateGrid(gridPane, previousColumns, this.getWidth());
    }

    private void resizeAndUpdateGrid(GridPane gridPane, int[] previousColumns, double newWidth) {
        int newColumns;
        if (singleRow) {
            if (taskSupplier != null) {
                newColumns = taskSupplier.get().size();
            } else {
                newColumns = columns.get();
            }
        } else {
            double availableWidth =
                    newWidth - gridPane.getPadding().getLeft() - gridPane.getPadding().getRight();
            newColumns = Math.max(1, (int) (availableWidth / (200 + gridPane.getHgap())));
        }

        if (newColumns != previousColumns[0]) {
            columns.set(newColumns);
            previousColumns[0] = newColumns;
            updateGrid(gridPane, newColumns);
        } else {
            updateCardWidths(gridPane, newColumns);
        }
    }

    private void updateCardWidths(GridPane gridPane, int columns) {
        double availableWidth =
                this.getWidth() - gridPane.getPadding().getLeft() - gridPane.getPadding()
                        .getRight();
        double cardWidth = (availableWidth - (columns - 1) * gridPane.getHgap()) / columns;

        for (var node : gridPane.getChildren()) {
            if (node instanceof VBox) {
                ((VBox) node).setPrefWidth(cardWidth);
            }
        }
    }

    public void rerender() {
        if (taskSupplier != null) {
            GridPane gridPane = (GridPane) this.getContent();
            updateGrid(gridPane, columns.get());
        }
    }

    private void updateGrid(GridPane gridPane, int columns) {
        if (taskSupplier == null) {
            return;
        }
        var tasks = taskSupplier.get();
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