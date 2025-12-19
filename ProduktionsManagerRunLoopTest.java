import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit 5 Test für die run()-Schleife des Produktions_Manager.
 * Prüft:
 * 1) Wiederholte Prüfung auf neue Bestellungen
 * 2) Verschieben von zuVerarbeiten -> inProduktion
 * 3) Start der Produktion (Holzbearbeitungs_Roboter erkennbar)
 */
public class ProduktionsManagerRunLoopTest {

    private Produktions_Manager pm;
    private String prevScale;

    @After
    public void tearDown() throws Exception {
        if (pm != null) {
            pm.stoppe();
            pm.join(500);
        }
        if (prevScale != null) System.setProperty("aeki.time.scale", prevScale); else System.clearProperty("aeki.time.scale");
    }

    @Test
    public void runLoop_movesOrder_and_startsProduction() throws Exception {
        // Produktion beschleunigen, Property wird im Roboter-Konstruktor gelesen
        prevScale = System.getProperty("aeki.time.scale");
        System.setProperty("aeki.time.scale", "0.001");

        pm = new Produktions_Manager();
        pm.start();

        // Bestellung erzeugen (direkt; Fabrik optional)
        Bestellung b = new Bestellung(9999, 1, 0);

        // Bestellung an PM übergeben
        pm.neueBestellungHinzufuegen(b);

        // Polling bis Timeout: warten, bis Bestellung in Produktion übernommen wurde
        long deadline = System.currentTimeMillis() + 2_000L; // max 2s
        boolean moved = false;
        while (System.currentTimeMillis() < deadline) {
            if (pm.debugInProduktionSize() == 1 && pm.debugZuVerarbeitenSize() == 0) {
                moved = true;
                break;
            }
            Thread.sleep(50L);
        }
        assertTrue("Bestellung wurde nicht in 'inProduktion' verschoben", moved);

        // Produktion gestartet erkennbar: Bestellung markiert als inProduktion
        assertTrue("Bestellung nicht als in Produktion markiert", b.gibInProduktion());

        // Zusätzlich: mindestens ein Produkt hat Zustand >= IN_PRODUKTION (1)
        boolean started = false;
        deadline = System.currentTimeMillis() + 2_000L;
        while (System.currentTimeMillis() < deadline) {
            if (!b.gibProdukte().isEmpty() && b.gibProdukte().get(0).aktuellerZustand() >= 1) {
                started = true;
                break;
            }
            Thread.sleep(50L);
        }
        assertTrue("Produktion nicht gestartet (Produktzustand < IN_PRODUKTION)", started);
    }
}
