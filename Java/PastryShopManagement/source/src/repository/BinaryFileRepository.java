package repository;

import domain.Entity;
import exceptions.RepositoryException;

import java.io.*;
import java.util.List;

public class BinaryFileRepository<T extends Entity & Serializable> extends InMemoryRepository<T> {
    private final String filename;

    public BinaryFileRepository(String filename) {
        this.filename = filename;
        loadFromFile();
    }

    @Override
    public void add(T element) {
        super.add(element);
        saveToFile();
    }

    @Override
    public void update(T element) {
        super.update(element);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        super.delete(id);
        saveToFile();
    }

    private void loadFromFile() {
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<T> entities = (List<T>) ois.readObject();
            for (T entity : entities) super.add(entity);
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException("Eroare la citirea binară: " + filename);
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(findAll());
        } catch (IOException e) {
            throw new RepositoryException("Eroare la scrierea binară: " + filename);
        }
    }
}
