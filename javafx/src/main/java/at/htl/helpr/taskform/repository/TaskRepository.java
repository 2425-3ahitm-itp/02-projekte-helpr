package at.htl.helpr.taskform.repository;

import at.htl.helpr.taskform.model.Task;
import java.util.List;

public interface TaskRepository {
    public void create(Task entity, long authorId);
    public void update(Task entity);
    public void delete(long id);
    public List<Task> findAll();
    public Task findById(long id);
}