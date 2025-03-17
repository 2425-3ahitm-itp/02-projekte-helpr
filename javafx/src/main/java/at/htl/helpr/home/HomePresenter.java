package at.htl.helpr.home;

import at.htl.helpr.profile.ProfilPresenter;
import at.htl.helpr.profile.ProfilView;
import at.htl.helpr.taskform.model.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class HomePresenter {

    private final HomeView view;
    private List<Task> taskList;

    public HomePresenter(HomeView view) {
        this.view = view;
        attachEvents();
    }

    public void setTaskList(List<Task> tasks) {
        this.taskList = tasks;
        updateTaskCards();
    }
    private void attachEvents() {
        getView().getProfilePicture().setOnMouseClicked(mouseEvent -> openProfilView());
        getView().getUsernameLabel().setOnMouseClicked(mouseEvent -> openProfilView());

    }



    private void openProfilView() {
        Stage currentStage = (Stage) getView().getProfilePicture().getScene().getWindow();
        currentStage.close();

        Stage stage = new Stage();
        var view = new ProfilView();
        var presenter = new ProfilPresenter(view);

        var scene = new Scene(view, 750, 450);

        stage.setTitle("Helpr Profil");
        stage.setScene(scene);
        stage.show();
    }



    private void updateTaskCards() {
        List<Task> tasks = getTaskList();
        GridPane cardGrid = view.getCardGrid();
        cardGrid.getChildren().clear();

        if (tasks == null) return;

        int col = 0, row = 0;
        for (Task task : tasks) {
            VBox card = createTaskCard(task);
            cardGrid.add(card, col, row);

            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createTaskCard(Task task) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #d3d3d3; -fx-background-color: white; -fx-border-radius: 8; -fx-padding: 10;");
        card.setPrefSize(150, 180);

        Label titleLabel = new Label(task.getTitle());
        titleLabel.setFont(new Font(14));

        Label locationLabel = new Label(task.getLocation());
        locationLabel.setFont(new Font(12));

        Region imagePlaceholder = new Region();
        imagePlaceholder.setStyle("-fx-background-color: lightgray; -fx-border-color: black; -fx-border-style: dashed;");
        imagePlaceholder.setPrefSize(100, 60);

        Label priceLabel = new Label(task.getReward() + "€");
        Label effortLabel = new Label(task.getEffort() + " Std.");

        HBox bottomRow = new HBox(10, priceLabel, effortLabel);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        card.getChildren().addAll(titleLabel, locationLabel, imagePlaceholder, bottomRow);
        return card;
    }


    public List<Task> getTaskList() {
        return taskList;
    }

    public HomeView getView() {
        return view;
    }
}
