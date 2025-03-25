package at.htl.helpr.profile.repository;

import at.htl.helpr.controller.Database;
import at.htl.helpr.profile.model.User;
import java.nio.CharBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void create(User entity) {
        String sql = """
                    INSERT INTO u_user (username, email, password, profile_picture)
                    VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getPassword());
            stmt.setBytes(4, entity.getProfilePicture());

            if (entity.getProfilePicture() != null) {
                stmt.setBytes(4, entity.getProfilePicture());
            } else {
                stmt.setNull(4, Types.BINARY);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @Override
    public void update(User entity) {
        String sql = """
                    UPDATE u_user SET username = ?, email = ?, password = ?, profile_picture = ?
                    WHERE user_id = ?
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getPassword());
            stmt.setBytes(4, entity.getProfilePicture());
            stmt.setLong(5, entity.getId());

            if (entity.getProfilePicture() != null) {
                stmt.setBytes(4, entity.getProfilePicture());
            } else {
                stmt.setNull(4, Types.BINARY);
            }

            stmt.setLong(5, entity.getId());

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
                    SELECT user_id, username, email, password, profile_picture
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
                    SELECT user_id, username, email, password, profile_picture
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

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        CharBuffer passwordBuffer = CharBuffer.allocate(100);
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setProfilePicture(rs.getBytes("profile_picture"));
        return user;
    }
}