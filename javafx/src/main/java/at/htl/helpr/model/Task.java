package at.htl.helpr.model;

import javafx.beans.property.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;

public class Task {
    private final LongProperty id = new SimpleLongProperty();
    private final IntegerProperty status = new SimpleIntegerProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final IntegerProperty estimated_effort = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<Timestamp> created_at = new SimpleObjectProperty<>();

    //Constructor
    public Task() {
        this.created_at.set(Timestamp.from(Instant.now()));
    }

    public Task(Long id, int status, String location, int estimated_effort, String titel,
            String description) {
        this(status, location, estimated_effort, titel, description);
        this.id.set(id);
    }

    public Task(int status, String location, int estimated_effort, String titel,
            String description) {
        this();
        this.status.set(status);
        this.location.set(location);
        this.estimated_effort.set(estimated_effort);
        this.title.set(titel);
        this.description.set(description);
    }


    //Copy-Constructor
    public Task(Task toCopy) {
        this.id.set(toCopy.getId());
        this.status.set(toCopy.getStatus());
        this.location.set(toCopy.getLocation());
        this.estimated_effort.set(toCopy.getEstimated_effort());
        this.title.set(toCopy.getTitle());
        this.description.set(toCopy.getDescription());
        this.created_at.set(toCopy.getCreated_at());
    }

    //Getter
    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public int getStatus() {
        return status.get();
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public int getEstimated_effort() {
        return estimated_effort.get();
    }

    public IntegerProperty estimated_effortProperty() {
        return estimated_effort;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public Timestamp getCreated_at() {
        return created_at.get();
    }

    public ObjectProperty<Timestamp> created_atProperty() {
        return created_at;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setEstimated_effort(int estimated_effort) {
        this.estimated_effort.set(estimated_effort);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at.set(created_at);
    }

    //to-String

    @Override
    public String toString() {
        return getId() + " | " + getTitle() + "; " + getStatus() + "; " + getEstimated_effort();
    }
}
