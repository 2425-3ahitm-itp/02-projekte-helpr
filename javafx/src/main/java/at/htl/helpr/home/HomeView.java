package at.htl.helpr.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class HomeView extends BorderPane {

    private final GridPane cardGrid;

    private final Button filterButton;
    private final HBox paymentBox;
    private final TextField minPaymentField;
    private final TextField maxPaymentField;
    private final ToggleButton paymentToggle;
    private final HBox effortBox;
    private final TextField effortField;
    private final ToggleButton effortToggle;
    private final HBox postalCodeBox;
    private final TextField postalCodeField;
    private final ToggleButton postalToggle;
    private final HBox cityBox;
    private final TextField cityField;
    private final ToggleButton cityToggle;
    private final HBox dateBox;
    private final VBox dateFields;
    private final DatePicker fromDateField;
    private final DatePicker toDateField;
    private final ToggleButton dateToggle;

    private final Button searchButton;
    private final TextField searchField;
    private final Circle profilePicture;
    private final Label usernameLabel;
    private final Button loginButton;
    private final HBox profileBoxUserSection;

    public HomeView() {
        setPadding(new Insets(10));

        // styling toggle button
        getStylesheets().add(getClass().getResource("homeToggleButtonStyle.css")
                .toExternalForm());
        getStylesheets().add(getClass().getResource("homeDatePickerStyle.css")
                .toExternalForm());

        // --- Sidebar (Links) ---
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #d3d3d3;");

// Filter Anwenden Button
        filterButton = createStyledButton("Filter Anwenden", true);

// Bezahlung Filter
        paymentBox = new HBox(5);
        Label paymentLabel = new Label("Bezahlung:");
        paymentToggle = createStyledToggleButton();
        Region paymentSpacer = new Region();
        HBox.setHgrow(paymentSpacer, Priority.ALWAYS);
        HBox paymentLabelBox = new HBox(5, paymentLabel, paymentSpacer, paymentToggle);
        paymentLabelBox.setAlignment(Pos.CENTER_LEFT);

        minPaymentField = new TextField();
        minPaymentField.setPromptText("MIN");
        minPaymentField.setPrefWidth(80);

        maxPaymentField = new TextField();
        maxPaymentField.setPromptText("MAX");
        maxPaymentField.setPrefWidth(80);

        paymentBox.getChildren().addAll(minPaymentField, maxPaymentField);

// Aufwand Filter
        effortBox = new HBox(5);
        Label effortLabel = new Label("Aufwand:");
        effortToggle = createStyledToggleButton();
        Region effortSpacer = new Region();
        HBox.setHgrow(effortSpacer, Priority.ALWAYS);
        HBox effortLabelBox = new HBox(5, effortLabel, effortSpacer, effortToggle);
        effortLabelBox.setAlignment(Pos.CENTER_LEFT);

        effortField = new TextField();

        effortBox.getChildren().addAll(effortField);

// PLZ Filter
        postalCodeBox = new HBox(5);
        Label postalCodeLabel = new Label("PLZ:");
        postalToggle = createStyledToggleButton();
        Region postalSpacer = new Region();
        HBox.setHgrow(postalSpacer, Priority.ALWAYS);
        HBox postalCodeLabelBox = new HBox(5, postalCodeLabel, postalSpacer, postalToggle);
        postalCodeLabelBox.setAlignment(Pos.CENTER_LEFT);

        postalCodeField = new TextField();

        postalCodeBox.getChildren().addAll(postalCodeField);

// Ort Filter
        cityBox = new HBox(5);
        Label cityLabel = new Label("Ort:");
        cityToggle = createStyledToggleButton();
        Region citySpacer = new Region();
        HBox.setHgrow(citySpacer, Priority.ALWAYS);
        HBox cityLabelBox = new HBox(5, cityLabel, citySpacer, cityToggle);
        cityLabelBox.setAlignment(Pos.CENTER_LEFT);

        cityField = new TextField();

        cityBox.getChildren().addAll(cityField);

// Erstelldatum Filter
        dateBox = new HBox(5);
        Label creationDateLabel = new Label("Erstelldatum:");
        dateToggle = createStyledToggleButton();
        Region dateSpacer = new Region();
        HBox.setHgrow(dateSpacer, Priority.ALWAYS);
        HBox creationDateLabelBox = new HBox(5, creationDateLabel, dateSpacer, dateToggle);
        creationDateLabelBox.setAlignment(Pos.CENTER_LEFT);

        dateFields = new VBox(5);
        fromDateField = new DatePicker();
        fromDateField.setPromptText("von");
        toDateField = new DatePicker();
        toDateField.setPromptText("bis");
        dateFields.getChildren().addAll(fromDateField, toDateField);

        // add class date-picker to datepicker

        dateBox.getChildren().addAll(dateFields);

// --- Sidebar zusammensetzen
        sidebar.getChildren().addAll(
                filterButton,
                paymentLabelBox, paymentBox,
                effortLabelBox, effortBox,
                postalCodeLabelBox, postalCodeBox,
                cityLabelBox, cityBox,
                creationDateLabelBox, dateBox
        );

        designToggleButton();

        // --- Suchleiste mit Profilbild & Username (Oben) ---
        HBox searchBar = new HBox(15);
        searchBar.setPadding(new Insets(10));
        searchBar.setAlignment(Pos.CENTER_LEFT);

        HBox profileBox = new HBox();
        loginButton = new Button("Login");

        profileBoxUserSection = new HBox(8);

        loginButton.setStyle("-fx-background-color: #cce5ff; "
                + "-fx-border-color: #99c2ff; -fx-font-size: 14px;");

        // Profilbild (Kreisförmig)
        profilePicture = new Circle(20);
        profilePicture.setFill(Color.LIGHTGRAY);
        profilePicture.setStroke(Color.DARKGRAY);
        profilePicture.setStrokeWidth(1);

        // Username
        usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font(14));

        profileBoxUserSection.getChildren().addAll(profilePicture, usernameLabel);

        profileBox.getChildren().addAll(profileBoxUserSection, loginButton);

        // Container für Profilbild + Username
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBoxUserSection.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Suche...");
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-font-size: 14px;");

        searchButton = new Button("Suchen");
        searchButton.setStyle("-fx-background-color: #cce5ff; "
                + "-fx-border-color: #99c2ff; -fx-font-size: 14px;");

        searchBar.getChildren().addAll(profileBox, searchField, searchButton);

        // --- Aufgaben-Kartenübersicht (Mitte) ---
        cardGrid = new GridPane();
        cardGrid.setPadding(new Insets(15));
        cardGrid.setHgap(20);
        cardGrid.setVgap(20);

        // Taskliste
//        TaskList taskList = new TaskList(false);

        // Layout setzen
        setLeft(sidebar);
        setTop(searchBar);
//        setCenter(cardGrid);
    }

    private void designToggleButton() {
        effortToggle.setGraphic(new Region());
        effortToggle.getGraphic().getStyleClass().add("thumb");

        postalToggle.setGraphic(new Region());
        postalToggle.getGraphic().getStyleClass().add("thumb");

        cityToggle.setGraphic(new Region());
        cityToggle.getGraphic().getStyleClass().add("thumb");

        dateToggle.setGraphic(new Region());
        dateToggle.getGraphic().getStyleClass().add("thumb");

        paymentToggle.setGraphic(new Region());
        paymentToggle.getGraphic().getStyleClass().add("thumb");


    }


    private Button createStyledButton(String text, boolean highlighted) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle(highlighted ?
                "-fx-background-color: #cce5ff; -fx-border-color: #99c2ff; -fx-font-size: 14px;" :
                "-fx-background-color: transparent; -fx-border-color: black; -fx-border-style: dashed; -fx-font-size: 14px;");
        return button;
    }


    private ToggleButton createStyledToggleButton() {
        ToggleButton toggle = new ToggleButton();
        toggle.setPrefSize(40, 20);
        toggle.getStyleClass().add("switch-toggle");
        return toggle;
    }


    public GridPane getCardGrid() {
        return cardGrid;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Circle getProfilePicture() {
        return profilePicture;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Button getFilterButton() {
        return filterButton;
    }

    public HBox getPaymentBox() {
        return paymentBox;
    }

    public TextField getMinPaymentField() {
        return minPaymentField;
    }

    public TextField getMaxPaymentField() {
        return maxPaymentField;
    }

    public ToggleButton getPaymentToggle() {
        return paymentToggle;
    }

    public HBox getEffortBox() {
        return effortBox;
    }

    public TextField getEffortField() {
        return effortField;
    }

    public ToggleButton getEffortToggle() {
        return effortToggle;
    }

    public HBox getPostalCodeBox() {
        return postalCodeBox;
    }

    public TextField getPostalCodeField() {
        return postalCodeField;
    }

    public ToggleButton getPostalToggle() {
        return postalToggle;
    }

    public HBox getCityBox() {
        return cityBox;
    }

    public TextField getCityField() {
        return cityField;
    }

    public ToggleButton getCityToggle() {
        return cityToggle;
    }

    public HBox getDateBox() {
        return dateBox;
    }

    public VBox getDateFields() {
        return dateFields;
    }

    public DatePicker getFromDateField() {
        return fromDateField;
    }

    public DatePicker getToDateField() {
        return toDateField;
    }

    public ToggleButton getDateToggle() {
        return dateToggle;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public HBox getProfileBoxUserSection() {
        return profileBoxUserSection;
    }
}
