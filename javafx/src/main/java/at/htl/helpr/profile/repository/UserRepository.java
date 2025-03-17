package at.htl.helpr.profile.repository;

import at.htl.helpr.profile.model.User;
import java.util.List;

public interface UserRepository {
    public void create(User entity);
    public void update(User entity);
    public void delete(long id);
    public List<User> findAll();
    public User findById(long id);
}
