/**
 * Lieferant liefert Material synchron an das Lager.
 */
public class Lieferant {
    public void bestellungAufgebenFuerMaterial(Lager lager,
                                               int holz,
                                               int schrauben,
                                               int farbe,
                                               int karton,
                                               int glas) {
        // Lieferung erfolgt sofort: Bestände im übergebenen Lager erhöhen
        if (holz > 0) lager.addHolz(holz);
        if (schrauben > 0) lager.addSchrauben(schrauben);
        if (farbe > 0) lager.addFarbe(farbe);
        if (karton > 0) lager.addKarton(karton);
        if (glas > 0) lager.addGlas(glas);
    }
}
