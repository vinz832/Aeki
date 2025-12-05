import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LieferantTest {

    @Test
    void lieferantFuellteLagerAuf() {
        Fabrik f = new Fabrik();
        Lager lager = f.getLager();
        Lieferant lieferant = f.getLieferant();

        // Erzeuge großen Bedarf, der nicht gedeckt ist
        Bestellung b = f.bestellungAufgeben(200, 0);
        assertNotNull(b);
        int zeit = lager.gibBeschaffungsZeit(b);
        assertEquals(2, zeit);

        // Fülle Lager auf
        lager.lagerAuffuellen(lieferant);

        // Nun sollte eine kleine Bestellung ohne Beschaffungszeit auskommen
        Bestellung b2 = f.bestellungAufgeben(1, 0);
        assertNotNull(b2);
        int zeit2 = lager.gibBeschaffungsZeit(b2);
        assertEquals(0, zeit2);
    }
}
