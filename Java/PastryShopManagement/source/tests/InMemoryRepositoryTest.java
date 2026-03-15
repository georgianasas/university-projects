import domain.Tort;
import exceptions.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryRepository;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {
    private InMemoryRepository<Tort> repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryRepository<>();
    }

    @Test
    void testAddAndFind() {
        repo.add(new Tort(1, "Ciocolata"));
        assertEquals("Ciocolata", repo.findById(1).getTipTortului());
    }

    @Test
    void testDuplicateId() {
        repo.add(new Tort(1, "Vanilie"));
        assertThrows(AppException.class, () -> repo.add(new Tort(1, "Altul")));
    }

    @Test
    void testDelete() {
        repo.add(new Tort(1, "Capsuni"));
        repo.delete(1);
        assertThrows(AppException.class, () -> repo.findById(1));
    }
}
