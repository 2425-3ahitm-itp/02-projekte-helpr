package at.htl.helpr.components;

import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Supplier;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TaskList extends ScrollPane {

    private final TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();

    private Supplier<List<Task>> taskSupplier;
    private final IntegerProperty columns = new SimpleIntegerProperty(4);
    private final boolean singleRow;
    private final String placeholderText;

    public TaskList(boolean singleRow, String placeholderText) {
        this.singleRow = singleRow;
        this.placeholderText = placeholderText;
        this.setPadding(new Insets(10));
        this.setFitToWidth(true);
        this.setContent(new FlowPane());
    }

    public TaskList(boolean singleRow, Supplier<List<Task>> taskSupplier, String placeholderText) {
        this(singleRow, placeholderText);
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

        if (tasks.isEmpty()) {
            Label placeholderLabel = new Label(placeholderText);
//            placeholderLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
            StackPane placeholderPane = new StackPane(placeholderLabel);
            placeholderPane.setPrefSize(this.getWidth(), this.getHeight());
            flowPane.getChildren().add(placeholderPane);
            StackPane.setAlignment(placeholderLabel, javafx.geometry.Pos.CENTER);

            // Ensure the placeholder text is visible
            placeholderLabel.setWrapText(true);
            placeholderLabel.setMinWidth(this.getWidth() - 20);
            placeholderLabel.setMinHeight(200);
            placeholderPane.setMinHeight(200);
            placeholderPane.setMinWidth(200);
        } else {
            for (Task task : tasks) {
                VBox card = createTaskCard(task);
                flowPane.getChildren().add(card);
            }

            if (singleRow) {
                flowPane.setPrefWrapLength(Double.MAX_VALUE);
                flowPane.setOrientation(Orientation.HORIZONTAL);
                flowPane.setMinWidth(tasks.size() * 200 + 20 * tasks.size());
                this.setMinHeight(250);

                this.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            } else {
                this.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                this.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            }
        }
    }

    private VBox createTaskCard(Task task) {
        VBox card = new VBox();
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // Title and Location at the top
        Label titleLabel = new Label(task.getTitle());
        titleLabel.setFont(Font.font("", FontWeight.BOLD, 14));
        Label locationLabel = new Label(task.getLocation());

        // Price and Effort at the bottom in a HBox
        HBox bottomSection = new HBox();
        bottomSection.setSpacing(10);

        Label priceLabel = new Label(task.getReward() + " €");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label effortLabel = new Label(String.valueOf(task.getEffort()));

        bottomSection.getChildren().addAll(priceLabel, spacer, effortLabel);

        // Add all components to the card
        card.getChildren().addAll(titleLabel, locationLabel);

        // Fetch the first image path for the task
        List<String> imagePaths = taskRepository.getTaskImages(task.getId());
        if (!imagePaths.isEmpty()) {
            String thumbnailPath = imagePaths.getFirst();

            try (InputStream imageStream = getClass().getResourceAsStream(thumbnailPath)) {
                if (imageStream != null) {
                    ImageView imageView = new ImageView(new Image(imageStream));
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(177); // Adjusted width
                    imageView.setPreserveRatio(false);
                    VBox.setMargin(imageView, new Insets(10, 0, 10, 0));
                    card.getChildren().add(imageView);
                } else {
                    throw new RuntimeException("IMAGE '" + thumbnailPath + "' WAS NOT FOUND");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            // Empty image placeholder with dotted border
            Pane imagePlaceholder = new Pane();
            imagePlaceholder.setPrefHeight(100);
            imagePlaceholder.setPrefWidth(150); // Adjusted width
            imagePlaceholder.setStyle(
                    "-fx-border-color: black; -fx-border-style: dotted; -fx-border-width: 2;");
            VBox.setMargin(imagePlaceholder, new Insets(10, 0, 10, 0));
            card.getChildren().add(imagePlaceholder);
        }

        card.getChildren().add(bottomSection);

        return card;
    }
}