package at.htl.helpr.components;

import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import at.htl.helpr.util.I18n;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TaskList extends ScrollPane {

    private final TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
    private final IntegerProperty columns = new SimpleIntegerProperty(4);
    private final boolean singleRow;
    private final String placeholderText;
    private static final int CARD_GAP = 20;
    private Supplier<List<Task>> taskSupplier;

    public TaskList(boolean singleRow, String placeholderText) {
        this.singleRow = singleRow;
        this.placeholderText = placeholderText;
        this.setPadding(new Insets((double) CARD_GAP / 2));
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
            flowPane.setHgap(CARD_GAP);
            flowPane.setVgap(CARD_GAP);

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
        List<Task> tasks;

        try {
            tasks = taskSupplier.get();
        } catch (RuntimeException e) {
            return;
        }

        flowPane.getChildren().clear();

        if (tasks.isEmpty()) {
            Label placeholderLabel = new Label(placeholderText);
            // placeholderLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
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
                this.setMinHeight(275);

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

        // add neomorphism to the card
        card.setStyle(
                "-fx-background-color: #f0f0f0; -fx-background-radius: 10px; -fx-padding: 10px; "
                        + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.0, 0, 1);");

        // Title, Location and creation date at the top
        Label titleLabel = new Label(task.getTitle());
        titleLabel.setFont(Font.font("", FontWeight.BOLD, 14));
        Label locationLabel = new Label(task.getLocation());
        Locale.setDefault(Locale.GERMAN);
        Label dateLable = new Label(
                task.getCreatedAt().format(DateTimeFormatter.ofPattern("dd. MMMM yyyy")));

        // Price and Effort at the bottom in a HBox
        HBox bottomSection = new HBox();
        bottomSection.setSpacing(10);

        Label priceLabel = new Label(task.getReward() + " €");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String[] effortColors = new String[]{"#99FF99", "#CCFF99", "#FFFF99", "#FFCC99", "#FF9999"};

        Label effortLabel = I18n.get().bind(new Label(), "tasklist.effort", task.getEffort());

        // set bg color of effortlabel to the effort color
        int effortIndex = Math.min(task.getEffort(), effortColors.length - 1);
        String effortColor = effortColors[effortIndex];

        // add borderradius 4px and font bold
        effortLabel.setStyle("-fx-background-color: " + effortColor
                + "; -fx-background-radius: 4px; -fx-padding: 5px;");
        effortLabel.setFont(Font.font("", FontWeight.BOLD, 12));

        bottomSection.getChildren().addAll(priceLabel, spacer, effortLabel);

        // make bottomSection align vertical center
        bottomSection.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Add all components to the card
        card.getChildren().addAll(titleLabel, locationLabel, dateLable);

        // Fetch the first image path for the task
        List<String> imagePaths = taskRepository.getTaskImages(task.getId());

        if (imagePaths.isEmpty()) {
            imagePaths.add("placeholder.png");
        }

        String thumbnailPath = "store/task_images/" + imagePaths.getFirst();
        try (InputStream is = new FileInputStream(thumbnailPath)) {
            ImageView imageView = new ImageView(new Image(is));
            imageView.setFitHeight(100);
            imageView.setFitWidth(177);
            imageView.setPreserveRatio(false);
            VBox.setMargin(imageView, new Insets(10, 0, 10, 0));
            card.getChildren().add(imageView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // } else {
        // // Empty image placeholder with dotted border
        // Pane imagePlaceholder = new Pane();
        // imagePlaceholder.setPrefHeight(100);
        // imagePlaceholder.setPrefWidth(150); // Adjusted width
        // imagePlaceholder.setStyle(
        // "-fx-border-color: black; -fx-border-style: dotted; -fx-border-width: 2;");
        // VBox.setMargin(imagePlaceholder, new Insets(10, 0, 10, 0));
        // card.getChildren().add(imagePlaceholder);
        // }

        card.getChildren().add(bottomSection);

        return card;
    }
}