/**
 
 * * Tests für die Funktionen des Lagers (Auffüllen)

 * @author Owen, Matthieu, Alexander, Moacir, Vinzenz
 * @version 19.12.2025
 * */

import junit.framework.TestCase;

public class LagerTest extends TestCase {

    public void testBeschaffungsZeit_0_beiGenugBestand() {
        Fabrik f = new Fabrik();
        Lager lager = f.getLager();
        Bestellung b = f.bestellungAufgeben(1, 1); // reale Bestellung mit Produkten
        assertEquals(0, lager.gibBeschaffungsZeit(b));
        // Lieferzeit wird bei Bestaetigung gesetzt; hier nur Prüfung der Beschaffung
    }

    public void testLagerAuffuellen_erhoehtBestaende() {
        Fabrik f = new Fabrik();
        Lager lager = f.getLager();
        Lieferant lieferant = f.getLieferant();
        // Triggert Nachbestellung: sehr große Bestellung
        Bestellung b = f.bestellungAufgeben(50, 50);
        assertEquals(2, lager.gibBeschaffungsZeit(b));
        // Auffüllen
        lager.lagerAuffuellen(lieferant);
        // Nach Auffüllen sollten weitere kleine Bestellungen ohne Beschaffung möglich sein
        Bestellung b2 = f.bestellungAufgeben(1, 0);
        assertEquals(0, lager.gibBeschaffungsZeit(b2));
    }
    public void testMaterialWirdVomLagerAbgebucht()
{
    Lager lager = Lager.getInstance();
    Fabrik f = new Fabrik();
    
    // Lager zuerst auffuellen, damit wir definierte Startwerte haben
    Lieferant lieferant = new Lieferant();
    lager.lagerAuffuellen(lieferant);
    
    int startHolz = lager.gibHolz();
    
    // Kleine Bestellung, die sicher aus dem Lager bedient werden kann
    Bestellung b = f.bestellungAufgeben(1, 0); // 1 Standardtuer
    
    // Jetzt Beschaffungszeit inklusive Reservierung testen
    lager.gibBeschaffungsZeit(b); // reserviert Material
    
    int expectedHolzNachBestellung = startHolz - Standardtuer.HOLZEINHEITEN;
    
    assertEquals(expectedHolzNachBestellung, lager.gibHolz());
}

    public void testVerbrauchUndNachbestellungBeiBedarf() {
        Fabrik f = new Fabrik();
        Lager lager = f.getLager();

        // Startwerte erfassen (Singleton-Lager)
        int startHolz = lager.gibHolz();
        int startSchrauben = lager.gibSchrauben();
        int startFarbe = lager.gibFarbe();
        int startKarton = lager.gibKarton();
        int startGlas = lager.gibGlas();

        // 1) Kleine Bestellung: Material wird reserviert und Bestände sinken
        Bestellung klein = f.bestellungAufgeben(1, 1); // 1x Standard, 1x Premium
        assertEquals(0, lager.gibBeschaffungsZeit(klein));

        int expectedHolz = startHolz - (Standardtuer.HOLZEINHEITEN + Premiumtuer.HOLZEINHEITEN);
        int expectedSchrauben = startSchrauben - (Standardtuer.SCHRAUBEN + Premiumtuer.SCHRAUBEN);
        int expectedFarbe = startFarbe - (Standardtuer.FARBEINHEITEN + Premiumtuer.FARBEINHEITEN);
        int expectedKarton = startKarton - (Standardtuer.KARTONEINHEITEN + Premiumtuer.KARTONEINHEITEN);
        int expectedGlas = startGlas - Premiumtuer.GLASEINHEITEN;

        assertEquals(expectedHolz, lager.gibHolz());
        assertEquals(expectedSchrauben, lager.gibSchrauben());
        assertEquals(expectedFarbe, lager.gibFarbe());
        assertEquals(expectedKarton, lager.gibKarton());
        assertEquals(expectedGlas, lager.gibGlas());

        // 2) Große Bestellung: Produktion prüft und löst Nachbestellung aus (Log prüfen)
        Bestellung riesig = f.bestellungAufgeben(100_000, 100_000);
        assertNotNull(riesig);

        java.io.PrintStream original = System.out;
        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(bos));
        try {
            lager.verbraucheMaterialFuer(riesig);
        } finally {
            System.setOut(original);
        }

        String out = bos.toString();
        assertTrue("Nachbestell-Log erwartet", out.contains("[Lager] Material unzureichend"));
    }
}
