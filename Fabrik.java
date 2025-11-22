 
/**
 * Beschreiben Sie hier die Klasse Fabrik.
 * 
    • nimmt Bestellungen entgegen und gibt diese auf der Konsole aus. 
    • Enthält eine ArrayList in der alle Bestellungen gespeichert werden 
    • enthält die Methode main, die den Einstieg in das Programm ermöglicht

 * @author Moacir, Owen, Mathieu, Alexander, Vinzenz
 * @version (23.11.2025)
 */
 


import java.util.ArrayList;
import java.util.List;

/**
 * Fabrik nimmt Bestellungen entgegen und gibt sie auf der Konsole aus.
 * Funktion: Entgegennehmen neuer Bestellungen (mit Validierung)
 * Eine Fabrik erzeugt selbst keine Produkte – das geschieht in der Klasse Bestellung,
 * sobald bestellungAufgeben(...) aufgerufen wird.
 */
public class Fabrik {
    
    //Interne Liste aller Besttelungenm die in der Fabrik aufgegeben wurden
    private List<Bestellung> bestellungen;

    //Erstellt eine neue leere Fabrik
    public Fabrik() {
        bestellungen = new ArrayList<>();
    }

    /**
     * Demo-Programm: legt 2-3 Bestellungen an und gibt sie aus.
     * @param args keine speziellen Argumente erforderlich
     */
    public static void main(String[] args) {
        Fabrik fabrik = new Fabrik();
        
        // Bestellungen in der Fabrik aufgeben
        fabrik.bestellungAufgeben(2, 1);
        fabrik.bestellungAufgeben(0, 2);
        fabrik.bestellungenAusgeben();
        
        // Bestellung-Instanz NUR zum Anzeigen erstellen (keine echte Bestellung!)
        System.out.println("\n--- Bestellungen über Bestellung-Anzeige anzeigen ---");
        Bestellung anzeige = new Bestellung();
        anzeige.setztFabrik(fabrik);
        anzeige.alleBestellungenAnzeigen();
    }

    /**
     * Aufgabe einer Bestellung.
     * Validierung:
     * - Mengen dürfen nicht negativ sein
     * - Mindestens ein Produkt muss bestellt werden
     *
     * Bei erfolgreicher Bestellung:
     * - wird eine eindeutige Bestellnummer über den IdGenerator vergeben
     * - werden die entsprechenden Produkte in der Bestellung erzeugt
     * - wird die Bestellung zur internen Liste hinzugefügt
     * 
     * @param standardTueren Anzahl Standardtüren (>=0)
     * @param premiumTueren Anzahl Premiumtüren (>=0)
     */
    public void bestellungAufgeben(int standardTueren, int premiumTueren) {
        if (standardTueren < 0 || premiumTueren < 0) {
            System.out.println("Fehler: Mengen dürfen nicht negativ sein.");
            return;
        }
        if (standardTueren == 0 && premiumTueren == 0) {
            System.out.println("Fehler: Es wurden keine Produkte bestellt.");
            return;
        }

        int id = IdGenerator.nextOrderId();
        Bestellung b = new Bestellung(id, standardTueren, premiumTueren);
        bestellungen.add(b);
        
        //Konsolausgabe der neu angelegten Bestellung
        System.out.println(b.toString());
    }

    /**
     * Gibt alle Bestellungen kompakt auf der Konsole aus.
     * Jede Bestellung wird mit ihrer Bestellnummer und Produktanzahl dargestellt.
     */
    public void bestellungenAusgeben() {
        for (int i = 0; i < bestellungen.size(); i++) {
            System.out.println(bestellungen.get(i).toString());
        }
    }

    // Hilfsmethode für Tests: Anzahl Bestellungen
    //Liefer die Anzahl aller bisher aufgegebenen Bestellungen dieser Fabrik
    //@return Anzahl der Bestellungen
    public int anzahlBestellungen() {
        return bestellungen.size();
    }

    /**
     * Gibt die Liste aller Bestellungen zurück.
     * @return unveränderbare Liste aller Bestellungen
     */
    public List<Bestellung> gibAlleBestellungen() {
        return new ArrayList<>(bestellungen);
    }
}
