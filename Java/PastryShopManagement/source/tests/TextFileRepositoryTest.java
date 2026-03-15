import domain.Tort;
import org.junit.jupiter.api.*;
import repository.TextFileRepository;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextFileRepositoryTest {
    private File tempFile;
    private TextFileRepository<Tort> repo;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("torturi", ".txt");

        repo = new TextFileRepository<>(tempFile.getAbsolutePath()) {
            // Initializer block: calls loadFromFile after construction
            {
                loadFromFile();
            }

            @Override
            protected Tort readEntity(String line) {
                String[] parts = line.split(",");
                return new Tort(Integer.parseInt(parts[0]), parts[1]);
            }

            @Override
            protected String writeEntity(Tort entity) {
                return entity.getID() + "," + entity.getTipTortului();
            }
        };
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void testAddAndPersist() throws IOException {
        repo.add(new Tort(1, "Ciocolata"));
        List<String> lines = java.nio.file.Files.readAllLines(tempFile.toPath());
        assertTrue(lines.get(0).contains("Ciocolata"));
    }

    @Test
    void testUpdateAndReload() {
        repo.add(new Tort(1, "Vanilie"));
        repo.update(new Tort(1, "Capsuni"));
        assertEquals("Capsuni", repo.findById(1).getTipTortului());
    }

    @Test
    void testDelete() {
        repo.add(new Tort(2, "Fructe"));
        repo.delete(2);
        assertTrue(repo.findAll().isEmpty());
    }
}