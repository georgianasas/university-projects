import domain.Tort;
import org.junit.jupiter.api.Test;
import repository.HibernateRepository;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateRepositoryTest {

    @Test
    void testCrudOperations() {
        HibernateRepository<Tort> repo = new HibernateRepository<>(Tort.class);

        // Generate a random ID to avoid primary key collisions during repeated tests
        int id = new Random().nextInt(10000) + 1000;
        Tort t = new Tort(id, "Tort Test Hibernate");

        // 1. ADD
        repo.add(t);
        Tort found = repo.findById(id);
        assertNotNull(found);
        assertEquals("Tort Test Hibernate", found.getTipTortului());

        // 2. UPDATE
        found.setTipTortului("Tort Updated");
        repo.update(found);

        Tort updated = repo.findById(id);
        assertEquals("Tort Updated", updated.getTipTortului());

        // 3. DELETE
        repo.delete(id);
        assertNull(repo.findById(id));
    }
}