package at.htl.helpr.taskform.repository;

import at.htl.helpr.taskform.model.Task;
import at.htl.helpr.taskform.repository.filter.TaskQueryBuilder;
import java.util.List;

public interface TaskRepository {
    public void create(Task entity);
    public void update(Task entity);
    public void delete(long id);
    public List<Task> findAll();
    public Task findById(long id);

    public List<String> getTaskImages(long taskId);

    public List<Task> findAllTasksByUser(long userId);
    public List<Task> findAllTasksAppliedByUser(long userId);

    public List<Task> getTaskBySearchQueryAndLimit(String search);

    public List<Task> getTasksWithFilter(TaskQueryBuilder queryBuilder);
}