package card;

/**
 * Equipment is a subclass of card that deals with equipments to Minions and Generals such as shields and weapons.
 */
public class Equipment extends Card {
    private int bonusAttack;
    private int bonusHealth;

    /**
     * Constructor
     *
     * @param name        Name of the card
     * @param cost        Manacost of the card
     * @param effect      Text effect of the card
     * @param bonusAttack The value of how much the equipment changes the attack
     * @param bonusHealth The value of how much the equipment changes the health
     */
    public Equipment(String name, int cost, String effect, int bonusAttack, int bonusHealth) {
        super(name, cost, effect);
        this.bonusAttack = bonusAttack;
        this.bonusHealth = bonusHealth;
    }
}
