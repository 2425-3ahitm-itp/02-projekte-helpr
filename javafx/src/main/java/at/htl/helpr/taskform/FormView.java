package at.htl.helpr.taskform;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FormView extends VBox {

    private final Label titleErrorLabel = new Label();
    private final TextField titleField = new TextField();
    private final Label descriptionErrorLabel = new Label();
    private final TextArea descriptionArea = new TextArea();
    private final Spinner<Integer> estimatedEffortSpinner = new Spinner<>();
    private final Button createButton = new Button("Erstellen");

    private final static String ERROR_PREFIX = "-";

    public FormView() {

        estimatedEffortSpinner.setValueFactory(
                new IntegerSpinnerValueFactory(1, 10, 1, 1)
        );

        this.setupLayout();
    }

    private void setupLayout() {

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        estimatedEffortSpinner.setMinWidth(100);

        // title
        Label title = new Label("Task erstellen");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        HBox titleBox = new HBox(5, new Label("Titel"), titleErrorLabel);
        titleErrorLabel.setStyle("-fx-text-fill: red;");

        // description
        HBox descriptionBox = new HBox(5, new Label("Beschreibung"), descriptionErrorLabel);
        descriptionErrorLabel.setStyle("-fx-text-fill: red;");

        // estimated effort
        Label estimatedEffortLabel = new Label("Schwierigkeit");
        estimatedEffortLabel.setPrefWidth(Integer.MAX_VALUE);
        HBox difficultyBox = new HBox(5, estimatedEffortLabel, estimatedEffortSpinner);

        // max width create button
        createButton.setPrefWidth(Double.MAX_VALUE);

        this.getChildren().addAll(
                title,
                titleBox, titleField,
                descriptionBox, descriptionArea,
                difficultyBox, createButton
        );

//        this.getChildren().add(formLayout);
    }

    public void setTitleErrorMessage(String message) {
        if (message.isEmpty()) {
            titleErrorLabel.textProperty().set("");
            return;
        }
        titleErrorLabel.textProperty().set(String.format("%s %s", ERROR_PREFIX, message));
    }

    public void setDescriptionErrorMessage(String message) {
        if (message.isEmpty()) {
            descriptionErrorLabel.textProperty().set("");
            return;
        }
        descriptionErrorLabel.textProperty().set(String.format("%s %s", ERROR_PREFIX, message));
    }

    public Label getTitleErrorLabel() {
        return titleErrorLabel;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public Label getDescriptionErrorLabel() {
        return descriptionErrorLabel;
    }

    public TextArea getDescriptionArea() {
        return descriptionArea;
    }

    public Spinner<Integer> getEstimatedEffortSpinner() {
        return estimatedEffortSpinner;
    }

    public Button getCreateButton() {
        return createButton;
    }
}
