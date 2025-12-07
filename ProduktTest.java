/**
 * /**
 *  
 * Überprüft Startzustand, Zustandswechsel und korrekte Textausgabe der Zustände von Produkt-Objekten.
.

 * @author Owen, Matthieu, Alexander, Moacir, Vinzenz
 * @version 8.12.2025
 * */


import junit.framework.TestCase;

public class ProduktTest extends TestCase {

    public void testStartZustand_istBestellt() {
        Produkt p = new Standardtuer();
        assertEquals(0, p.aktuellerZustand());
    }

    public void testZustandWechsel_mehrereZustaende() {
        Produkt p = new Standardtuer();

        p.zustandAendern(2);
        assertEquals(2, p.aktuellerZustand());

        p.zustandAendern(4);
        assertEquals(4, p.aktuellerZustand());
    }

    public void testZustandAlsText_stimmt() {
        Produkt p = new Premiumtuer();

        p.zustandAendern(3);
        assertEquals("TRANSPORTVORBEREITUNG", p.zustandAlsText());
    }
    public void testZustandAlsTextStartzustand()
{
    Produkt p = new Standardtuer(); // oder new Premiumtuer() – beide starten bei Zustand 0
    
    assertEquals("BESTELLT", p.zustandAlsText());
}
}