import junit.framework.TestCase;

public class LieferzeitTest extends TestCase {
    public void testLieferzeit_berechnung_ohneBeschaffung() {
        Fabrik f = new Fabrik();
        // 2 Standard (3 Tage je) + 1 Premium (5 Tage) = 11 Produktion
        Bestellung b = f.bestellungAufgeben(2, 1);
        int expectedProd = 2 * Standardtuer.PRODUKTIONSZEIT + 1 * Premiumtuer.PRODUKTIONSZEIT; // 11
        assertEquals(0, b.gibBeschaffungsZeit());
        assertEquals(expectedProd + 0 + 1, b.gibLieferZeit());
    }

    public void testLieferzeit_berechnung_mitBeschaffung() {
        Fabrik f = new Fabrik();
        Bestellung b = f.bestellungAufgeben(60, 60); // Zwingt Materialmangel
        int expectedProd = 60 * Standardtuer.PRODUKTIONSZEIT + 60 * Premiumtuer.PRODUKTIONSZEIT;
        assertEquals(2, b.gibBeschaffungsZeit());
        assertEquals(expectedProd + 2 + 1, b.gibLieferZeit());
    }
}
