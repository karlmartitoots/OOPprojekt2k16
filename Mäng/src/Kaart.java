import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by PusH on 3/25/2016.
 */
public class Kaart {
    BufferedImage display;
    double life;
    double attack;
    double cardcost;
    boolean activated;
    boolean selected;
    int kaartX=0;
    int kaartY=0;
    Kaart(double life,double attack,double cardcost,int X,int Y,String filename) throws IOException {
        this.life=life;
        kaartY=X;
        kaartY=Y;
        this.attack=attack;
        this.cardcost=cardcost;
        display = ImageIO.read(new File(filename));
    }



}
