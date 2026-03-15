import domain.Tort;
import exceptions.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryRepository;
import service.TortService;

import static org.junit.jupiter.api.Assertions.*;

public class TortServiceTest {
    private TortService service;

    @BeforeEach
    void setUp() {
        service = new TortService(new InMemoryRepository<>());
    }

    @Test
    void testAddValid() {
        service.add(new Tort(1, "Vanilie"));
        assertEquals(1, service.all().size());
    }

    @Test
    void testInvalidTip() {
        assertThrows(AppException.class, () -> service.add(new Tort(2, "")));
    }
}
