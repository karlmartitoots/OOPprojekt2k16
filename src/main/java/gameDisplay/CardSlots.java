package gameDisplay;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import card.*;

public class CardSlots {
    BufferedImage display;
    int maxHP, attack, cardcost, ID, speed;
    int Xcoord, Ycoord, cardLength, cardWidth;
    String filename;
    Double cooldown;
    String minionName, effect;

    CardSlots(int X, int Y) throws IOException {
        this.Xcoord =X;
        this.Ycoord =Y;
    }
    void DrawSelf(Graphics graphics) {
        graphics.drawImage(display, Xcoord, Ycoord, cardWidth, cardLength, null);
    }

    void addCard(String minionname, int attack, int maxHP, int cardcost, int kaardilaius, int kaardipikkus) throws IOException {
        this.attack = attack;
        this.cardcost = cardcost;
        this.maxHP=maxHP;
        this.minionName =minionname;
        this.cardWidth =kaardilaius;
        this.cardLength =kaardipikkus;
        display = ImageIO.read(new File("src\\main\\resources\\" + minionname + ".png"));
    }
    List<Minion> DrawAllies(Graphics graphics, double width, double height, List<Minion>  allMinions) throws IOException {
        allMinions.add(new Minion(minionName, cardcost, effect, ID, attack, maxHP, speed));
        return allMinions;
    }




}