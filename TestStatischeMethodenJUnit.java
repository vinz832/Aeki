import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestStatischeMethodenJUnit {
    @Test
    void testStandardtuerMaterial() {
        assertEquals(5, Standardtuer.gibHolzeinheiten());
        assertEquals(10, Standardtuer.gibSchrauben());
        assertEquals(1, Standardtuer.gibTuergriffe());
        assertEquals(1, Standardtuer.FARBEINHEITEN);
    }

    @Test
    void testPremiumtuerMaterial() {
        assertEquals(6, Premiumtuer.gibHolzeinheiten());
        assertEquals(12, Premiumtuer.gibSchrauben());
        assertEquals(2, Premiumtuer.gibGlaseinheiten());
        assertEquals(1, Premiumtuer.gibTuergriffe());
        assertEquals(1, Premiumtuer.FARBEINHEITEN);
    }
}
