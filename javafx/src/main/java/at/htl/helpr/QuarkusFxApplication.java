package at.htl.helpr;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javafx.application.Application;

@QuarkusMain
public class QuarkusFxApplication implements QuarkusApplication {

    @Override
    public int run(String... args) {
        Application.launch(App.class, args);
        return 0;
    }
}
