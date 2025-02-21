package at.htl.helpr.controller;

import at.htl.helpr.model.Task;
import org.postgresql.geometric.PGpoint;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TaskRepository implements Repository<Task> {


    @Override
    public void create(Task task) {
        String sql = """
                INSERT INTO task (
                    title,
                    description,
                    status,
                    location,
                    estimated_effort
                ) VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = Database.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);) {

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getStatus());
            statement.setObject(4, task.getLocation());
            statement.setInt(5, task.getEstimatedEffort());

            // log statement
            System.out.println(statement.getMetaData());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("No task created");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    task.idProperty().setValue(keys.getLong(1));
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
                UPDATE task SET title=?,description=?,status=?,estimated_effort=?
                    WHERE id=?
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getStatus());
            statement.setInt(4, task.getEstimatedEffort());
            statement.setLong(5, task.getId());

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
                    WHERE id=?
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

            while (rs.next()) {
                var point = rs.getObject("location", PGpoint.class);
                Task contact = new Task(
                        rs.getLong("id"),
                        rs.getInt("status"),
                        rs.getObject("location", PGpoint.class),
                        rs.getInt("estimated_effort"),
                        rs.getString("title"),
                        rs.getString("description")
                );
                taskList.add(contact);
            }
            return taskList;

        } catch (SQLException e) {
            throw new RuntimeException("Error while invoking findall() of tasks: " + e.getMessage(),
                    e);
        }
    }


    @Override
    public Task findById(long id) {
        String sql = "SELECT * FROM task WHERE id=?";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    var point = result.getObject("location", PGpoint.class);
                    return new Task(
                            result.getLong("id"),
                            result.getInt("status"),
                            result.getObject("location", PGpoint.class),
                            result.getInt("estimated_effort"),
                            result.getString("title"),
                            result.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while findById a task: " + e.getMessage());
        }
        return null;
    }
}
