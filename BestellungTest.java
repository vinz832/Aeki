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
}
