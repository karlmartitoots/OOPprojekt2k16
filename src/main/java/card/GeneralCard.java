package card;

import userFeatures.Square;


/**
 * GeneralCard is subclass of the MinionCard class. It is a minion that represents the main leader of the army in the game.
 */
public class GeneralCard extends MinionCard {

    private final Square startingWhite = getStartingWhite();

    private final Square startingBlack = getStartingBlack();

    /**
     * Constructor
     *
     * @param name   Name of the card
     * @param cost   Manacost of the card
     * @param description Description of the card
     * @param ID     ID value of the card
     * @param attack Attack value of the card
     * @param health Health value of the card
     * @param speed  Speed value of the card
     */
    public GeneralCard(String name, int cost, String description, int ID, int attack, int health, int speed) {
        super(name, cost, description, ID, attack, health, speed);
    }

    /**
     * Method to get the starting square on the board if the general is on the White side
     * @return Starting Square if white
     */
    public Square getStartingWhite() {
        Square startingWhite = new Square(6, 5, null);
        startingWhite.setxCordOnBoard(1);
        return startingWhite;
    }

    /**
     * Method to get the the starting square on the board if the general is on the Black side
     * @return Starting Square if black
     */

    public Square getStartingBlack() {
        Square startingBlack = new Square(13, 4, null);
        startingBlack.setxCordOnBoard(8);
        return startingBlack;
    }

    public void summonMinion(Square squareToSummon){
        //TODO: implement
    }

}
