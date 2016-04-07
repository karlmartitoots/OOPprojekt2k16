package userFeatures;
import java.util.ArrayList;
import java.util.List;

import card.*;

public class Deck {
    private List<Card> deckOfCards = new ArrayList<>();
    private final int deckSize = 30;

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
     * Swaps the cards with indexes i and j places
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
    /*
    public List<Card> loadPaki(String filename) {
        filename += ".txt";
        List<Card> pakk = new ArrayList<>();
        Andmebaas andmebaas = new Andmebaas();
        try {
            int counter = 0;
            Scanner sc = new Scanner(new File(filename), "UTF-8");
            while (sc.hasNext() && counter < deckSize) {
                pakk.add(andmebaas.readKaart(sc.nextLine()));
                counter++;
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return pakk;
    }
    */

    /**
     * Gets the current cards in the deck
     *
     * @return cardsInDeck
     */
    public List<Card> getDeckOfCards() {
        return deckOfCards;
    }
}
