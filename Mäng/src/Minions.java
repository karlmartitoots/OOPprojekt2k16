
import java.awt.*;
import java.io.IOException;

/**
 * Created by PusH on 3/25/2016.
 */
public class Minions extends Kaart {


    Minions(double life, double attack, double cost, int X, int Y, String filename, String minionname) throws IOException {
        super(life, attack, cost, X, Y, filename, minionname);
    }

    void DrawAllies(Graphics g, double width, double height){
        g.drawImage(super.getDisplay(), (int) width - 500, (int) height - 600, 500, 600, null);
    }
}
