import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBestellungJUnit {
    @Test
    void testBestellungAusgebenUndBestaetigen() {
        Bestellung b1 = new Bestellung(10, 3, 2);
        assertEquals(10, b1.gibBestellungsNr());
        assertEquals(3, b1.gibAnzahlStandardTueren());
        assertEquals(2, b1.gibAnzahlPremiumTueren());
        assertFalse(b1.gibBestellBestaetigung());

        b1.bestellungBestaetigen();
        assertTrue(b1.gibBestellBestaetigung());

        b1.setzeBeschaffungsZeit(5);
        assertEquals(5, b1.gibBeschaffungsZeit());
    }
}
