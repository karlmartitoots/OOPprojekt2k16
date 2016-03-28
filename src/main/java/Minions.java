
import java.awt.*;
import java.io.IOException;
public class Minions extends Kaart {


    Minions(String minionname, int life, int attack, int cost, String effect, String flavor) throws IOException {
        super(minionname, attack, life, cost, effect, flavor);
    }

    void DrawAllies(Graphics g, double width, double height){
        g.drawImage(super.getDisplay(), (int) width - 500, (int) height - 600, 500, 600, null);
    }
}