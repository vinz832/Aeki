/**
 * Tests für Funktionen der Fabrik: 
 * -> Bestellfunktionen (Aufgeben, Ausgeben)
 * -> Validierung
 * -> Summierung
 * -> Korrekte Initialisierung
 * 
*/ 

import junit.framework.TestCase;

/**
 * Tests für Fabrik.
 */
public class FabrikTest extends TestCase {
    public void testBestellungAufgeben_legtmAb() {
        Fabrik f = new Fabrik();
        f.bestellungAufgeben(2, 1);
        assertEquals(1, f.anzahlBestellungen());
    }
    public void testBestellungenAusgeben_keineException() {
        Fabrik f = new Fabrik();
        f.bestellungAufgeben(1, 0);
        f.bestellungAufgeben(0, 1);
        // BlueJ/JUnit3: Wir rufen einfach aus und erwarten keine Exception
        f.bestellungenAusgeben();
    }
    public void testBestellungAufgeben_negativeWerte_werdenNichtGespeichert() {
        Fabrik f = new Fabrik();
        int vorher = f.anzahlBestellungen();

        f.bestellungAufgeben(-1, 2);
        f.bestellungAufgeben(2, -1);

        assertEquals(vorher, f.anzahlBestellungen());
    }
    public void testBestellungAufgeben_nullNull_wirdNichtGespeichert() {
        Fabrik f = new Fabrik();
        int vorher = f.anzahlBestellungen();

        f.bestellungAufgeben(0, 0);

        assertEquals(vorher, f.anzahlBestellungen());
    }
    public void testBestellungAufgeben_mehrereBestellungen_zaehltKorrekt() {
        Fabrik f = new Fabrik();

        f.bestellungAufgeben(1, 0);
        f.bestellungAufgeben(0, 1);
        f.bestellungAufgeben(2, 1);

        assertEquals(3, f.anzahlBestellungen());
    }
    public void testBestellungAufgeben_inhaltDerBestellung_stimmt() {
        Fabrik f = new Fabrik();

        f.bestellungAufgeben(2, 1);

        assertEquals(1, f.anzahlBestellungen());
        Bestellung b = f.gibAlleBestellungen().get(0);

        assertEquals(2, b.gibAnzahlStandardTueren());
        assertEquals(1, b.gibAnzahlPremiumTueren());
        assertEquals(3, b.gibProdukte().size());
    }
    public void testBestellungAufgeben_setztZeiten() {
    Fabrik f = new Fabrik();
    Bestellung b = f.bestellungAufgeben(2, 1);
    int expectedProd = 2 * Standardtuer.PRODUKTIONSZEIT 
                     + 1 * Premiumtuer.PRODUKTIONSZEIT;
    assertEquals(0, b.gibBeschaffungsZeit());
    assertEquals(expectedProd + 1, b.gibLieferZeit());
}

    /**
     * Verifiziert, dass Lieferungen asynchron nach 48 Sekunden eintreffen.
     * Vorgehen:
     * 1) Eine kleine Bestellung reserviert Material und reduziert den Bestand.
     * 2) Eine sehr große Bestellung triggert den Lieferanten (Auffüllen nach 48s).
     * 3) Vor Ablauf der Zeit bleibt der Bestand unverändert, danach ist er aufgefüllt.
     */
    public void testVerzoegerteLieferung_nach48Sek() throws Exception {
        Fabrik f = new Fabrik();
        Lager lager = f.getLager();

        // Delta berechnen (wie viel die kleine Bestellung verbraucht)
        int deltaHolz = Standardtuer.HOLZEINHEITEN + Premiumtuer.HOLZEINHEITEN;
        int deltaSchrauben = Standardtuer.SCHRAUBEN + Premiumtuer.SCHRAUBEN;

        // 1) Kleine Bestellung: reserviert sofort und reduziert Lager
        Bestellung klein = f.bestellungAufgeben(1, 1);
        f.reserveMaterialFuer(klein);
        int holzNachReservierung = lager.gibHolz();
        int schraubenNachReservierung = lager.gibSchrauben();

        // 2) Große Bestellung: löst Beschaffungszeit = 2 aus und startet Lieferanten-Thread
        Bestellung gross = f.bestellungAufgeben(10_000, 10_000);
        f.reserveMaterialFuer(gross);

        // 3a) Vor Ablauf der 48s (z. B. nach 10s) muss Bestand unverändert bleiben
        Thread.sleep(10_000L);
        assertEquals(holzNachReservierung, lager.gibHolz());
        assertEquals(schraubenNachReservierung, lager.gibSchrauben());

        // 3b) Nach Ablauf der Zeit (+ kleine Puffer) muss aufgefüllt sein
        Thread.sleep(40_000L + 1_500L);
        assertEquals(holzNachReservierung + deltaHolz, lager.gibHolz());
        assertEquals(schraubenNachReservierung + deltaSchrauben, lager.gibSchrauben());
    }

    /**
     * Verifiziert, dass eine Bestellung durch den Produktions_Manager mit dem
     * Holzbearbeitungs_Roboter letztlich abgeschlossen wird. Die Zeitskalierung
     * wird reduziert, um die Laufzeit im Test kurz zu halten.
     */
    public void testProduktionWirdAbgeschlossen_mitTimeScaling() throws Exception {
        String vorher = System.getProperty("aeki.time.scale");
        System.setProperty("aeki.time.scale", "0.001"); // 1000x schneller
        try {
            Fabrik f = new Fabrik();
            Bestellung b = f.bestellungAufgeben(1, 1);
            assertNotNull(b);

            long deadline = System.currentTimeMillis() + 7000L; // bis zu 7s warten
            boolean fertig = false;
            while (System.currentTimeMillis() < deadline) {
                if (b.gibAbgeschlossen()) { fertig = true; break; }
                Thread.sleep(100L);
            }
            assertTrue("Bestellung wurde nicht abgeschlossen", fertig);
        } finally {
            if (vorher != null) {
                System.setProperty("aeki.time.scale", vorher);
            } else {
                System.clearProperty("aeki.time.scale");
            }
        }
    }
}
