import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Kaart {
    private BufferedImage display;
    private int life, cost, attack;
    private boolean activated, selected, bigScreen;
    private int kaartX = 0;
    private int kaartY = 0;
    private String minionname, effect, flavor;

    Kaart(String minionname, int attack, int life, int cost, String effect, String flavor) {
        this.life=life;
        this.attack=attack;
        this.cost = cost;
        this.minionname=minionname;
        this.effect = effect;
        this.flavor = flavor;
        try {
            display = ImageIO.read(new File("src\\main\\resources\\" + minionname + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public BufferedImage getDisplay() {
        return display;
    }

    public int getLife() {
        return life;
    }

    public int getCost() {
        return cost;
    }

    public int getAttack() {
        return attack;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isBigScreen() {
        return bigScreen;
    }

    public int getKaartX() {
        return kaartX;
    }

    public int getKaartY() {
        return kaartY;
    }

    public String getEffect() {
        return effect;
    }

    public String getFlavor() {
        return flavor;
    }

    public String getMinionname() {
        return minionname;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setKaartX(int kaartX) {
        this.kaartX = kaartX;
    }

    public void setKaartY(int kaartY) {
        this.kaartY = kaartY;
    }

    public void setBigScreen(boolean bigScreen) {
        this.bigScreen = bigScreen;
    }

}
