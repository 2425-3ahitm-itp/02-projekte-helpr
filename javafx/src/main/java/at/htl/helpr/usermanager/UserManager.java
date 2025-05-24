package at.htl.helpr.usermanager;

import at.htl.helpr.usermanager.model.User;
import at.htl.helpr.usermanager.repository.UserRepository;
import at.htl.helpr.usermanager.repository.exceptions.UserAlreadyExistsException;

public class UserManager {

    private static UserManager instance;
    private User currentUser = null;
    private UserRepository userRepo;

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getUser() {
        if (isLoggedIn()) {
            return currentUser;
        }
        return null;
    }

    public User login(String username, String password) throws Exception {

        if (!username.matches(currentUser.getUsername())) {
            throw new Exception("Username is incorrect");
        }

        if (!password.matches(currentUser.getPassword())) {
            throw new Exception("Password is incorrect");
        }

        userRepo.findByUsernameAndPassword(username, password);

        return currentUser;
    }

    public User register(String username, String password) throws UserAlreadyExistsException {
        
        if (!username.matches(currentUser.getUsername())) {
            throw new UserAlreadyExistsException("User does already exist");
        }

        userRepo.registerWithUsernameAndPassword(username, password);

        return currentUser;
    }

}