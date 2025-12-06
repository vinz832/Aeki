 /**
 * Standardtuer Produkt.
  * 
  * 
  * Jedes Produkt befindet sich in einem gewissen Zustand wie z.B. 
        ○ Bestellt
        ○ In Produktion 
        ○ Fertiggestellt 
        ○ Transportvorbereitung
        ○ Versand 
    • Dieser wird in einer Instanzvariable abgespeichert.
    • sind Information vorhanden, die für alle Instanzen dieser Klassen gleich sind. Beispielsweise wären das
        ○  die Anzahl Holzeinheiten,
        ○  Anzahl Schrauben, 
        ○ Anzahl Türgriffe 
        ○ Verwendeter Lack 
Auch die Methoden, um auf diese Information zuzugreifen, gehören der Klasse an und nicht den einzelnen Instanzen.
 * @author  Moacir, Owen, Alexander, Mathieu, Vinzenz
 * @version 8.12.2025


 */
public class Standardtuer extends Produkt {
    
    //Anzahl Einheiten und Materialien die für eine Standardtür benötigt werden
    public static final int HOLZEINHEITEN = 5;
    public static final int SCHRAUBEN = 10;
    public static final int TUERGRIFFE = 1;
    public static final int FARBEINHEITEN = 1;
    public static final int KARTONEINHEITEN = 1;
    public static final int PRODUKTIONSZEIT = 3; // in Tagen (Beispiel)

    /**
     * Erzeugt eine Standardtuer (initial Zustand BESTELLT=0).
     */
    public Standardtuer() {
        super();
    }

    /**
     * Gibt die Anzahl Holzeinheiten zurück.
     * @return Anzahl Holzeinheiten
     */
    public static int gibHolzeinheiten() {
        return HOLZEINHEITEN;
    }

    /**
     * Gibt die Anzahl Schrauben zurück.
     * @return Anzahl Schrauben
     */
    public static int gibSchrauben() {
        return SCHRAUBEN;
    }

    /**
     * Gibt die Anzahl Türgriffe zurück.
     * @return Anzahl Türgriffe
     */
    public static int gibTuergriffe() {
        return TUERGRIFFE;
    }

    /**
     * Gibt die verwendeten Farbeinheiten (Lack) zurück.
     * @return Anzahl Farbeinheiten
     */
    public static int gibVerwendeterLack() {
        return FARBEINHEITEN;
    }
}
