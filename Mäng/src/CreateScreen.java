import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

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
                if (ke.getKeyCode() == ke.VK_ESCAPE) {
                    System.out.println("Väljun programmist");
                    setVisible(false);
                    dispose();
                }

            }
        });

    }

    private Pakk kaardiPakk;
    private ArrayList<KaartSlots> kaardislotid = new ArrayList<>();
    private MouseMotionListener drawing2 = new MouseMotionListener() {
        public void mouseDragged(MouseEvent e) {
            mouseY = e.getY();
            mouseX = e.getX();
            for (Kaart kaart : kaardiPakk.getKaardiPakk()) {
                if (mouseX > kaart.getKaartX() && kaart.getKaartX() + kaardilaius > mouseX) {
                    if (mouseY > kaart.getKaartY() && kaart.getKaartY() + kaardipikkus > mouseY) {
                        kaart.setSelected(true);

                    }
                }
            }
            for (Kaart kaart : kaardiPakk.getKaardiPakk()) {
                if (kaart.isSelected()) {
                    drawCard(kaart, mouseX, mouseY, kaardilaius, kaardipikkus);

                }
            }

        }

        public void mouseMoved(MouseEvent e) {
            mouseY = e.getY();
            mouseX = e.getX();
            if (300 > mouseY) {
                if (!menudisplayout) {
                    menudisplayout = true;
                }
            }
            if (mouseY > 740) {
                menudisplayout = true;

            }
            for (Kaart kaart : kaardiPakk.getKaardiPakk()) {
                if (mouseX > kaart.getKaartX() && kaart.getKaartX() + kaardilaius > mouseX && mouseY > kaart.getKaartY() && kaart.getKaartY() + kaardipikkus > mouseY) {

                    kaart.setBigScreen(true);
                } else {
                    kaart.setBigScreen(false);
                }
            }


            repaint();
        }
    };

    private void drawCard(Kaart kaart, int mouseX, int mouseY, int kaardilaius, int kaardipikkus) {
        kaart.setKaartX(mouseX - kaardilaius / 2);
        kaart.setKaartY(mouseY - kaardipikkus / 2);
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
        for (Kaart kaart : kaardiPakk.getKaardiPakk()) {
            kaart.setSelected(false);
        }
        if (menudisplayout) {
            for (Kaart kaart : kaardiPakk.getKaardiPakk()) {
                for (KaartSlots kaartslot : kaardislotid) {
                    if (kaart.getKaartX() > kaartslot.kaartX && kaartslot.kaartX + kaardilaius > kaart.getKaartX()) {
                        if (kaart.getKaartX() > kaartslot.kaartY && kaartslot.kaartY + kaardipikkus > kaart.getKaartY()) {
                            try {
                                kaartslot.addKaart(kaart.getMinionname(), kaart.getAttack(), kaart.getLife(), kaart.getCost(), kaardilaius, kaardipikkus);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                }

            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    class DrawPane extends JPanel {
        private BufferedImage displayOut, displayIn;

        DrawPane() {


            try {
                for (int i = 0; i < 10; i++) {
                    if (i < 5) {
                        kaardislotid.add(new KaartSlots(30 + 180 * i, 300));
                    } else {
                        kaardislotid.add(new KaartSlots(30 + 180 * (i - 5), 500));
                    }
                }

                displayOut = ImageIO.read(new File("Mäng\\playerdisplay.png"));
                displayIn = ImageIO.read(new File("Mäng\\playerdisplaysmall.png"));
                List<Kaart> pakk = new ArrayList<>();
                Kaart esimiene = new Kaart("esimene", 10, 10, 10, "none", "");
                esimiene.setKaartX(200);
                esimiene.setKaartY(500);
                pakk.add(esimiene);
                Kaart teine = new Kaart("teine", 10, 10, 10, "none", "");
                teine.setKaartX(400);
                teine.setKaartY(500);
                pakk.add(teine);
                kaardiPakk = new Pakk(pakk);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void paintComponent(Graphics g) {
            //draw on g here e.g.

            if (menudisplayout) {
                g.drawImage(displayOut, 0, 280, getWidth() - 500, 600, null);
            } else {
                g.drawImage(displayIn, 0, 280, getWidth() - 500, 600, null);
            }
            for (Kaart kaart : kaardiPakk.getKaardiPakk()) {
                g.drawImage(kaart.getDisplay(), kaart.getKaartX(), kaart.getKaartY(), kaardilaius, kaardipikkus, null);
                if (kaart.isBigScreen()) {
                    g.drawImage(kaart.getDisplay(), getWidth() - 500, getHeight() - 600, 500, 600, null);
                }


            }
            for (KaartSlots kaartslot : kaardislotid) {
                if (menudisplayout)
                    kaartslot.DrawSelf(g);

            }


        }
    }

}