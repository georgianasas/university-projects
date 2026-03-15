package domain;

import jakarta.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@jakarta.persistence.Entity
@Table(name = "comenzi")
public class Comanda extends Entity {

    @ManyToMany(fetch = FetchType.EAGER) // Load cakes when order is loaded
    private List<Tort> torturi;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public Comanda() {
        super(0);
        this.torturi = new ArrayList<>();
    }

    public Comanda(int id, List<Tort> torturi, Date data) {
        super(id);
        this.torturi = torturi;
        this.data = data;
    }

    public List<Tort> getTorturi() { return torturi; }
    public void setTorturi(List<Tort> torturi) { this.torturi = torturi; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    @Override
    public String toString() {
        String names = torturi.stream().map(Tort::getTipTortului).collect(Collectors.joining(", "));
        return getID() + ". Data: " + SDF.format(data) + " [ " + names + " ]";
    }
}