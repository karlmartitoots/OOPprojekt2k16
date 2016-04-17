package userFeatures;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import card.*;
import collection.Collection;

public class Deck {

    private List<Card> deckOfCards = new ArrayList<>();
    private final int deckSize = 7;

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
     * Loads a deck of deckSize cards
     * @param allCards All cards currently available
     */
    public void loadDeck(List<Card> allCards) {
        synchronized (allCards) {
            for (int i = 0; i < deckSize; i++) {
                deckOfCards.add(allCards.get(
                        (int) Math.round(Math.random() * allCards.size())));
            }
        }
    }

    /**
     * Gets the current cards in the deck
     *
     * @return cardsInDeck
     */
    public List<Card> getDeckOfCards() {
        return deckOfCards;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "\ndeckOfCards=" + deckOfCards +
                ", \ndeckSize=" + deckSize +
                '}';
    }
}
