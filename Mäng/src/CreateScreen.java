/**
 * Created by PusH on 18.03.2016.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CreateScreen extends JFrame implements MouseListener{
    int x=0;
    int mouseX=0;
    int kaardilaius=150;
    int kaardipikkus=200;
    int mouseY=0;
    boolean menudisplayout=false;
    public CreateScreen()  {

        setContentPane(new DrawPane());

        setTitle("Card Game");


        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addMouseMotionListener(drawing2);
        addMouseListener(this);
        setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {  // handler
                if(ke.getKeyCode() == ke.VK_ESCAPE) {
                    System.out.println("Väljun programmist");
                    setVisible(false);
                    dispose();
                }

            }
        });

    }
    ArrayList<Kaart> kaardipakk= new ArrayList();
    ArrayList<KaartSlots> kaardislotid= new ArrayList();
    ArrayList<Minions> minionid= new ArrayList();
    MouseMotionListener drawing2 = new MouseMotionListener() {
        public void mouseDragged(MouseEvent e) {
            mouseY= e.getY();
            mouseX=e.getX();
            for (Kaart kaart : kaardipakk) {
                if(mouseX>kaart.kaartX &&kaart.kaartX+kaardilaius>mouseX) {
                    if(mouseY>kaart.kaartY &&kaart.kaartY+kaardipikkus>mouseY) {
                    kaart.selected = true;

                }}
            }
            for (Kaart kaart : kaardipakk) {
                if(kaart.selected&&kaart.activated==false){
                    kaart.kaartX=mouseX-kaardilaius/2;
                    kaart.kaartY=mouseY-kaardipikkus/2;
                    System.out.println(mouseX);

                }
            }
        }

        public void mouseMoved(MouseEvent e) {
            mouseY= e.getY();
            mouseX=e.getX();
            System.out.println(mouseY);
            if(300>mouseY){
                if(menudisplayout=false){
                    menudisplayout=true;
                }
            }
            if (mouseY > 740 ) {
                menudisplayout = true;

            }
            for (Kaart kaart : kaardipakk) {
                if(mouseX>kaart.kaartX &&kaart.kaartX+kaardilaius>mouseX&&mouseY>kaart.kaartY &&kaart.kaartY+kaardipikkus>mouseY) {

                    kaart.bigScreen=true;
                    }
                else{
                    kaart.bigScreen=false;
                }
            }


            repaint();
        }
    };




    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseY= e.getY();
        mouseX=e.getX();

        this.repaint();


    }


    @Override
    public void mouseReleased(MouseEvent e) {
        for (Kaart kaart : kaardipakk) {
            System.out.println("off");
            kaart.selected=false;
        }
        if(menudisplayout==true){
            for (Kaart kaart : kaardipakk) {
                for (KaartSlots kaartslot : kaardislotid) {
                    if(kaart.kaartX>kaartslot.kaartX&&kaartslot.kaartX+kaardilaius>kaart.kaartX){
                        if(kaart.kaartY>kaartslot.kaartY&&kaartslot.kaartY+kaardipikkus>kaart.kaartY){
                        try {
                            kaartslot.addKaart(kaart.attack,kaart.cardcost,kaart.life,kaart.filename,kaart.minionname,kaardilaius,kaardipikkus);
                            kaardipakk.remove(kaart);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }}

                    }
                }

            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e)
    {

    }









    class DrawPane extends JPanel {
        BufferedImage displayout,displayin;
        DrawPane()  {



            try {
                for (int i = 0; i <10 ; i++) {
                    if(i<5){
                        kaardislotid.add(new KaartSlots(30 + 180 * i, 300));
                    }
                    else{
                        kaardislotid.add(new KaartSlots(30 + 180 * (i-5), 500));
                    }
                    }

                displayout = ImageIO.read(new File("Mäng\\playerdisplay.png"));
                displayin = ImageIO.read(new File("Mäng\\playerdisplaysmall.png"));
                kaardipakk.add(new Kaart(10,10,10,200,500,"Mäng\\kaart1.png","Mäng\\testminion.png"));
                kaardipakk.add(new Kaart(10,10,10,400,300,"Mäng\\kaart2.png","Mäng\\testminionpng"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void paintComponent(Graphics g){
            //draw on g here e.g.

            if(menudisplayout==true) {
                g.drawImage(displayout, 0, 280,getWidth()-500, 600, null);
            }
            if(menudisplayout==false){
                g.drawImage(displayin, 0, 280, getWidth()-500, 600, null);
            }
            for (Kaart kaart : kaardipakk) {
                g.drawImage(kaart.display, kaart.kaartX, kaart.kaartY,kaardilaius,kaardipikkus, null);
                if(kaart.bigScreen){
                    g.drawImage(kaart.display, getWidth()-500, getHeight()-600,500,600, null);
                }


        }
            for (KaartSlots kaartslot : kaardislotid) {
                if(menudisplayout)
                kaartslot.DrawSelf(g);

            }



        }
    }

}