 
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
import java.util.List;

/**
 * Repräsentiert eine Kundenbestellung.
 * Zeigt auch alle Bestellungen aus der Fabrik an.
 */
public class Bestellung {
    // Referenz zur Fabrik für Anzeige-Instanzen
    private Fabrik fabrikReferenz;

    private List<Produkt> bestellteProdukte;

    private boolean bestellBestaetigung;

    private int beschaffungsZeit;

    private int bestellungsNr;

    private int anzahlStandardTueren;

    private int anzahlPremiumTueren;

    /**
     * Konstruktor für Anzeige-Zwecke: Erstellt KEINE neue Bestellung.
     * Verwende setztFabrik() um die Fabrik zu setzen, dann alleBestellungenAnzeigen().
     */
    public Bestellung() {
        this.bestellteProdukte = new ArrayList<>();
        this.bestellungsNr = 0;
        this.anzahlStandardTueren = 0;
        this.anzahlPremiumTueren = 0;
        this.fabrikReferenz = null;
    }

    /**
     * Setzt die Fabrik für diese Anzeige-Instanz.
     * @param fabrik die Fabrik, deren Bestellungen angezeigt werden sollen
     */
    public void setztFabrik(Fabrik fabrik) {
        if (fabrik == null) {
            System.out.println("Fehler: Fabrik darf nicht null sein.");
            return;
        }
        this.fabrikReferenz = fabrik;
    }

    /**
     * Konstruktor: erstellt die Bestellung und legt die Produkte an.
     * @param bestellungsNr laufende Bestellnummer (>0)
     * @param anzahlStandardTueren >=0
     * @param anzahlPremiumTueren >=0
     */
    public Bestellung(int bestellungsNr, int anzahlStandardTueren, int anzahlPremiumTueren) {
        this.bestellteProdukte = new ArrayList<>();
        
        if (bestellungsNr <= 0) {
            System.out.println("Fehler: bestellungsNr muss > 0 sein.");
            this.bestellungsNr = 1;
        } else {
            this.bestellungsNr = bestellungsNr;
        }
        
        if (anzahlStandardTueren < 0 || anzahlPremiumTueren < 0) {
            System.out.println("Fehler: Anzahl der Tueren darf nicht negativ sein.");
            this.anzahlStandardTueren = 0;
            this.anzahlPremiumTueren = 0;
        } else {
            this.anzahlStandardTueren = anzahlStandardTueren;
            this.anzahlPremiumTueren = anzahlPremiumTueren;
        }

        for (int i = 0; i < this.anzahlStandardTueren; i++) {
            bestellteProdukte.add(new Standardtuer());
        }
        for (int i = 0; i < this.anzahlPremiumTueren; i++) {
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
            System.out.println("Fehler: Beschaffungszeit darf nicht negativ sein.");
            return;
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
        return bestellteProdukte;
    }

    public String toString() {
        return "#" + bestellungsNr + ": " + anzahlStandardTueren + "x Standard, " + 
               anzahlPremiumTueren + "x Premium (Produkte: " + bestellteProdukte.size() + ")";
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
        
        if (alleBestellungen.size() == 0) {
            System.out.println("Keine Bestellungen vorhanden.");
        } else {
            System.out.println("\n=== Alle Bestellungen aus der Fabrik ===");
            for (int i = 0; i < alleBestellungen.size(); i++) {
                System.out.println(alleBestellungen.get(i).toString());
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
