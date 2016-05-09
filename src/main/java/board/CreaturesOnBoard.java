package board;

import card.GeneralCard;
import card.MinionCard;
import userinterface.Side;

import java.util.*;

/**
 * Class to store info about all the minions that are on the board.
 */
public class CreaturesOnBoard {

    private Map<Side, GeneralCard> allGeneralsOnBoard = new HashMap<>(3);
    private Map<Integer, MinionCard> allMinionsOnBoard = new HashMap<>();

    public void setAllGeneralsOnBoard(GeneralCard whiteGeneral, GeneralCard blackGeneral) {
        allGeneralsOnBoard.put(Side.WHITE, whiteGeneral);
        allGeneralsOnBoard.put(Side.BLACK, blackGeneral);
    }

    /**
     * For getting all possible generals.
     * @return Returns a HashMap of ID->General.
     */
    public Map<Side, GeneralCard> getAllGeneralsOnBoard() {
        return allGeneralsOnBoard;
    }

    /**
     * For getting all possible minions.
     * @return Returns a HashMap of ID->Minion.
     */
    public Map<Integer, MinionCard> getAllMinionsOnBoard(){
        return allMinionsOnBoard;
    }

}
