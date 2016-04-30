
package card;

import userFeatures.Side;
import userFeatures.Square;

/**
 * Subclass of the card class. The class represents a minon type of card which are the main type of creatures in the game.
 */
public class MinionCard extends Card {
    private Square currentPosition;
    private final int ID;
    private final int attack;
    private final int maxHp;
    private final int speed;
    private int currentHp;
    private boolean hasMoved;
    private Side side;

    /**
     * Construtor
     *
     * @param name   Name of the minion
     * @param cost   Manacost of the minion
     * @param description Text description of the minion
     * @param ID     ID of the minion
     * @param attack Attack value of the minion
     * @param maxHp  HP value of the minion
     * @param speed  Speed of the minion
     */
    public MinionCard(String name, int cost, String description, int ID, int attack, int maxHp, int speed) {
        super(name, cost, description);
        this.ID = ID;
        this.attack = attack;
        this.maxHp = maxHp;
        this.speed = speed;
        this.currentHp = maxHp;
    }

    public Side getSide(){
        return side;
    }

    public void setSide(Side side){
        this.side = side;
    }

    public int getAttack() {
        return attack;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
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
     * @param nextPosition Next position of the minion
     */
    public void setCurrentPosition(Square nextPosition) {
        this.currentPosition = nextPosition;
    }

    /**
     * Gets the speed of the minion
     * @return Speed of the minion
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns if the minion has moved during this turn or not
     *
     * @return hasMoved
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Sets the value for the minion if it has moved this turn or not.
     *
     * @param hasMoved value if the minion has moved this turn or not
     */
    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
