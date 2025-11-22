 

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests für Fabrik.
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

    @Test
    public void bestellungAufgeben_negativeWerte_werdenNichtGespeichert() {
        Fabrik f = new Fabrik();
        int vorher = f.anzahlBestellungen();

        f.bestellungAufgeben(-1, 2);
        f.bestellungAufgeben(2, -1);

        assertEquals(vorher, f.anzahlBestellungen());
    }

    @Test
    public void bestellungAufgeben_nullNull_wirdNichtGespeichert() {
        Fabrik f = new Fabrik();
        int vorher = f.anzahlBestellungen();

        f.bestellungAufgeben(0, 0);

        assertEquals(vorher, f.anzahlBestellungen());
    }

    @Test
    public void bestellungAufgeben_mehrereBestellungen_zaehltKorrekt() {
        Fabrik f = new Fabrik();

        f.bestellungAufgeben(1, 0);
        f.bestellungAufgeben(0, 1);
        f.bestellungAufgeben(2, 1);

        assertEquals(3, f.anzahlBestellungen());
    }

    @Test
    public void bestellungAufgeben_inhaltDerBestellung_stimmt() {
        Fabrik f = new Fabrik();

        f.bestellungAufgeben(2, 1);

        assertEquals(1, f.anzahlBestellungen());
        Bestellung b = f.gibAlleBestellungen().get(0);

        assertEquals(2, b.gibAnzahlStandardTueren());
        assertEquals(1, b.gibAnzahlPremiumTueren());
        assertEquals(3, b.gibProdukte().size());
    }
}
