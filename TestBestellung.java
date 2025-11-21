public class TestBestellung {
    public static void main(String[] args) {
        System.out.println("=== Test: Einzelne Bestellung ausgeben ===\n");
        
        // Erstelle eine einzelne Bestellung
        Bestellung b1 = new Bestellung(10, 3, 2);
        
        System.out.println("Vor Bestätigung:");
        b1.bestellungAusgeben();
        
        // Bestätige die Bestellung
        b1.bestellungBestaetigen();
        b1.setzeBeschaffungsZeit(5);
        
        System.out.println("\nNach Bestätigung:");
        b1.bestellungAusgeben();
        
        System.out.println("Beschaffungszeit: " + b1.gibBeschaffungsZeit() + " Tage");
    }
}
