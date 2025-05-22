package at.htl.helpr.login;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.scenemanager.Presenter;
import at.htl.helpr.scenemanager.SceneManager;
import at.htl.helpr.signup.SignupPresenter;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        getView().getUsernameErrorBox().setVisible(false);
        getView().getUsernameErrorBox().setManaged(false);
        getView().getPasswordErrorBox().setVisible(false);
        getView().getPasswordErrorBox().setManaged(false);
        getView().getOverallErrorBox().setVisible(false);
        getView().getOverallErrorBox().setManaged(false);
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();

        deleteOldErrorHandling();

        if (!username.isBlank() && !password.isBlank()) {
            //TODO: Authentifizierungs-Check.


            //TODO: Fehlermeldungen ausgeben
            /*if (password = false || username = false) {
                if (username = false) {
                    getView().getUsernameErrorBox().setVisible(true);
                    getView().getUsernameErrorBox().setManaged(true);
                    System.out.println("Invalid username");
                }
                if (password = false) {
                    getView().getPasswordErrorBox().setVisible(true);
                    getView().getPasswordErrorBox().setManaged(true);
                    System.out.println("Invalid password");
                }
                return;

            }*/

        } else {
            getView().getOverallErrorBox().setVisible(true);
            getView().getOverallErrorBox().setManaged(true);
            System.out.println("Benutzername oder Passwort leer.");
            return;
        }

        // Weiterleitung zur HomeView (nach erfolgreichem Login)
        Stage currentStage = (Stage) view.getScene().getWindow();
        sceneManager.setScene(HomePresenter.class);
        currentStage.setTitle("Helpr-Startseite");
        currentStage.show();
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
        // z.B. Felder zurücksetzen beim Öffnen
        view.getUsernameField().clear();
        view.getPasswordField().clear();
    }

    @Override
    public void onHide() {
        // z.B. Ressourcen freigeben oder Events deregistrieren
    }
}
