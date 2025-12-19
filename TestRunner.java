import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Minimaler JUnit-Runner für Fabrik-Tests.
 * Führt die vorhandene Testklasse `FabrikTest` aus und fasst das Ergebnis zusammen.
 */
public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Starte JUnit-Tests: FabrikTest, ProduktionsManagerTest, HolzbearbeitungsRoboterTest, LagerVerbrauchNachbestellungTest");
        Result result = JUnitCore.runClasses(
            FabrikTest.class,
            ProduktionsManagerTest.class,
            HolzbearbeitungsRoboterTest.class,
            LagerVerbrauchNachbestellungTest.class
        );
        for (Failure failure : result.getFailures()) {
            System.out.println("FAIL: " + failure.toString());
        }
        System.out.println("Erfolgreich: " + result.getRunCount() + ", Fehler: " + result.getFailureCount());
        System.out.println("Dauer (ms): " + result.getRunTime());
        System.out.println(result.wasSuccessful() ? "ALLE TESTS OK" : "TESTS FEHLGESCHLAGEN");
        // Beende die JVM explizit, damit keine verbleibenden Nicht-Daemon-Threads (z.B. PM/Roboter)
        // die Testausführung am sauberen Terminieren hindern oder die Konsole fluten.
        System.exit(result.wasSuccessful() ? 0 : 1);
    }
}