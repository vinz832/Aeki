 
/**
 * Beschreiben Sie hier die Klasse ID Generator,
 * ID-Generator für Bestellnummern.

 * @author Owen, Mathieu, Alexander, Moacir, Vinzenz
 * @version 23.11.2025
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
