/**
 * Tests für Funktionen der Fabrik: 
 * -> Bestellfunktionen (Aufgeben, Ausgeben)
 * -> Validierung
 * -> Summierung
 * -> Korrekte Initialisierung
 * 
*/ 

import junit.framework.TestCase;

/**
 * Tests für Fabrik.
 */
public class FabrikTest extends TestCase {
    public void testBestellungAufgeben_legtmAb() {
        Fabrik f = new Fabrik();
        f.bestellungAufgeben(2, 1);
        assertEquals(1, f.anzahlBestellungen());
    }
    public void testBestellungenAusgeben_keineException() {
        Fabrik f = new Fabrik();
        f.bestellungAufgeben(1, 0);
        f.bestellungAufgeben(0, 1);
        // BlueJ/JUnit3: Wir rufen einfach aus und erwarten keine Exception
        f.bestellungenAusgeben();
    }
    public void testBestellungAufgeben_negativeWerte_werdenNichtGespeichert() {
        Fabrik f = new Fabrik();
        int vorher = f.anzahlBestellungen();

        f.bestellungAufgeben(-1, 2);
        f.bestellungAufgeben(2, -1);

        assertEquals(vorher, f.anzahlBestellungen());
    }
    public void testBestellungAufgeben_nullNull_wirdNichtGespeichert() {
        Fabrik f = new Fabrik();
        int vorher = f.anzahlBestellungen();

        f.bestellungAufgeben(0, 0);

        assertEquals(vorher, f.anzahlBestellungen());
    }
    public void testBestellungAufgeben_mehrereBestellungen_zaehltKorrekt() {
        Fabrik f = new Fabrik();

        f.bestellungAufgeben(1, 0);
        f.bestellungAufgeben(0, 1);
        f.bestellungAufgeben(2, 1);

        assertEquals(3, f.anzahlBestellungen());
    }
    public void testBestellungAufgeben_inhaltDerBestellung_stimmt() {
        Fabrik f = new Fabrik();

        f.bestellungAufgeben(2, 1);

        assertEquals(1, f.anzahlBestellungen());
        Bestellung b = f.gibAlleBestellungen().get(0);

        assertEquals(2, b.gibAnzahlStandardTueren());
        assertEquals(1, b.gibAnzahlPremiumTueren());
        assertEquals(3, b.gibProdukte().size());
    }
    public void testBestellungAufgeben_setztZeiten() {
    Fabrik f = new Fabrik();
    Bestellung b = f.bestellungAufgeben(2, 1);
    int expectedProd = 2 * Standardtuer.PRODUKTIONSZEIT 
                     + 1 * Premiumtuer.PRODUKTIONSZEIT;
    assertEquals(0, b.gibBeschaffungsZeit());
    assertEquals(expectedProd + 1, b.gibLieferZeit());
}
}
