package at.htl.helpr.profile;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.List;

public class ProfilView extends BorderPane {
    private ListView<String> angenommeneAufgabenList;
    private ListView<String> erstellteAufgabenList;

    public ProfilView() {
        setPadding(new Insets(10));

        // Linke Seitenleiste
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(250);
        sidebar.setAlignment(Pos.CENTER);

        // Profilbild (Kreisförmig)
        Circle profileCircle = new Circle(40);
        profileCircle.setFill(Color.LIGHTGRAY);
        profileCircle.setStroke(Color.DARKGRAY);
        profileCircle.setStrokeWidth(1);

        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 14px;");

        Button neueAufgabeButton = new Button("Neue Aufgabe");
        neueAufgabeButton.setMaxWidth(Double.MAX_VALUE);

        Button abmeldenButton = new Button("Abmelden");
        abmeldenButton.setMaxWidth(Double.MAX_VALUE);
        abmeldenButton.setStyle("-fx-border-color: black; -fx-border-style: dashed;");

        Button startseiteButton = new Button("Startseite");
        startseiteButton.setMaxWidth(Double.MAX_VALUE);
        startseiteButton.setStyle("-fx-background-color: #cce5ff; -fx-border-color: #99c2ff;");

        sidebar.getChildren().addAll(profileCircle, usernameLabel, neueAufgabeButton, abmeldenButton, startseiteButton);

        // Aufgabenbereiche
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(15));

        Label angenommeneLabel = new Label("Angenommene Aufgaben");
        angenommeneLabel.setStyle("-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");
        angenommeneAufgabenList = new ListView<>();
        angenommeneAufgabenList.setPrefHeight(150);

        Label erstellteLabel = new Label("Erstellte Aufgaben");
        erstellteLabel.setStyle("-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");
        erstellteAufgabenList = new ListView<>();
        erstellteAufgabenList.setPrefHeight(150);

        mainContent.getChildren().addAll(angenommeneLabel, angenommeneAufgabenList, erstellteLabel, erstellteAufgabenList);

        // Hauptlayout setzen
        setLeft(sidebar);
        setCenter(mainContent);
    }

    public void setAngenommeneAufgaben(List<String> aufgaben) {
        angenommeneAufgabenList.getItems().setAll(aufgaben);
    }

    public void setErstellteAufgaben(List<String> aufgaben) {
        erstellteAufgabenList.getItems().setAll(aufgaben);
    }
}
