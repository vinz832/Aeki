
/**
 
 * * Tests für die Materialbedarfs-Methoden von Standardtuer und Premiumtuer.

 * @author Owen, Matthieu, Alexander, Moacir, Vinzenz
 * @version 8.12.2025
 * */
import junit.framework.TestCase;

public class StandardUndPremiumTuerTest extends TestCase
{
    public void testMaterialbedarfStandardtuer()
    {
        // Testet, ob die statischen Getter die erwarteten Konstanten zurückgeben
        assertEquals(Standardtuer.HOLZEINHEITEN, Standardtuer.gibHolzeinheiten());
        assertEquals(Standardtuer.SCHRAUBEN,      Standardtuer.gibSchrauben());
        assertEquals(Standardtuer.TUERGRIFFE,    Standardtuer.gibTuergriffe());
        assertEquals(Standardtuer.FARBEINHEITEN, Standardtuer.gibVerwendeterLack());
        // Falls ihr wollt, könnt ihr noch einen Getter für Karton hinzufügen (gibKartoneinheiten)
        // und dann auch den testen.
    }

    public void testMaterialbedarfPremiumtuer()
    {
        assertEquals(Premiumtuer.HOLZEINHEITEN,  Premiumtuer.gibHolzeinheiten());
        assertEquals(Premiumtuer.SCHRAUBEN,      Premiumtuer.gibSchrauben());
        assertEquals(Premiumtuer.GLASEINHEITEN,  Premiumtuer.gibGlaseinheiten());
        assertEquals(Premiumtuer.TUERGRIFFE,     Premiumtuer.gibTuergriffe());
        assertEquals(Premiumtuer.FARBEINHEITEN,  Premiumtuer.gibVerwendeterLack());
        // analog: ggf. noch Kartoneinheiten testen, wenn ihr einen Getter ergänzt
    }
}