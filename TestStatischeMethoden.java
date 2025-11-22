/**
 * Testprogramm für die statischen Methoden der Produktklassen.
 * Diese Klasse dient NICHT als JUnit-Test, sondern als kleines Konsolen-
 * Testprogramm, das in BlueJ ausgeführt werden kann. 
 */
public class TestStatischeMethoden {
    // Instanzvariablen für BlueJ-Verbindungen
    private Standardtuer standardBeispiel;
    private Premiumtuer premiumBeispiel;
    
    /**
     * Konstruktor - erstellt Beispiel-Instanzen
     */
    public TestStatischeMethoden() {
        standardBeispiel = new Standardtuer();
        premiumBeispiel = new Premiumtuer();
    }
    
    /**
     * Testet und zeigt alle Material-Informationen an.
     * Gibt alle Materialeigenschaften von Standardtür und Premiumtür
     * über die statischen Getter-Methoden aus.
     */
    public void materialInformationenAnzeigen() {
        System.out.println("=== Standardtür - Material-Informationen ===");
        System.out.println("Holzeinheiten: " + Standardtuer.gibHolzeinheiten());
        System.out.println("Schrauben: " + Standardtuer.gibSchrauben());
        System.out.println("Türgriffe: " + Standardtuer.gibTuergriffe());
        System.out.println("Verwendeter Lack: " + Standardtuer.gibVerwendeterLack());
        
        System.out.println("\n=== Premiumtür - Material-Informationen ===");
        System.out.println("Holzeinheiten: " + Premiumtuer.gibHolzeinheiten());
        System.out.println("Schrauben: " + Premiumtuer.gibSchrauben());
        System.out.println("Glaseinheiten: " + Premiumtuer.gibGlaseinheiten());
        System.out.println("Türgriffe: " + Premiumtuer.gibTuergriffe());
        System.out.println("Verwendeter Lack: " + Premiumtuer.gibVerwendeterLack());
    }
    
      /**
     * Startpunkt des Testprogramms.
     * Führt eine Ausgabe aller Materialwerte durch.
     */
    public static void main(String[] args) {
        TestStatischeMethoden test = new TestStatischeMethoden();
        test.materialInformationenAnzeigen();
    }
}
