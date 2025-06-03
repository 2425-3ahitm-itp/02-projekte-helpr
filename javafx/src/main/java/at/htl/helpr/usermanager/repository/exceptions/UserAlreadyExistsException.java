package at.htl.helpr.usermanager.repository.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String username) {
        super(String.format("User with username <%s> already exists!", username));
    }

    public UserAlreadyExistsException() {
        super("User already exists with that username!");
    }

}
