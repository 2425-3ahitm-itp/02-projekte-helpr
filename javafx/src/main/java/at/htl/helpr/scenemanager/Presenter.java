package at.htl.helpr.scenemanager;

import javafx.scene.Scene;

public abstract class Presenter {

    protected SceneManager sceneManager;
    protected Object view;
    protected Object model;

    public Presenter(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public abstract Scene getScene();
    public abstract void onShow();
    public abstract void onHide();
}
