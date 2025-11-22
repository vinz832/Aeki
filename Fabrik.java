 
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
 */
public class Fabrik {
    private List<Bestellung> bestellungen;

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
        System.out.println(b.toString());
    }

    /**
     * Gibt alle Bestellungen kompakt auf der Konsole aus.
     */
    public void bestellungenAusgeben() {
        for (int i = 0; i < bestellungen.size(); i++) {
            System.out.println(bestellungen.get(i).toString());
        }
    }

    // Hilfsmethode für Tests: Anzahl Bestellungen
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
