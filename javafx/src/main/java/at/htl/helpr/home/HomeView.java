package at.htl.helpr.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private final TextField fromDateField;
    private final TextField toDateField;
    private final ToggleButton dateToggle;

    private final Button searchButton;
    private final TextField searchField;
    private final Circle profilePicture;
    private final Label usernameLabel;

    public HomeView() {
        setPadding(new Insets(10));

        // styling toggle button
        getStylesheets().add(getClass().getResource("homeToggleButtonStyle.css").toExternalForm());


        // --- Sidebar (Links) ---
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #d3d3d3;");

// Filter Anwenden Button
        filterButton = createStyledButton("Filter Anwenden", true);

// Bezahlung Filter
        Label paymentLabel = new Label("Bezahlung:");
        paymentBox = new HBox(5);
        minPaymentField = new TextField();
        minPaymentField.setPromptText("MIN");
        minPaymentField.setPrefWidth(70);
        maxPaymentField = new TextField();
        maxPaymentField.setPromptText("MAX");
        maxPaymentField.setPrefWidth(70);
        paymentToggle = createStyledToggleButton();
        paymentBox.getChildren().addAll(minPaymentField, maxPaymentField, paymentToggle);

// Aufwand Filter
        Label effortLabel = new Label("Aufwand:");
        effortBox = new HBox(5);
        effortField = new TextField();
        effortField.setPrefWidth(140);
        effortToggle = createStyledToggleButton();
        effortBox.getChildren().addAll(effortField, effortToggle);

        // PLZ Filter
        Label postalCodeLabel = new Label("PLZ:");
        postalCodeBox = new HBox(5);
        postalCodeField = new TextField();
        postalCodeField.setPrefWidth(140);
        postalToggle = createStyledToggleButton();
        postalCodeBox.getChildren().addAll(postalCodeField, postalToggle);

        // Ort Filter
        Label cityLabel = new Label("Ort:");
        cityBox = new HBox(5);
        cityField = new TextField();
        cityField.setPrefWidth(140);
        cityToggle = createStyledToggleButton();
        cityBox.getChildren().addAll(cityField, cityToggle);

        // Erstelldatum Filter
        Label creationDateLabel = new Label("Erstelldatum:");
        dateBox = new HBox(5);
        dateFields = new VBox(5);
        fromDateField = new TextField();
        fromDateField.setPromptText("von");
        toDateField = new TextField();
        toDateField.setPromptText("bis");
        dateFields.getChildren().addAll(fromDateField, toDateField);

// ToggleButton für Erstelldatum
        dateToggle = createStyledToggleButton();
        dateToggle.setPrefWidth(20); // << gleiche Breite wie alle anderen
        dateToggle.getStyleClass().add("toggle-button"); // falls du Styling hast

        dateBox.getChildren().addAll(dateFields, dateToggle);

        // Alles zusammensetzen
        sidebar.getChildren().addAll(
                filterButton,
                paymentLabel, paymentBox,
                effortLabel, effortBox,
                postalCodeLabel, postalCodeBox,
                cityLabel, cityBox,
                creationDateLabel, dateBox
        );

        designToggleButton();

        // --- Suchleiste mit Profilbild & Username (Oben) ---
        HBox searchBar = new HBox(15);
        searchBar.setPadding(new Insets(10));
        searchBar.setAlignment(Pos.CENTER_LEFT);

        // Profilbild (Kreisförmig)
        profilePicture = new Circle(20);
        profilePicture.setFill(Color.LIGHTGRAY);
        profilePicture.setStroke(Color.DARKGRAY);
        profilePicture.setStrokeWidth(1);

        // Username
        usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font(14));

        // Container für Profilbild + Username
        HBox profileBox = new HBox(8, profilePicture, usernameLabel);
        profileBox.setAlignment(Pos.CENTER_LEFT);

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
}
