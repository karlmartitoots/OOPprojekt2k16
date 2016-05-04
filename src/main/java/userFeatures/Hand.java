package userFeatures;

import card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * For holding all cards in hand and operating with them through gamelogic.
 */
class Hand {
    private static final int maximalHandSize = 7;
    private static final int preferredCardHeight = 250;//pixels
    private static final int preferredCardWidth = 140;//pixels
    private static final int topMostPixelValue = 525;
    private static final int leftMostPixelValue = 15;
    private List<Card> hand = new ArrayList<>();

    /**
     * Adds a specific card to the players hand
     *
     * @param card Card to add to hand
     */
    void addCard(Card card) {
        if (hand.size() <= maximalHandSize) {
            hand.add(card);
        }
    }

    /**
     * Discards all the cards in the players hand.
     */
    public void discardHand() {
        hand.clear();
    }

    /**
     * Discards a random card from the hand of the player
     */
    public void discardRandomCard() {
        hand.remove((int) (Math.random() * hand.size()));
    }

    /**
     * Discards a specific card from the players hand
     *
     * @param i index of the card
     */
    public void discardSpecificCard(int i) {
        hand.remove(i);
    }

    /**
     * Get cards in ur hand as list
     * @return Cards in hand
     */
    public List<Card> getHand(){return hand;}

    @Override
    public String toString() {
        return "Hand{" +
                ", \nhand=" + hand +
                '}';
    }

    /**
     * Returns the maximum possible hand size
     *
     * @return maximum maximalHandSize
     */
    public static int getMaximalHandSize() {
        return maximalHandSize;
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
