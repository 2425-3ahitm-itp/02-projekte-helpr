package at.htl.helpr;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

  @Override
  public void start(Stage primaryStage) throws IOException {
    Scene scene = new Scene(new StackPane(), 320, 240);
    primaryStage.setTitle("App");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}