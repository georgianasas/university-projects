package repository;

import domain.Entity;
import java.util.List;

public interface Repository<T extends Entity> {
    void add(T element);
    void update(T element);
    void delete(int id);
    T findById(int id);
    List<T> findAll();
}
