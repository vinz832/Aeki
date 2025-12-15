
/**
 * Beschreiben Sie hier die Klasse Lager 
    • Beinhaltet die Information zu den maximal lagerbaren Materialeinheiten
        ○ Diese Informationen sind in den Klassenvariablen MAXHOLZEINHEITEN, MAXSCHRAUBEN, MAXFARBEINHEITEN, MAXKARTONEINHEITEN und MAXGLASEINHEITEN gespeichert.
    
Neue Methode in der Klasse Lager:

gibBeschaffungsZeit
    • erhält als Parameter eine Kundenbestellung und liefert 0 Tage zurück, wenn alle Materialien für die Produktion aller bestellter Produkte vorhanden sind
    • Tage, wenn das Material beim Lieferanten nachbestellt werden muss.
    • Dafür muss die Liste mit allen Produkten der Bestellung durchsucht und die Anzahl benötigter Materialien ausgerechnet werden. 
    
lagerAuffuellen
    • Diese Methode bestellt fehlende Produkte beim Lieferanten nach und füllt nach Erhalt das Lager wieder auf.

lagerBestandAusgeben
Diese Methode druckt die im Lager vorhandenen Materialeinheiten auf die Konsole aus.

 * @author Owen, Matthieu, Alexander, Moacir, Vinzenz
 * @version 08.12.2025
 */

import java.util.List;

/**
 * Lager verwaltet Materialbestände und ermittelt Beschaffungszeiten.
 * Es handelt sich um eine zentrale Instanz, die von der Fabrik für alle
 * Bestellungen genutzt wird.
 */
public class Lager {
    // Singleton instance to ensure a single global stock source
    private static final Lager INSTANCE = new Lager();
    private static final int MAXHOLZEINHEITEN = 100;
    private static final int MAXSCHRAUBEN = 500;
    private static final int MAXFARBEINHEITEN = 200;
    private static final int MAXKARTONEINHEITEN = 200;
    private static final int MAXGLASEINHEITEN = 100;

    private int holz;
    private int schrauben;
    private int farbe;
    private int karton;
    private int glas;
    
    // Private constructor initializes the stock to max values
       /**
     * Privater Konstruktor: initialisiert das Lager mit maximalen Anfangsbeständen.
     * Dies unterstützt das Singleton-Muster.
     */
    private Lager() {
        holz = MAXHOLZEINHEITEN;
        schrauben = MAXSCHRAUBEN;
        farbe = MAXFARBEINHEITEN;
        karton = MAXKARTONEINHEITEN;
        glas = MAXGLASEINHEITEN;
    }

  
     
    /**
     * Liefert die Singleton-Instanz des Lagers.
     * @return globale Lagerinstanz
     */
    public static Lager getInstance() {
        return INSTANCE;
    }

