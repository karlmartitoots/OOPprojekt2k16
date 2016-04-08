package card;

public class Equipment extends Card {

    private int bonusAttack;
    private int bonusHealth;

    public Equipment(String name, int cost, String effect, int bonusAttack, int bonusHealth) {
        super(name, cost, effect);
        this.bonusAttack = bonusAttack;
        this.bonusHealth = bonusHealth;
    }
}
