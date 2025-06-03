package at.htl.helpr.usermanager.repository.exceptions;

public class LoginFailedException extends Exception {

    public LoginFailedException(String username) {
        super("Login failed for username " + username);
    }
}
