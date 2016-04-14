package card;

import javafx.scene.image.Image;



/**
 * The card class represents the skeleton of card. It has consists of the three main features a card has: Name,
 * manacost and description.
 */
public abstract class Card {

    private final String name;
    private final int cost;
    private final String description;
    private final Image image;
    private final Image smallImage;

    /**
     * Gets the image of the card
     *
     * @return image of the card
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets the small image of the card
     *
     * @return small image of the card
     */
    public Image getSmallImage() {
        return smallImage;
    }


    /**
     * Constructor
     *  @param name   Name of the card
     * @param cost   Manacost of the card
     * @param description Text description of the card
     */
    public Card(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.image = new Image(name + ".jpg");
        this.smallImage = new Image(name + "Small.jpg");
    }

}
