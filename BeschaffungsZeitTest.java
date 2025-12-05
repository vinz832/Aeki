import junit.framework.TestCase;

public class BeschaffungsZeitTest extends TestCase {
    public void testBeschaffung_0_fuer_kleineBestellung() {
        Fabrik f = new Fabrik();
        Bestellung b = f.bestellungAufgeben(1, 0);
        assertEquals(0, b.gibBeschaffungsZeit());
    }

    public void testBeschaffung_2_wenn_material_fehlt() {
        Fabrik f = new Fabrik();
        Bestellung b = f.bestellungAufgeben(100, 100);
        assertEquals(2, b.gibBeschaffungsZeit());
    }
}
