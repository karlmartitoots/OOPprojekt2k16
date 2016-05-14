package card;


public class EquipmentCard extends Card {

    private int bonusAttack;
    private int bonusHealth;
    private int bonusSpeed;

    public EquipmentCard(String name, int cost, String effect, int bonusAttack, int bonusHealth, int bonusSpeed) {
        super(name, cost, effect);
        this.bonusAttack = bonusAttack;
        this.bonusHealth = bonusHealth;
        this.bonusSpeed = bonusSpeed;
    }

    public int getBonusAttack() {
        return bonusAttack;
    }

    public int getBonusHealth() {
        return bonusHealth;
    }
}
