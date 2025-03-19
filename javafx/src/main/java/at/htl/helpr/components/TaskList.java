package at.htl.helpr.components;

import at.htl.helpr.taskform.model.Task;
import javafx.animation.PathTransition.OrientationType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
        this.setPadding(new Insets(10));
        this.setFitToWidth(true);
        this.setContent(new FlowPane());
    }

    public TaskList(boolean singleRow, Supplier<List<Task>> taskSupplier) {
        this(singleRow);
        this.taskSupplier = taskSupplier;
        rerender();
    }

    public void setTaskSupplier(Supplier<List<Task>> taskSupplier) {
        this.taskSupplier = taskSupplier;
    }

    private void updateCardWidths(FlowPane flowPane) {
        double cardWidth = 200; // Fixed width for each card
        for (var node : flowPane.getChildren()) {
            if (node instanceof VBox) {
                ((VBox) node).setPrefWidth(cardWidth);
                ((VBox) node).setMinWidth(cardWidth); // Ensure the card does not shrink
            }
        }
    }

    public void rerender() {
        if (taskSupplier != null) {
            FlowPane flowPane = (FlowPane) this.getContent();
            flowPane.setPadding(new Insets(10));
            flowPane.setHgap(10);
            flowPane.setVgap(10);


            this.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                updateCardWidths(flowPane);
            });

            updateFlowPane(flowPane);
            updateCardWidths(flowPane);
        }
    }

    private void updateFlowPane(FlowPane flowPane) {
        if (taskSupplier == null) {
            return;
        }
        var tasks = taskSupplier.get();
        flowPane.getChildren().clear();

        for (Task task : tasks) {
            VBox card = createTaskCard(task);
            flowPane.getChildren().add(card);
        }

        if (singleRow) {
            flowPane.setPrefWrapLength(Double.MAX_VALUE);
            flowPane.setOrientation(Orientation.HORIZONTAL);
            flowPane.setMinWidth(tasks.size() * 200 + 20 * tasks.size());

            this.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        } else {
            this.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            this.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
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