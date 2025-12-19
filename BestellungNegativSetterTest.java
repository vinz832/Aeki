import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Verifiziert, dass negative Werte in Settern ignoriert werden und Fehlermeldungen ausgeben.
 */
public class BestellungNegativSetterTest {

    @Test
    public void testSetzeBeschaffungsZeitIgnoriertNegativeWerte() {
        Bestellung b = new Bestellung(1, 1, 0);
        b.setzeBeschaffungsZeit(5);

        PrintStream original = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        try {
            b.setzeBeschaffungsZeit(-1);
        } finally {
            System.setOut(original);
        }

        assertEquals(5, b.gibBeschaffungsZeit());
        assertTrue(bos.toString().contains("Fehler: Beschaffungszeit darf nicht negativ sein."));
    }

    @Test
    public void testSetzeLieferZeitIgnoriertNegativeWerte() {
        Bestellung b = new Bestellung(1, 0, 1);
        b.setzeLieferZeit(7);

        PrintStream original = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        try {
            b.setzeLieferZeit(-2);
        } finally {
            System.setOut(original);
        }

        assertEquals(7, b.gibLieferZeit());
        assertTrue(bos.toString().contains("Fehler: Lieferzeit darf nicht negativ sein."));
    }
}
