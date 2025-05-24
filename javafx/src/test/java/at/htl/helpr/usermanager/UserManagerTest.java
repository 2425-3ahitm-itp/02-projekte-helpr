package at.htl.helpr.usermanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import at.htl.helpr.sql.SqlRunner;
import at.htl.helpr.usermanager.model.User;
import at.htl.helpr.usermanager.repository.UserRepositoryImpl;
import at.htl.helpr.usermanager.repository.exceptions.LoginFailedException;
import at.htl.helpr.usermanager.repository.exceptions.UserAlreadyExistsException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class UserManagerTest {

    private final UserManager userManager = UserManager.getInstance();
    private UserRepositoryImpl userRepo = new UserRepositoryImpl();

    @BeforeEach
    void beforeEach() {
        SqlRunner.runSchema();
    }

    @Test
    void register_user() throws UserAlreadyExistsException {
        String username = "Thomas";
        String password = "hallo";

        User user = userManager.register(username, password);

        assertThat(userManager.getUser()).isNotNull();
        assertThat(userManager.getUser()).isEqualTo(user);
        assertThat(userManager.isLoggedIn()).isTrue();
    }

    @Test
    void login_success() throws LoginFailedException, UserAlreadyExistsException {

        String username = "Thomas";
        String password = "hallo";

        User registeredUser = userManager.register(username, password);
        userManager.logout();

        User loggedInUser = userManager.login(username, password);

        userManager.getUser();

        assertThat(userManager.getUser()).isNotNull();
        assertThat(userManager.getUser()).isEqualTo(loggedInUser);
        assertThat(userManager.getUser().getId()).isEqualTo(registeredUser.getId());
        assertThat(userManager.isLoggedIn()).isTrue();
    }
}