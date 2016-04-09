package card;

import javafx.scene.image.Image;
import userFeatures.Square;

import java.io.IOException;

/**
 * General is subclass of the Minion class. It is a minion that represents the main leader of the army in the game.
 */
public class General extends Minion {
    private final Square startingWhite = new Square(1, 5, null);
    private final Square startingBlack = new Square(9, 6, null);
    private Image image;
    private Image smallImage;

    /**
     * Gets the image of the card
     * @return image of the card
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets the small image of the card
     * @return small image of the card
     */
    public Image getSmallImage() {
        return smallImage;
    }

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
        image = new Image(name + ".jpg");
        smallImage = new Image(name + "Small.jpg");
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
