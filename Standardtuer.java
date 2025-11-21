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
 * @author (Ihr Name) Moacir, Owen, Alexander, Mathieu, Vinzenz
 * @version (eine Versionsnummer oder ein Datum)


 */
public class Standardtuer extends Produkt {
    public static final int HOLZEINHEITEN = 5;
    public static final int SCHRAUBEN = 10;
    public static final int FARBEINHEITEN = 1;
    public static final int KARTONEINHEITEN = 1;
    public static final int PRODUKTIONSZEIT = 3; // in Tagen (Beispiel)

    /**
     * Erzeugt eine Standardtuer (initial Zustand BESTELLT=0).
     */
    public Standardtuer() {
        super();
    }
}
