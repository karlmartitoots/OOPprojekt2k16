package userinterface;

import card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * For holding all cards in cardsInHand and operating with them through gamelogic.
 */
class PlayerHand {
    private static final int maximumHandSize = 7;
    private static final int preferredCardHeight = 250;//pixels
    private static final int preferredCardWidth = 140;//pixels
    private static final int topMostPixelValue = 525;
    private static final int leftMostPixelValue = 15;
    private List<Card> cardsInHand = new ArrayList<>();

    /**
     * Adds a specific card to the players cardsInHand
     *
     * @param card Card to add to cardsInHand
     */
    void addCardIfPossible(Card card) {
        if (cardsInHand.size() < maximumHandSize) {
            cardsInHand.add(card);
        }
    }

    /**
     * Discards all the cards in the players cardsInHand.
     */
    public void discardHand() {
        cardsInHand.clear();
    }

    /**
     * Discards a random card from the cardsInHand of the player
     */
    public void discardRandomCard() {
        cardsInHand.remove((int) (Math.random() * cardsInHand.size()));
    }

    /**
     * Discards a specific card from the players cardsInHand
     *
     * @param i index of the card
     */
    public void discardSpecificCard(int i) {
        cardsInHand.remove(i);
    }

    public void discardSpecificCard(Card card){
        cardsInHand.remove(card);
    }
    /**
     * Get cards in ur cardsInHand as list
     * @return Cards in cardsInHand
     */
    public List<Card> getCardsInHand(){return cardsInHand;}

    @Override
    public String toString() {
        return "Hand{" +
                "\ncardsInHand=" + cardsInHand +
                '}';
    }

    /**
     * Returns the maximum possible cardsInHand size
     *
     * @return maximum maximumHandSize
     */
    public static int getMaximumHandSize() {
        return maximumHandSize;
    }

    /**
     * @return preferred height of the card in pixels
     */
    public static int getPreferredCardHeight() {
        return preferredCardHeight;
    }

    /**
     * @return preferred width of the card in pixels
     */
    public static int getPreferredCardWidth() {
        return preferredCardWidth;
    }

    /**
     * @return the top most pixel value for Hand location on board
     */
    public static int getTopMostPixelValue() {
        return topMostPixelValue;
    }

    /**
     * @return the left most pixel value for Hand location on board.
     */
    public static int getLeftMostPixelValue() {
        return leftMostPixelValue;
    }
}
