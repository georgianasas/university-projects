import domain.Comanda;
import domain.Tort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ComandaTextRepository;
import repository.InMemoryRepository;
import repository.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComandaTextRepositoryTest {
    private File tempFile;
    private ComandaTextRepository comandaRepo;
    private Repository<Tort> tortRepo;

    @BeforeEach
    void setUp() throws IOException {
        // 1. Create a dummy Tort repo with data
        tortRepo = new InMemoryRepository<>();
        tortRepo.add(new Tort(1, "Tort Ciocolata"));
        tortRepo.add(new Tort(2, "Tort Mere"));

        // 2. Create a temporary file for orders
        tempFile = File.createTempFile("comenzi_test", ".txt");

        // 3. Initialize the repo under test
        comandaRepo = new ComandaTextRepository(tempFile.getAbsolutePath(), tortRepo);
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void testAddAndReadFromFile() {
        List<Tort> torts = List.of(tortRepo.findById(1), tortRepo.findById(2));
        Comanda c = new Comanda(100, torts, new Date());

        comandaRepo.add(c);

        // Reload repo to verify file persistence
        ComandaTextRepository newRepo = new ComandaTextRepository(tempFile.getAbsolutePath(), tortRepo);
        Comanda loaded = newRepo.findById(100);

        assertNotNull(loaded);
        assertEquals(2, loaded.getTorturi().size());
        assertEquals("Tort Ciocolata", loaded.getTorturi().get(0).getTipTortului());
    }
}