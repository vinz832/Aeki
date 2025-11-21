 

/**
 * Premiumtuer mit Glas.
 */
public class Premiumtuer extends Produkt {
    public static final int HOLZEINHEITEN = 6;
    public static final int SCHRAUBEN = 12;
    public static final int GLASEINHEITEN = 2;
    public static final int TUERGRIFFE = 1;
    public static final int FARBEINHEITEN = 1;
    public static final int KARTONEINHEITEN = 1;
    public static final int PRODUKTIONSZEIT = 5; // in Tagen (Beispiel)

    public Premiumtuer() {
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
     * Gibt die Anzahl Glaseinheiten zurück.
     * @return Anzahl Glaseinheiten
     */
    public static int gibGlaseinheiten() {
        return GLASEINHEITEN;
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
