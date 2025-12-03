import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LagerTest {
    @Test
    public void beschaffungsZeit_0_beiGenugBestand() {
        Fabrik f = new Fabrik();
        Bestellung b = f.bestellungAufgeben(1, 1); // reserviert Material im Lager
        assertEquals(0, b.gibBeschaffungsZeit());
        assertTrue(b.gibLieferZeit() > 0);
    }

    @Test
    public void lagerAuffuellen_erhoehtBestaende() {
        Fabrik f = new Fabrik();
        // Triggert Nachbestellung: sehr große Bestellung
        Bestellung b = f.bestellungAufgeben(50, 50);
        assertEquals(2, b.gibBeschaffungsZeit());
        // Nach Auffüllen sollten weitere kleine Bestellungen ohne Beschaffung möglich sein
        Bestellung b2 = f.bestellungAufgeben(1, 0);
        assertEquals(0, b2.gibBeschaffungsZeit());
    }
}
