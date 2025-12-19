import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

/**
 * Tests für den Produktions_Manager Thread-Verhalten, Handover und Abschlussmeldung.
 */
public class ProduktionsManagerTest extends TestCase {

    public void testPMIstThreadUndGestartet() throws Exception {
        Fabrik f = new Fabrik();
        // Suche nach Thread mit Name "Produktions_Manager"
        boolean gefundenUndAlive = false;
        Set<Thread> alle = Thread.getAllStackTraces().keySet();
        for (Thread t : alle) {
            if ("Produktions_Manager".equals(t.getName()) && t.isAlive()) {
                gefundenUndAlive = true;
                break;
            }
        }
        assertTrue("Produktions_Manager-Thread nicht gefunden oder nicht alive", gefundenUndAlive);
    }

    public void testHolzRoboterGestartetImKonstruktor() throws Exception {
        Fabrik f = new Fabrik();
        boolean roboterAlive = false;
        Set<Thread> alle = Thread.getAllStackTraces().keySet();
        for (Thread t : alle) {
            if ("Holzbearbeitungs_Roboter".equals(t.getName()) && t.isAlive()) {
                roboterAlive = true;
                break;
            }
        }
        assertTrue("Holzbearbeitungs_Roboter-Thread nicht gefunden oder nicht alive", roboterAlive);
    }

    public void testFabrikUebergibtBestellungAnPM_undListenZaehlung() throws Exception {
        Fabrik f = new Fabrik();
        Bestellung b = f.bestellungAufgeben(1, 1);
        assertNotNull(b);

        // innerer Zustand: sollte zeitnah in Produktion gehen
        long deadline = System.currentTimeMillis() + 5000L; // bis zu 5s warten
        boolean inProd = false;
        while (System.currentTimeMillis() < deadline) {
            if (b.gibInProduktion()) { inProd = true; break; }
            Thread.sleep(50L);
        }
        assertTrue("Bestellung wurde nicht in Produktion übernommen", inProd);
    }

    public void testAbschlussUndLogAusgabe() throws Exception {
        // Produktion stark beschleunigen
        String vorher = System.getProperty("aeki.time.scale");
        System.setProperty("aeki.time.scale", "0.001");

        // Stdout abgreifen
        PrintStream original = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        try {
            Fabrik f = new Fabrik();
            Bestellung b = f.bestellungAufgeben(1, 1);
            assertNotNull(b);

            long deadline = System.currentTimeMillis() + 10000L; // bis zu 10s warten
            boolean fertig = false;
            while (System.currentTimeMillis() < deadline) {
                if (b.gibAbgeschlossen()) { fertig = true; break; }
                Thread.sleep(50L);
            }
            assertTrue("Bestellung wurde nicht abgeschlossen", fertig);

            // Meldung prüfen (Robust: beide Varianten akzeptieren)
            String out = bos.toString("UTF-8");
            int nr = b.gibBestellungsNr();
            boolean variante1 = out.contains("[PM] Bestellung abgeschlossen #" + nr);
            boolean variante2 = out.contains("[PM] Bestellung #" + nr + " abgeschlossen");
            assertTrue("Abschlussmeldung nicht gefunden", variante1 || variante2);

            // Nach Abschluss nicht mehr in Produktion
            assertFalse(b.gibInProduktion());
        } finally {
            System.setOut(original);
            if (vorher != null) System.setProperty("aeki.time.scale", vorher); else System.clearProperty("aeki.time.scale");
        }
    }
}