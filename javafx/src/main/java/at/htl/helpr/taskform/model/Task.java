package at.htl.helpr.taskform.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;


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

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public long getAuthorId() {
        return authorId.get();
    }

    public LongProperty authorIdProperty() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId.set(authorId);
    }

    public java.lang.String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(java.lang.String title) {
        this.title.set(title);
    }

    public java.lang.String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description.set(description);
    }

    public int getReward() {
        return reward.get();
    }

    public IntegerProperty rewardProperty() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward.set(reward);
    }

    public int getEffort() {
        return effort.get();
    }

    public IntegerProperty effortProperty() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort.set(effort);
    }

    public java.lang.String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(java.lang.String location) {
        this.location.set(location);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
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
