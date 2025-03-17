package at.htl.helpr.taskform.model;

public enum TaskStatus {
    OPEN,
    ACTIVE,
    COMPLETED,
    CANCELLED;

    public static TaskStatus fromInt(int i) {
        if (i < 0 || i >= values().length) {
            throw new RuntimeException("Invalid TaskStatus value: " + i);
        }
        return values()[i];
    }
}
