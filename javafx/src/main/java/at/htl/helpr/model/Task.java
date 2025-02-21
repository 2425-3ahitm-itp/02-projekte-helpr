package at.htl.helpr.model;

import javafx.beans.property.*;
import org.postgresql.geometric.PGpoint;

import java.sql.Timestamp;
import java.time.Instant;


public class Task {
    private final LongProperty id = new SimpleLongProperty();
    private final IntegerProperty status = new SimpleIntegerProperty();
    private final ObjectProperty<PGpoint> location = new SimpleObjectProperty<>();
    private final IntegerProperty estimatedEffort = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<Timestamp> createdAt = new SimpleObjectProperty<>();

    //Constructor
    public Task() {
        this.createdAt.set( Timestamp.from( Instant.now() ) );
    }

    public Task( Long id, int status, PGpoint location, int estimated_effort, String titel,
                 String description ) {
        this( status, location, estimated_effort, titel, description );
        this.id.set( id );
    }

    public Task( int status, PGpoint location, int estimated_effort, String titel,
                 String description ) {
        this();
        this.status.set( status );
        this.location.set( location );
        this.estimatedEffort.set( estimated_effort );
        this.title.set( titel );
        this.description.set( description );
    }


    //Copy-Constructor
    public Task( Task toCopy ) {
        this.id.set( toCopy.getId() );
        this.status.set( toCopy.getStatus() );
        this.location.set( toCopy.getLocation() );
        this.estimatedEffort.set( toCopy.getEstimatedEffort() );
        this.title.set( toCopy.getTitle() );
        this.description.set( toCopy.getDescription() );
        this.createdAt.set( toCopy.getCreatedAt() );
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

    public PGpoint getLocation() {
        return location.get();
    }

    public ObjectProperty<PGpoint> locationProperty() {
        return location;
    }

    public int getEstimatedEffort() {
        return estimatedEffort.get();
    }

    public IntegerProperty estimatedEffortProperty() {
        return estimatedEffort;
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

    public Timestamp getCreatedAt() {
        return createdAt.get();
    }

    public ObjectProperty<Timestamp> createdAtProperty() {
        return createdAt;
    }

    public void setId( long id ) {
        this.id.set( id );
    }

    public void setStatus( int status ) {
        this.status.set( status );
    }

    public void setLocation( PGpoint location ) {
        this.location.set( location );
    }

    public void setEstimatedEffort( int estimatedEffort) {
        this.estimatedEffort.set(estimatedEffort);
    }

    public void setTitle( String title ) {
        this.title.set( title );
    }

    public void setDescription( String description ) {
        this.description.set( description );
    }

    public void setCreatedAt( Timestamp createdAt) {
        this.createdAt.set(createdAt);
    }

    //to-String

    @Override
    public String toString() {
        return getId() + " | " + getTitle() + "; " + getStatus() + "; " + getEstimatedEffort();
    }
}
