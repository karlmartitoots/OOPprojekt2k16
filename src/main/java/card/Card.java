package card;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * The card class represents the skeleton of card. It has consists of the three main features a card has: Name,
 * manacost and description.
 */
public abstract class Card {
    private final String name;
    private final int cost;
    private final String description;
    private Image image;
    private Image smallImage;
    private boolean chosen=false;
    private List<Attribute> attributes = new ArrayList<>();
    /**
     * Constructor
     *  @param name   Name of the card
     * @param cost   Manacost of the card
     * @param description Text description of the card
     */
    Card(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        try {
            this.image = new Image(name + ".jpg");
            this.smallImage = new Image(name + "Small.jpg");
        }catch(IllegalArgumentException e){
            System.out.println(name + ".jpg" + " ei leitud! Väljun...");
            System.exit(-1);
        }
    }

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

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
    public boolean getChosen(){
        return chosen;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Card{" +
                "\nname='" + name + '\'' +
                ", \ncost=" + cost +
                ", \ndescription='" + description + '\'' +
                "}\n";
    }
}
