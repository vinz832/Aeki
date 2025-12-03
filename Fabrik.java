//moacir
//Alex
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
    private List<Bestellung> bestellungen;
    // Globales, gemeinsames Lager und Lieferant für alle Fabrik-Instanzen
    private static final Lager lager = Lager.getInstance();
    private static final Lieferant lieferant = new Lieferant();

    public Fabrik() {
        bestellungen = new ArrayList<>();
    }

    /**
     * Liefert die Lager-Instanz dieser Fabrik (für BlueJ-Inspektion/Verknüpfungen).
     */
    public Lager getLager() {
        return lager;
    }

    /**
     * Liefert die Lieferant-Instanz dieser Fabrik (für BlueJ-Inspektion/Verknüpfungen).
     */
    public Lieferant getLieferant() {
        return lieferant;
    }

    /**
     * Reserviert Material im globalen Lager und setzt Beschaffungszeit/Lieferzeit.
     * Wird beim Bestaetigen der Bestellung aufgerufen.
     */
    public void reserveMaterialFuer(Bestellung b) {
        int beschaffungsZeit = lager.gibBeschaffungsZeit(b);
        if (beschaffungsZeit == 2) {
            // Material war knapp – nachbestellen
            lager.lagerAuffuellen(lieferant);
        }
        int produktionsZeit = b.gibAnzahlStandardTueren() * Standardtuer.PRODUKTIONSZEIT
                            + b.gibAnzahlPremiumTueren() * Premiumtuer.PRODUKTIONSZEIT;
        int lieferZeit = produktionsZeit + beschaffungsZeit + 1;
        b.setzeBeschaffungsZeit(beschaffungsZeit);
        b.setzeLieferZeit(lieferZeit);
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
     * @return die erstellte Bestellung oder null bei Fehler
     */
    public Bestellung bestellungAufgeben(int standardTueren, int premiumTueren) {
        if (standardTueren < 0 || premiumTueren < 0) {
            System.out.println("Fehler: Mengen dürfen nicht negativ sein.");
            return null;
        }
        if (standardTueren == 0 && premiumTueren == 0) {
            System.out.println("Fehler: Es wurden keine Produkte bestellt.");
            return null;
        }

        int id = IdGenerator.nextOrderId();
        Bestellung b = new Bestellung(id, standardTueren, premiumTueren, this);

        // Vorläufige Lieferzeit ohne Materialreservierung (wird bei Bestaetigung gesetzt)
        int produktionsZeit = standardTueren * Standardtuer.PRODUKTIONSZEIT
                            + premiumTueren * Premiumtuer.PRODUKTIONSZEIT;
        b.setzeBeschaffungsZeit(0);
        b.setzeLieferZeit(produktionsZeit + 1);

        bestellungen.add(b);
        System.out.println(b.toString());
        return b;
    }

    /**
     * Gibt alle Bestellungen kompakt auf der Konsole aus.
     * Jede Bestellung wird mit ihrer Bestellnummer und Produktanzahl dargestellt.
     */
    public void bestellungenAusgeben() {
        for (int i = 0; i < bestellungen.size(); i++) {
            Bestellung b = bestellungen.get(i);
            System.out.println(b.toString());
            System.out.println("Beschaffungszeit: " + b.gibBeschaffungsZeit() + " Tag(e)");
            System.out.println("Lieferzeit: " + b.gibLieferZeit() + " Tag(e)");
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
