package at.htl.helpr.controller;

import at.htl.helpr.model.TaskStatus;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
        task.setStatus( TaskStatus.ACTIVE );
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
        task.setStatus( TaskStatus.ACTIVE );
        task.setLocation( new PGpoint( 43.76, 4.543 ) );
        task.setEstimatedEffort( 5 );

        repository.create( task );

        task.setTitle( "Updated Task" );
        task.setDescription( "This is the updated task" );
        task.setStatus( TaskStatus.COMPLETED);
        task.setEstimatedEffort( 10 );

        repository.update( task );

        Task updatedTask = repository.findById( task.getId() );
        assertThat( updatedTask ).isNotNull();
        assertThat( updatedTask.getTitle() ).isEqualTo( task.getTitle() );
        assertThat( updatedTask.getDescription() ).isEqualTo( task.getDescription() );
        assertThat( updatedTask.getStatus() ).isEqualTo( task.getStatus() );
        assertThat( updatedTask.getEstimatedEffort() ).isEqualTo( task.getEstimatedEffort() );
    }

    @Test
    void delete() {
        TaskRepository repository = new TaskRepository();
        Task task = new Task();
        task.setTitle( "Task to be deleted" );
        task.setDescription( "This task will be deleted" );
        task.setStatus( TaskStatus.CANCELLED );
        task.setLocation( new PGpoint( 1.234, 5.678 ) );
        task.setEstimatedEffort( 3 );

        repository.create( task );
        long taskId = task.getId();

        repository.delete( taskId );

        Task deletedTask = repository.findById( taskId );
        assertThat( deletedTask ).isNull();
    }


    @Test
    void findAll() {
        TaskRepository repository = new TaskRepository();

        Task task1 = new Task();
        task1.setTitle( "Task 1" );
        task1.setDescription( "Description 1" );
        task1.setStatus( TaskStatus.OPEN );
        task1.setLocation( new PGpoint( 1.0, 1.0 ) );
        task1.setEstimatedEffort( 3 );
        repository.create( task1 );

        Task task2 = new Task();
        task2.setTitle( "Task 2" );
        task2.setDescription( "Description 2" );
        task2.setStatus( TaskStatus.OPEN );
        task2.setLocation( new PGpoint( 2.0, 2.0 ) );
        task2.setEstimatedEffort( 5 );
        repository.create( task2 );

        List<Task> tasks = repository.findAll();
        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 2 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactlyInAnyOrder( "Task 1", "Task 2" );
    }


    @Test
    void findAllAndDelete() {
        TaskRepository repository = new TaskRepository();

        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setStatus(TaskStatus.OPEN);
        task1.setLocation(new PGpoint(1.0, 1.0));
        task1.setEstimatedEffort(3);
        repository.create(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setStatus(TaskStatus.ACTIVE);
        task2.setLocation(new PGpoint(2.0, 2.0));
        task2.setEstimatedEffort(5);
        repository.create(task2);

        List<Task> tasks = repository.findAll();
        assertThat(tasks).isNotNull();
        assertThat(tasks).hasSize(2);
        assertThat(tasks).extracting(Task::getTitle).containsExactlyInAnyOrder("Task 1", "Task 2");

        // Delete task1
        repository.delete(task1.getId());

        tasks = repository.findAll();
        assertThat(tasks).isNotNull();
        assertThat(tasks).hasSize(1);
        assertThat(tasks).extracting(Task::getTitle).containsExactly("Task 2");
    }

    @Test
    void findById() {
        TaskRepository repository = new TaskRepository();
        Task task = new Task();
        task.setTitle( "Find Task" );
        task.setDescription( "This task will be found by ID" );
        task.setStatus( TaskStatus.OPEN );
        task.setLocation( new PGpoint( 3.456, 7.890 ) );
        task.setEstimatedEffort( 4 );

        repository.create( task );
        long taskId = task.getId();

        Task foundTask = repository.findById( taskId );
        assertThat( foundTask ).isNotNull();
        assertThat( foundTask.getId() ).isEqualTo( taskId );
        assertThat( foundTask.getTitle() ).isEqualTo( task.getTitle() );
        assertThat( foundTask.getDescription() ).isEqualTo( task.getDescription() );
        assertThat( foundTask.getStatus() ).isEqualTo( task.getStatus() );
        assertThat( foundTask.getLocation() ).isEqualTo( task.getLocation() );
        assertThat( foundTask.getEstimatedEffort() ).isEqualTo( task.getEstimatedEffort() );
    }

}