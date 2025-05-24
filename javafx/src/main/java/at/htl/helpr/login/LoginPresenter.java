package at.htl.helpr.login;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.scenemanager.Presenter;
import at.htl.helpr.scenemanager.SceneManager;
import at.htl.helpr.signup.SignupPresenter;
import at.htl.helpr.usermanager.UserManager;
import at.htl.helpr.usermanager.repository.exceptions.LoginFailedException;
import javafx.scene.Scene;

public class LoginPresenter implements Presenter {

    private final LoginView view;
    private final SceneManager sceneManager;
    private Scene scene;

    public LoginPresenter(LoginView view, SceneManager sceneManager) {
        this.view = view;
        this.sceneManager = sceneManager;
        attachEvents();
        deleteOldErrorHandling();
    }

    private void attachEvents() {
        view.getLoginButton().setOnAction(event -> handleLogin());
        view.getCancelButton().setOnAction(event -> handleCancel());
        view.getSignUpLink().setOnAction(event -> openSignUpView());
    }

    private void deleteOldErrorHandling() {
        getView().getOverallErrorBox().setVisible(false);
        getView().getOverallErrorBox().setManaged(false);
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();

        deleteOldErrorHandling();

        if (!username.isBlank() && !password.isBlank()) {
            // TODO: Authentifizierungs-Check.

            try {
                UserManager.getInstance().login(username, password);
            } catch (LoginFailedException e) {
                getView().getOverallErrorLabel().setText("Nutzername oder Passwort falsch!");
                getView().getOverallErrorBox().setVisible(true);
                getView().getOverallErrorBox().setManaged(true);
                return;
            }

        } else {
            getView().getOverallErrorLabel()
                    .setText("Eingabe ungültig. Beide Felder müssen ausgefüllt werden.");
            getView().getOverallErrorBox().setVisible(true);
            getView().getOverallErrorBox().setManaged(true);
            System.out.println("Benutzername oder Passwort leer.");
            return;
        }

        // Weiterleitung zur HomeView (nach erfolgreichem Login)
        sceneManager.setScene(HomePresenter.class);

    }

    private void handleCancel() {
        sceneManager.setScene(HomePresenter.class);
    }

    private void openSignUpView() {
        sceneManager.setScene(SignupPresenter.class);
    }

    public LoginView getView() {
        return view;
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            scene = new Scene(view, 1080, 648);
        }
        return scene;
    }

    @Override
    public void onShow() {
        // Felder zurücksetzen beim Öffnen
        view.getUsernameField().clear();
        view.getPasswordField().clear();
    }

    @Override
    public void onHide() {
    }
}
