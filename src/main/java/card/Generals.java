package card;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store all generals that are in the game
 */
//I figure this is a better solution than using a class for each of the seperatly although we can play around with that.
public class Generals {

    private General taavi = new General("taavi", 0, "", 1, 30, 30, 5);
    private General märt = new General("märt", 0, "", 2, 30, 30, 5);

    private List<General> allGenerals = new ArrayList<>();

    public Generals() {
        allGenerals.add(taavi.getID() - 1, taavi);
        allGenerals.add(märt.getID() - 1, märt);
    }

    public List<General> getAllGenerals() {
        return allGenerals;
    }

}
