import domain.Comanda;
import domain.Tort;
import exceptions.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryRepository;
import service.ComandaService;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ComandaServiceTest {
    private ComandaService service;

    @BeforeEach
    void setUp() {
        var tortRepo = new InMemoryRepository<Tort>();
        tortRepo.add(new Tort(1, "Ciocolata"));
        var comandaRepo = new InMemoryRepository<Comanda>();
        service = new ComandaService(comandaRepo, tortRepo);
    }

    @Test
    void testAddValid() {
        service.add(1, Arrays.asList(1), new Date());
        assertEquals(1, service.all().size());
    }

    @Test
    void testInvalidData() {
        assertThrows(AppException.class, () -> service.add(2, Arrays.asList(1), null));
    }
}