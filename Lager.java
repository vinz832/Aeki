
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
 * @version 05.12.2025
 */

import java.util.List;

/**
 * Lager verwaltet Materialbestände und ermittelt Beschaffungszeiten.
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
    private Lager() {
        holz = MAXHOLZEINHEITEN;
        schrauben = MAXSCHRAUBEN;
        farbe = MAXFARBEINHEITEN;
        karton = MAXKARTONEINHEITEN;
        glas = MAXGLASEINHEITEN;
    }

    // Global accessor: always use this to get the shared Lager
    public static Lager getInstance() {
        return INSTANCE;
    }

    /**
     * Ermittelt die Beschaffungszeit für eine Bestellung basierend auf aktuellen Beständen.
     * Optional: reserviert Material aus dem Lager.
     * @return 0 wenn genug Material vorhanden, sonst 2
     */
    public int gibBeschaffungsZeit(Bestellung bestellung) {
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
     * Füllt das Lager durch Bestellung beim Lieferanten wieder auf.
     */
    public void lagerAuffuellen(Lieferant lieferant) {
        lieferant.bestellungAufgebenFuerMaterial(this,
            MAXHOLZEINHEITEN - holz,
            MAXSCHRAUBEN - schrauben,
            MAXFARBEINHEITEN - farbe,
            MAXKARTONEINHEITEN - karton,
            MAXGLASEINHEITEN - glas);
    }

    /** Wird vom Lieferanten benutzt, um Bestände zu erhöhen. */
    void addHolz(int menge) { holz = Math.min(MAXHOLZEINHEITEN, holz + menge); }
    void addSchrauben(int menge) { schrauben = Math.min(MAXSCHRAUBEN, schrauben + menge); }
    void addFarbe(int menge) { farbe = Math.min(MAXFARBEINHEITEN, farbe + menge); }
    void addKarton(int menge) { karton = Math.min(MAXKARTONEINHEITEN, karton + menge); }
    void addGlas(int menge) { glas = Math.min(MAXGLASEINHEITEN, glas + menge); }

    public void lagerBestandAusgeben() {
        System.out.println("=== Lagerbestand ===");
        System.out.println("Holz: " + holz);
        System.out.println("Schrauben: " + schrauben);
        System.out.println("Farbe: " + farbe);
        System.out.println("Karton: " + karton);
        System.out.println("Glas: " + glas);
        System.out.println();
    }
}
