/**
 * Klasse zum Anzeigen aller Bestellungen aus der Fabrik.
 * Diese Klasse erstellt KEINE neuen Bestellungen, sondern zeigt nur vorhandene an.
 * 
 * @author Owen, Alexander, Moacir, Mathieu, Vinzenz
 * @version 1.0
 */
public class BestellungenAnzeige {
    private Fabrik fabrik;
    
    /**
     * Konstruktor - verbindet die Anzeige mit einer Fabrik.
     * @param fabrik die Fabrik, deren Bestellungen angezeigt werden sollen
     */
    public BestellungenAnzeige(Fabrik fabrik) {
        if (fabrik == null) {
            throw new IllegalArgumentException("Fabrik darf nicht null sein.");
        }
        this.fabrik = fabrik;
    }
    
    /**
     * Zeigt alle Bestellungen aus der Fabrik an.
     * Es werden KEINE neuen Bestellungen erstellt.
     */
    public void alleBestellungenAnzeigen() {
        System.out.println("\n=== Alle Bestellungen aus der Fabrik ===");
        fabrik.bestellungenAusgeben();
        System.out.println("=== Gesamt: " + fabrik.anzahlBestellungen() + " Bestellung(en) ===\n");
    }
    
    /**
     * Gibt die Anzahl der Bestellungen zurück.
     * @return Anzahl der Bestellungen in der Fabrik
     */
    public int gibAnzahlBestellungen() {
        return fabrik.anzahlBestellungen();
    }
}
