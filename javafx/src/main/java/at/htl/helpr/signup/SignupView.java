package at.htl.helpr.signup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignupView extends BorderPane {

    private static final Logger log = LoggerFactory.getLogger(SignupView.class);
    private final VBox signUpBox;
    private final Label titleLabel;
    private final TextField usernameField;
    private final HBox usernameErrorBox;
    private final PasswordField passwordField;
    private final HBox passwordErrorBox;
    private final Button signUpButton;
    private final HBox overallErrorBox;
    private final HBox loginBox;
    private final Label alreadyAccountLabel;
    private final Hyperlink loginLink;
    private final Button cancelButton;
    private final VBox layoutHelp;

    public SignupView() {
        setPadding(new Insets(10));

        // Login Panel
        signUpBox = new VBox(10);
        signUpBox.setPadding(new Insets(20));
        signUpBox.setAlignment(Pos.CENTER_LEFT);
        signUpBox.setStyle("-fx-border-color: black; -fx-background-color: white;");
        signUpBox.setMaxWidth(300);
        signUpBox.setMinHeight(400);

        VBox titleLableBox = new VBox(Double.MAX_VALUE);
        titleLableBox.setAlignment(Pos.CENTER);
        titleLabel = new Label("Sign Up");
        titleLabel.setFont(new Font(18));
        titleLabel.setStyle("-fx-background-color: #e6f4ff; -fx-padding: 5px 20px;");
        titleLableBox.getChildren().add(titleLabel);

        Label usernameLabel = new Label("Benutzername:");
        usernameField = new TextField();

        usernameErrorBox = new HBox(Double.MAX_VALUE);
        usernameErrorBox.setAlignment(Pos.CENTER);
        usernameErrorBox.setVisible(false);
        usernameErrorBox.setManaged(false);
        Label usernameErrorLabel = new Label("Nutzername ist ungültig. Erlaubt sind nur Buchstaben und Zahlen.");
        usernameErrorLabel.setWrapText(true);
        usernameErrorLabel.setStyle("-fx-text-fill: red;");
        usernameErrorBox.getChildren().addAll(usernameErrorLabel);

        Label passwordLabel = new Label("Passwort:");
        passwordField = new PasswordField();

        passwordErrorBox = new HBox(Double.MAX_VALUE);
        passwordErrorBox.setAlignment(Pos.CENTER);
        passwordErrorBox.setVisible(false);
        passwordErrorBox.setManaged(false);
        Label passwordErrorLabel = new Label("Passwort ist zu kurz. Mindestens 8 Zeichen erforderlich.");
        passwordErrorLabel.setWrapText(true);
        passwordErrorLabel.setStyle("-fx-text-fill: red;");
        passwordErrorBox.getChildren().addAll(passwordErrorLabel);

        signUpButton = new Button("Sign Up");
        signUpButton.setMaxWidth(Double.MAX_VALUE);
        signUpButton.setStyle("-fx-background-color: #e6f4ff;");

        overallErrorBox = new HBox(Double.MAX_VALUE);
        overallErrorBox.setAlignment(Pos.CENTER);
        overallErrorBox.setVisible(false);
        overallErrorBox.setManaged(false);
        Label overallErrorLabel = new Label("Eingabe ungültig. Beide Felder müssen ausgefüllt werden.");
        overallErrorLabel.setWrapText(true);
        overallErrorLabel.setStyle("-fx-text-fill: red;");
        overallErrorBox.getChildren().addAll(overallErrorLabel);

        alreadyAccountLabel = new Label("Kein Account?");
        loginLink = new Hyperlink("Log In");
        loginLink.setStyle("-fx-text-fill: #87cefa;");
        loginBox = new HBox(5, alreadyAccountLabel, loginLink);
        loginBox.setAlignment(Pos.CENTER);

        VBox.setMargin(signUpButton, new Insets(10, 0, 0, 0));
        VBox.setMargin(loginBox, new Insets(5, 0, 0, 0));

        signUpBox.getChildren().addAll(
                titleLableBox,
                usernameLabel, usernameField, usernameErrorBox,
                passwordLabel, passwordField, passwordErrorBox,
                signUpButton, overallErrorBox,
                loginBox
        );


        // Cancel Button
        cancelButton = new Button("Abbrechen");
        cancelButton.setMaxWidth(300);
        cancelButton.setStyle("-fx-border-style: dashed; -fx-border-color: black; -fx-background-color: transparent;");
        BorderPane.setAlignment(cancelButton, Pos.CENTER);

        layoutHelp = new VBox(20);
        layoutHelp.setAlignment(Pos.CENTER);
        layoutHelp.getChildren().addAll(signUpBox, cancelButton);
        setCenter(layoutHelp);
    }

    // Getter
    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getSignUpButton() {
        return signUpButton;
    }

    public Hyperlink getLoginLink() {
        return loginLink;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public HBox getUsernameErrorBox() {
        return usernameErrorBox;
    }

    public HBox getPasswordErrorBox() {
        return passwordErrorBox;
    }

    public HBox getOverallErrorBox() {
        return overallErrorBox;
    }
}
