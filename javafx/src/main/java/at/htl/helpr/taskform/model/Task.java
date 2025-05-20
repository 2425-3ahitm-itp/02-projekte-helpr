package at.htl.helpr.taskform.model;

import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {

    private final LongProperty id = new SimpleLongProperty();
    private final LongProperty authorId = new SimpleLongProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final IntegerProperty reward = new SimpleIntegerProperty();
    private final IntegerProperty effort = new SimpleIntegerProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>();

    //Constructor

    public Task() {
    }

    public Task(
            long id,
            long authorId,
            String title,
            String description,
            int reward,
            int effort,
            String location,
            LocalDateTime createdAt
    ) {
        this.id.set(id);
        this.authorId.set(authorId);
        this.title.set(title);
        this.description.set(description);
        this.reward.set(reward);
        this.effort.set(effort);
        this.location.set(location);
        this.createdAt.set(createdAt);
    }

    //Copy-Constructor

    public Task(Task task) {
        this.id.set(task.getId());
        this.authorId.set(task.getAuthorId());
        this.title.set(task.getTitle());
        this.description.set(task.getDescription());
        this.reward.set(task.getReward());
        this.effort.set(task.getEffort());
        this.location.set(task.getLocation());
        this.createdAt.set(task.getCreatedAt());
    }

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

    public long getAuthorId() {
        return authorId.get();
    }

    public void setAuthorId(long authorId) {
        this.authorId.set(authorId);
    }

    public LongProperty authorIdProperty() {
        return authorId;
    }

    public java.lang.String getTitle() {
        return title.get();
    }

    public void setTitle(java.lang.String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public java.lang.String getDescription() {
        return description.get();
    }

    public void setDescription(java.lang.String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public int getReward() {
        return reward.get();
    }

    public void setReward(int reward) {
        this.reward.set(reward);
    }

    public IntegerProperty rewardProperty() {
        return reward;
    }

    public int getEffort() {
        return effort.get();
    }

    public void setEffort(int effort) {
        this.effort.set(effort);
    }

    public IntegerProperty effortProperty() {
        return effort;
    }

    public java.lang.String getLocation() {
        return location.get();
    }

    public void setLocation(java.lang.String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    // endregion

    @Override
    public java.lang.String toString() {
        return "Task{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", title=" + title +
                ", description=" + description +
                ", reward=" + reward +
                ", effort=" + effort +
                ", location=" + location +
                ", createdAt=" + createdAt +
                '}';
    }
}
