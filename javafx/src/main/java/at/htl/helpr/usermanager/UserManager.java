package at.htl.helpr.usermanager;

import at.htl.helpr.usermanager.model.User;

public class UserManager {

    private static UserManager instance;
    private User currentUser = null;

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

}