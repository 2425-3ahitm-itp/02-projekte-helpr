package at.htl.helpr.profile.repository;

import at.htl.helpr.usermanager.model.User;
import at.htl.helpr.usermanager.repository.UserRepositoryImpl;
import at.htl.helpr.usermanager.repository.exceptions.UserAlreadyExistsException;
import at.htl.helpr.sql.SqlRunner;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
public class UserRepositoryImplTest {

    UserRepositoryImpl userRepository = new UserRepositoryImpl();

    User user1;
    User user2;

    @BeforeEach
    void setUp() throws UserAlreadyExistsException {
        SqlRunner.runSchema();
        SqlRunner.runInserts();
        setupUsers();
    }

    // insert test users before all tests

    void setupUsers() throws UserAlreadyExistsException {
        // fill user1 and user2
        user1 = userRepository.registerWithUsernameAndPassword("tomas", "somepassword");
        user2 = userRepository.registerWithUsernameAndPassword("tomas2", "pw2");
    }


    @Test
    void updateProfilePicture() {
        byte[] testImage = {1, 2, 3, 4, 5};
        user1.setProfilePicture(testImage);

        userRepository.update(user1);

        User retrievedUser = userRepository.findById(user1.getId());
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getProfilePicture()).isEqualTo(testImage);
    }

    @Test
    void update() {

        long userId = user1.getId();

        user1.setUsername("updatedname");
        final var newPassword = "completelynewpassword";
        user1.setPassword(newPassword);
        byte[] profilePic = {10, 20, 30};
        user1.setProfilePicture(profilePic);

        userRepository.update(user1);

        User updatedUser = userRepository.findById(userId);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo("updatedname");
        assertThat(BcryptUtil.matches(newPassword, updatedUser.getPassword())).isTrue();
        assertThat(updatedUser.getProfilePicture()).isEqualTo(profilePic);
    }

    @Test
    void delete() {

        long userId = user1.getId();

        User beforeDelete = userRepository.findById( userId );
        assertThat( beforeDelete ).isNotNull();

        userRepository.delete( userId );

        User afterDelete = userRepository.findById( userId );
        assertThat( afterDelete ).isNull();
    }

    @Test
    void findAll() throws UserAlreadyExistsException {
        List<User> initialUsers = userRepository.findAll();
        int initialCount = initialUsers.size();

        userRepository.registerWithUsernameAndPassword("newuser1", "password1");
        userRepository.registerWithUsernameAndPassword("newuser2", "password2");

        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers).hasSize(initialCount + 2);
        assertThat(allUsers).extracting(User::getUsername).contains("tomas", "tomas2");
    }

    @Test
    void findById() {
        User foundUser = userRepository.findById(user1.getId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(user1.getUsername());
    }

    @Test
    void findByUsername() {
        User foundUser = userRepository.findByUsername(user1.getUsername());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(user1.getUsername());
    }

    @Test
    void findByIdNotFound() {
        User foundUser = userRepository.findById(9999);
        assertThat(foundUser).isNull();
    }

    @Test
    void registerUserWithUsernameAndPassword() throws UserAlreadyExistsException {
        String username = "tommy";
        String password = "mysecurepassword";

        User registeredUser = userRepository.registerWithUsernameAndPassword(username, password);

        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getUsername()).isEqualTo(username);

        User foundUser = userRepository.findById(registeredUser.getId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(username);
    }

    @Test
    void registerUserWithExistingUsername() {
        String username = user1.getUsername();
        String password = "newpassword";

        assertThatThrownBy(() -> userRepository.registerWithUsernameAndPassword(username, password))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("User with username <" + username + "> already exists!");
    }

    @Test
    void loginWithUsernameAndPassword() {
        String username = user1.getUsername();
        String password = "somepassword";

        User loggedInUser = userRepository.findByUsernameAndPassword(username, password);

        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.getUsername()).isEqualTo(username);
    }

    @Test
    void registerUserWithUsernameAndPassword() throws UserAlreadyExistsException {
        String username = "tommy";
        String password = "mysecurepassword";

        User registeredUser = userRepository.registerWithUsernameAndPassword( username, password );

        assertThat( registeredUser ).isNotNull();
        assertThat( registeredUser.getUsername() ).isEqualTo( username );

        User foundUser = userRepository.findById( registeredUser.getId() );
        assertThat( foundUser ).isNotNull();
        assertThat( foundUser.getUsername() ).isEqualTo( username );
    }

    @Test
    void registerUserWithExistingUsername() {
        String username = user1.getUsername();
        String password = "newpassword";


        assertThatThrownBy(
                () -> userRepository.registerWithUsernameAndPassword( username, password )
        ).isInstanceOf( UserAlreadyExistsException.class )
                .hasMessageContaining( "User with username <" + username + "> already exists!" );
    }

    @Test
    void loginWithUsernameAndPassword() {
        String username = user1.getUsername();
        String password = "somepassword";

        User loggedInUser = userRepository.findByUsernameAndPassword( username, password );

        assertThat( loggedInUser ).isNotNull();
        assertThat( loggedInUser.getUsername() ).isEqualTo( username );
    }


}