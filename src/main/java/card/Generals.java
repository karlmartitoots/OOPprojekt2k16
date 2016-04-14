package card;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store all generals that are in the game
 */
//I figure this is a better solution than using a class for each of them separatly although we can play around with that.
public class Generals {

    private GeneralCard taavi = new GeneralCard("taavi", 0, "", 1, 30, 30, 5);
    private GeneralCard märt = new GeneralCard("märt", 0, "", 2, 30, 30, 5);
    /*
    KARL:
    I was considering using this kind of config for the generals:
    private GeneralCard player1general;
    private GeneralCard player2general;
    private List<GeneralCard> allGenerals = new ArrayList<>(2);

    When we load the gameboard, we load in the generals as well so the players choose
    their generals before the gameboard has been loaded or they are assigned some random generals

    But I'd like your input on this aswell.
     */

    private List<GeneralCard> allGenerals = new ArrayList<>();

    public Generals() {
        allGenerals.add(null);
        allGenerals.add(taavi.getID(), taavi);
        allGenerals.add(märt.getID(), märt);
    }

    public List<GeneralCard> getAllGenerals() {
        return allGenerals;
    }


}
