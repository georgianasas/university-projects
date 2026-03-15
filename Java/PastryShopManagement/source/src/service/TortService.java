package service;
import domain.Tort;
import exceptions.AppException;
import repository.Repository;
import java.util.List;

public class TortService {
    private final Repository<Tort> repo;

    public TortService(Repository<Tort> repo) {
        this.repo = repo;
    }

    private void validate(Tort t) {
        if (t.getTipTortului() == null || t.getTipTortului().trim().isEmpty())
            throw new AppException("Tipul tortului nu poate fi gol.");
    }

    public void add(Tort t) {
        validate(t);
        repo.add(t);
    }

    public void update(Tort t) {
        validate(t);
        repo.update(t);
    }

    public void delete(int id) {
        repo.delete(id);
    }

    public Tort get(int id) {
        return repo.findById(id);
    }

    public List<Tort> all() {
        return repo.findAll();
    }
}
