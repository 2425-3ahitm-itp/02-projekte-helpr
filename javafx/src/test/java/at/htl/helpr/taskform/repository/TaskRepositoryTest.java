package at.htl.helpr.taskform.repository;

import at.htl.helpr.sql.SqlRunner;
import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.filter.PaymentMinMaxFilter;
import at.htl.helpr.taskform.repository.filter.TaskQueryBuilder;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import java.util.List;

import static at.htl.helpr.Utils.get100CharsString;
import static org.assertj.core.api.Assertions.*;

@QuarkusTest
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
class TaskRepositoryTest {

    TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();

    @BeforeEach
    void setUp() {
        SqlRunner.runSchema();
        SqlRunner.runString( String.format("""
                INSERT INTO u_user (username, email, password)
                VALUES
                ('john_doe', 'john@example.com', '%s'),
                ('jane_smith', 'jane@example.com', 'helloworld2');
                """, get100CharsString( 'u' ) ) );
    }

    @AfterAll
    static void cleanup() {
        SqlRunner.runSchema();
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

        taskRepository.create( task );

        System.out.println( task.getId() );

        Task retrievedTask = taskRepository.findById( task.getId() );
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
        task.setAuthorId( 1L );
        task.setTitle( "Initial Task" );
        task.setDescription( "This is the initial task" );
        task.setReward( 20 );
        task.setEffort( 5 );
        task.setLocation( "Initial Location" );

        taskRepository.create( task );

        task.setTitle( "Updated Task" );
        task.setDescription( "This is the updated task" );
        task.setReward( 25 );
        task.setEffort( 10 );
        task.setLocation( "Updated Location" );

        taskRepository.update( task );

        Task updatedTask = taskRepository.findById( task.getId() );
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
        task.setAuthorId( 1L );
        task.setTitle( "Task to be deleted" );
        task.setDescription( "This task will be deleted" );
        task.setReward( 15 );
        task.setEffort( 3 );
        task.setLocation( "Delete Location" );

        taskRepository.create( task );
        long taskId = task.getId();

        taskRepository.delete( taskId );

        Task deletedTask = taskRepository.findById( taskId );
        assertThat( deletedTask ).isNull();
    }

    @Test
    @Order( 1030 )
    void findAll() {

        // Clear existing tasks first
        List<Task> existingTasks = taskRepository.findAll();
        for ( Task t : existingTasks ) {
            taskRepository.delete( t.getId() );
        }

        Task task1 = new Task();
        task1.setAuthorId( 1L );
        task1.setTitle( "Task 1" );
        task1.setDescription( "Description 1" );
        task1.setReward( 10 );
        task1.setEffort( 3 );
        task1.setLocation( "Location 1" );
        taskRepository.create( task1 );

        Task task2 = new Task();
        task2.setAuthorId( 2L );
        task2.setTitle( "Task 2" );
        task2.setDescription( "Description 2" );
        task2.setReward( 15 );
        task2.setEffort( 5 );
        task2.setLocation( "Location 2" );
        taskRepository.create( task2 );

        List<Task> tasks = taskRepository.findAll();
        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 2 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactlyInAnyOrder( "Task 1", "Task 2" );
    }

    @Test
    @Order( 1040 )
    void findAllAndDelete() {

        Task task1 = new Task();
        task1.setAuthorId( 1L );
        task1.setTitle( "Task 1" );
        task1.setDescription( "Description 1" );
        task1.setReward( 10 );
        task1.setEffort( 3 );
        task1.setLocation( "Location 1" );
        taskRepository.create( task1 );

        Task task2 = new Task();
        task2.setAuthorId( 2L );
        task2.setTitle( "Task 2" );
        task2.setDescription( "Description 2" );
        task2.setReward( 15 );
        task2.setEffort( 5 );
        task2.setLocation( "Location 2" );
        taskRepository.create( task2 );

        List<Task> tasks = taskRepository.findAll();
        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 2 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactlyInAnyOrder( "Task 1", "Task 2" );

        // Delete task1
        taskRepository.delete( task1.getId() );

        tasks = taskRepository.findAll();
        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 1 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactly( "Task 2" );
    }

