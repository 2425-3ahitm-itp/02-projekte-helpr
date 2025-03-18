package at.htl.helpr.taskform.repository;

import at.htl.helpr.controller.Database;
import at.htl.helpr.taskform.model.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {

    @Override
    public void create(Task task) {
        String sql = """
                INSERT INTO task (author_id, title, description, reward, effort, location)
                VALUES (?,?,?,?,?,?)
                """;

        try (
                Connection conn = Database.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, task.getAuthorId());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getDescription());
            statement.setInt(4, task.getReward());
            statement.setInt(5, task.getEffort());
            statement.setString(6, task.getLocation());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("No task created");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    task.setId(keys.getLong(1));
                } else {
                    throw new SQLException(
                            String.format("Insert into TASK failed, no ID obtained for %s",
                                    task.getTitle()));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while creating task: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Task task) {
        String sql = """
                UPDATE task SET title=?, description=?, reward=?, effort=?, location=?
                WHERE task_id=?
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getReward());
            statement.setInt(4, task.getEffort());
            statement.setString(5, task.getLocation());
            statement.setLong(6, task.getId());

            if (statement.executeUpdate() == 0) {
                throw new SQLException(String.format("Update of TASK %s failed, no rows affected",
                        task.getTitle()));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating task: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) {
        String sql = """
                DELETE FROM task
                WHERE task_id=?
                """;

        try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setLong(1, id);

            if (statement.executeUpdate() == 0) {
                throw new SQLException(
                        String.format("Delete TASK with id=%d failed, no rows affected", id));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting task: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> taskList = new LinkedList<>();

        String sql = """
                SELECT * FROM task
                """;

        try (Connection connection = Database.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            return getTasksFromResultSet(stmt.executeQuery());

        } catch (SQLException e) {
            throw new RuntimeException("Error while invoking findall() of tasks: " + e.getMessage(),
                    e);
        }
    }

    @Override
    public Task findById(long id) {
        String sql = "SELECT * FROM task WHERE task_id=?";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return new Task(
                            result.getLong("task_id"),
                            result.getLong("author_id"),
                            result.getString("title"),
                            result.getString("description"),
                            result.getInt("reward"),
                            result.getInt("effort"),
                            result.getString("location"),
                            result.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while findById a task: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Task> findAllTasksByUser(long userId) {
        List<Task> taskList = new LinkedList<>();

        String sql = """
                SELECT * FROM task
                WHERE author_id = ?
                """;

        return getTasksWithSqlAndUserID(userId, sql);
    }


    @Override
    public List<Task> findAllTasksAppliedByUser(long userId) {

        String sql = """
                SELECT *
                FROM application
                LEFT JOIN task USING (task_id)
                WHERE application.user_id = ?;
                """;

        return getTasksWithSqlAndUserID(userId, sql);
    }

    public List<Task> getTaskBySearchQueryAndLimit(String search) {
        String sql = """
                SELECT
                    *
                FROM task
                WHERE ? % ANY(STRING_TO_ARRAY(title,' '));
                """;

        try (Connection connection = Database.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {

            stmt.setString(1, search);

            return getTasksFromResultSet(stmt.executeQuery());

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error while invoking findAllTasksByUser() of tasks: " + e.getMessage(), e);
        }
    }

    private List<Task> getTasksFromResultSet(ResultSet rs) throws SQLException {
        List<Task> taskList = new ArrayList<>();

        while (rs.next()) {
            Task task = new Task(
                    rs.getLong("task_id"),
                    rs.getLong("author_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("reward"),
                    rs.getInt("effort"),
                    rs.getString("location"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
            taskList.add(task);
        }

        return taskList;
    }


    private List<Task> getTasksWithSqlAndUserID(long userId, String sql) {
        List<Task> taskList = new ArrayList<>();
        try (Connection connection = Database.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {

            stmt.setLong(1, userId);

            return getTasksFromResultSet(stmt.executeQuery());

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error while invoking findAllTasksByUser() of tasks: " + e.getMessage(),
                    e);
        }
    }

}