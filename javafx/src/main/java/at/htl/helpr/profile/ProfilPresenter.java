package at.htl.helpr.profile;

import at.htl.helpr.taskform.model.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class ProfilPresenter {

    private final ProfilView view;

    public ProfilPresenter(ProfilView view) {
        this.view = view;
        attachEvents();
    }

    private void attachEvents() {

    }

}
