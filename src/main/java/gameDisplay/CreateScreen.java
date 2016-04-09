package gameDisplay;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import card.*;
import userFeatures.*;

public class CreateScreen extends JFrame implements MouseListener {
    private int mouseX = 0;
    private int kaardilaius = 150;
    private int kaardipikkus = 200;
    private int mouseY = 0;
    private boolean menudisplayout = false;

    public CreateScreen() {

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
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.out.println("Väljun programmist");
                    setVisible(false);
                    dispose();
                }

            }
        });

    }

    private Deck kaardiPakk;
    private ArrayList<CardSlots> kaardislotid = new ArrayList<>();
    private MouseMotionListener drawing2 = new MouseMotionListener() {
        public void mouseDragged(MouseEvent e) {
//            mouseY = e.getY();
//            mouseX = e.getX();
//            for (Card card : kaardiPakk.getKaardiPakk()) {
//                if (mouseX > card.getKaartX() && card.getKaartX() + kaardilaius > mouseX) {
//                    if (mouseY > card.getKaartY() && card.getKaartY() + kaardipikkus > mouseY) {
//                        card.setSelected(true);
//
//                    }
//                }
//            }
//            for (Card card : kaardiPakk.getKaardiPakk()) {
//                if (card.isSelected()) {
//                    drawCard(card, mouseX, mouseY, kaardilaius, kaardipikkus);
//
//                }
//            }
//
        }
//
        public void mouseMoved(MouseEvent e) {
//            mouseY = e.getY();
//            mouseX = e.getX();
//            if (300 > mouseY) {
//                if (!menudisplayout) {
//                    menudisplayout = true;
//                }
//            }
//            if (mouseY > 740) {
//                menudisplayout = true;
//
//            }
//            for (Card card : kaardiPakk.getKaardiPakk()) {
//                if (mouseX > kaart.getKaartX() && kaart.getKaartX() + kaardilaius > mouseX && mouseY > kaart.getKaartY() && kaart.getKaartY() + kaardipikkus > mouseY) {
//
//                    kaart.setBigScreen(true);
//                } else {
//                    kaart.setBigScreen(false);
//                }
//            }
//
//
//            repaint();
        }
    };

    private void drawCard(Card card, int mouseX, int mouseY, int kaardilaius, int kaardipikkus) {
//        card.setKaartX(mouseX - kaardilaius / 2);
//        card.setKaartY(mouseY - kaardipikkus / 2);
    }


    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseY = e.getY();
        mouseX = e.getX();

        this.repaint();


    }


    @Override
    public void mouseReleased(MouseEvent e) {
//        for (Card card : kaardiPakk.getKaardiPakk()) {
//            card.setSelected(false);
//        }
//        if (menudisplayout) {
//            for (Card card : kaardiPakk.getKaardiPakk()) {
//                for (CardSlots cardSlots : kaardislotid) {
//                    if (card.getKaartX() > cardSlots.Xcoord && cardSlots.Xcoord + kaardilaius > card.getKaartX()) {
//                        if (card.getKaartX() > cardSlots.Ycoord && cardSlots.Ycoord + kaardipikkus > card.getKaartY()) {
//                            try {
//                                cardSlots.addCard(card.getMinionname(), card.getAttack(), card.getLife(), card.getCost(), kaardilaius, kaardipikkus);
//                            } catch (IOException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//
//                    }
//                }
//
//            }
//        }
//
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    class DrawPane extends JPanel {
        private BufferedImage displayOut, displayIn;
//
//        DrawPane() {
//
//
//            try {
//                for (int i = 0; i < 10; i++) {
//                    if (i < 5) {
//                        kaardislotid.add(new CardSlots(30 + 180 * i, 300));
//                    } else {
//                        kaardislotid.add(new CardSlots(30 + 180 * (i - 5), 500));
//                    }
//                }
//
//                displayOut = ImageIO.read(new File("src\\main\\resources\\playerdisplay.png"));
//                displayIn = ImageIO.read(new File("src\\main\\resources\\playerdisplaysmall.png"));
//                List<Card> pakk = new ArrayList<>();
//                Card esimene = new Card("esimene", 10, 10, 10, "none", "");
//                esimene.setKaartX(200);
//                esimene.setKaartY(500);
//                pakk.add(esimene);
//                Card teine = new Card("teine", 10, 10, 10, "none", "");
//                teine.setKaartX(400);
//                teine.setKaartY(500);
//                pakk.add(teine);
//                kaardiPakk = new Deck(pakk);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
        public void paintComponent(Graphics g) {
//            //draw on g here e.g.
//
//            if (menudisplayout) {
//                g.drawImage(displayOut, 0, 280, getWidth() - 500, 600, null);
//            } else {
//                g.drawImage(displayIn, 0, 280, getWidth() - 500, 600, null);
//            }
//            for (Card card : kaardiPakk.getKaardiPakk()) {
//                  g.drawImage(card.getDisplay(), card.getKaartX(), card.getKaartY(), kaardilaius, kaardipikkus, null);
//                  if (card.isBigScreen()) {
//                      g.drawImage(card.getDisplay(), getWidth() - 500, getHeight() - 600, 500, 600, null);
//                  }
//            }
//            for (CardSlots cardSlots : kaardislotid) {
//                if (menudisplayout)
//                    cardSlots.DrawSelf(g);
//
//            }
//
//
        }
    }

}
