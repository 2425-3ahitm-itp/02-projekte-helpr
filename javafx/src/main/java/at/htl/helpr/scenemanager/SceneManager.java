package at.htl.helpr.scenemanager;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * The SceneManager class is responsible for managing and switching between different scenes in the application.
 * It provides functionality for adding presenters, switching scenes, and ensuring that the UI is updated correctly.
 *
 * <p>Usage Example:</p>
 * <pre>{@code
 * SceneManager sceneManager = new SceneManager(stage);
 * sceneManager.addPresenter(HomePresenter.class, new HomePresenter(homeView, sceneManager));
 * sceneManager.setScene(HomePresenter.class);
 * }</pre>
 *
 * <p>Typical lifecycle:</p>
 * <ol>
 *   <li>Create a SceneManager instance with the primary Stage.</li>
 *   <li>Add presenters for the scenes using {@link #addPresenter}.</li>
 *   <li>Switch between scenes using {@link #setScene(Class)}.</li>
 * </ol>

 * @see Presenter
 * @see javafx.scene.Scene
 * @see javafx.stage.Stage
 */
public class SceneManager {

    private static SceneManager instance;
    private final Stage primaryStage;
    private final Map<Class<? extends Presenter>, Presenter> presenters = new HashMap<>();
    private Presenter currentPresenter;

    /**
     * Private constructor to initialize the SceneManager with the given primary Stage.
     *
     * @param stage The primary Stage for the application.
     */
    private SceneManager(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Initializes the SceneManager instance. This method must be called before using the SceneManager.
     *
     * @param stage The primary Stage of the application.
     * @throws IllegalStateException If the SceneManager is already initialized.
     */
    public static void initialize(Stage stage) {
        if (instance == null) {
            instance = new SceneManager(stage);
        }
    }

    /**
     * Returns the singleton instance of the SceneManager.
     *
     * @return The SceneManager instance.
     * @throws IllegalStateException If the SceneManager has not been initialized.
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SceneManager not initialized. Call initialize() first.");
        }
        return instance;
    }

    /**
     * Adds a presenter to the SceneManager.
     *
     * @param presenterClass The class of the presenter to be added.
     * @param presenter The presenter instance to be added.
     */
    public void addPresenter(Class<? extends Presenter> presenterClass, Presenter presenter) {
        presenters.put(presenterClass, presenter);
    }

    /**
     * Switches the current scene to the one managed by the specified presenter class.
     * The current presenter is notified to hide, and the new presenter is shown.
     *
     * @param presenterClass The class of the presenter that controls the scene to be set.
     * @throws IllegalArgumentException If the presenter class is not registered.
     */
    public void setScene(Class<? extends Presenter> presenterClass) {
        Presenter nextPresenter = presenters.get(presenterClass);

        if (nextPresenter == null) {
            throw new IllegalArgumentException("Presenter not registered: " + presenterClass.getSimpleName());
        }

        if (currentPresenter != null) {
            currentPresenter.onHide();  // Hide the current presenter
        }

        Scene nextScene = nextPresenter.getScene();
        primaryStage.setScene(nextScene);  // Set the new scene to the primary stage

        currentPresenter = nextPresenter;  // Update the current presenter
        currentPresenter.onShow();  // Notify the new presenter to show itself
    }
}
