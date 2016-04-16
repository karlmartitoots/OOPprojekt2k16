package board;

import card.GeneralCard;
import card.MinionCard;
import userFeatures.GameBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to store all minions that are in the game
 */
public class CreaturesOnBoard {

    private GeneralCard whiteGeneral = new GeneralCard("taavi", 0, "taavi", 1, 30, 30, 5);
    private GeneralCard blackGeneral = new GeneralCard("märt", 0, "märt", 2, 30, 30, 5);
    private Map<Integer, GeneralCard> allGenerals = new HashMap<>(3);
    private Map<Integer, MinionCard> allMinions = new HashMap<>();

    public CreaturesOnBoard() {
        allGenerals.put(0, null);
        allGenerals.put(1, whiteGeneral);
        allGenerals.put(2, blackGeneral);
    }

    public Map<Integer, GeneralCard> getAllGenerals() {
        return allGenerals;
    }

    public Map<Integer, MinionCard> getAllMinions(){
        return allMinions;
    }

}
