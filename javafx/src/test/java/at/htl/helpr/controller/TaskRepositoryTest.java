package at.htl.helpr.controller;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import at.htl.helpr.model.Task;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;

@QuarkusTest
class TaskRepositoryTest {


    @BeforeEach
    void setUp() {
        String sql = """
                drop table if exists "task";
                create table "task" (
                    "id" BIGSERIAL not null primary key,
                    "title" VARCHAR(100) not null,
                    "description" VARCHAR(3000) not null,
                    "status" INTEGER not null,
                    "location" POINT not null,
                    "estimated_effort" INTEGER not null,
                    "created_at" TIMESTAMP default now() not null
                );
                comment on table "task" is 'null';
                """;

        try ( Connection conn = Database.getConnection();
              PreparedStatement statement = conn.prepareStatement(sql)
        ) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating task: " + e.getMessage(), e);
        }
    }

    @Test
    void create() {
        TaskRepository repository = new TaskRepository();
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("This is a test task");
        task.setStatus(1);
        task.setLocation("POINT(1 1)");
        task.setEstimated_effort(5);

        repository.create(task);

//        Task retrievedTask = repository.findById(task.getId());
//        assertThat(retrievedTask).isNotNull();
//        assertThat(retrievedTask.getTitle()).isEqualTo(task.getTitle());
//        assertThat(retrievedTask.getDescription()).isEqualTo(task.getDescription());
//        assertThat(retrievedTask.getStatus()).isEqualTo(task.getStatus());
//        assertThat(retrievedTask.getLocation()).isEqualTo(task.getLocation());
//        assertThat(retrievedTask.getEstimated_effort()).isEqualTo(task.getEstimated_effort());
    }

//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void findAll() {
//    }
//
//    @Test
//    void findById() {
//    }
}