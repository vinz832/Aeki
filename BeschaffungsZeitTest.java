import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BeschaffungsZeitTest {
    @Test
    public void beschaffung_0_fuer_kleineBestellung() {
        Fabrik f = new Fabrik();
        Bestellung b = f.bestellungAufgeben(1, 0);
        assertEquals(0, b.gibBeschaffungsZeit());
    }

    @Test
    public void beschaffung_2_wenn_material_fehlt() {
        Fabrik f = new Fabrik();
        Bestellung b = f.bestellungAufgeben(100, 100);
        assertEquals(2, b.gibBeschaffungsZeit());
    }
}
