package at.htl.helpr.taskform.repository;

import at.htl.helpr.sql.SqlRunner;
import at.htl.helpr.taskform.model.Task;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@QuarkusTest
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
class TaskRepositoryTest {

    private final TaskRepository repository = new TaskRepositoryImpl();

    @BeforeEach
    void setUp() {
        SqlRunner.runSchema();
        SqlRunner.runInserts();
    }

    @Test
    @Order( 1000 )
    void create() {
        Task task = new Task();
        task.setAuthorId( 1L );
        task.setTitle( "Test Task" );
        task.setDescription( "This is a test task" );
        task.setReward( 30 );
        task.setEffort( 5 );
        task.setLocation( "Test Location" );

        repository.create( task );

        System.out.println( task.getId() );

        Task retrievedTask = repository.findById( task.getId() );
        assertThat( retrievedTask ).isNotNull();
        assertThat( retrievedTask.getTitle() ).isEqualTo( task.getTitle() );
        assertThat( retrievedTask.getDescription() ).isEqualTo( task.getDescription() );
        assertThat( retrievedTask.getReward() ).isEqualTo( task.getReward() );
        assertThat( retrievedTask.getEffort() ).isEqualTo( task.getEffort() );
        assertThat( retrievedTask.getLocation() ).isEqualTo( task.getLocation() );
        assertThat( retrievedTask.getAuthorId() ).isEqualTo( task.getAuthorId() );
    }

    @Test
    @Order( 1010 )
    void update() {
        Task task = new Task();
        task.setAuthorId( 2L );
        task.setTitle( "Initial Task" );
        task.setDescription( "This is the initial task" );
        task.setReward( 20 );
        task.setEffort( 5 );
        task.setLocation( "Initial Location" );

        repository.create( task );

        task.setTitle( "Updated Task" );
        task.setDescription( "This is the updated task" );
        task.setReward( 25 );
        task.setEffort( 10 );
        task.setLocation( "Updated Location" );

        repository.update( task );

        Task updatedTask = repository.findById( task.getId() );
        assertThat( updatedTask ).isNotNull();
        assertThat( updatedTask.getTitle() ).isEqualTo( task.getTitle() );
        assertThat( updatedTask.getDescription() ).isEqualTo( task.getDescription() );
        assertThat( updatedTask.getReward() ).isEqualTo( task.getReward() );
        assertThat( updatedTask.getEffort() ).isEqualTo( task.getEffort() );
        assertThat( updatedTask.getLocation() ).isEqualTo( task.getLocation() );
    }

    @Test
    @Order( 1020 )
    void delete() {
        Task task = new Task();
        task.setAuthorId( 3L );
        task.setTitle( "Task to be deleted" );
        task.setDescription( "This task will be deleted" );
        task.setReward( 15 );
        task.setEffort( 3 );
        task.setLocation( "Delete Location" );

        repository.create( task );
        long taskId = task.getId();

        repository.delete( taskId );

        Task deletedTask = repository.findById( taskId );
        assertThat( deletedTask ).isNull();
    }

    @Test
    @Order( 1030 )
    void findAll() {

        // Clear existing tasks first
        List<Task> existingTasks = repository.findAll();
        for ( Task t : existingTasks ) {
            repository.delete( t.getId() );
        }

        Task task1 = new Task();
        task1.setAuthorId( 1L );
        task1.setTitle( "Task 1" );
        task1.setDescription( "Description 1" );
        task1.setReward( 10 );
        task1.setEffort( 3 );
        task1.setLocation( "Location 1" );
        repository.create( task1 );

        Task task2 = new Task();
        task2.setAuthorId( 2L );
        task2.setTitle( "Task 2" );
        task2.setDescription( "Description 2" );
        task2.setReward( 15 );
        task2.setEffort( 5 );
        task2.setLocation( "Location 2" );
        repository.create( task2 );

        List<Task> tasks = repository.findAll();
        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 2 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactlyInAnyOrder( "Task 1", "Task 2" );
    }

    @Test
    @Order( 1040 )
    void findAllAndDelete() {

        // Clear existing tasks first
        List<Task> existingTasks = repository.findAll();
        for ( Task t : existingTasks ) {
            repository.delete( t.getId() );
        }

        Task task1 = new Task();
        task1.setAuthorId( 1L );
        task1.setTitle( "Task 1" );
        task1.setDescription( "Description 1" );
        task1.setReward( 10 );
        task1.setEffort( 3 );
        task1.setLocation( "Location 1" );
        repository.create( task1 );

        Task task2 = new Task();
        task2.setAuthorId( 2L );
        task2.setTitle( "Task 2" );
        task2.setDescription( "Description 2" );
        task2.setReward( 15 );
        task2.setEffort( 5 );
        task2.setLocation( "Location 2" );
        repository.create( task2 );

        List<Task> tasks = repository.findAll();
        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 2 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactlyInAnyOrder( "Task 1", "Task 2" );

        // Delete task1
        repository.delete( task1.getId() );

        tasks = repository.findAll();
        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 1 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactly( "Task 2" );
    }

    @Test
    @Order( 1050 )
    void findById() {
        Task task = new Task();
        task.setAuthorId( 4L );
        task.setTitle( "Find Task" );
        task.setDescription( "This task will be found by ID" );
        task.setReward( 20 );
        task.setEffort( 4 );
        task.setLocation( "Find Location" );

        repository.create( task );
        long taskId = task.getId();

        Task foundTask = repository.findById( taskId );
        assertThat( foundTask ).isNotNull();
        assertThat( foundTask.getId() ).isEqualTo( taskId );
        assertThat( foundTask.getTitle() ).isEqualTo( task.getTitle() );
        assertThat( foundTask.getDescription() ).isEqualTo( task.getDescription() );
        assertThat( foundTask.getReward() ).isEqualTo( task.getReward() );
        assertThat( foundTask.getEffort() ).isEqualTo( task.getEffort() );
        assertThat( foundTask.getLocation() ).isEqualTo( task.getLocation() );
        assertThat( foundTask.getAuthorId() ).isEqualTo( task.getAuthorId() );
    }

    @Test
    @Order( 1080 )
    void getTaskBySearchQuery() {
        // Clear existing tasks first
        List<Task> existingTasks = repository.findAll();
        for ( Task t : existingTasks ) {
            repository.delete( t.getId() );
        }

        Task task1 = new Task();
        task1.setAuthorId( 1L );
        task1.setTitle( "Shopping Task" );
        task1.setDescription( "Buy groceries" );
        task1.setReward( 10 );
        task1.setEffort( 2 );
        task1.setLocation( "Supermarket" );
        repository.create( task1 );

        Task task2 = new Task();
        task2.setAuthorId( 2L );
        task2.setTitle( "Cleaning Task" );
        task2.setDescription( "Clean the house" );
        task2.setReward( 20 );
        task2.setEffort( 3 );
        task2.setLocation( "Home" );
        repository.create( task2 );

        List<Task> tasks = repository.getTaskBySearchQueryAndLimit( "shopping", 1 );

        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 1 );
        assertThat( tasks.getFirst().getTitle() ).isEqualTo( "Shopping Task" );
    }

}