package at.htl.helpr.profile;

import at.htl.helpr.components.TaskList;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
import at.htl.helpr.usermanager.UserManager;
import at.htl.helpr.util.I18n;
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
            () -> repository.findAllTasksByUser(UserManager.getInstance().getUser().getId()),
            I18n.get().translate("tasklist.no-tasks-created"));

    private final TaskList appliedTasks = new TaskList(true,
            () -> repository.findAllTasksAppliedByUser(UserManager.getInstance().getUser().getId()),
            I18n.get().translate("tasklist.no-tasks-accepted"));

    private final VBox sidebar;
    private final Circle profileCircle;
    private final Label usernameLabel;
    private final Button newTaskButton;
    private final Button logoutButton;
    private final Button homeButton;
    private final VBox mainContent;
    private final Label acceptedLabel;
    private final Label createdLabel;

    public ProfilView() {
        setPadding(new Insets(10));

        sidebar = new VBox(15);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(250);
        sidebar.setAlignment(Pos.TOP_CENTER);

        profileCircle = new Circle(40);
        profileCircle.setFill(Color.LIGHTGRAY);
        profileCircle.setStroke(Color.DARKGRAY);
        profileCircle.setStrokeWidth(1);

        usernameLabel = new Label();
        usernameLabel.setStyle("-fx-font-size: 14px;");

        newTaskButton = new Button();
        newTaskButton.setMaxWidth(Double.MAX_VALUE);
        I18n.get().bind(newTaskButton, "profile.buttons.create-new-task");

        logoutButton = I18n.get().bind(new Button(), "profile.buttons.logout");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setStyle("-fx-border-color: black; -fx-border-style: dashed;");

        homeButton = new Button();
        homeButton.setMaxWidth(Double.MAX_VALUE);
        homeButton.setStyle("-fx-background-color: #cce5ff; -fx-border-color: #99c2ff;");
        I18n.get().bind(homeButton, "profile.buttons.navigate-home");

        sidebar.getChildren().addAll(profileCircle, usernameLabel, newTaskButton, logoutButton,
                homeButton);

        mainContent = new VBox(20);
        mainContent.setPadding(new Insets(15));

        acceptedLabel = I18n.get().bind(new Label(), "profile.sections.applied-tasks");
        acceptedLabel
                .setStyle("-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");

        createdLabel = I18n.get().bind(new Label(), "profile.sections.created-tasks");
        createdLabel
                .setStyle("-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");
        // appliedTasks.setMinHeight(150);
        // appliedTasks.setPrefHeight(150);
        // createdTasks.setMinHeight(150);
        // createdTasks.setPrefHeight(150);

        mainContent.getChildren().addAll(acceptedLabel, appliedTasks, createdLabel, createdTasks);

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

    public Button getLogoutButton() {
        return logoutButton;
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
