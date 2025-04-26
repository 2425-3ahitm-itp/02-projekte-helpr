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

    private final Button searchButton;
    private final TextField searchField;
    private final Circle profilePicture;
    private final Label usernameLabel;

    public HomeView() {
        setPadding(new Insets(10));

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
        paymentToggle = new ToggleButton();
        paymentBox.getChildren().addAll(minPaymentField, maxPaymentField, paymentToggle);

// Aufwand Filter
        Label effortLabel = new Label("Aufwand:");
        effortBox = new HBox(5);
        effortField = new TextField();
        effortField.setPrefWidth(170);
        effortToggle = new ToggleButton();
        effortToggle.setPrefWidth(20);
        effortBox.getChildren().addAll(effortField, effortToggle);

// PLZ Filter
        Label postalCodeLabel = new Label("PLZ:");
        postalCodeBox = new HBox(5);
        postalCodeField = new TextField();
        postalCodeField.setPrefWidth(170);
        postalToggle = new ToggleButton();
        postalToggle.setPrefWidth(20);
        postalCodeBox.getChildren().addAll(postalCodeField, postalToggle);

// Ort Filter
        Label cityLabel = new Label("Ort:");
        cityBox = new HBox(5);
        cityField = new TextField();
        cityField.setPrefWidth(170);
        cityToggle = new ToggleButton();
        cityToggle.setPrefWidth(20);
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

        dateBox.getChildren().addAll(dateFields);

// Alles zusammensetzen
        sidebar.getChildren().addAll(
                filterButton,
                paymentLabel, paymentBox,
                effortLabel, effortBox,
                postalCodeLabel, postalCodeBox,
                cityLabel, cityBox,
                creationDateLabel, dateBox
        );

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


    private Button createStyledButton(String text, boolean highlighted) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle(highlighted ?
                "-fx-background-color: #cce5ff; -fx-border-color: #99c2ff; -fx-font-size: 14px;" :
                "-fx-background-color: transparent; -fx-border-color: black; -fx-border-style: dashed; -fx-font-size: 14px;");
        return button;
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
