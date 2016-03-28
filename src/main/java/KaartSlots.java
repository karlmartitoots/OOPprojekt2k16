import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class KaartSlots {
    BufferedImage display;
    int life, attack, cardcost;
    int kaartX,kaartY,kaardipikkus,kaardilaius;
    String filename;
    Double cooldown;
    String minionname, effect, flavor;

    KaartSlots(int X, int Y) throws IOException {


        this.kaartX=X;
        this.kaartY=Y;


        this.minionname=minionname;
    }
    void DrawSelf(Graphics g) {


        g.drawImage(display, kaartX, kaartY,kaardilaius,kaardipikkus, null);
    }

    void addKaart(String minionname, int attack, int life, int cardcost, int kaardilaius, int kaardipikkus) throws IOException {
        this.attack = attack;
        this.cardcost = cardcost;
        this.life=life;
        this.minionname=minionname;
        this.kaardilaius=kaardilaius;
        this.kaardipikkus=kaardipikkus;
        display = ImageIO.read(new File("src\\main\\resources\\" + minionname + ".png"));
    }
    List<Minions> DrawAllies(Graphics g, double width, double height, List<Minions>  minionid) throws IOException {
        minionid.add(new Minions(minionname, attack, life, cardcost, effect, flavor));
        return minionid;
    }




}
