
package card;

import userFeatures.Side;
import userFeatures.Square;

import java.util.Objects;

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

    private MinionCard(String name, int cost, String description, int ID, int attack, int maxHp, int speed, Side side) {
        super(name, cost, description);
        this.ID = ID;
        this.attack = attack;
        this.maxHp = maxHp;
        this.speed = speed;
        this.currentHp = maxHp;
        this.side = side;
    }

    /**
     * Static factory method for creating a minion on the white side. Gives possibility to create two of the same minion, with different objects.
     * @param name   Name of the minion
     * @param cost   Manacost of the minion
     * @param description Text description of the minion
     * @param ID     ID of the minion
     * @param attack Attack value of the minion
     * @param maxHp  HP value of the minion
     * @param speed  Speed of the minion
     * @return The minion on the white side
     */
    public static MinionCard createWhiteMinionCard(String name, int cost, String description, int ID, int attack, int maxHp, int speed){
        return new MinionCard(name, cost, description, ID, attack, maxHp, speed, Side.WHITE);
    }

    /**
     * Static factory method for creating a minion on the black side. Gives possibility to create two of the same minion, with different objects.
     * @param name   Name of the minion
     * @param cost   Manacost of the minion
     * @param description Text description of the minion
     * @param ID     ID of the minion
     * @param attack Attack value of the minion
     * @param maxHp  HP value of the minion
     * @param speed  Speed of the minion
     * @return The minion on the black side
     */
    public static MinionCard createBlackMinionCard(String name, int cost, String description, int ID, int attack, int maxHp, int speed){
        return new MinionCard(name, cost, description, ID, attack, maxHp, speed, Side.BLACK);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinionCard that = (MinionCard) o;
        return getID() == that.getID() &&
                getAttack() == that.getAttack() &&
                getMaxHp() == that.getMaxHp() &&
                getSpeed() == that.getSpeed() &&
                getCurrentHp() == that.getCurrentHp() &&
                hasMoved == that.hasMoved &&
                Objects.equals(getCurrentPosition(), that.getCurrentPosition()) &&
                getSide() == that.getSide();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrentPosition(), getID(), getAttack(), getMaxHp(), getSpeed(), getCurrentHp(), hasMoved, getSide());
    }

    @Override
    public String toString() {
        return "MinionCard{" +
                "currentPosition=" + currentPosition +
                ", ID=" + ID +
                ", attack=" + attack +
                ", maxHp=" + maxHp +
                ", speed=" + speed +
                ", currentHp=" + currentHp +
                ", hasMoved=" + hasMoved +
                ", side=" + side +
                "} " + super.toString();
    }
}
