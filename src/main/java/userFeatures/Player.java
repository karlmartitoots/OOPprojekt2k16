package userFeatures;

import card.Card;
import card.MinionCard;
import collection.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * When the game begins, 2 players are created. A Player class will hold anything, that
 * can or has to be different between players.
 * TODO: implement
 */
public class Player {

    private final int handSize = 3;
    private final int deckSize = 7;
    private final int maximumMana = 10;
    /**
     * When mana is first initiated it's 0 and initial mana will be loaded by the Game itself, when
     * everything is loaded.
     */
    private int playerCurrentMaximumMana = 0;
    private int usableMana = 0;
    private Hand playerHand = new Hand();
    private Deck playerDeck = new Deck(new ArrayList<>());
    private List<MinionCard> controlledAllies = new ArrayList<>();
    private Side side;

    /**
     * Initiates a player for the game
     * @param side Either black or white, depending on the setup
     */
    public Player(Side side){
        this.side = side;
        initiateDeck();
        initiateHand();
    }

    /**
     * Loads in the player's deck for the first time.
     */
    private void initiateDeck() {
        Map<Integer, Card> allCards = new Collection().getAllCards();
        for (int i = 0; i < deckSize; i++) {
            playerDeck.addCard(allCards.get((int) Math.round(Math.random() * allCards.size())));
        }
    }

    /**
     * Loads in the player's hand for the first time.
     */
    private void initiateHand() {
        for (int i = 0; i < handSize; i++) {
            playerHand.addCard(playerDeck.draw());
        }
    }

    /**
     * Method for adding mana to a Player object.
     */
    public void addMana() {
        if (playerCurrentMaximumMana < maximumMana) {
            playerCurrentMaximumMana++;
        }
    }
    /**
     * Sets players usable mana to the current maximum amount of mana the player can have.
     */
    public void resetMana() {
        usableMana = playerCurrentMaximumMana;
    }

    /**
     * Method that tries to use mana, if it is sucessful, the mana is used up. If not then then no mana is used.
     *
     * @param manaToUse mana to be used in the given action
     * @return True if the auction is sucessful, false othewirse
     */
    public boolean useMana(int manaToUse) {
        if (manaToUse > usableMana) {
            return false;
        }
        usableMana -= manaToUse;
        return true;
    }

    /**
     * Getter methor for player hand
     *
     * @return hand of the player
     */
    public Hand getPlayerHand() {
        return playerHand;
    }
}

