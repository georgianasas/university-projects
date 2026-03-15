import domain.Tort;
import org.junit.jupiter.api.*;
import repository.BinaryFileRepository;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryFileRepositoryTest {
    private File tempFile;
    private BinaryFileRepository<Tort> repo;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("torturi_bin", ".bin");
        repo = new BinaryFileRepository<>(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void testAddAndFind() {
        repo.add(new Tort(1, "Ciocolata"));
        assertEquals("Ciocolata", repo.findById(1).getTipTortului());
    }

    @Test
    void testUpdate() {
        repo.add(new Tort(1, "Vanilie"));
        repo.update(new Tort(1, "Capsuni"));
        assertEquals("Capsuni", repo.findById(1).getTipTortului());
    }

    @Test
    void testDelete() {
        repo.add(new Tort(3, "Nuca"));
        repo.delete(3);
        assertTrue(repo.findAll().isEmpty());
    }
}
