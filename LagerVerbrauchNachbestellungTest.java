import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Testet, dass bei Produktionsstart mit Materialmangel eine Nachbestellung ausgelöst wird.
 * Es wird NUR der Log-Hinweis geprüft, nicht die spätere Anlieferung (die 48s dauern kann).
 */
public class LagerVerbrauchNachbestellungTest {

    @Test
    public void testNachbestellungWirdAusgeloestBeiMangel() {
        Fabrik f = new Fabrik();
        Lager lager = f.getLager();

        // Riesige Bestellung erzeugen, die sicher Mangel verursacht
        Bestellung b = f.bestellungAufgeben(100_000, 100_000);
        assertNotNull(b);

        // Log abgreifen
        PrintStream original = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        try {
            lager.verbraucheMaterialFuer(b);
        } finally {
            System.setOut(original);
        }

        String out = bos.toString();
        assertTrue("Nachbestell-Log erwartet", out.contains("[Lager] Material unzureichend"));
    }
}
