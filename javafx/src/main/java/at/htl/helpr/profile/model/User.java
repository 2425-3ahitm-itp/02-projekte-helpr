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
    private final ObjectProperty<byte[]> profilePicture = new SimpleObjectProperty<>();

    // region Constructors

    public User() {
    }

    public User(long id, String username, String password, byte[] profilePicture) {
        this.id.set(id);
        this.username.set(username);
        this.password.set(password);
        this.profilePicture.set(profilePicture);
    }

    // copy constructor

    public User(User user) {
        this.id.set(user.getId());
        this.username.set(user.getUsername());
        this.password.set(user.getPassword());
        this.profilePicture.set(user.getProfilePicture());
    }

    // endregion

    // region getter and setter
    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public byte[] getProfilePicture() {
        return profilePicture.get();
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture.set(profilePicture);
    }

    public ObjectProperty<byte[]> profilePictureProperty() {
        return profilePicture;
    }

    // endregion

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", profilePicture=" + profilePicture +
                '}';
    }
}
