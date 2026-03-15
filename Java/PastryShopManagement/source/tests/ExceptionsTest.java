import exceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsTest {

    @Test
    void testAppException() {
        AppException e1 = new AppException("eroare test");
        AppException e2 = new AppException("eroare cu cauza", new RuntimeException("cauza"));
        assertEquals("eroare test", e1.getMessage());
        assertNotNull(e2.getCause());
    }

    @Test
    void testRepositoryException() {
        RepositoryException e = new RepositoryException("repo error");
        assertEquals("repo error", e.getMessage());
    }

    @Test
    void testDuplicateIDException() {
        DuplicateIDException e = new DuplicateIDException("duplicat");
        assertEquals("duplicat", e.getMessage());
    }

    @Test
    void testObjectNotFoundException() {
        ObjectNotFoundException e = new ObjectNotFoundException("not found");
        assertEquals("not found", e.getMessage());
    }
}
