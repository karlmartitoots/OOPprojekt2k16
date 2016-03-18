/**
 * Created by PusH on 18.03.2016.
 */
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class CreateScreen extends JFrame{
    public CreateScreen(){
        addKeyListener(new DrawUus());
        setContentPane(new DrawPane());
        setTitle("Card Game");
        setSize(500,500);
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
        public void paintComponent(Graphics g){
            //draw on g here e.g.
            g.fillRect(20, 20, 100, 200);
        }
    }

}