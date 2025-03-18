package at.htl.helpr.profile;

import at.htl.helpr.components.TaskList;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
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

    private final TaskRepository repository = new TaskRepositoryImpl();

    private final TaskList createdTasks = new TaskList(true,
            () -> repository.findAllTasksByUser(1));

    private final TaskList appliedTasks = new TaskList(true,
            () -> repository.findAllTasksAppliedByUser(1));

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

        sidebar.getChildren()
                .addAll(profileCircle, usernameLabel, neueAufgabeButton, abmeldenButton,
                        startseiteButton);

        // Aufgabenbereiche
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(15));

        Label angenommeneLabel = new Label("Angenommene Aufgaben");
        angenommeneLabel.setStyle(
                "-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");

        Label erstellteLabel = new Label("Erstellte Aufgaben");
        erstellteLabel.setStyle(
                "-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");
        appliedTasks.setPrefHeight(150);

        mainContent.getChildren().addAll(angenommeneLabel, appliedTasks, erstellteLabel,
                createdTasks);

        // Hauptlayout setzen
        setLeft(sidebar);
        setCenter(mainContent);
    }

}
