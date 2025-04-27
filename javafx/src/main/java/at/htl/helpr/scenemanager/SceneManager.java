package at.htl.helpr.scenemanager;

import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private Stage primaryStage;
    private Map<Class<? extends Presenter>, Presenter> presenters = new HashMap<>();
    Presenter currentPresenter;


    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void addPresenter(Class<? extends Presenter> presenterClass, Presenter presenter) {
        presenters.put(presenterClass, presenter);
    }

    public void setScene(Class<? extends Presenter> presenterClass) {
        Presenter nextPresenter = presenters.get(presenterClass);

        if (currentPresenter != null) {
            currentPresenter.onHide();
        }

        primaryStage.setScene(nextPresenter.getScene());
        currentPresenter = nextPresenter;
        currentPresenter.onShow();
    }
}
