package at.htl.helpr.scenemanager;

import javafx.scene.Scene;

public interface Presenter {

    Scene getScene();

    void onShow();

    void onHide();
}
