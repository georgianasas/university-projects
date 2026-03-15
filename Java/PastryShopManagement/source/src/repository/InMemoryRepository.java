package repository;

import domain.Entity;
import exceptions.AppException;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository<T extends Entity> implements Repository<T> {

    private final List<T> elements = new ArrayList<>();

    @Override
    public void add(T element) {
        if (findByIdInternal(element.getID()) != null) {
            throw new AppException("ID duplicat: " + element.getID());
        }
        validateId(element.getID());
        elements.add(element);
    }

    @Override
    public void update(T element) {
        validateId(element.getID());
        T existing = findByIdInternal(element.getID());
        if (existing == null) throw new AppException("Nu există ID: " + element.getID());
        int idx = elements.indexOf(existing);
        elements.set(idx, element);
    }

    @Override
    public void delete(int id) {
        validateId(id);
        T existing = findByIdInternal(id);
        if (existing == null) throw new AppException("Nu există ID: " + id);
        elements.remove(existing);
    }

    @Override
    public T findById(int id) {
        validateId(id);
        T e = findByIdInternal(id);
        if (e == null) throw new AppException("Nu există ID: " + id);
        return e;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(elements);
    }


    private T findByIdInternal(int id) {
        for (T e : elements) {
            if (e.getID() == id) return e;
        }
        return null;
    }

    private void validateId(int id) {
        if (id <= 0) throw new AppException("ID-ul trebuie să fie pozitiv (>= 1).");
    }
}
