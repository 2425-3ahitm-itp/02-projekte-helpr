package at.htl.helpr.taskform;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TaskFormView extends VBox {

    private final Label titleErrorLabel = new Label();
    private final TextField titleField = new TextField();
    private final Label zipErrorLabel = new Label();
    private final TextField zipField = new TextField();
    private final Label cityErrorLabel = new Label();
    private final TextField cityField = new TextField();
    private final Label paymentErrorLabel = new Label();
    private final TextField paymentField = new TextField();
    private final Label descriptionErrorLabel = new Label();
    private final TextArea descriptionArea = new TextArea();
    private final Spinner<Integer> estimatedEffortSpinner = new Spinner<>();
    private final Button createButton = new Button("Erstellen");
    private final Button cancelButton = new Button("Abbrechen");

    private final VBox leftLayout = new VBox();
    private final VBox rightLayout = new VBox();
    private final HBox layoutform = new HBox();

    private final static String ERROR_PREFIX = "-";

    public TaskFormView() {

        estimatedEffortSpinner.setValueFactory(
                new IntegerSpinnerValueFactory(1, 10, 1, 1)
        );

        this.setupLayout();
    }

    private void setupLayout() {

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        leftLayout.setPadding(new Insets(10));
        rightLayout.setPadding(new Insets(10));
        layoutform.setPadding(new Insets(10));

        // max width layouts
        leftLayout.setMaxWidth(Double.MAX_VALUE);
        rightLayout.setMaxWidth(Double.MAX_VALUE);

        // fill width
        leftLayout.setFillWidth(true);
        rightLayout.setFillWidth(true);

        // min width
        leftLayout.setMinWidth(350);
        rightLayout.setMinWidth(350);
        estimatedEffortSpinner.setMinWidth(100);

        // title
        Label title = new Label("Task erstellen");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        HBox titleBox = new HBox(5, new Label("Titel:"), titleErrorLabel);
        titleErrorLabel.setStyle("-fx-text-fill: red;");

        // description
        HBox descriptionBox = new HBox(5, new Label("Beschreibung:"), descriptionErrorLabel);
        descriptionErrorLabel.setStyle("-fx-text-fill: red;");

        // zip code
        HBox zipBox = new HBox(5, new Label("PLZ:"), zipErrorLabel);
        zipErrorLabel.setStyle("-fx-text-fill: red;");

        // city
        HBox cityBox = new HBox(5, new Label("Stadt:"), cityErrorLabel);
        cityErrorLabel.setStyle("-fx-text-fill: red;");

        // payment
        HBox paymentBox = new HBox(5, new Label("Entlohnung:"), paymentErrorLabel);
        paymentErrorLabel.setStyle("-fx-text-fill: red;");

        // estimated effort
        Label estimatedEffortLabel = new Label("Schwierigkeit:");
        estimatedEffortLabel.setPrefWidth(Integer.MAX_VALUE);
        HBox difficultyBox = new HBox(5, estimatedEffortLabel, estimatedEffortSpinner);
        difficultyBox.setPadding(new Insets(10));

        // max width create button
        createButton.setPrefWidth(Double.MAX_VALUE);
        cancelButton.setPrefWidth(Double.MAX_VALUE);

        // ZIP-Code VBox
        VBox zipVBox = new VBox(2, zipBox, zipField);
        zipVBox.setAlignment(Pos.TOP_LEFT);
        zipVBox.setFillWidth(true);

        // City VBox
        VBox cityVBox = new VBox(2, cityBox, cityField);
        cityVBox.setAlignment(Pos.TOP_LEFT);
        cityVBox.setFillWidth(true);

        // zip code, city HBox
        HBox locationBox = new HBox(10, zipVBox, cityVBox);
        locationBox.setAlignment(Pos.TOP_LEFT);

        // buttons VBox
        VBox buttonBox = new VBox(10, createButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(createButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);

        descriptionArea.setPrefHeight(130);

        leftLayout.getChildren().addAll(
                titleBox,titleField,
                descriptionBox,descriptionArea
        );

        rightLayout.getChildren().addAll(
                        locationBox,
                        paymentBox, paymentField,
                        difficultyBox,
                        buttonBox
        );

        layoutform.getChildren().addAll(leftLayout, rightLayout);

        this.getChildren().addAll(title, layoutform);
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
