import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by PusH on 3/25/2016.
 */
public class Kaart1 extends Kaart {
    BufferedImage display;

    double life;
    double attack;
    double cardcost;
    boolean activated;
    boolean selected,bigScreen;
    int kaartX=500;
    int kaartY=100;
    String minionname;
    Kaart1(double life, double attack, double cardcost,int X,int Y, String filename,String minionname) throws IOException {
        super(life, attack, cardcost,X,Y, filename,minionname);
        display = ImageIO.read(new File(filename));
        kaartX=500;

    }


}
