/**
 * 
 *   Test für grundlegende Funktionen der Klasse Bestellung:
 *   -> Erstellung
 *   -> Zustandsänderungen
 *   -> Zeitberechnung
 *   -> Interaktion mit der Fabrik.


 * @author Owen, Matthieu, Alexander, Moacir, Vinzenz
 * @version 8.12.2025
 * */

import junit.framework.TestCase;

public class BestellungTest extends TestCase {
    public void testBestellungErstellenUndBestaetigen() {
        Bestellung b = new Bestellung(42, 2, 1);
        assertEquals(42, b.gibBestellungsNr());
        assertEquals(2, b.gibAnzahlStandardTueren());
        assertEquals(1, b.gibAnzahlPremiumTueren());
        assertFalse(b.gibBestellBestaetigung());
        b.bestellungBestaetigen();
        assertTrue(b.gibBestellBestaetigung());
    }

    public void testBeschaffungszeitSetzenUndLesen() {
        Bestellung b = new Bestellung(1, 1, 1);
        b.setzeBeschaffungsZeit(7);
        assertEquals(7, b.gibBeschaffungsZeit());
    }

    public void testProdukteListe() {
        Bestellung b = new Bestellung(5, 2, 3);
        assertEquals(5, b.gibBestellungsNr());
        assertEquals(5, b.gibProdukte().size());
    }

    public void testLieferzeitSetzenUndLesen() {
        Bestellung b = new Bestellung(7, 1, 0);
        b.setzeLieferZeit(4);
        assertEquals(4, b.gibLieferZeit());
    }

    public void testBestellungBestaetigenBerechnetZeitenMitFabrik() {
        Fabrik f = new Fabrik();
        Bestellung b = f.bestellungAufgeben(1, 1); // Fabrik setzt Referenz
        assertNotNull(b);
        assertEquals(0, b.gibBeschaffungsZeit()); // vorläufig

        b.bestellungBestaetigen();
        assertTrue(b.gibBestellBestaetigung());
        // Kleine Bestellung sollte bei vollem Lager Beschaffungszeit 0 liefern
        assertEquals(0, b.gibBeschaffungsZeit());
        int erwarteteProduktion = 1 * Standardtuer.PRODUKTIONSZEIT + 1 * Premiumtuer.PRODUKTIONSZEIT;
        assertEquals(erwarteteProduktion + 0 + 1, b.gibLieferZeit());
    }

    public void testLagerWiederAuffuellenOhneParameter() {
        Fabrik f = new Fabrik();
        Lager lager = f.getLager();
        Bestellung bGross = f.bestellungAufgeben(50, 50);
        assertNotNull(bGross);
        // Erzwinge Beschaffungszeit 2 (Material fehlt)
        int besch2 = lager.gibBeschaffungsZeit(bGross);
        assertEquals(2, besch2);

        // Fülle Lager über Bestellung-Helfer wieder auf (ohne Parameter)
        Bestellung anzeige = new Bestellung();
        anzeige.setztFabrik(f);
        anzeige.lagerWiederAuffuellen();

        // Kleine Bestellung sollte wieder mit 0 durchgehen
        Bestellung bKlein = f.bestellungAufgeben(1, 0);
        assertNotNull(bKlein);
        int besch0 = lager.gibBeschaffungsZeit(bKlein);
        assertEquals(0, besch0);
    }
    public void testBestellungBestaetigen_setztZeitenKorrekt()
{
    Fabrik f = new Fabrik();
    
    // Große Bestellung → Lager hat nicht genug → Beschaffungszeit = 2
    Bestellung b = f.bestellungAufgeben(100, 100);

    // Bestellung bestätigen → jetzt wird die echte Beschaffungszeit & Lieferzeit gesetzt
    b.bestellungBestaetigen();
    
    int expectedBeschaffung = 2; // Weil Material fehlt
    int expectedProd = 100 * Standardtuer.PRODUKTIONSZEIT
                     + 100 * Premiumtuer.PRODUKTIONSZEIT;
    int expectedLieferzeit = expectedProd + expectedBeschaffung + 1;

    assertTrue(b.gibBestellBestaetigung());
    assertEquals(expectedBeschaffung, b.gibBeschaffungsZeit());
    assertEquals(expectedLieferzeit, b.gibLieferZeit());
}
 
}
