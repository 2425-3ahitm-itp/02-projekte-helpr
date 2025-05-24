package at.htl.helpr.profile;

import at.htl.helpr.components.TaskList;
import at.htl.helpr.taskform.repository.TaskRepository;
import at.htl.helpr.taskform.repository.TaskRepositoryImpl;
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
            () -> repository.findAllTasksByUser(1),
            I18n.get().rawTranslate("profile.task-list.no-created-tasks"));

    private final TaskList appliedTasks = new TaskList(true,
            () -> repository.findAllTasksAppliedByUser(1),
            I18n.get().rawTranslate("profile.task-list.no-applied-tasks"));

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
        usernameLabel.textProperty().bind(I18n.get().translate("profile.labels.username-placeholder"));

        newTaskButton = new Button();
        newTaskButton.setMaxWidth(Double.MAX_VALUE);
        I18n.get().bind(newTaskButton, "profile.buttons.create-new-task");

        loginButton = new Button();
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-border-color: black; -fx-border-style: dashed;");
        I18n.get().bind(loginButton, "profile.buttons.logout");

        homeButton = new Button();
        homeButton.setMaxWidth(Double.MAX_VALUE);
        homeButton.setStyle("-fx-background-color: #cce5ff; -fx-border-color: #99c2ff;");
        I18n.get().bind(homeButton, "profile.buttons.navigate-home");

        sidebar.getChildren().addAll(profileCircle, usernameLabel, newTaskButton, loginButton, homeButton);

        mainContent = new VBox(20);
        mainContent.setPadding(new Insets(15));

        acceptedLabel = new Label();
        acceptedLabel.setStyle("-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");
        acceptedLabel.textProperty().bind(I18n.get().translate("profile.sections.applied-tasks"));

        createdLabel = new Label();
        createdLabel.setStyle("-fx-background-color: #cce5ff; -fx-padding: 5px; -fx-font-size: 14px;");
        createdLabel.textProperty().bind(I18n.get().translate("profile.sections.created-tasks"));

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
