package domain;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class Entity implements Serializable {
    @Id
    private int id;

    public Entity() {} // Hibernate

    public Entity(int id) {
        this.id = id;
    }

    public int getID() { return id; }
    public void setID(int id) { this.id = id; }
}