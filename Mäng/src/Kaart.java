import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by PusH on 3/25/2016.
 */
public class Kaart {
    BufferedImage display;
    double life,cardcost,attack;
    boolean activated,selected,bigScreen;
    int kaartX=0;
    int kaartY=0;
    String filename,minionname;
    Kaart(double life,double attack,double cardcost,int X,int Y,String filename,String minionname) throws IOException {
        this.life=life;
        kaartY=X;
        kaartY=Y;
        this.attack=attack;
        this.cardcost=cardcost;
        this.filename=filename;
        display = ImageIO.read(new File(filename));
        this.minionname=minionname;
    }



}
