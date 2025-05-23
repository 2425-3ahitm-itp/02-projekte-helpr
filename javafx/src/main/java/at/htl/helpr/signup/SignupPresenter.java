package at.htl.helpr.signup;

import at.htl.helpr.home.HomePresenter;
import at.htl.helpr.login.LoginPresenter;
import at.htl.helpr.scenemanager.Presenter;
import at.htl.helpr.scenemanager.SceneManager;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignupPresenter implements Presenter {

    private final SignupView view;
    private final SceneManager sceneManager;
    private Scene scene;

    public SignupPresenter(SignupView view, SceneManager sceneManager) {
        this.view = view;
        this.sceneManager = sceneManager;
        attachEvents();
    }

    private void attachEvents() {
        view.getSignUpButton().setOnAction(event -> handleLogin());
        view.getCancelButton().setOnAction(event -> handleCancel());
        view.getLoginLink().setOnAction(event -> openSignUpView());
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
            if ((username.matches(".*[^a-zA-Z0-9].*")) || (password.length() < 8)) {
                if (username.matches(".*[^a-zA-Z0-9].*")) {
                    getView().getUsernameErrorBox().setVisible(true);
                    getView().getUsernameErrorBox().setManaged(true);
                    System.out.println("Invalid username");
                }
                if (password.length() < 8) {
                    getView().getPasswordErrorBox().setVisible(true);
                    getView().getPasswordErrorBox().setManaged(true);
                    System.out.println("Invalid password");
                }
                return;
            }
        } else {
            getView().getOverallErrorBox().setVisible(true);
            getView().getOverallErrorBox().setManaged(true);
            System.out.println("Benutzername oder Passwort leer.");
            return;
        }

        //TODO: Neuen User ertellen.


        //TODO: Den neu erstellten User einloggen.

        // Weiterleitung zur HomeView (nach erfolgreichem Sign up und Login)
        sceneManager.setScene(HomePresenter.class);
    }

    private void handleCancel() {
        sceneManager.setScene(HomePresenter.class);

    }

    private void openSignUpView() {
        sceneManager.setScene(LoginPresenter.class);

    }

    public SignupView getView() {
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
