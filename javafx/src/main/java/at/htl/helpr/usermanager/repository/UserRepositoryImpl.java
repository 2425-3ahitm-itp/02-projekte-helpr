package at.htl.helpr.usermanager.repository;

import at.htl.helpr.controller.Database;
import at.htl.helpr.usermanager.model.User;
import at.htl.helpr.usermanager.repository.exceptions.UserAlreadyExistsException;
import io.quarkus.elytron.security.common.BcryptUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    /**
     * Finds a user by username and password.
     *
     * @param username
     *            the username of the user
     * @param password
     *            the password of the user
     * @return the user if found, null otherwise
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {

        String sql = """
                select user_id, username, password, profile_picture
                from u_user
                where username = ?
                """;

        try (var conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            // check with bcrypt
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setProfilePicture(rs.getBytes("profile_picture"));

                    // check with bcrypt util from quarkus security
                    if (BcryptUtil.matches(password, user.getPassword())) {
                        return user;
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public User registerWithUsernameAndPassword(String username, String password)
            throws UserAlreadyExistsException {

        // check if username exists
        if (findByUsername(username) != null) {
            throw new UserAlreadyExistsException(username);
        }

        String sql = """
                INSERT INTO u_user (username, password)
                VALUES (?, ?)
                """;

        try (var conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.setString(2, BcryptUtil.bcryptHash(password));

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    User user = new User();
                    user.setId(generatedKeys.getLong(1));
                    user.setUsername(username);
                    user.setPassword(BcryptUtil.bcryptHash(password));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void update(User entity) {
        String sql = """
                    UPDATE u_user SET username = ?, password = ?, profile_picture = ?
                    WHERE user_id = ?
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getUsername());
            stmt.setString(2, BcryptUtil.bcryptHash(entity.getPassword()));
            stmt.setBytes(3, entity.getProfilePicture());
            stmt.setLong(4, entity.getId());

            if (entity.getProfilePicture() != null) {
                stmt.setBytes(3, entity.getProfilePicture());
            } else {
                stmt.setNull(3, Types.BINARY);
            }

            stmt.setLong(4, entity.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Override
    public void delete(long id) {
        String sql = """
                    DELETE FROM u_user WHERE user_id = ?
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = """
                    SELECT user_id, username, password, profile_picture
                    FROM u_user
                """;
        List<User> users = new ArrayList<>();

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(getUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all users", e);
        }

        return users;
    }

    @Override
    public User findById(long id) {
        String sql = """
                    SELECT user_id, username, password, profile_picture
                    FROM u_user
                    WHERE user_id = ?
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by id", e);
        }

        return null;
    }

    public User findByUsername(String username) {
        String sql = """
                SELECT user_id, username, password, profile_picture
                FROM u_user
                WHERE username = ?
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by username", e);
        }

        return null;
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setProfilePicture(rs.getBytes("profile_picture"));
        return user;
    }
}