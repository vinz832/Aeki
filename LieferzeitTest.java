import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LieferzeitTest {
    @Test
    public void lieferzeit_berechnung_ohneBeschaffung() {
        Fabrik f = new Fabrik();
        // 2 Standard (3 Tage je) + 1 Premium (5 Tage) = 11 Produktion
        Bestellung b = f.bestellungAufgeben(2, 1);
        int expectedProd = 2 * Standardtuer.PRODUKTIONSZEIT + 1 * Premiumtuer.PRODUKTIONSZEIT; // 11
        assertEquals(0, b.gibBeschaffungsZeit());
        assertEquals(expectedProd + 0 + 1, b.gibLieferZeit());
    }

    @Test
    public void lieferzeit_berechnung_mitBeschaffung() {
        Fabrik f = new Fabrik();
        Bestellung b = f.bestellungAufgeben(60, 60); // Zwingt Materialmangel
        int expectedProd = 60 * Standardtuer.PRODUKTIONSZEIT + 60 * Premiumtuer.PRODUKTIONSZEIT;
        assertEquals(2, b.gibBeschaffungsZeit());
        assertEquals(expectedProd + 2 + 1, b.gibLieferZeit());
    }
}
