/**
 * 
 *   Test für Zusammenspiel von Lager, Lieferant und Bestellungen.

 * @author Owen, Matthieu, Alexander, Moacir, Vinzenz
 * @version 8.12.2025
 * */

import junit.framework.TestCase;

public class LieferantTest extends TestCase {
    public void testLieferantFuellteLagerAuf() {
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