    @Test
    @Order( 1050 )
    void findById() {
        Task task = new Task();
        task.setAuthorId( 1L );
        task.setTitle( "Find Task" );
        task.setDescription( "This task will be found by ID" );
        task.setReward( 20 );
        task.setEffort( 4 );
        task.setLocation( "Find Location" );

        taskRepository.create( task );
        long taskId = task.getId();

        Task foundTask = taskRepository
                .findById( taskId );
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
        SqlRunner.runString( """
                    INSERT INTO task (author_id, title, description, reward, effort, location)
                    VALUES
                        (1, 'Shopping Task', 'Buy groceries', 10, 2, 'Supermarket'),
                        (2, 'Cleaning Task', 'Clean the house', 20, 3, 'Home');
                """ );

        List<Task> tasks = taskRepository.getTaskBySearchQueryAndLimit( "shopping" );

        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 1 );
        assertThat( tasks.getFirst().getTitle() ).isEqualTo( "Shopping Task" );
    }

    @Test
    @Order( 1060 )
    void findAllTasksByUser() {

        Task task1 = new Task();
        task1.setAuthorId( 1L );
        task1.setTitle( "User 1 Task" );
        task1.setDescription( "Task for user 1" );
        task1.setReward( 10 );
        task1.setEffort( 2 );
        task1.setLocation( "Location 1" );
        taskRepository.create( task1 );

        Task task2 = new Task();
        task2.setAuthorId( 2L );
        task2.setTitle( "User 2 Task" );
        task2.setDescription( "Task for user 2" );
        task2.setReward( 20 );
        task2.setEffort( 3 );
        task2.setLocation( "Location 2" );
        taskRepository.create( task2 );

        List<Task> tasks = taskRepository.findAllTasksByUser( 1L );
        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 1 );
        assertThat( tasks.getFirst().getTitle() ).isEqualTo( "User 1 Task" );
    }

    @Test
    @Order( 1070 )
    void findAllTasksAppliedByUser() {

        SqlRunner.runString( """
                INSERT INTO task (author_id, title, description, reward, effort, location)
                VALUES
                    (1, 'Applied Task 1', 'Task applied by user 1', 10, 2, 'Location 1'),
                    (2, 'Applied Task 2', 'Task applied by user 2', 20, 3, 'Location 2');
                
                INSERT INTO application (user_id, task_id)
                VALUES
                    (1, 1);
                """ );

        List<Task> tasks = taskRepository.findAllTasksAppliedByUser( 1L );
        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 1 );
        assertThat( tasks.getFirst().getTitle() ).isEqualTo( "Applied Task 1" );
    }


    // getTasksWithFilter tests

    @Test
    @Order( 1090 )
    void getTasksWithEffortFilterBetween3And4() {
        runFilterSetup();

        TaskQueryBuilder queryBuilder = new TaskQueryBuilder();
        queryBuilder.addFilter( new PaymentMinMaxFilter( 3, 4 ) );

        List<Task> tasks = taskRepository.getTasksWithFilter( queryBuilder );

        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 3 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactlyInAnyOrder(
                "Help with moving furniture",
                "Computer setup help",
                "Lawn mowing service"
        );
    }

    @Test
    @Order( 1100 )
    void getTasksWithMultipleFilters() {
        runFilterSetup();

        TaskQueryBuilder queryBuilder = new TaskQueryBuilder();
        queryBuilder
                .addFilter( new PaymentMinMaxFilter( 2, 4 ) )
                .addFilter( ( query, params ) -> {
                    query.append( "reward > ?" );
                    params.add( 20 );
                } )
                .addFilter( ( query, params ) -> {
                    query.append( "location <> ?" );
                    params.add( "Westside Park" );
                } );

        List<Task> tasks = taskRepository.getTasksWithFilter( queryBuilder );

        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 3 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactlyInAnyOrder(
                "Help with moving furniture",
                "Computer setup help",
                "Lawn mowing service"
        );
    }

    @Test
    @Order( 1110 )
    void getTasksWithCustomFilter() {
        runFilterSetup();

        TaskQueryBuilder queryBuilder = new TaskQueryBuilder();
        queryBuilder.addFilter( ( query, params ) -> {
            query.append( "location = ?" );
            params.add( "Westside Park" );
        } );

        List<Task> tasks = taskRepository.getTasksWithFilter( queryBuilder );

        assertThat( tasks ).isNotNull();
        assertThat( tasks ).hasSize( 1 );
        assertThat( tasks ).extracting( Task::getTitle ).containsExactly( "Dog walking this weekend" );
    }

    private void runFilterSetup() {
        SqlRunner.runString( """
                    -- insert users
                    INSERT INTO u_user (username, email, password)
                    VALUES
                    ('john_doe', 'john@example.com', 'helloworld1'),
                    ('jane_smith', 'jane@example.com', 'helloworld2'),
                    ('mike_wilson', 'mike@example.com', 'hellorworld3'),
                    ('sarah_johnson', 'sarah@example.com', 'hellorowlrd4'),
                    ('alex_brown', 'alex@example.com', 'hellorowlrd5');
                
                    -- insert tasks
                    INSERT INTO task (author_id, title, description, reward, effort, location, created_at)
                    VALUES
                    (1, 'Help with moving furniture', 'Need help moving a couch and bookshelf from living room to bedroom', 25, 3, 'Downtown Area', '2025-03-15 10:30:00'),
                    (2, 'Dog walking this weekend', 'Looking for someone to walk my dog Saturday and Sunday mornings', 30, 2, 'Westside Park', '2025-03-16 08:45:00'),
                    (3, 'Grocery shopping assistance', 'Need help with grocery shopping as I recover from surgery', 20, 2, 'Northside Market', '2025-03-14 14:15:00'),
                    (2, 'Computer setup help', 'Need assistance setting up my new computer and transferring files', 40, 4, 'Southside Apartments', '2025-03-17 09:00:00'),
                    (4, 'Lawn mowing service', 'Looking for someone to mow my lawn this weekend', 35, 3, 'Eastside Neighborhood', '2025-03-16 16:20:00');
                
                    -- insert applications
                    INSERT INTO application (user_id, task_id, created_at)
                    VALUES
                    (3, 1, '2025-03-15 11:45:00'),
                    (4, 1, '2025-03-15 12:30:00'),
                    (5, 2, '2025-03-16 09:10:00'),
                    (1, 3, '2025-03-14 15:00:00'),
                    (3, 4, '2025-03-17 10:15:00'),
                    (2, 5, '2025-03-16 17:00:00'),
                    (5, 3, '2025-03-14 16:30:00');
                """ );
    }
}