 

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests fuer Fabrik.
 */
public class FabrikTest {

    @Test
    public void bestellungAufgeben_legtmAb() {
        Fabrik f = new Fabrik();
        f.bestellungAufgeben(2, 1);
        assertEquals(1, f.anzahlBestellungen());
    }

    @Test
    public void bestellungenAusgeben_keineException() {
        Fabrik f = new Fabrik();
        f.bestellungAufgeben(1, 0);
        f.bestellungAufgeben(0, 1);
        assertDoesNotThrow(() -> f.bestellungenAusgeben());
    }
}
