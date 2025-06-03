package at.htl.helpr.login;

import at.htl.helpr.util.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class LoginView extends BorderPane {

    private final VBox loginBox;
    private final Label titleLabel;
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Button loginButton;
    private final Label overallErrorLabel;
    private final HBox overallErrorBox;
    private final HBox signUpBox;
    private final Label noAccountLabel;
    private final Hyperlink signUpLink;
    private final Button cancelButton;
    private final VBox layoutHelp;

    public LoginView() {
        setPadding(new Insets(10));

        // Login Panel
        loginBox = new VBox(10);
        loginBox.setPadding(new Insets(20));
        loginBox.setAlignment(Pos.CENTER_LEFT);
        loginBox.setStyle("-fx-border-color: black; -fx-background-color: white;");
        loginBox.setMaxWidth(300);
        loginBox.setMinHeight(400);

        VBox titleLableBox = new VBox(Double.MAX_VALUE);
        titleLableBox.setAlignment(Pos.CENTER);
        titleLabel = I18n.get().bind(new Label(), "login.title");
        titleLabel.setFont(new Font(18));
        titleLabel.setStyle("-fx-background-color: #e6f4ff; -fx-padding: 5px 20px;");
        titleLableBox.getChildren().add(titleLabel);

        Label usernameLabel = I18n.get().bind(new Label(), "login.username.label");
        usernameField = new TextField();

        Label passwordLabel = I18n.get().bind(new Label(), "login.password.label");
        passwordField = new PasswordField();

        loginButton = I18n.get().bind(new Button(), "login.button");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-background-color: #e6f4ff;");

        overallErrorBox = new HBox(Double.MAX_VALUE);
        overallErrorBox.setAlignment(Pos.CENTER);
        overallErrorBox.setVisible(false);
        overallErrorBox.setManaged(false);
        overallErrorLabel = new Label();
        overallErrorLabel.setWrapText(true);
        overallErrorLabel.setStyle("-fx-text-fill: red;");
        overallErrorBox.getChildren().addAll(overallErrorLabel);

        noAccountLabel = I18n.get().bind(new Label(), "login.no.account");
        signUpLink = I18n.get().bind(new Hyperlink(), "login.signup.link");
        signUpLink.setStyle("-fx-text-fill: #87cefa;");
        signUpBox = new HBox(5, noAccountLabel, signUpLink);
        signUpBox.setAlignment(Pos.CENTER);

        VBox.setMargin(loginButton, new Insets(10, 0, 0, 0));
        VBox.setMargin(signUpBox, new Insets(5, 0, 0, 0));

        loginBox.getChildren().addAll(titleLableBox, usernameLabel, usernameField, passwordLabel,
                passwordField, loginButton, overallErrorBox, signUpBox);

        // Cancel Button
        cancelButton = I18n.get().bind(new Button(), "login.cancel.button");
        cancelButton.setMaxWidth(300);
        cancelButton.setStyle("-fx-border-style: dashed; -fx-border-color: black;"
                + "-fx-background-color: transparent;");
        BorderPane.setAlignment(cancelButton, Pos.CENTER);

        layoutHelp = new VBox(20);
        layoutHelp.setAlignment(Pos.CENTER);
        layoutHelp.getChildren().addAll(loginBox, cancelButton);
        setCenter(layoutHelp);
    }

    // Getter
    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Hyperlink getSignUpLink() {
        return signUpLink;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public HBox getOverallErrorBox() {
        return overallErrorBox;
    }

    public Label getOverallErrorLabel() {
        return overallErrorLabel;
    }
}