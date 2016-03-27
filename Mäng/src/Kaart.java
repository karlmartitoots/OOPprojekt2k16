import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Kaart {
    private BufferedImage display;
    private double life, cost, attack;
    private boolean activated, selected, bigScreen;
    private int kaartX = 0;
    private int kaartY = 0;
    private String filename, minionname;

    Kaart(double life, double attack, double cost, int X, int Y, String filename, String minionname) throws IOException {
        this.life=life;
        kaartY=X;
        kaartY=Y;
        this.attack=attack;
        this.cost = cost;
        this.filename=filename;
        display = ImageIO.read(new File(filename));
        this.minionname=minionname;
    }

    public BufferedImage getDisplay() {
        return display;
    }

    public double getLife() {
        return life;
    }

    public double getCost() {
        return cost;
    }

    public double getAttack() {
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

    public String getFilename() {
        return filename;
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
