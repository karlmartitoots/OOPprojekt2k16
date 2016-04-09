package userFeatures;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
     * Loads a deck of 30 cards
     * @param fileName Name of the file that contains all cards
     * @return Returns a deck of cards
     * @throws FileNotFoundException Throws a FileNotFoundException if fileName.txt doesnt exist.
     */
    public List<Card> loadDeck(String fileName) throws FileNotFoundException {
        fileName += ".txt";
        List<Card> deck = new ArrayList<>();
        try (
                Scanner sc = new Scanner(new File(fileName), "UTF-8")){
            int counter = 0;
            String[] parts;
            while (sc.hasNextLine() && counter < deckSize) {
                parts = sc.nextLine().split(";");
                switch(parts[0]){
                    case "Equipment":
                        deck.add(new Equipment(parts[1], Integer.parseInt(parts[2]), parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5])));
                        break;
                    case "Minion":
                        deck.add(new Equipment(parts[1], Integer.parseInt(parts[2]), parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5])));
                        break;
                    case "Spell":
                        deck.add(new Equipment(parts[1], Integer.parseInt(parts[2]), parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5])));
                        break;
                    default:
                        System.out.println("File with cards is broken on line: " + (counter + 1));
                        System.exit(-1);
                }
                counter++;
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Can't load deck. File of cards not found.");
        }
        return deck;
    }


    /**
     * Gets the current cards in the deck
     *
     * @return cardsInDeck
     */
    public List<Card> getDeckOfCards() {
        return deckOfCards;
    }
}
