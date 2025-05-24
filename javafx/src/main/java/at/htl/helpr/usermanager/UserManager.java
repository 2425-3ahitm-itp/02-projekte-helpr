package at.htl.helpr.usermanager;

import at.htl.helpr.usermanager.model.User;
import at.htl.helpr.usermanager.repository.UserRepository;
import at.htl.helpr.usermanager.repository.UserRepositoryImpl;
import at.htl.helpr.usermanager.repository.exceptions.LoginFailedException;
import at.htl.helpr.usermanager.repository.exceptions.UserAlreadyExistsException;

/**
 * The UserManager class is responsible for managing user authentication and session state within
 * the application. It follows the Singleton pattern to ensure a single instance is used throughout
 * the system.
 * <p>
 * This class provides functionality for: - registering new users - logging users in and out -
 * tracking the currently logged-in user - validating login credentials
 * <p>
 * The UserManager interacts with a UserRepository to persist and retrieve user data.
 *
 * @see UserRepository
 */
public class UserManager {

    private static UserManager instance = new UserManager();
    private User currentUser = null;
    private final UserRepositoryImpl userRepo = new UserRepositoryImpl();

    /**
     * Private constructor to initialize the UserManager.
     */
    private UserManager() {
    }

    /**
     * Returns the singleton instance of the UserManager.
     *
     * @return The UserManager instance.
     */
    public static UserManager getInstance() {
        return instance;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return {@code true} if a user is logged in; {@code false} otherwise.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * gets the currently logged-in user, if the user is not logged-in then null
     *
     * @return User or null
     */
    public User getUser() {
        return currentUser;
    }

    /**
     * Attempts to log in a user with the provided username and password
     *
     * @param username the username of the user trying to log in
     * @param password the corresponding password
     * @return the logged-in User object
     * @throws Exception if the username or the password are invalid
     */
    public User login(String username, String password) throws LoginFailedException {

        currentUser = userRepo.findByUsernameAndPassword(username, password);

        if (currentUser == null) {
            throw new LoginFailedException(username);
        }

        return currentUser;
    }

    /**
     * registers a new user with the provided username and password
     *
     * @param username the desired username for the new user
     * @param password the desired password for the new user
     * @return the User object
     * @throws UserAlreadyExistsException if a user with the same username already exists
     */

    public User register(String username, String password) throws UserAlreadyExistsException {

        currentUser = userRepo.registerWithUsernameAndPassword(username, password);

        return currentUser;
    }

    /**
     * Logs out the currently logged-in user
     */
    public void logout() {
        currentUser = null;
    }

}