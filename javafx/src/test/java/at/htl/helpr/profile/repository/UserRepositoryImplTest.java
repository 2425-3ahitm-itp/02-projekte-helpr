package at.htl.helpr.profile.repository;

import at.htl.helpr.profile.model.User;
import at.htl.helpr.sql.SqlRunner;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import at.htl.helpr.Utils;

import java.util.List;

import static at.htl.helpr.Utils.get100CharsString;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class UserRepositoryImplTest {

    UserRepositoryImpl userRepository = new UserRepositoryImpl();

    @BeforeEach
    void setUp() {
        SqlRunner.runSchema();
        SqlRunner.runInserts();
    }

    @Test
    void create() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword(get100CharsString( 'c' ));

        userRepository.create(user);

        User retrievedUser = userRepository.findById(user.getId());
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(retrievedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(retrievedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(retrievedUser.getProfilePicture()).isNull();
    }

    @Test
    void createWithProfilePicture() {
        User user = new User();
        user.setUsername("userwithpic");
        user.setEmail("pic@example.com");
        user.setPassword("securepass");
        byte[] testImage = {1, 2, 3, 4, 5};
        user.setProfilePicture(testImage);

        userRepository.create(user);

        User retrievedUser = userRepository.findById(user.getId());
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getProfilePicture()).isEqualTo(testImage);
    }

    @Test
    void update() {
        User user = new User();
        user.setUsername("updateuser");
        user.setEmail("update@example.com");
        user.setPassword(get100CharsString( 'h' ));

        userRepository.create(user);
        long userId = user.getId();

        user.setUsername("updatedname");
        user.setEmail("newemail@example.com");
        final var newPassword = get100CharsString( 'g' );
        user.setPassword(newPassword);
        byte[] profilePic = {10, 20, 30};
        user.setProfilePicture(profilePic);

        userRepository.update(user);

        User updatedUser = userRepository.findById(userId);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo("updatedname");
        assertThat(updatedUser.getEmail()).isEqualTo("newemail@example.com");
        assertThat(updatedUser.getPassword()).isEqualTo(newPassword);
        assertThat(updatedUser.getProfilePicture()).isEqualTo(profilePic);
    }

    @Test
    void delete() {
        User user = new User();
        user.setUsername("deleteuser");
        user.setEmail("delete@example.com");
        user.setPassword(get100CharsString( 'a' ));

        userRepository.create(user);
        Long userId = user.getId();

        User beforeDelete = userRepository.findById(userId);
        assertThat(beforeDelete).isNotNull();

        userRepository.delete(userId);

        User afterDelete = userRepository.findById(userId);
        assertThat(afterDelete).isNull();
    }

    @Test
    void findAll() {
        List<User> initialUsers = userRepository.findAll();
        int initialCount = initialUsers.size();

        User user1 = new User();
        user1.setUsername("findalluser1");
        user1.setEmail("findall1@example.com");
        user1.setPassword(get100CharsString( 'a' ));
        userRepository.create(user1);

        User user2 = new User();
        user2.setUsername("findalluser2");
        user2.setEmail("findall2@example.com");
        user2.setPassword(get100CharsString( 'b' ));
        userRepository.create(user2);

        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers).hasSize(initialCount + 2);
        assertThat(allUsers).extracting(User::getUsername)
                .contains("findalluser1", "findalluser2");
    }

    @Test
    void findById() {
        List<User> users = userRepository.findAll();
        assertThat(users).isNotEmpty();

        User existingUser = users.getFirst();
        Long existingId = existingUser.getId();
        User foundUser = userRepository.findById(existingId);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(existingId);
        assertThat(foundUser.getUsername()).isEqualTo(existingUser.getUsername());

        User nonExistent = userRepository.findById(999999L);
        assertThat(nonExistent).isNull();
    }

}