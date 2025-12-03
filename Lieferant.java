/**
 * Lieferant liefert Material synchron an das Lager.
 */
public class Lieferant {
    public void bestellungAufgebenFuerMaterial(Lager lager, int benoetigtesHolz,
                                               int benoetigteSchrauben,
                                               int benoetigteFarbe,
                                               int benoetigterKarton,
                                               int benoetigtesGlas) {
        // Lieferung erfolgt sofort: Bestände im Lager erhöhen
        if (benoetigtesHolz > 0) lager.addHolz(benoetigtesHolz);
        if (benoetigteSchrauben > 0) lager.addSchrauben(benoetigteSchrauben);
        if (benoetigteFarbe > 0) lager.addFarbe(benoetigteFarbe);
        if (benoetigterKarton > 0) lager.addKarton(benoetigterKarton);
        if (benoetigtesGlas > 0) lager.addGlas(benoetigtesGlas);
    }
}