    /**
     * Ermittelt die Beschaffungszeit für eine Bestellung basierend auf aktuellen Beständen.
     * Optional: reserviert Material aus dem Lager.
     * @return 0 wenn genug Material vorhanden, sonst 2
     */
    public synchronized int gibBeschaffungsZeit(Bestellung bestellung) {
        List<Produkt> produkte = bestellung.gibProdukte();

        int bedarfHolz = 0;
        int bedarfSchrauben = 0;
        int bedarfFarbe = 0;
        int bedarfKarton = 0;
        int bedarfGlas = 0;

        for (Produkt p : produkte) {
            if (p instanceof Standardtuer) {
                bedarfHolz += Standardtuer.gibHolzeinheiten();
                bedarfSchrauben += Standardtuer.gibSchrauben();
                bedarfFarbe += Standardtuer.FARBEINHEITEN;
                bedarfKarton += Standardtuer.KARTONEINHEITEN;
            } else if (p instanceof Premiumtuer) {
                bedarfHolz += Premiumtuer.gibHolzeinheiten();
                bedarfSchrauben += Premiumtuer.gibSchrauben();
                bedarfFarbe += Premiumtuer.FARBEINHEITEN;
                bedarfKarton += Premiumtuer.KARTONEINHEITEN;
                bedarfGlas += Premiumtuer.gibGlaseinheiten();
            }
        }

        boolean genugMaterial =
            bedarfHolz <= holz &&
            bedarfSchrauben <= schrauben &&
            bedarfFarbe <= farbe &&
            bedarfKarton <= karton &&
            bedarfGlas <= glas;

        if (genugMaterial) {
            // Reserviere Material
            holz -= bedarfHolz;
            schrauben -= bedarfSchrauben;
            farbe -= bedarfFarbe;
            karton -= bedarfKarton;
            glas -= bedarfGlas;
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * Prüft die Beschaffungszeit, ohne Material zu reservieren.
     * @return 0 wenn genug Material vorhanden, sonst 2
     */
    public synchronized int pruefeBeschaffungsZeitOhneReservierung(Bestellung bestellung) {
        List<Produkt> produkte = bestellung.gibProdukte();

        int bedarfHolz = 0;
        int bedarfSchrauben = 0;
        int bedarfFarbe = 0;
        int bedarfKarton = 0;
        int bedarfGlas = 0;

        for (Produkt p : produkte) {
            if (p instanceof Standardtuer) {
                bedarfHolz += Standardtuer.gibHolzeinheiten();
                bedarfSchrauben += Standardtuer.gibSchrauben();
                bedarfFarbe += Standardtuer.FARBEINHEITEN;
                bedarfKarton += Standardtuer.KARTONEINHEITEN;
            } else if (p instanceof Premiumtuer) {
                bedarfHolz += Premiumtuer.gibHolzeinheiten();
                bedarfSchrauben += Premiumtuer.gibSchrauben();
                bedarfFarbe += Premiumtuer.FARBEINHEITEN;
                bedarfKarton += Premiumtuer.KARTONEINHEITEN;
                bedarfGlas += Premiumtuer.gibGlaseinheiten();
            }
        }

        boolean genugMaterial =
            bedarfHolz <= holz &&
            bedarfSchrauben <= schrauben &&
            bedarfFarbe <= farbe &&
            bedarfKarton <= karton &&
            bedarfGlas <= glas;

        return genugMaterial ? 0 : 2;
    }

    /**
     * Füllt das Lager durch Bestellung beim Lieferanten wieder auf.
     */
    public synchronized void lagerAuffuellen(Lieferant lieferant) {
        lieferant.bestellungAufgebenFuerMaterial(this,
            MAXHOLZEINHEITEN - holz,
            MAXSCHRAUBEN - schrauben,
            MAXFARBEINHEITEN - farbe,
            MAXKARTONEINHEITEN - karton,
            MAXGLASEINHEITEN - glas);
    }

    /** Wird vom Lieferanten benutzt, um Bestände zu erhöhen. */
    synchronized void addHolz(int menge) { holz = Math.min(MAXHOLZEINHEITEN, holz + menge); }
    synchronized void addSchrauben(int menge) { schrauben = Math.min(MAXSCHRAUBEN, schrauben + menge); }
    synchronized void addFarbe(int menge) { farbe = Math.min(MAXFARBEINHEITEN, farbe + menge); }
    synchronized void addKarton(int menge) { karton = Math.min(MAXKARTONEINHEITEN, karton + menge); }
    synchronized void addGlas(int menge) { glas = Math.min(MAXGLASEINHEITEN, glas + menge); }

    public void lagerBestandAusgeben() {
        System.out.println("=== Lagerbestand ===");
        System.out.println("Holz: " + holz);
        System.out.println("Schrauben: " + schrauben);
        System.out.println("Farbe: " + farbe);
        System.out.println("Karton: " + karton);
        System.out.println("Glas: " + glas);
        System.out.println();
    }

    /**
     * BlueJ-Helfer: Berechnet die Beschaffungszeit direkt aus dem Lager,
     * ohne dass eine Bestellung übergeben werden muss.
     * Es wird intern eine temporäre Bestellung erzeugt und die vorhandene
     * Methode gibBeschaffungsZeit(...) genutzt. Bei genügend Material
     * wird Material auch reserviert.
     * @param standardTueren Anzahl Standardtüren
     * @param premiumTueren Anzahl Premiumtüren
     * @return 0 bei genügend Material, sonst 2
     */
    public int gibBeschaffungsZeitFuer(int standardTueren, int premiumTueren) {
        int tmpId = IdGenerator.nextOrderId();
        Bestellung tmp = new Bestellung(tmpId, standardTueren, premiumTueren);
        return gibBeschaffungsZeit(tmp);
    }

    /**
     * BlueJ-Helfer: Füllt das Lager ohne Parameter wieder auf.
     * Intern wird ein Lieferant erzeugt und die vorhandene Auffülllogik verwendet.
     */
    public void lagerAuffuellenOhneParameter() {
        Lieferant lieferant = new Lieferant();
        lagerAuffuellen(lieferant);
    }

    /**
     * Produktionsverbrauch: Zieht Material bei Produktionsstart einer Bestellung ab
     * und triggert bei Engpässen eine Nachbestellung (asynchron über Lieferant).
     * Verbrauchsregeln pro Produkt:
     *  - Standardtuer: Holz 2, Schrauben 10, Farbe 2, Karton 1
     *  - Premiumtuer:  Holz 4, Schrauben 5,  Glas 5,  Farbe 1, Karton 5
     */
    public synchronized void verbraucheMaterialFuer(Bestellung bestellung) {
        List<Produkt> produkte = bestellung.gibProdukte();

        int bedarfHolz = 0;
        int bedarfSchrauben = 0;
        int bedarfFarbe = 0;
        int bedarfKarton = 0;
        int bedarfGlas = 0;

        for (int i = 0; i < produkte.size(); i++) {
            Produkt p = produkte.get(i);
            if (p instanceof Standardtuer) {
                bedarfHolz += 2;
                bedarfSchrauben += 10;
                bedarfFarbe += 2;
                bedarfKarton += 1;
            } else if (p instanceof Premiumtuer) {
                bedarfHolz += 4;
                bedarfSchrauben += 5;
                bedarfFarbe += 1;
                bedarfKarton += 5;
                bedarfGlas += 5;
            }
        }

        int holzVorher = holz;
        int schraubenVorher = schrauben;
        int farbeVorher = farbe;
        int kartonVorher = karton;
        int glasVorher = glas;

        // Tatsächlich abziehbare Mengen (nicht unter 0)
        int abzugHolz = Math.min(holz, bedarfHolz);
        int abzugSchrauben = Math.min(schrauben, bedarfSchrauben);
        int abzugFarbe = Math.min(farbe, bedarfFarbe);
        int abzugKarton = Math.min(karton, bedarfKarton);
        int abzugGlas = Math.min(glas, bedarfGlas);

        holz -= abzugHolz;
        schrauben -= abzugSchrauben;
        farbe -= abzugFarbe;
        karton -= abzugKarton;
        glas -= abzugGlas;

        System.out.println("[Lager] Verbrauch für Bestellung #" + bestellung.gibBestellungsNr()
                + ": Holz-" + abzugHolz + ", Schrauben-" + abzugSchrauben
                + ", Farbe-" + abzugFarbe + ", Karton-" + abzugKarton
                + ", Glas-" + abzugGlas + ".");

        boolean mangel = (bedarfHolz > holzVorher) || (bedarfSchrauben > schraubenVorher)
                       || (bedarfFarbe > farbeVorher) || (bedarfKarton > kartonVorher)
                       || (bedarfGlas > glasVorher);
        if (mangel) {
            System.out.println("[Lager] Material unzureichend – Lieferant wird nachbestellen.");
            lagerAuffuellenOhneParameter();
        }
    }
    public int gibHolz() {
        return holz;
    }

    public int gibSchrauben() {
        return schrauben;
    }

    public int gibFarbe() {
        return farbe;
    }

    public int gibKarton() {
        return karton;
    }

    public int gibGlas() {
        return glas;
    }
}
