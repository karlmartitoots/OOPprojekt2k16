/**
 * Created by PusH on 18.03.2016.
 */
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CreateScreen extends JFrame{
    public CreateScreen()  {
        addKeyListener(new DrawUus());
        setContentPane(new DrawPane());

        setTitle("Card Game");
        setSize(900,900);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    int x;
    public class DrawUus extends KeyAdapter{
        public void keyPressed(Graphics g,KeyEvent e){
            x+=1;
            g.fillOval(x, 25, 25, 25);

        }
    }
    class DrawPane extends JPanel {
        BufferedImage img;
        DrawPane()  {

            try {
                img = ImageIO.read(new File("Mäng\\playerdisplay.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void paintComponent(Graphics g){
            //draw on g here e.g.

            g.drawImage(img,0,280,900,600,null);

        }
    }

}