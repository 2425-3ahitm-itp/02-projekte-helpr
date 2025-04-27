package at.htl.helpr.taskform.repository;

import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.filter.TaskQueryBuilder;
import java.util.List;

public interface TaskRepository {

    void create(Task entity);

    void update(Task entity);

    void delete(long id);

    List<Task> findAll();

    Task findById(long id);

    List<String> getTaskImages(long taskId);

    List<Task> findAllTasksByUser(long userId);

    List<Task> findAllTasksAppliedByUser(long userId);

    List<Task> getTaskBySearchQueryAndLimit(String search);

    List<Task> getTasksWithFilter(TaskQueryBuilder queryBuilder);
}