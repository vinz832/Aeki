


import java.util.LinkedList;

/**
 * Produktions-Manager: Verteilt Bestellungen an Roboter und überwacht den Fortschritt.
 * Hält zwei Listen: zu verarbeiten und in Produktion.
 * 
 *  @author Owen, Mathieu, Alexander, Moacir, Vinzenz
 * @version 8.12.2025
 */
public class Produktions_Manager extends Thread {
    private final LinkedList<Bestellung> zuVerarbeiten = new LinkedList<Bestellung>();
    private final LinkedList<Bestellung> inProduktion = new LinkedList<Bestellung>();

    private final Holzbearbeitungs_Roboter holzRoboter;
    private volatile boolean aktiv = true;

    public Produktions_Manager() {
        super("Produktions_Manager");
        // Roboter liest ggf. Zeitskalierung aus System-Property
        this.holzRoboter = new Holzbearbeitungs_Roboter();
        this.holzRoboter.setName("Holzbearbeitungs_Roboter");
        this.holzRoboter.setDaemon(true);
        this.holzRoboter.start();
    }

    /**
     * Fügt eine neue Bestellung der Warteschlange hinzu.
        *
        * @param b Bestellung, die in die Verarbeitung aufgenommen wird
     */
    public void bestellungEingegangen(Bestellung b) {
        if (b == null) return;
        synchronized (zuVerarbeiten) {
            zuVerarbeiten.addLast(b);
            System.out.println("[PM] Bestellung eingegangen #" + b.gibBestellungsNr() +
                               ", Produkte=" + b.gibProdukte().size());
        }
    }

    /**
     * Stoppt Manager und Roboter robust.
     */
    public void stopManager() {
        aktiv = false;
        this.interrupt();
        holzRoboter.stopRoboter();
    }

    // Alias gemäss Testvorgaben: sauberer Stop-Mechanismus per "stoppe()"
    public void stoppe() { stopManager(); }

    /**
     * Platzhalter für Schritt 6: Startet die Produktion einer Bestellung.
        *
        * @param b Bestellung, deren Produkte in die Produktion gehen
     */
    protected void starteProduktion(Bestellung b) {
        if (b == null) return;
        int anzahl = b.gibProdukte().size();
        System.out.println("[PM] Starte Produktion für Bestellung #" + b.gibBestellungsNr() + " (Produkte=" + anzahl + ")");

        // Produktionsverbrauch im Lager abbuchen und ggf. Nachbestellung auslösen
        Lager.getInstance().verbraucheMaterialFuer(b);

        // Jedem Produkt die Holzbearbeitungsstation zuweisen und einreihen
        for (int i = 0; i < anzahl; i++) {
            Produkt p = b.gibBestellteProdukte().get(i);
            p.produktionsStationHinzufuegen(holzRoboter);
            boolean queued = p.naechsteProduktionsStation();
            if (queued) {
                System.out.println("[PM] Produkt weitergeleitet: " + p.getClass().getSimpleName() + " -> Holzbearbeitung");
            } else {
                System.out.println("[PM] Keine Produktionsstation verfügbar für: " + p.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void run() {
        System.out.println("[PM] gestartet");
        while (aktiv && !Thread.currentThread().isInterrupted()) {
            // 1) Neue Bestellungen in Produktion übernehmen
            Bestellung neu = null;
            synchronized (zuVerarbeiten) {
                if (!zuVerarbeiten.isEmpty()) {
                    neu = zuVerarbeiten.removeFirst();
                }
            }
            if (neu != null) {
                neu.setzeInProduktion(true);
                synchronized (inProduktion) { inProduktion.addLast(neu); }
                System.out.println("[PM] In Produktion #" + neu.gibBestellungsNr());
                try {
                    starteProduktion(neu);
                } catch (Exception e) {
                    System.out.println("[PM] Fehler in starteProduktion: " + e.getMessage());
                }
            }

            // 2) Fertigstellen prüfen
            synchronized (inProduktion) {
                if (!inProduktion.isEmpty()) {
                    LinkedList<Bestellung> fertig = new LinkedList<Bestellung>();
                    for (int i = 0; i < inProduktion.size(); i++) {
                        Bestellung b = inProduktion.get(i);
                        boolean allDone = true;
                        for (int j = 0; j < b.gibProdukte().size(); j++) {
                            Produkt p = b.gibProdukte().get(j);
                            if (p.aktuellerZustand() < 2) { // 2 = FERTIG
                                allDone = false;
                                break;
                            }
                        }
                        if (allDone) {
                            fertig.add(b);
                        }
                    }
                    for (Bestellung b : fertig) {
                        b.setzeAbgeschlossen(true);
                        b.setzeInProduktion(false);
                        inProduktion.remove(b);
                        System.out.println("[PM] Bestellung abgeschlossen #" + b.gibBestellungsNr());
                    }
                }
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("[PM] beendet");
    }

    // Hilfs-Getter für Inspektion/Tests (optional)
    public int gibAnzahlZuVerarbeiten() {
        synchronized (zuVerarbeiten) { return zuVerarbeiten.size(); }
    }
    public int gibAnzahlInProduktion() {
        synchronized (inProduktion) { return inProduktion.size(); }
    }

    // Minimal-invasive Debug-APIs für Tests (Aliases, ohne Design zu verändern)
    public int debugZuVerarbeitenSize() { return gibAnzahlZuVerarbeiten(); }
    public int debugInProduktionSize() { return gibAnzahlInProduktion(); }

    // Alias für das Hinzufügen neuer Bestellungen (benennungsfreundlich für Tests)
    public void neueBestellungHinzufuegen(Bestellung b) { bestellungEingegangen(b); }
}
