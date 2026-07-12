/**
 * This holds the overall stats of the game. It includes how many times a certain idol was saved, 
 * the amount of times the siren was deafeated, number of games lost, and the total amount of gold
 * spent in hanamaru's shop.
 * 
 * OverallStats
 */
import java.util.ArrayList;

public class OverallStats {
    private ArrayList<Idol> aquors; // the aquors ppl
    private int numSiren; // # of times siren was killed
    private int gamesLost; // # of times player got pwned
    private int goldSpent; // total gold spent

    /**
     * Creates an OverallStats object. Instansitaties the Aquors array list and initializes other values to 0;
     */
    public OverallStats(){
        aquors = new ArrayList<>();

        aquors.add(new Idol("Chika", "Yasudaya Ryokan"));
        aquors.add(new Idol("Riko", "Numazu Deep Sea Aquarium"));
        aquors.add(new Idol("You", "Izu-Mito Sea Paradise"));
        aquors.add(new Idol("Hanamaru", "Shougetsu Confectionary"));
        aquors.add(new Idol("Ruby", "Nagahama Castle Ruins"));
        aquors.add(new Idol("Dia", "Numazugoyotei"));
        aquors.add(new Idol("Kanan", "Uchiura Bay Pier"));
        aquors.add(new Idol("Mari", "Awashima Marine Park"));

        numSiren = 0;
        gamesLost = 0;
        goldSpent = 0;
    }

    /**
     * Return the list of idol in aquors
     * 
     * @return ArrayList of idols
     */
    public ArrayList<Idol> getAquors() {
        return aquors;
    }

    /**
     * Returns the amount of times the siren was defeated
     * 
     * @return number of times siren was deafeated
     */
    public int getNumSiren() {
        return numSiren;
    }


    /**
     * Returns the number of games lost
     * 
     * @return number of games lost
     */
    public int getGamesLost() {
        return gamesLost;
    }

    /**
     * Returns the gold spent in Hanamaru's shop
     * 
     * @return total gold spent
     */
    public int getGoldSpent() {
        return goldSpent;
    }
}
