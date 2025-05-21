package at.htl.helpr.taskform;

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

public class TaskFormView extends VBox {

    private final static String ERROR_PREFIX = "-";
    private final Label titleErrorLabel = new Label();
    private final TextField titleField = new TextField();
    private final Label zipErrorLabel = new Label();
    private final TextField zipField = new TextField();
    private final Label cityErrorLabel = new Label();
    private final TextField cityField = new TextField();
    private final Label rewardErrorLabel = new Label();
    private final TextField rewardField = new TextField();
    private final Label descriptionErrorLabel = new Label();
    private final TextArea descriptionArea = new TextArea();
    private final Spinner<Integer> estimatedEffortSpinner = new Spinner<>();
    private final Button createButton = new Button("Erstellen");
    private final Button cancelButton = new Button("Abbrechen");
    private final VBox leftLayout = new VBox();
    private final VBox rightLayout = new VBox();
    private final HBox layoutform = new HBox();
    private final HBox imageBox = new HBox(10);
    private final Button prevButton = new Button("<");
    private final Button nextButton = new Button(">");
    private final HBox imageLayout = new HBox(10);
    private final Button uploadButton = new Button("Bild Hochladen");
    private final VBox imageSlider = new VBox();

    public TaskFormView() {

        estimatedEffortSpinner.setValueFactory(
                new IntegerSpinnerValueFactory(1, 10, 1, 1)
        );

        this.setupLayout();
    }

    private void setupLayout() {
        // general layout setup
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        leftLayout.setPadding(new Insets(10));
        rightLayout.setPadding(new Insets(10));

        // fill width
        leftLayout.setFillWidth(true);
        rightLayout.setFillWidth(true);

        // min width
        leftLayout.setMinWidth(350);
        rightLayout.setMinWidth(350);
        estimatedEffortSpinner.setMinWidth(100);

        // title
        Label title = new Label("Aufgabe erstellen");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        HBox titleBox = new HBox(10, new Label("Titel:"), titleErrorLabel);
        titleErrorLabel.setStyle("-fx-text-fill: red;");
        titleBox.setPadding(new Insets(10, 0, 10, 0));

        // description
        HBox descriptionBox = new HBox(10, new Label("Beschreibung:"), descriptionErrorLabel);
        descriptionErrorLabel.setStyle("-fx-text-fill: red;");
        descriptionArea.setPrefHeight(55);
        descriptionBox.setPadding(new Insets(10, 0, 10, 0));

        // zip code
        HBox zipBox = new HBox(10, new Label("PLZ:"), zipErrorLabel);
        zipErrorLabel.setStyle("-fx-text-fill: red;");
        zipBox.setPadding(new Insets(10, 0, 10, 0));

        // city
        HBox cityBox = new HBox(10, new Label("Stadt:"), cityErrorLabel);
        cityErrorLabel.setStyle("-fx-text-fill: red;");
        cityBox.setPadding(new Insets(10));

        // payment
        HBox rewardBox = new HBox(10, new Label("Entlohnung:"), rewardErrorLabel);
        rewardErrorLabel.setStyle("-fx-text-fill: red;");
        rewardBox.setPadding(new Insets(10, 0, 10, 0));

        // estimated effort
        Label estimatedEffortLabel = new Label("Schwierigkeit:");
        estimatedEffortLabel.setPrefWidth(Integer.MAX_VALUE);
        HBox difficultyBox = new HBox(10, estimatedEffortLabel, estimatedEffortSpinner);
        difficultyBox.setPadding(new Insets(10, 0, 5, 0));

        // max width create button
        createButton.setPrefWidth(Double.MAX_VALUE);
        cancelButton.setPrefWidth(Double.MAX_VALUE);

        // ZIP-Code VBox
        VBox zipVBox = new VBox(0, zipBox, zipField);
        zipVBox.setAlignment(Pos.TOP_LEFT);
        zipVBox.setFillWidth(true);

        // City VBox
        VBox cityVBox = new VBox(0, cityBox, cityField);
        cityVBox.setAlignment(Pos.TOP_LEFT);
        cityVBox.setFillWidth(true);

        // zip code, city HBox
        HBox locationBox = new HBox(10, zipVBox, cityVBox);
        locationBox.setAlignment(Pos.TOP_LEFT);

        // buttons VBox
        VBox buttonBox = new VBox(10, createButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));
        buttonBox.setMinWidth(300);
        buttonBox.setFillWidth(true);

        setupImageSlider();

        // left layout
        leftLayout.getChildren().addAll(
                titleBox, titleField,
                descriptionBox, descriptionArea,
                imageSlider
        );

        // right layout
        rightLayout.getChildren().addAll(
                locationBox,
                rewardBox, rewardField,
                difficultyBox,
                buttonBox
        );

        // form layout
        layoutform.getChildren().addAll(leftLayout, rightLayout);

        this.getChildren().addAll(title, layoutform);
    }

    private void setupImageSlider() {
        // configs slider buttons
        prevButton.setDisable(true);
        nextButton.setDisable(true);
        prevButton.setStyle("-fx-font-size: 15px;");
        nextButton.setStyle("-fx-font-size: 15px;");

        // configs imageBox
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20px;");
        imageBox.setPrefWidth(320);
        imageBox.setPrefHeight(180);
        imageBox.setStyle("-fx-border-color: grey; -fx-border-width: 1px;");

        imageLayout.setAlignment(Pos.CENTER);
        imageLayout.getChildren().addAll(prevButton, imageBox, nextButton);

        // configs imadeSlider
        imageSlider.setPadding(new Insets(20, 0, 10, 0));
        imageSlider.setPrefWidth(350);
        imageSlider.setPrefHeight(200);
        imageSlider.setSpacing(15);
        imageSlider.setAlignment(Pos.CENTER);

        // TODO: Comment this to activate feature
        uploadButton.setDisable(true);

        imageSlider.getChildren().addAll(imageLayout, uploadButton);
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

    public void setZipErrorMessage(String message) {
        if (message.isEmpty()) {
            zipErrorLabel.textProperty().set("");
            return;
        }
        zipErrorLabel.textProperty().set(String.format("%s %s", ERROR_PREFIX, message));
    }

    public void setCityErrorMessage(String message) {
        if (message.isEmpty()) {
            cityErrorLabel.textProperty().set("");
            return;
        }
        cityErrorLabel.textProperty().set(String.format("%s %s", ERROR_PREFIX, message));
    }

    public void setRewardErrorMessage(String message) {
        if (message.isEmpty()) {
            rewardErrorLabel.textProperty().set("");
            return;
        }
        rewardErrorLabel.textProperty().set(String.format("%s %s", ERROR_PREFIX, message));
    }

    //region Getter
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

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getUploadButton() {
        return uploadButton;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public Button getPrevButton() {
        return prevButton;
    }

    public TextField getRewardField() {
        return rewardField;
    }

    public Label getRewardErrorLabel() {
        return rewardErrorLabel;
    }

    public TextField getCityField() {
        return cityField;
    }

    public TextField getZipField() {
        return zipField;
    }

    public Label getZipErrorLabel() {
        return zipErrorLabel;
    }

    public Label getCityErrorLabel() {
        return cityErrorLabel;
    }
    //endregion
}
