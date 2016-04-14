package collection;


import card.MinionCard;
import javafx.scene.image.Image;

public class SampleMinion extends MinionCard {

    public SampleMinion(String name, int cost, String effect, int ID, int attack, int maxHp, int speed) {
        super(name, cost, effect, ID, attack, maxHp, speed);
        name = "Sample Minion";
        cost = 3;
        effect = "This guy does nothing.";
        ID = 0;
        attack = 0;
        maxHp = 999;
        speed = 20;
    }

    @Override
    public Image getImage() {
        return new Image("src/main/resources/testminion.jpg", true);
    }
}
