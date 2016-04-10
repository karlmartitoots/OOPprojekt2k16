package card;

import javafx.scene.image.Image;
import userFeatures.Square;


/**
 * General is subclass of the Minion class. It is a minion that represents the main leader of the army in the game.
 */
public class General extends Minion {
    private final Square startingWhite = new Square(1, 5, null);
    private final Square startingBlack = new Square(8, 4, null);



    /**
     * Constructor
     *
     * @param name   Name of the card
     * @param cost   Manacost of the card
     * @param effect Effect of the card
     * @param ID     ID value of the card
     * @param attack Attack value of the card
     * @param health Health value of the card
     * @param speed  Speed value of the card
     */
    public General(String name, int cost, String effect, int ID, int attack, int health, int speed) {
        super(name, cost, effect, ID, attack, health, speed);
        ;
    }

    /**
     * Method to get the starting square on the board if the general is on the White side
     * @return Starting Square if white
     */
    public Square getStartingWhite() {
        return startingWhite;
    }

    /**
     * Method to get the the starting square on the board if the general is on the Black side
     * @return Starting Square if black
     */

    public Square getStartingBlack() {
        return startingBlack;
    }
}
