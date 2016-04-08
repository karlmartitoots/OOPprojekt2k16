
package card;

import userFeatures.Square;

/**
 * Subclass of the card class. The class represents a minon type of card which are the main type of creatures in the game.
 */
public class Minion extends Card {
    private Square currentPosition;
    private final int ID;
    private final int attack;
    private final int maxHp;
    private final int speed;
    private int currentHp;

    /**
     * Construtor
     *
     * @param name   Name of the minion
     * @param cost   Manacost of the minion
     * @param effect Text effect of the minion
     * @param ID     ID of the minion
     * @param attack Attack value of the minion
     * @param maxHp  HP value of the minion
     * @param speed  Speed of the minion
     */

    public Minion(String name, int cost, String effect, int ID, int attack, int maxHp, int speed) {
        super(name, cost, effect);
        this.ID = ID;
        this.attack = attack;
        this.maxHp = maxHp;
        this.speed = speed;
        this.currentHp = maxHp;
    }

    /**
     * Getter for card ID
     * @return ID of the card
     */

    public int getID() {
        return ID;
    }

    /**
     * Getter for the square the minion currently stands on
     * @return Square the minion is standing on
     */
    public Square getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the square the minion is currently standing on
     * @param currentPosition Current position of the minion
     */
    public void setCurrentPosition(Square currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Gets the speed of the minion
     * @return Speed of the minion
     */
    public int getSpeed() {
        return speed;
    }
}
