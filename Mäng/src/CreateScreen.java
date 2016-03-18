/**
 * Created by PusH on 18.03.2016.
 */
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CreateScreen extends JFrame implements MouseListener{
    int x=0;
    int mouseX=0;
    int mouseY=0;
    boolean menudisplayout=false;
    public CreateScreen()  {

        setContentPane(new DrawPane());

        setTitle("Card Game");
        setSize(900,900);
        setResizable(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addMouseMotionListener(drawing2);
        addMouseListener(this);
        setVisible(true);
    }
    MouseMotionListener drawing2 = new MouseMotionListener() {
        public void mouseDragged(MouseEvent e) {

        }

        public void mouseMoved(MouseEvent e) {

            mouseY= e.getY();
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

        System.out.println("test");
        this.repaint();


    }


    @Override
    public void mouseReleased(MouseEvent e) {

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

        }
    }

}