package userFeatures;

import card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final int HandSize = 7;
    private List<Card> Hand = new ArrayList<>();

    /**
     * Adds a specific card to the players hand
     *
     * @param card Card to add to hand
     */
    public void addCard(Card card) {
        Hand.add(card);
    }

    /**
     * Discards all the cards in the players hand.
     */
    public void discardHand() {
        Hand.clear();
    }

    /**
     * Discards a random card from the hand of the player
     */
    public void discardRandomCard() {
        Hand.remove((int) (Math.random() * Hand.size()));
    }

    /**
     * Discards a specific card from the players hand
     *
     * @param i index of the card
     */
    public void discardSpecificCard(int i) {
        Hand.remove(i);
    }
    
}
