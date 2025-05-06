package at.htl.helpr.profile.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final ObjectProperty<byte[]> profilePicture = new SimpleObjectProperty<>();

    // region Constructors

    public User() {
    }

    public User(long id, String username, String password, String email, byte[] profilePicture) {
        this.id.set(id);
        this.username.set(username);
        this.password.set(password);
        this.email.set(email);
        this.profilePicture.set(profilePicture);
    }

    // copy constructor

    public User(User user) {
        this.id.set(user.getId());
        this.username.set(user.getUsername());
        this.password.set(user.getPassword());
        this.email.set(user.getEmail());
        this.profilePicture.set(user.getProfilePicture());
    }

    // endregion

    // region getter and setter
    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public byte[] getProfilePicture() {
        return profilePicture.get();
    }

    public ObjectProperty<byte[]> profilePictureProperty() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture.set(profilePicture);
    }

    // endregion


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", email=" + email +
                ", profilePicture=" + profilePicture +
                '}';
    }
}
