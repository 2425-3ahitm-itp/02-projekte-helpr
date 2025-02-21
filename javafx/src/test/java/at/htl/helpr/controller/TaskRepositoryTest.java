package at.htl.helpr.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import at.htl.helpr.model.Task;
import org.postgresql.geometric.PGpoint;

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
              PreparedStatement statement = conn.prepareStatement( sql )
        ) {

            statement.execute();

        } catch ( SQLException e ) {
            throw new RuntimeException( "Error while updating task: " + e.getMessage(), e );
        }
    }

    @Test
    void create() {
        TaskRepository repository = new TaskRepository();
        Task task = new Task();
        task.setTitle( "Test Task" );
        task.setDescription( "This is a test task" );
        task.setStatus( 1 );
        task.setLocation( new PGpoint( 0.343, 4.234 ) );
        task.setEstimatedEffort( 5 );

        repository.create( task );

        System.out.println( task.getId() );

        Task retrievedTask = repository.findById( task.getId() );
        assertThat( retrievedTask ).isNotNull();
        assertThat( retrievedTask.getTitle() ).isEqualTo( task.getTitle() );
        assertThat( retrievedTask.getDescription() ).isEqualTo( task.getDescription() );
        assertThat( retrievedTask.getStatus() ).isEqualTo( task.getStatus() );
        assertThat( retrievedTask.getLocation() ).isEqualTo( task.getLocation() );
        assertThat( retrievedTask.getEstimatedEffort() ).isEqualTo( task.getEstimatedEffort() );
    }

    @Test
    void update() {
        TaskRepository repository = new TaskRepository();
        Task task = new Task();
        task.setTitle( "Initial Task" );
        task.setDescription( "This is the initial task" );
        task.setStatus( 1 );
        task.setLocation( new PGpoint( 43.76, 4.543 ) );
        task.setEstimatedEffort( 5 );

        repository.create( task );

        task.setTitle( "Updated Task" );
        task.setDescription( "This is the updated task" );
        task.setStatus( 2 );
        task.setEstimatedEffort( 10 );

        repository.update( task );

        Task updatedTask = repository.findById( task.getId() );
        assertThat( updatedTask ).isNotNull();
        assertThat( updatedTask.getTitle() ).isEqualTo( task.getTitle() );
        assertThat( updatedTask.getDescription() ).isEqualTo( task.getDescription() );
        assertThat( updatedTask.getStatus() ).isEqualTo( task.getStatus() );
        assertThat( updatedTask.getEstimatedEffort() ).isEqualTo( task.getEstimatedEffort() );
    }
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