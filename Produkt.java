 
/**
 * Beschreiben Sie hier die Klasse Produkt.
 * Können entweder Standarttüren oder Premiumtüren sein. 
 * Abstrakte Basisklasse fuer Produkte.

 * @author (Ihr Name) Owen, Mathieu, Alexander, Moacir, Vinzenz
 * @version (eine Versionsnummer oder ein Datum)
 */
public abstract class Produkt {
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
     * Aendert den Zustand des Produkts.
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
}
