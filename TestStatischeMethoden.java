/**
 * Testprogramm für die statischen Methoden der Produktklassen.
 */
public class TestStatischeMethoden {
    public static void main(String[] args) {
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
}
