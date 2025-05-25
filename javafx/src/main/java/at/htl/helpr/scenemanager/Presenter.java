package at.htl.helpr.scenemanager;

import javafx.scene.Scene;

public interface Presenter {

    Scene getScene();

    default void onShow() {
    }

    default void onHide() {
    }
}
