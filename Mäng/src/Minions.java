import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by PusH on 3/25/2016.
 */
public class Minions {

    double attack;
    double cardcost;
    int kaartX,kaartY;
    BufferedImage display;

    Minions(double attack, double cardcost, int X, int Y, String filename) throws IOException {

        this.attack = attack;
        this.cardcost = cardcost;
        this.kaartX=X;
        this.kaartY=Y;

        display = ImageIO.read(new File(filename));
    }
    void DrawAllies(Graphics g, double width, double height){
        g.drawImage(display, (int)width-500, (int)height-600,500,600, null);
    }
}
