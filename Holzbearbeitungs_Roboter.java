/**
 * Roboter zur Holzbearbeitung.
 * Verarbeitet Standard- und Premiumtüren mit unterschiedlichen Bearbeitungszeiten.
 * Enthält testfreundliche Zeitskalierung: Standard = 600000ms, Premium = 1800000ms.
 */
public class Holzbearbeitungs_Roboter extends Roboter {
    // Basis-Zeiten in Millisekunden (Produktionsvorgabe)
    private static final long MS_STANDARD = 600_000L;   // 10 Minuten
    private static final long MS_PREMIUM  = 1_800_000L; // 30 Minuten

    // Zeitskalierung (1.0 = Echtzeit). Kann per Konstruktor oder System-Property gesetzt werden.
    private final double zeitSkalierung;

    /**
     * Erstellt den Roboter mit optionaler Zeitskalierung aus System-Property "aeki.time.scale".
     */
    public Holzbearbeitungs_Roboter() {
        this(leseZeitSkalierungAusProperty());
    }

    /**
     * Erstellt den Roboter mit expliziter Zeitskalierung.
     * @param zeitSkalierung 1.0 = Echtzeit; <1.0 beschleunigt; >1.0 verlangsamt
     */
    public Holzbearbeitungs_Roboter(double zeitSkalierung) {
        super();
        if (zeitSkalierung <= 0) {
            this.zeitSkalierung = 1.0;
        } else {
            this.zeitSkalierung = zeitSkalierung;
        }
    }

    private static double leseZeitSkalierungAusProperty() {
        try {
            String prop = System.getProperty("aeki.time.scale");
            if (prop == null) return 1.0;
            double val = Double.parseDouble(prop);
            return (val > 0) ? val : 1.0;
        } catch (Exception e) {
            return 1.0;
        }
    }

    private long skaliere(long basisMs) {
        double v = basisMs * zeitSkalierung;
        if (v < 0) v = 0;
        return (long) v;
    }

    /**
     * Produziert ein Produkt an der Holzbearbeitungsstation.
     *
     * @param p zu bearbeitendes Produkt (Standardtuer oder Premiumtuer)
     */
    @Override
    protected void produziereProdukt(Produkt p) {
        if (p == null) return;

        long sleepMs;
        if (p instanceof Standardtuer) {
            sleepMs = skaliere(MS_STANDARD);
        } else if (p instanceof Premiumtuer) {
            sleepMs = skaliere(MS_PREMIUM);
        } else {
            // Unbekannter Produkttyp – keine Wartezeit
            sleepMs = 0L;
        }

        // Markiere Start der Produktion
        p.zustandAendern(1); // IN_PRODUKTION
        System.out.println("[Holzbearb] Start: " + p.getClass().getSimpleName() + ", sleep=" + sleepMs + "ms");

        if (sleepMs > 0) {
            try {
                Thread.sleep(sleepMs);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                System.out.println("[Holzbearb] Unterbrochen: " + p.getClass().getSimpleName());
                return;
            }
        }

        // Markiere Ende der Station – bereit für nächsten Schritt
        p.zustandAendern(2); // FERTIG (für nächste Station bereit)
        System.out.println("[Holzbearb] Fertig: " + p.getClass().getSimpleName());
    }
}
