/**
 * Created by PusH on 18.03.2016.
 */
import java.awt.Graphics;
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
    int kaardilaius=200;
    int kaardipikkus=250;
    int mouseY=0;
    boolean menudisplayout=false;
    public CreateScreen()  {

        setContentPane(new DrawPane());

        setTitle("Card Game");
        setSize(900,900);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addMouseMotionListener(drawing2);
        addMouseListener(this);
        setVisible(true);

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

    MouseMotionListener drawing2 = new MouseMotionListener() {
        public void mouseDragged(MouseEvent e) {
            mouseY= e.getY();
            mouseX=e.getX();
            for (Kaart kaart : kaardipakk) {
                if(mouseX>kaart.kaartX &&kaart.kaartX+kaardilaius>mouseX) {
                    if(mouseY>kaart.kaartY &&kaart.kaartY+kaardipikkus>mouseY) {
                    kaart.selected = true;
                    System.out.println("tests");
                }}
            }
            for (Kaart kaart : kaardipakk) {
                if(kaart.selected){
                    kaart.kaartX=mouseX-kaardilaius/2;
                    kaart.kaartY=mouseY-kaardipikkus/2;
                    System.out.println(mouseX);

                }
            }
        }

        public void mouseMoved(MouseEvent e) {
            mouseY= e.getY();
            mouseX=e.getX();
            if(300>mouseY){
                if(menudisplayout=false){
                    menudisplayout=true;
                }
            }
            if (mouseY > 740 ) {
                menudisplayout = true;
                System.out.println("TEST");
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
                displayout = ImageIO.read(new File("Mäng\\playerdisplay.png"));
                displayin = ImageIO.read(new File("Mäng\\playerdisplaysmall.png"));
                kaardipakk.add(new Kaart(10,10,10,200,500,"Mäng\\kaart1.png"));
                kaardipakk.add(new Kaart(10,10,10,400,300,"Mäng\\kaart2.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void paintComponent(Graphics g){
            //draw on g here e.g.

            if(menudisplayout==true) {
                g.drawImage(displayout, 0, 280, 900, 600, null);
            }
            if(menudisplayout==false){
                g.drawImage(displayin, 0, 280, 900, 600, null);
            }
            for (Kaart kaart : kaardipakk) {
                g.drawImage(kaart.display, kaart.kaartX, kaart.kaartY,kaardilaius,kaardipikkus, null);
            }

        }
    }

}