package at.htl.helpr.profile.repository;

import at.htl.helpr.profile.model.User;
import at.htl.helpr.sql.SqlRunner;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class UserRepositoryImplTest {

    UserRepository repository = new UserRepositoryImpl();

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

        repository.create(user);

        User retrievedUser = repository.findById(user.getId());
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

        repository.create(user);

        User retrievedUser = repository.findById(user.getId());
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getProfilePicture()).isEqualTo(testImage);
    }

    @Test
    void update() {
        User user = new User();
        user.setUsername("updateuser");
        user.setEmail("update@example.com");
        user.setPassword(get100CharsString( 'h' ));

        repository.create(user);
        long userId = user.getId();

        user.setUsername("updatedname");
        user.setEmail("newemail@example.com");
        final var newPassword = get100CharsString( 'g' );
        user.setPassword(newPassword);
        byte[] profilePic = {10, 20, 30};
        user.setProfilePicture(profilePic);

        repository.update(user);

        User updatedUser = repository.findById(userId);
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

        repository.create(user);
        Long userId = user.getId();

        User beforeDelete = repository.findById(userId);
        assertThat(beforeDelete).isNotNull();

        repository.delete(userId);

        User afterDelete = repository.findById(userId);
        assertThat(afterDelete).isNull();
    }

    @Test
    void findAll() {
        List<User> initialUsers = repository.findAll();
        int initialCount = initialUsers.size();

        User user1 = new User();
        user1.setUsername("findalluser1");
        user1.setEmail("findall1@example.com");
        user1.setPassword(get100CharsString( 'a' ));
        repository.create(user1);

        User user2 = new User();
        user2.setUsername("findalluser2");
        user2.setEmail("findall2@example.com");
        user2.setPassword(get100CharsString( 'b' ));
        repository.create(user2);

        List<User> allUsers = repository.findAll();
        assertThat(allUsers).hasSize(initialCount + 2);
        assertThat(allUsers).extracting(User::getUsername)
                .contains("findalluser1", "findalluser2");
    }

    @Test
    void findById() {
        List<User> users = repository.findAll();
        assertThat(users).isNotEmpty();

        User existingUser = users.getFirst();
        Long existingId = existingUser.getId();
        User foundUser = repository.findById(existingId);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(existingId);
        assertThat(foundUser.getUsername()).isEqualTo(existingUser.getUsername());

        User nonExistent = repository.findById(999999L);
        assertThat(nonExistent).isNull();
    }

    public static String get100CharsString(char base) {

        return String.valueOf( base ).repeat( 100 );

    }
}