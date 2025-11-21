 
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProduktTest {

    @Test
    public void zustandWechsel() {
        Produkt p = new Standardtuer();
        p.zustandAendern(1);
        assertEquals(1, p.aktuellerZustand());
    }
}
