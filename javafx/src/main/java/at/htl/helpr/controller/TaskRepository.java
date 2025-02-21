package at.htl.helpr.controller;

import at.htl.helpr.model.Task;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class TaskRepository implements Repository<Task> {

    private static final DatasourceProvider dataSourceProvider = new DatasourceProvider();
    private static final DataSource dataSource = dataSourceProvider.getDataSource();


    @Override
    public void create(Task task) {
        String sql = """
                INSERT INTO task (
                    title, 
                    description, 
                    status, 
                    location, 
                    estimated_effort, 
                    created_at
                ) VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getStatus());
            statement.setString(4, task.getLocation());
            statement.setInt(5, task.getEstimated_effort());
            LocalDateTime createdAt =
                    (task.getCreated_at() != null) ? task.getCreated_at().toLocalDateTime()
                            : LocalDateTime.now();
            statement.setTimestamp(6, Timestamp.valueOf(createdAt));

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
            throw new RuntimeException("Error while inserting task" + e.getMessage(), e);
        }
    }


    @Override
    public void update(Task task) {

        String sql = """
                UPDATE task  SET title=?,description=?,status=?  
                             WHERE co_id=?  
                """ + task.getId();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getStatus());

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

        try (Connection connection = dataSource.getConnection();
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

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                Task contact = new Task(
                        rs.getLong("id"),
                        rs.getInt("statzs"),
                        rs.getString("location"),
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

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return new Task(
                            result.getLong("id"),
                            result.getInt("statzs"),
                            result.getString("location"),
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
