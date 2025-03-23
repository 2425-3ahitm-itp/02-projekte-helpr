package at.htl.helpr;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javafx.application.Application;

@QuarkusMain
public class yQuarkusFxApplication implements QuarkusApplication {

    @Override
    public int run(String... args) throws Exception {
        Application.launch(App.class, args);
        return 0;
    }
}
