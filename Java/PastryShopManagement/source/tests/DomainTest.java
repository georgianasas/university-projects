import domain.Comanda;
import domain.Tort;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DomainTest {

    @Test
    void testTortToStringAndAccessors() {
        Tort t = new Tort(10, "Ciocolata");
        assertEquals(10, t.getID());
        assertEquals("Ciocolata", t.getTipTortului());
        t.setTipTortului("Vanilie");
        assertEquals("Vanilie", t.getTipTortului());
        String s = t.toString();
        assertTrue(s.contains("10"));
        assertTrue(s.contains("Vanilie"));
    }

    @Test
    void testComandaFieldsAndToString() {
        Tort t1 = new Tort(1, "Cioco");
        Tort t2 = new Tort(2, "Vanilie");
        List<Tort> torts = new ArrayList<>(Arrays.asList(t1, t2));

        Date data = new Date();
        Comanda c = new Comanda(5, torts, data);

        assertEquals(5, c.getID());
        assertEquals(torts, c.getTorturi());
        assertEquals(data, c.getData());

        c.setData(new Date(0));
        c.setTorturi(new ArrayList<>(List.of(new Tort(3, "Fructe"))));

        assertEquals(1, c.getTorturi().size());
        assertEquals("Fructe", c.getTorturi().get(0).getTipTortului());

        String str = c.toString();
        assertTrue(str.contains("Fructe"));
        assertTrue(str.contains("Data:"));
    }
}