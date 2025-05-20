package at.htl.helpr.profile.repository;

import at.htl.helpr.profile.model.User;
import at.htl.helpr.profile.repository.exceptions.UserAlreadyExistsException;
import java.util.List;

public interface UserRepository {

    User findByUsernameAndPassword(String username, String password);

    User registerWithUsernameAndPassword(String username, String password) throws UserAlreadyExistsException;

    void update(User entity);

    void delete(long id);

    List<User> findAll();

    User findById(long id);
}
