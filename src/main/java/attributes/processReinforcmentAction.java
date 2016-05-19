package attributes;

import card.MinionCard;
import collection.Collection;
import gamelogic.Side;
import gamelogic.Square;

import java.util.List;

public class processReinforcmentAction {
    public static void summonSquires(int count, List<Square> gameBoard, Side side) {
        for (int i = 0; i < count; i++) {
            while (true) {
                int squareToSummon = (int) (Math.random() * 100);
                Square possibleSquareToSummon = gameBoard.get(squareToSummon);
                if (!possibleSquareToSummon.hasMinionOnSquare()) {
                    possibleSquareToSummon.setSquaresCard((MinionCard) new Collection().getAllCards().get(169));
                    possibleSquareToSummon.getCard().setCurrentPosition(possibleSquareToSummon);
                    possibleSquareToSummon.getCard().setSide(side);
                    possibleSquareToSummon.getCard().setHasAttacked(true);
                    possibleSquareToSummon.getCard().blockMovement();
                }
            }
        }
    }
}
