import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by PusH on 3/25/2016.
 */
public class KaartSlots {
    BufferedImage display;
    double life,attack,cardcost;
    int kaartX,kaartY,kaardipikkus,kaardilaius;
    String filename;
    Double cooldown;
    String minionname;


    KaartSlots(int X, int Y) throws IOException {


        this.kaartX=X;
        this.kaartY=Y;


        this.minionname=minionname;
    }
    void DrawSelf(Graphics g) {


        g.drawImage(display, kaartX, kaartY,kaardilaius,kaardipikkus, null);
    }
    void addKaart(double attack,double cardcost,double life,String filename,String minionname,int kaardilaius,int kaardipikkus) throws IOException {
        this.attack = attack;
        this.cardcost = cardcost;
        this.life=life;
        this.filename=filename;
        this.minionname=minionname;
        this.kaardilaius=kaardilaius;
        this.kaardipikkus=kaardipikkus;
        display = ImageIO.read(new File(filename));
    }
    List<Minions> DrawAllies(Graphics g, double width, double height, List<Minions>  minionid) throws IOException {
        minionid.add(new Minions(attack,cardcost,kaartX,kaartY,minionname));
        return minionid;
    }




}
