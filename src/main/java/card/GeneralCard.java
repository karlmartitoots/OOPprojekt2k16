package card;

import userFeatures.Square;

/**
 * GeneralCard is subclass of the MinionCard class. It is a minion that represents the main leader of the army in the game.
 */
public class GeneralCard extends MinionCard {

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

    public void summonMinion(Square squareToSummon){
        //TODO: implement
    }

}
