package at.htl.helpr.components;

import at.htl.helpr.taskform.model.Task;
import javafx.animation.PathTransition.OrientationType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
            this.setMinHeight(250);

            System.out.println("SETTING FLOWPANE TO CORRECT HEIGHT");

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

        // Title and Location at the top
        Label titleLabel = new Label(task.getTitle());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        Label locationLabel = new Label(task.getLocation());

        // Empty image placeholder with dotted border
        Pane imagePlaceholder = new Pane();
        imagePlaceholder.setPrefHeight(100);
        imagePlaceholder.setStyle("-fx-border-color: black; -fx-border-style: dotted; -fx-border-width: 2;");
        VBox.setMargin(imagePlaceholder, new Insets(10, 0, 10, 0));

        // Price and Effort at the bottom in a HBox
        HBox bottomSection = new HBox();
        bottomSection.setSpacing(10);

        Label priceLabel = new Label(task.getReward() + " €");
        priceLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label effortLabel = new Label(String.valueOf(task.getEffort()));

        bottomSection.getChildren().addAll(priceLabel, spacer, effortLabel);

        // Add all components to the card
        card.getChildren().addAll(titleLabel, locationLabel, imagePlaceholder, bottomSection);

        return card;
    }
}