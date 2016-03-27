import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Kaart1 extends Kaart {

    Kaart1(String minionname, int life, int attack, int cost, String effect, String flavor) throws IOException {
        super(minionname, attack, life, cost, effect, flavor);
    }


}
