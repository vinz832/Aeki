import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testet, dass der Holzbearbeitungs_Roboter Produkte aus der Warteschlange abarbeitet.
 */
public class RoboterQueueConsumptionTest {

    @Test
    public void testHolzRoboterArbeitsQueue() throws Exception {
        // Beschleunigte Produktion
        Holzbearbeitungs_Roboter rob = new Holzbearbeitungs_Roboter(0.001);
        rob.start();
        try {
            Standardtuer p = new Standardtuer();
            assertEquals(0, rob.gibQueueLaenge());
            rob.fuegeProduktHinzu(p);
            // Warten bis Produkt fertig oder Timeout
            long deadline = System.currentTimeMillis() + 5000L;
            while (System.currentTimeMillis() < deadline && p.aktuellerZustand() != 2) {
                Thread.sleep(25L);
            }
            assertEquals("Produkt sollte FERTIG sein", 2, p.aktuellerZustand());
            // Warteschlange sollte wieder leer sein
            long deadline2 = System.currentTimeMillis() + 1000L;
            while (System.currentTimeMillis() < deadline2 && rob.gibQueueLaenge() != 0) {
                Thread.sleep(10L);
            }
            assertEquals(0, rob.gibQueueLaenge());
        } finally {
            rob.stopRoboter();
            rob.join(2000L);
        }
    }
}
