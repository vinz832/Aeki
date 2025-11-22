import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProduktTest {

    @Test
    public void startZustand_istBestellt() {
        Produkt p = new Standardtuer();
        assertEquals(0, p.aktuellerZustand());
    }

    @Test
    public void zustandWechsel_mehrereZustaende() {
        Produkt p = new Standardtuer();

        p.zustandAendern(2);
        assertEquals(2, p.aktuellerZustand());

        p.zustandAendern(4);
        assertEquals(4, p.aktuellerZustand());
    }

    @Test
    public void zustandAlsText_stimmt() {
        Produkt p = new Premiumtuer();

        p.zustandAendern(3);
        assertEquals("TRANSPORTVORBEREITUNG", p.zustandAlsText());
    }
}