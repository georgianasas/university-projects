package domain;

import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "torturi")
public class Tort extends Entity {
    private String tipTortului;

    public Tort() { super(0); } // Hibernate needs no-arg constructor

    public Tort(int id, String tipTortului) {
        super(id);
        this.tipTortului = tipTortului;
    }

    public String getTipTortului() { return tipTortului; }
    public void setTipTortului(String tipTortului) { this.tipTortului = tipTortului; }

    @Override
    public String toString() {
        return getID() + ". " + tipTortului;
    }

    // Equals and HashCode are crucial for Streams grouping
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tort)) return false;
        Tort tort = (Tort) o;
        return getID() == tort.getID();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getID());
    }
}