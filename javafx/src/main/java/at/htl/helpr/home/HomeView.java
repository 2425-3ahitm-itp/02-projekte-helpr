package at.htl.helpr.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class HomeView extends BorderPane {

    private final GridPane cardGrid;
    private final Button filterButton;
    private final Button locationFilter;
    private final Button dateFilter;
    private final Button priceFilter;
    private final Button searchButton;
    private final TextField searchField;

    public HomeView() {
        setPadding(new Insets(10));

        // --- Sidebar (Links) ---
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #d3d3d3;");

        // Filter Buttons
        filterButton = createStyledButton("Filtern", true);
        locationFilter = createStyledButton("Ort", false);
        dateFilter = createStyledButton("Datum", false);
        priceFilter = createStyledButton("Preis", false);

        sidebar.getChildren().addAll(filterButton, locationFilter, dateFilter, priceFilter);

        // --- Suchleiste mit Profilbild & Username (Oben) ---
        HBox searchBar = new HBox(15);
        searchBar.setPadding(new Insets(10));
        searchBar.setAlignment(Pos.CENTER_LEFT);

        // Profilbild (Kreisförmig)
        Circle profilePicture = new Circle(20);
        profilePicture.setFill(Color.LIGHTGRAY);
        profilePicture.setStroke(Color.DARKGRAY);
        profilePicture.setStrokeWidth(1);

        // Username
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font(14));

        // Container für Profilbild + Username
        HBox profileBox = new HBox(8, profilePicture, usernameLabel);
        profileBox.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Suche...");
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-font-size: 14px;");

        searchButton = new Button("Suchen");
        searchButton.setStyle("-fx-background-color: #cce5ff; -fx-border-color: #99c2ff; -fx-font-size: 14px;");

        searchBar.getChildren().addAll(profileBox, searchField, searchButton);

        // --- Aufgaben-Kartenübersicht (Mitte) ---
        cardGrid = new GridPane();
        cardGrid.setPadding(new Insets(15));
        cardGrid.setHgap(20);
        cardGrid.setVgap(20);

        // Layout setzen
        setLeft(sidebar);
        setTop(searchBar);
        setCenter(cardGrid);
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
}
