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

        // Login Panel (Center)
        loginBox = new VBox(10);
        loginBox.setPadding(new Insets(20));
        loginBox.setAlignment(Pos.CENTER_LEFT);
        loginBox.setStyle("-fx-border-color: black; -fx-background-color: white;");
        loginBox.setMaxWidth(300);
        loginBox.setMinHeight(400);

        VBox titleLableBox = new VBox(Double.MAX_VALUE);
        titleLableBox.setAlignment(Pos.CENTER);
        titleLabel = new Label("Log In");
        titleLabel.setFont(new Font(18));
        titleLabel.setStyle("-fx-background-color: #e6f4ff; -fx-padding: 5px 20px;");
        titleLableBox.getChildren().add(titleLabel);

        Label usernameLabel = new Label("Benutzername:");
        usernameField = new TextField();

        Label passwordLabel = new Label("Passwort:");
        passwordField = new PasswordField();

        loginButton = new Button("Log In");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-background-color: #e6f4ff;");

        overallErrorBox = new HBox(Double.MAX_VALUE);
        overallErrorBox.setAlignment(Pos.CENTER);
        overallErrorBox.setVisible(false);
        overallErrorBox.setManaged(false);
        overallErrorLabel = new Label("Eingabe ungültig. Beide Felder müssen ausgefüllt werden.");
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
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton, overallErrorBox,
                signUpBox
        );



        // Cancel Button (Bottom)
        cancelButton = new Button("Abbrechen");
        cancelButton.setMaxWidth(300);
        cancelButton.setStyle("-fx-border-style: dashed; -fx-border-color: black; -fx-background-color: transparent;");
        BorderPane.setAlignment(cancelButton, Pos.CENTER);

        layoutHelp = new VBox(20);
        layoutHelp.setAlignment(Pos.CENTER);
        layoutHelp.getChildren().addAll(loginBox, cancelButton);
        setCenter(layoutHelp);
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

    public Label getOverallErrorLabel() {
        return overallErrorLabel;
    }
}
