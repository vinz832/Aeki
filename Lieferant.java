/**
 * Lieferant liefert Material synchron an das Lager.
 */
public class Lieferant {
    public void bestellungAufgebenFuerMaterial(Lager lager, int benoetigtesHolz,
                                               int benoetigteSchrauben,
                                               int benoetigteFarbe,
                                               int benoetigterKarton,
                                               int benoetigtesGlas) {
        public void bestellungAufgebenFuerMaterial(Lager lager, int holz, int schrauben, int farbe, int karton, int glas) {
            // Always act on the global Lager instance to avoid inconsistencies
            Lager target = Lager.getInstance();
            target.addHolz(holz);
            target.addSchrauben(schrauben);
            target.addFarbe(farbe);
            target.addKarton(karton);
            target.addGlas(glas);
    }
}
