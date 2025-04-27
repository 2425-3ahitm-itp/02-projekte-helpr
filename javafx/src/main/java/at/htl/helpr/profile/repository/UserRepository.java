package at.htl.helpr.profile.repository;

import at.htl.helpr.profile.model.User;
import java.util.List;

public interface UserRepository {

    void create(User entity);

    void update(User entity);

    void delete(long id);

    List<User> findAll();

    User findById(long id);
}
