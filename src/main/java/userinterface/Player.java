package userinterface;

import card.Card;
import card.GeneralCard;
import card.MinionCard;
import collection.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * When the game begins, 2 players are created. A Player class will hold anything, that
 * can or has to be different between players.
 */
public class Player {

    private final int handSize = 3;
    private final int deckSize = 7;
    private final int maximumMana = 10;
    /**
     * When mana is first initiated it's 0 and initial mana will be loaded by the Game itself, when
     * everything is loaded.
     */
    private int playerCurrentMana = 0;
    private int usableMana = 0;
    private PlayerHand playerPlayerHand = new PlayerHand();
    private PlayerDeck playerPlayerDeck = new PlayerDeck(new ArrayList<>());
    private List<MinionCard> controlledAllies = new ArrayList<>();
    private GeneralCard general;
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
        for (int i = 0; i < deckSize; i++) {
            playerPlayerDeck.addCard(randomCard());
        }
    }

    private Card randomCard() {
        Map<Integer, Card> allCards = new Collection().getAllCards();
        List<Card> cardList = new ArrayList<>(allCards.values());
        int randomIndex = new Random().nextInt(cardList.size());
        return cardList.get(randomIndex);
    }

    /**
     * Loads in the player's hand for the first time.
     */
    private void initiateHand() {
        for (int i = 0; i < handSize; i++) {
            playerPlayerHand.addCardIfPossible(playerPlayerDeck.draw());
        }
    }

    /**
     * Method for adding 1 mana to a Player object.
     */
    public void incrementMana() {
        if (playerCurrentMana < maximumMana) {
            playerCurrentMana++;
        }
    }

    public void addMana(int amount){
        if ((playerCurrentMana + amount) <= maximumMana) {
            playerCurrentMana += amount;
        }
    }

    /**
     * Sets players usable mana to the current maximum amount of mana the player can have.
     */
    public void resetMana() {
        usableMana = playerCurrentMana;
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
    public PlayerHand getPlayerHand() {
        return playerPlayerHand;
    }

    public Side getSide() {
        return side;
    }

    public List<MinionCard> getControlledAllies() {
        return controlledAllies;
    }

    public boolean isAlive(){
        return general.getCurrentHp() > 0;
    }

    public void setGeneral(GeneralCard general) {
        this.general = general;
    }

    public GeneralCard getGeneral() {
        return general;
    }

    public int getUsableMana(){
        return usableMana;
    }

    public void resetUsableMana() {
        usableMana = playerCurrentMana;
    }

    public PlayerDeck getPlayerDeck() {
        return playerPlayerDeck;
    }
}

