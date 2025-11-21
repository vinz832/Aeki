 
/**
 * Beschreiben Sie hier die Klasse ID Generator,
 * ID-Generator für Bestellnummern.

 * @author (Ihr Name) Owen, Mathieu, Alexander, Moacir, Vinzenz
 * @version (eine Versionsnummer oder ein Datum)
 */

public final class IdGenerator {
    private static int current = 1;

    private IdGenerator() {
        // no instance
    }

    public static synchronized int nextOrderId() {
        return current++;
    }
}
