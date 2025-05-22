package at.htl.helpr.login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginView extends BorderPane {

    private static final Logger log = LoggerFactory.getLogger(LoginView.class);
    private final VBox loginBox;
    private final Label titleLabel;
    private final TextField usernameField;
    private final HBox usernameErrorBox;
    private final PasswordField passwordField;
    private final HBox passwordErrorBox;
    private final Button loginButton;
    private final HBox overallErrorBox;
    private final HBox signUpBox;
    private final Label noAccountLabel;
    private final Hyperlink signUpLink;
    private final Button cancelButton;

    public LoginView() {
        setPadding(new Insets(10));

        // Login Panel (Center)
        loginBox = new VBox(10);
        loginBox.setPadding(new Insets(20));
        loginBox.setAlignment(Pos.CENTER_LEFT);
        loginBox.setStyle("-fx-border-color: black; -fx-background-color: white;");
        loginBox.setMaxWidth(300);
        loginBox.setMaxHeight(400);

        VBox titleLableBox = new VBox(Double.MAX_VALUE);
        titleLableBox.setAlignment(Pos.CENTER);
        titleLabel = new Label("Log In");
        titleLabel.setFont(new Font(18));
        titleLabel.setStyle("-fx-background-color: #e6f4ff; -fx-padding: 5px 20px;");
        titleLableBox.getChildren().add(titleLabel);

        Label usernameLabel = new Label("Benutzername:");
        usernameField = new TextField();

        usernameErrorBox = new HBox(Double.MAX_VALUE);
        usernameErrorBox.setAlignment(Pos.CENTER);
        usernameErrorBox.setVisible(false);
        usernameErrorBox.setManaged(false);
        Label usernameErrorLabel = new Label("Nutzername ist inkorrekt.");
        usernameErrorLabel.setWrapText(true);
        usernameErrorLabel.setStyle("-fx-text-fill: red;");
        usernameErrorBox.getChildren().addAll(usernameErrorLabel);

        Label passwordLabel = new Label("Passwort:");
        passwordField = new PasswordField();

        passwordErrorBox = new HBox(Double.MAX_VALUE);
        passwordErrorBox.setAlignment(Pos.CENTER);
        passwordErrorBox.setVisible(false);
        passwordErrorBox.setManaged(false);
        Label passwordErrorLabel = new Label("Passwort ist inkorrekt.");
        passwordErrorLabel.setWrapText(true);
        passwordErrorLabel.setStyle("-fx-text-fill: red;");
        passwordErrorBox.getChildren().addAll(passwordErrorLabel);

        loginButton = new Button("Log In");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-background-color: #e6f4ff;");

        overallErrorBox = new HBox(Double.MAX_VALUE);
        overallErrorBox.setAlignment(Pos.CENTER);
        overallErrorBox.setVisible(false);
        overallErrorBox.setManaged(false);
        Label overallErrorLabel = new Label("Eingabe ungültig. Beide Felder müssen ausgefüllt werden.");
        overallErrorLabel.setWrapText(true);
        overallErrorLabel.setStyle("-fx-text-fill: red;");
        overallErrorBox.getChildren().addAll(overallErrorLabel);

        noAccountLabel = new Label("Kein Account?");
        signUpLink = new Hyperlink("Sign Up");
        signUpLink.setStyle("-fx-text-fill: #87cefa;");
        signUpBox = new HBox(5, noAccountLabel, signUpLink);
        signUpBox.setAlignment(Pos.CENTER);

        VBox.setMargin(loginButton, new Insets(10, 0, 0, 0));
        VBox.setMargin(signUpBox, new Insets(5, 0, 0, 0));

        loginBox.getChildren().addAll(
                titleLableBox,
                usernameLabel, usernameField, usernameErrorBox,
                passwordLabel, passwordField, passwordErrorBox,
                loginButton, overallErrorBox,
                signUpBox
        );

        setCenter(loginBox);

        // Cancel Button (Bottom)
        cancelButton = new Button("Abbrechen");
        cancelButton.setMaxWidth(300);
        cancelButton.setStyle("-fx-border-style: dashed; -fx-border-color: black; -fx-background-color: transparent;");
        BorderPane.setAlignment(cancelButton, Pos.CENTER);
        setBottom(cancelButton);
    }

    // Getter für Controller
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

    public HBox getUsernameErrorBox() {
        return usernameErrorBox;
    }

    public HBox getPasswordErrorBox() {
        return passwordErrorBox;
    }
}
