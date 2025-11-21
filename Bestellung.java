 
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
    // Referenz zur Fabrik, um alle Bestellungen anzuzeigen
    private static Fabrik fabrikReferenz;

    private final List<Produkt> bestellteProdukte = new ArrayList<>();

    private boolean bestellBestaetigung;

    private int beschaffungsZeit;

    private final int bestellungsNr;

    private final int anzahlStandardTueren;

    private final int anzahlPremiumTueren;

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
     * Setzt die Fabrik-Referenz, um auf alle Bestellungen zugreifen zu können.
     * @param fabrik die Fabrik-Instanz
     */
    public static void setzeFabrik(Fabrik fabrik) {
        fabrikReferenz = fabrik;
    }

    /**
     * Zeigt alle Bestellungen aus der Fabrik an.
     * Es werden KEINE neuen Bestellungen aufgegeben, sondern nur die vorhandenen angezeigt.
     */
    public static void alleBestellungenAnzeigen() {
        if (fabrikReferenz == null) {
            System.out.println("Keine Fabrik gesetzt. Bitte zuerst Bestellung.setzeFabrik() aufrufen.");
            return;
        }
        
        List<Bestellung> alleBestellungen = fabrikReferenz.gibAlleBestellungen();
        
        if (alleBestellungen.isEmpty()) {
            System.out.println("Keine Bestellungen vorhanden.");
        } else {
            System.out.println("=== Alle Bestellungen aus der Fabrik ===");
            for (Bestellung b : alleBestellungen) {
                System.out.println(b.toString());
            }
            System.out.println("=== Gesamt: " + alleBestellungen.size() + " Bestellung(en) ===");
        }
    }
}
