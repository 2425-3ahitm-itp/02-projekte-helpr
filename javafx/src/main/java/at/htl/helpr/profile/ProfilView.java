package at.htl.helpr.profile;

import at.htl.helpr.components.TaskList;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ProfilView extends BorderPane {

    private final TaskRepositoryImpl repository = new TaskRepositoryImpl();

    private final TaskList createdTasks = new TaskList(true,
            () -> repository.findAllTasksByUser(1), "Keine Aufgaben erstellt");

    private final TaskList appliedTasks = new TaskList(true,
            () -> repository.findAllTasksAppliedByUser(1), "Keine Aufgaben angenommen");


    private final VBox sidebar;
    private final Circle profileCircle;
    private final Label usernameLabel;
    private final Button newTaskButton;
    private final Button loginButton;
    private final Button homeButton;
    private final VBox mainContent;
    private final Label acceptedLabel;
    private final Label createdLabel;


    public ProfilView() {
        setPadding(new Insets(10));

        // Linke Seitenleiste
        sidebar = new VBox(15);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(250);
        sidebar.setAlignment(Pos.TOP_CENTER);

        // Profilbild (Kreisförmig)
        profileCircle = new Circle(40);
        profileCircle.setFill(Color.LIGHTGRAY);
        profileCircle.setStroke(Color.DARKGRAY);
        profileCircle.setStrokeWidth(1);

        usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 14px;");

        newTaskButton = new Button("Neue Aufgabe");
        newTaskButton.setMaxWidth(Double.MAX_VALUE);

        loginButton = new Button("Abmelden");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-border-color: black; -fx-border-style: dashed;");

        homeButton = new Button("Startseite");
        homeButton.setMaxWidth(Double.MAX_VALUE);
        homeButton.setStyle("-fx-background-color: #cce5ff; -fx-border-color: #99c2ff;");

        sidebar.getChildren()
                .addAll(profileCircle, usernameLabel, newTaskButton, loginButton,
                        homeButton);

        // Aufgabenbereiche
        mainContent = new VBox(20);
        mainContent.setPadding(new Insets(15));

        acceptedLabel = new Label("Angenommene Aufgaben");
        acceptedLabel.setStyle(
                "-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");

        createdLabel = new Label("Erstellte Aufgaben");
        createdLabel.setStyle(
                "-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");
        //        appliedTasks.setMinHeight(150);
//        appliedTasks.setPrefHeight(150);
//        createdTasks.setMinHeight(150);
//        createdTasks.setPrefHeight(150);

        mainContent.getChildren().addAll(acceptedLabel, appliedTasks, createdLabel,
                createdTasks);

        // Hauptlayout setzen
        setLeft(sidebar);
        setCenter(mainContent);
    }

    public TaskList getAppliedTasks() {
        return appliedTasks;
    }

    public TaskRepository getRepository() {
        return repository;
    }

    public TaskList getCreatedTasks() {
        return createdTasks;
    }

    public VBox getSidebar() {
        return sidebar;
    }

    public Circle getProfileCircle() {
        return profileCircle;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Button getNewTaskButton() {
        return newTaskButton;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public VBox getMainContent() {
        return mainContent;
    }

    public Label getAcceptedLabel() {
        return acceptedLabel;
    }

    public Label getCreatedLabel() {
        return createdLabel;
    }
}
