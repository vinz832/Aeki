 
/**
 * Beschreiben Sie hier die Klasse Bestellung.
 *     • verwaltet eine ArrayList, in der alle bestellten Produkte gespeichert
    • Enthält als Instanzvariablen:
        ○ Bestellbestätigung,
        ○ Beschaffungszeit,
        ○  Bestellnummer, 
        ○ wie auch die Anzahl bestellter Standardtüren und die Anzahl bestellter Premiumtüren.
        
    • Zu jeder Instanzvariablen muss jeweils eine Methode implementiert werden, um die Information abzufragen. Bei gewissen Instanzvariablen muss auch eine Methode vorhanden sein, um die Information in den Variablen zu ändern.

 * @author (Ihr Name) Owen, Alexander, Moacir, Mathieu, Vinzenz
 * @version (eine Versionsnummer oder ein Datum)
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repräsentiert eine Kundenbestellung.
 * Zeigt auch alle Bestellungen aus der Fabrik an.
 */
public class Bestellung {
    // Referenz zur Fabrik für Anzeige-Instanzen
    private Fabrik fabrikReferenz;

    private final List<Produkt> bestellteProdukte = new ArrayList<>();

    private boolean bestellBestaetigung;

    private int beschaffungsZeit;

    private final int bestellungsNr;

    private final int anzahlStandardTueren;

    private final int anzahlPremiumTueren;

    /**
     * Erstellt eine Bestellung-Instanz NUR zum Anzeigen von Bestellungen.
     * Diese Instanz erstellt KEINE neue Bestellung!
     * @param fabrik die Fabrik, deren Bestellungen angezeigt werden sollen
     * @return eine Anzeige-Instanz
     */
    public static Bestellung erstelleAnzeigeInstanz(Fabrik fabrik) {
        return new Bestellung(fabrik);
    }

    /**
     * Privater Konstruktor für Anzeige-Zwecke: Erstellt KEINE neue Bestellung.
     * Dieser Konstruktor dient nur zum Anzeigen von Bestellungen aus der Fabrik.
     * @param fabrik die Fabrik, deren Bestellungen angezeigt werden sollen
     */
    private Bestellung(Fabrik fabrik) {
        if (fabrik == null) {
            throw new IllegalArgumentException("Fabrik darf nicht null sein.");
        }
        this.fabrikReferenz = fabrik;
        // Keine echte Bestellung - nur für Anzeige
        this.bestellungsNr = 0;
        this.anzahlStandardTueren = 0;
        this.anzahlPremiumTueren = 0;
    }

    /**
     * Konstruktor: erstellt die Bestellung und legt die Produkte an.
     * @param bestellungsNr laufende Bestellnummer (>0)
     * @param anzahlStandardTueren >=0
     * @param anzahlPremiumTueren >=0
     */
    public Bestellung(int bestellungsNr, int anzahlStandardTueren, int anzahlPremiumTueren) {
        if (bestellungsNr <= 0) {
            throw new IllegalArgumentException("bestellungsNr muss > 0 sein.");
        }
        if (anzahlStandardTueren < 0 || anzahlPremiumTueren < 0) {
            throw new IllegalArgumentException("Anzahl der Tueren darf nicht negativ sein.");
        }
        this.bestellungsNr = bestellungsNr;
        this.anzahlStandardTueren = anzahlStandardTueren;
        this.anzahlPremiumTueren = anzahlPremiumTueren;

        for (int i = 0; i < anzahlStandardTueren; i++) {
            bestellteProdukte.add(new Standardtuer());
        }
        for (int i = 0; i < anzahlPremiumTueren; i++) {
            bestellteProdukte.add(new Premiumtuer());
        }
    }

    public void bestellungBestaetigen() {
        this.bestellBestaetigung = true;
    }

    public boolean gibBestellBestaetigung() {
        return bestellBestaetigung;
    }

    public void setzeBeschaffungsZeit(int tage) {
        if (tage < 0) {
            throw new IllegalArgumentException("Beschaffungszeit darf nicht negativ sein.");
        }
        this.beschaffungsZeit = tage;
    }

    public int gibBeschaffungsZeit() {
        return beschaffungsZeit;
    }

    public int gibBestellungsNr() {
        return bestellungsNr;
    }

    public int gibAnzahlStandardTueren() {
        return anzahlStandardTueren;
    }

    public int gibAnzahlPremiumTueren() {
        return anzahlPremiumTueren;
    }

    public List<Produkt> gibProdukte() {
        return Collections.unmodifiableList(bestellteProdukte);
    }

    @Override
    public String toString() {
        return String.format("#%d: %dx Standard, %dx Premium (Produkte: %d)",
                bestellungsNr, anzahlStandardTueren, anzahlPremiumTueren, bestellteProdukte.size());
    }

    /**
     * Zeigt alle Bestellungen aus der Fabrik an.
     * Diese Methode funktioniert nur bei Instanzen, die mit dem Fabrik-Konstruktor erstellt wurden.
     * Es werden KEINE neuen Bestellungen aufgegeben, sondern nur die vorhandenen angezeigt.
     */
    public void alleBestellungenAnzeigen() {
        if (fabrikReferenz == null) {
            System.out.println("Diese Bestellung-Instanz ist keine Anzeige-Instanz.");
            return;
        }
        
        List<Bestellung> alleBestellungen = fabrikReferenz.gibAlleBestellungen();
        
        if (alleBestellungen.isEmpty()) {
            System.out.println("Keine Bestellungen vorhanden.");
        } else {
            System.out.println("\n=== Alle Bestellungen aus der Fabrik ===");
            for (Bestellung b : alleBestellungen) {
                System.out.println(b.toString());
            }
            System.out.println("=== Gesamt: " + alleBestellungen.size() + " Bestellung(en) ===\n");
        }
    }

    /**
     * Gibt die Anzahl der Bestellungen zurück (nur für Anzeige-Instanzen).
     * @return Anzahl der Bestellungen oder 0 wenn keine Anzeige-Instanz
     */
    public int gibAnzahlAllerBestellungen() {
        if (fabrikReferenz == null) {
            return 0;
        }
        return fabrikReferenz.anzahlBestellungen();
    }
}
