package card;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The card class represents the skeleton of card. It has consists of the three main features a card has: Name,
 * manacost and effect.
 */
public abstract class Card {

    private final String name;
    private final int cost;
    private final String effect;
    private final Image image;

    /**
     * Constructor
     *  @param name   Name of the card
     * @param cost   Manacost of the card
     * @param effect Text effect of the card
     */
    public Card(String name, int cost, String effect) throws IOException {
        this.name = name;
        this.cost = cost;
        this.effect = effect;
        this.image = ImageIO.read(new File("src\\main\\resources\\" + name + ".png"));
    }

}
