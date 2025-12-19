/**
 * Beschreibt den Lieferanten.
 *
 * Neuerungen:
 * - Der Lieferant arbeitet asynchron als Thread und liefert erst nach 2 Tagen
 *   (simuliert: 48 Sekunden bei 1h = 1s) Material an das Lager.
 * - Der Hauptprogrammablauf wird nicht blockiert (start(), nicht run()).
 */
public class Lieferant extends Thread {
    // Zeitbeschleunigung: 2 Tage = 48 Stunden = 48 Sekunden
    private static final long LIEFER_VERZOEG_ERSEK = 48_000L;

    // Ziel-Lager und zu liefernde Mengen (pro Lieferung/Thread-Instanz)
    private final Lager zielLager;
    private final int holz;
    private final int schrauben;
    private final int farbe;
    private final int karton;
    private final int glas;

    /**
     * Leerer Standard-Konstruktor: erlaubt weiterhin die Nutzung als
     * BlueJ-Objekt/Factory, ohne selbst gestartet zu werden.
     */
    public Lieferant() {
        this.zielLager = null;
        this.holz = 0;
        this.schrauben = 0;
        this.farbe = 0;
        this.karton = 0;
        this.glas = 0;
    }

    /**
     * Konstruktor für eine konkrete, verzögerte Lieferung an ein Lager.
     *
     * @param lager Ziel-Lager, das aufgefüllt werden soll
     * @param holz zu liefernde Holzeinheiten
     * @param schrauben zu liefernde Schrauben
     * @param farbe zu liefernde Farbeinheiten
     * @param karton zu liefernde Kartoneinheiten
     * @param glas zu liefernde Glaseinheiten
     */
    public Lieferant(Lager lager, int holz, int schrauben, int farbe, int karton, int glas) {
        this.zielLager = lager;
        this.holz = holz;
        this.schrauben = schrauben;
        this.farbe = farbe;
        this.karton = karton;
        this.glas = glas;
    }

    /**
     * Factory-Methode im bisherigen API-Stil: Startet eine asynchrone Lieferung.
     * Es wird intern eine neue Thread-Instanz erstellt und gestartet.
     *
     * @param lager Ziel-Lager, das aufgefüllt werden soll
     * @param holz zu liefernde Holzeinheiten
     * @param schrauben zu liefernde Schrauben
     * @param farbe zu liefernde Farbeinheiten
     * @param karton zu liefernde Kartoneinheiten
     * @param glas zu liefernde Glaseinheiten
     */
    public void bestellungAufgebenFuerMaterial(Lager lager,
                                               int holz,
                                               int schrauben,
                                               int farbe,
                                               int karton,
                                               int glas) {
        Lieferant job = new Lieferant(lager, holz, schrauben, farbe, karton, glas);
        job.start();
    }

    /**
     * Führt die verzögerte Lieferung aus. Bei Unterbrechung wird der
     * Interrupt-Status wiederhergestellt und die Lieferung abgebrochen.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(LIEFER_VERZOEG_ERSEK);
        } catch (InterruptedException ie) {
            // Saubere Unterbrechungsbehandlung: Flag wieder setzen und beenden
            Thread.currentThread().interrupt();
            return;
        }

        if (zielLager != null) {
            if (holz > 0) zielLager.addHolz(holz);
            if (schrauben > 0) zielLager.addSchrauben(schrauben);
            if (farbe > 0) zielLager.addFarbe(farbe);
            if (karton > 0) zielLager.addKarton(karton);
            if (glas > 0) zielLager.addGlas(glas);
        }
    }
}
