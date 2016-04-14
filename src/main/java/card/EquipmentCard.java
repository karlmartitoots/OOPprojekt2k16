package card;


public class EquipmentCard extends Card {

    private int bonusAttack;
    private int bonusHealth;

    public EquipmentCard(String name, int cost, String effect, int bonusAttack, int bonusHealth) {
        super(name, cost, effect);
        this.bonusAttack = bonusAttack;
        this.bonusHealth = bonusHealth;
    }
}
