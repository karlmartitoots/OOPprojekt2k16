package userFeatures;
import java.util.ArrayList;
import java.util.List;

import card.*;

public class Deck {

    /**
     * Field for holding all cards in a deck.
     */
    private List<Card> deckOfCards = new ArrayList<>();

    /**
     * Constructor
     *
     * @param deckOfCards the Deck of cards
     */
    public Deck(List<Card> deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    /**
     * Shuffles the cards in the deck, making sure they are in random sequence. Uses the inbuilt RNG.
     */
    public void shuffle() {
        for (int i = 0; i < deckOfCards.size(); i++) {
            swap(i, (int) (Math.random() * deckOfCards.size()));
        }
    }

    /**
     * Swaps the places of cards with indexes i and j
     * @param i index of the first card
     * @param j index of the second card
     */
    private void swap(int i, int j) {
        Card holder = deckOfCards.get(i);
        deckOfCards.set(i, deckOfCards.get(j));
        deckOfCards.set(j, holder);
    }

    /**
     * Draws a card from the top of the deck
     * @return Card drawn
     */
    public Card draw() {
        if (deckOfCards.size() > 0) {
            return deckOfCards.remove(0);
        } else return null;
    }

    /**
     * Returns the next card in the deck, removes the card from the deck and shifts all cards toward the top.
     * @return
     */
    public Card getNextCard(){
        return deckOfCards.remove(0);
    }

    /**
     * Adds a new card on the bottom of the deck.
     * @param newCard The new card to be added.
     */
    public void addCard(Card newCard){
        deckOfCards.add(newCard);
    }
    /**
     * Gets the current cards in the deck
     *
     * @return cardsInDeck
     */
    public List<Card> getDeckOfCards() {
        return deckOfCards;
    }

    /**
     * A toString method for getting the object as a string.
     * @return String interpretation of a Deck object.
     */
    @Override
    public String toString() {
        return "Deck{" +
                "\ndeckOfCards=" + deckOfCards +
                '}';
    }
}
