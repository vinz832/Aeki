import java.util.LinkedList;
import java.util.List;


/**
 * Beschreiben Sie hier die Klasse Produkt.
 * Können entweder Standarttüren oder Premiumtüren sein. 
 * Abstrakte Basisklasse fuer Produkte.

 * @author Owen, Mathieu, Alexander, Moacir, Vinzenz
 * @version 8.12.2025
 */
public abstract class Produkt {
    // Produktionsstationen/Roboter, denen dieses Produkt zugeordnet wird
    private LinkedList<Roboter> produktionsStationen = new LinkedList<Roboter>();
    /**
     * Zustandswerte:
     * 0 = BESTELLT
     * 1 = IN_PRODUKTION
     * 2 = FERTIG
     * 3 = TRANSPORTVORBEREITUNG
     * 4 = VERSAND
     */
    protected int zustand = 0;

    /**
     * Ändert den Zustand des Produkts.
     * Negative Werte werden nicht akzeptiert
     * @param neuerZustand neuer Zustand (siehe Konstanten in Dokumentation)
     */
    public void zustandAendern(int neuerZustand) {
        if (neuerZustand < 0) {
            System.out.println("Fehler: Zustand darf nicht negativ sein: " + neuerZustand);
            return;
        }
        this.zustand = neuerZustand;
    }

    /**
     * Liefert den aktuellen Zustand.
     * @return aktueller Zustand
     */
    public int aktuellerZustand() {
        return zustand;
    }

    /**
     * Liefert den aktuellen Zustand als lesbaren Text.
     * @return aktueller Zustand als Text
     */
    public String zustandAlsText() {
        switch (zustand) {
            case 0: return "BESTELLT";
            case 1: return "IN_PRODUKTION";
            case 2: return "FERTIG";
            case 3: return "TRANSPORTVORBEREITUNG";
            case 4: return "VERSAND";
            default: return "UNBEKANNT";
        }
    }
    /**
     * Fügt eine Produktionsstation (Roboter) zur Bearbeitungsliste hinzu.
     *
     * @param roboter Roboter/Station, die dieses Produkt bearbeiten soll
     */
    public void produktionsStationHinzufuegen(Roboter roboter) {
        if (roboter == null) {
            System.out.println("Hinweis: Null-Roboter wird ignoriert.");
            return;
        }
        produktionsStationen.addLast(roboter);
    }

    /**
     * Liefert die aktuelle Liste der zugewiesenen Produktionsstationen.
     */
    public List<Roboter> gibProduktionsStationen() {
        return produktionsStationen;
    }
    /**
     * Leitet das Produkt an die nächste Produktionsstation weiter.
     * Nimmt den nächsten Roboter aus der Liste und uebergibt dieses Produkt
     * an dessen Warteschlange.
     * @return true, wenn ein Roboter gefunden wurde; sonst false
     */
    public boolean naechsteProduktionsStation() {
        if (produktionsStationen.isEmpty()) {
            System.out.println("Keine weitere Produktionsstation vorhanden.");
            return false;
        }
        Roboter rob = produktionsStationen.pollFirst();
        if (rob == null) {
            System.out.println("Unerwartet: Roboter war null.");
            return false;
        }
        System.out.println("Produkt an Roboter uebergeben: " + rob.getClass().getSimpleName());
        rob.fuegeProduktHinzu(this);
        return true;
    }
}
