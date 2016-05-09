package userinterface;
import card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Deck {
    /**
     * Field for holding all cards in a deck.
     */
    private List<Card> deckOfCards = new ArrayList<>();

    /**
     * Constructor
     *
     * @param deckOfCards the Deck of cards
     */
    Deck(List<Card> deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    /**
     * Shuffles the cards in the deck, making sure they are in random sequence. Uses the inbuilt function from Collections.
     */
    public void shuffle() {
        Collections.shuffle(deckOfCards);
    }

    /**
     * Draws a card from the top of the deck
     * @return Card drawn
     */
    Card draw() {
        if (deckOfCards.size() > 0) {
            return deckOfCards.remove(0);
        } else return null;
    }

    /**
     * Adds a new card on the bottom of the deck.
     * @param newCard The new card to be added.
     */
    void addCard(Card newCard){
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
