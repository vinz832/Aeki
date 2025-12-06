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
}
