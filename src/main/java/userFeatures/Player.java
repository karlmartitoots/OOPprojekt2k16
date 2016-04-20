package userFeatures;

import card.Card;
import collection.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {

    private final int handSize = 3;
    private final int deckSize = 7;
    private int playerMana = 0;
    private Hand playerHand = new Hand();
    private Deck playerDeck = new Deck(new ArrayList<>());
    private String side;

    /**
     * Initiates a player for the game
     * @param side Either black or white, depending on the setup
     */
    public Player(String side){
        this.side = side;
        initiateDeck();
        initiateHand();
    }

    /**
     * Loads in the player's deck for the first time.
     */
    private void initiateDeck() {
        Map<Integer, Card> allCards = new Collection().getAllCards();
        List<Card> listOfCards = new ArrayList<>();
        for (Card card : allCards.values()) {
                listOfCards.add(card);
            }
        for (int i = 0; i < deckSize; i++) {
            playerDeck.addCard(allCards.get((int) Math.round(Math.random() * allCards.size())));
        }
    }

    /**
     * Loads in the player's hand for the first time.
     */
    private void initiateHand() {
        for (int i = 0; i < handSize; i++) {
            playerHand.addCard(playerDeck.getNextCard());
        }
    }

    /**
     * Method for adding mana to a Player object.
     * @param newMana Amount of mana added.
     */
    public void addMana(int newMana){
        this.playerMana += newMana;
    }

}

