import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests für die Klasse Holzbearbeitungs_Roboter.
 * Prüft Vererbung/Eigenschaften, Bearbeitungszeiten, Zustandswechsel und Interrupt-Verhalten.
 */
public class HolzbearbeitungsRoboterTest {

    @Test
    public void testIstErweiterungVonRoboterUndQueueEigenschaften() {
        Holzbearbeitungs_Roboter rob = new Holzbearbeitungs_Roboter(0.001);
        assertTrue("Holzbearbeitungs_Roboter sollte Roboter sein", rob instanceof Roboter);

        // Queue-Operationen von Basisklasse verfügbar
        Standardtuer st = new Standardtuer();
        assertEquals(0, rob.gibQueueLaenge());
        rob.fuegeProduktHinzu(st);
        assertEquals(1, rob.gibQueueLaenge());

        rob.stopRoboter();
    }

    @Test
    public void testStandardtuerBearbeitungszeitUndZustand() {
        // Zeitskalierung 0.001 -> 600000ms wird 600ms
        Holzbearbeitungs_Roboter rob = new Holzbearbeitungs_Roboter(0.001);
        Standardtuer st = new Standardtuer();

        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        PrintStream prevOut = System.out;
        System.setOut(new PrintStream(outBuf));
        try {
            long start = System.currentTimeMillis();
            rob.produziereProdukt(st);
            long dur = System.currentTimeMillis() - start;

            assertTrue("Bearbeitungszeit (Standard) sollte >= 600ms sein, war: " + dur, dur >= 600);
            assertEquals("Produkt sollte nach Holzbearbeitung FERTIG sein", 2, st.aktuellerZustand());

            String log = outBuf.toString();
            assertTrue(log.contains("[Holzbearb] Start: Standardtuer"));
            assertTrue(log.contains("[Holzbearb] Fertig: Standardtuer"));
        } finally {
            System.setOut(prevOut);
        }
    }

    @Test
    public void testPremiumtuerBearbeitungszeitUndZustand() {
        // Zeitskalierung 0.001 -> 1800000ms wird 1800ms
        Holzbearbeitungs_Roboter rob = new Holzbearbeitungs_Roboter(0.001);
        Premiumtuer pt = new Premiumtuer();

        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        PrintStream prevOut = System.out;
        System.setOut(new PrintStream(outBuf));
        try {
            long start = System.currentTimeMillis();
            rob.produziereProdukt(pt);
            long dur = System.currentTimeMillis() - start;

            assertTrue("Bearbeitungszeit (Premium) sollte >= 1800ms sein, war: " + dur, dur >= 1800);
            assertEquals("Produkt sollte nach Holzbearbeitung FERTIG sein", 2, pt.aktuellerZustand());

            String log = outBuf.toString();
            assertTrue(log.contains("[Holzbearb] Start: Premiumtuer"));
            assertTrue(log.contains("[Holzbearb] Fertig: Premiumtuer"));
        } finally {
            System.setOut(prevOut);
        }
    }

    @Test
    public void testInterruptedExceptionWirdRobustBehandelt() throws Exception {
        // Skaliere auf 0.001 -> 600ms Schlaf, ausreichend um zu unterbrechen
        Holzbearbeitungs_Roboter rob = new Holzbearbeitungs_Roboter(0.001);
        Standardtuer st = new Standardtuer();

        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        PrintStream prevOut = System.out;
        System.setOut(new PrintStream(outBuf));
        try {
            Thread t = new Thread(() -> {
                rob.produziereProdukt(st);
            });
            t.start();
            Thread.sleep(100); // kurze Zeit warten bis Sleep aktiv ist
            t.interrupt();
            t.join(2000);

            // Sollte nicht FERTIG sein, sondern in Produktion geblieben/abgebrochen
            assertEquals("Bei Unterbrechung darf nicht FERTIG sein", 1, st.aktuellerZustand());

            String log = outBuf.toString();
            assertTrue("Fehlermeldung/Unterbrechung erwartet", log.contains("Unterbrochen: Standardtuer"));
        } finally {
            System.setOut(prevOut);
        }
    }

    @Test
    public void testArbeitsQueueAbarbeitung() throws Exception {
        Holzbearbeitungs_Roboter rob = new Holzbearbeitungs_Roboter(0.001);
        rob.start();
        try {
            Standardtuer p = new Standardtuer();
            assertEquals(0, rob.gibQueueLaenge());
            rob.fuegeProduktHinzu(p);
            long deadline = System.currentTimeMillis() + 5000L;
            while (System.currentTimeMillis() < deadline && p.aktuellerZustand() != 2) {
                Thread.sleep(25L);
            }
            assertEquals("Produkt sollte FERTIG sein", 2, p.aktuellerZustand());
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
