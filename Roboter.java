import java.util.LinkedList;

/**
 * Basisklasse für Produktionsroboter.
 * Verwaltet eine interne Warteschlange und arbeitet Produkte in einem Thread ab.
 */
public class Roboter extends Thread {
    private final LinkedList<Produkt> warteschlange = new LinkedList<Produkt>();
    private volatile boolean aktiv = true;

    /**
     * Fügt ein Produkt der Warteschlange hinzu.
     *
     * @param p Produkt, das in die Roboter-Queue aufgenommen wird
     */
    public void fuegeProduktHinzu(Produkt p) {
        if (p == null) {
            System.out.println("[Roboter] Null-Produkt ignoriert");
            return;
        }
        synchronized (warteschlange) {
            warteschlange.addLast(p);
            System.out.println("[Roboter] Produkt in Queue: " + p.getClass().getSimpleName() + ", Queue-Länge=" + warteschlange.size());
        }
    }

    /**
     * Gibt die aktuelle Queue-Länge zurück.
     */
    public int gibQueueLaenge() {
        synchronized (warteschlange) {
            return warteschlange.size();
        }
    }

    /**
     * Robustes Beenden des Roboters (setzt Interrupt und Flag).
     */
    public void stopRoboter() {
        aktiv = false;
        this.interrupt();
    }

    @Override
    public void run() {
        System.out.println("[Roboter] gestartet: " + getClass().getSimpleName());
        while (aktiv && !Thread.currentThread().isInterrupted()) {
            Produkt p = null;
            synchronized (warteschlange) {
                if (!warteschlange.isEmpty()) {
                    p = warteschlange.removeFirst();
                }
            }

            if (p != null) {
                try {
                    System.out.println("[Roboter] Bearbeite Produkt: " + p.getClass().getSimpleName());
                    produziereProdukt(p);
                    System.out.println("[Roboter] Fertig: " + p.getClass().getSimpleName());
                } catch (RuntimeException re) {
                    System.out.println("[Roboter] Fehler bei Produktion: " + re.getMessage());
                }
            } else {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        System.out.println("[Roboter] beendet: " + getClass().getSimpleName());
    }

    /**
     * Produktions-Hook für Unterklassen. Basisklasse führt keine echte Arbeit aus.
     *
     * @param p Produkt, das produziert werden soll
     */
    protected void produziereProdukt(Produkt p) {
        // Basis-Implementierung: kein Sleep, nur Zustand loggen
    }
}
