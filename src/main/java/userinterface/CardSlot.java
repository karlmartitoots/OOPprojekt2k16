package userinterface;

import card.Card;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CardSlot {
    private int slotNumber;
    private double slotWidth = 140, slotHeight = 250;
    private Group imageGroup = new Group();
    private Card card = null;
    private ImageView defaultImageView = new ImageView(new Image("emptySlot.jpg"));

    public CardSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        imageGroup.getChildren().add(defaultImageView);
    }

    public void updateCardSlotImage(){
        if(!hasCard()){
            imageGroup.getChildren().clear();
            imageGroup.getChildren().add(defaultImageView);
            return;
        }
        ImageView cardImageView = new ImageView(card.getImage());
        cardImageView.setFitHeight(slotHeight);
        cardImageView.setFitWidth(slotWidth);

        ImageView cardFrame = new ImageView(new Image("cardFrame.png"));
        cardFrame.setFitHeight(slotHeight);
        cardFrame.setFitWidth(slotWidth);

        int nameXAdjustment = 35, nameYAdjustment = 20;
        Text minionName = new Text(nameXAdjustment, nameYAdjustment, card.getName());
        int nameLength = card.getName().length();
        double fontFunction = 30/(1 + nameLength/10.0);
        minionName.setFont(new Font(fontFunction));
        minionName.setFill(Color.WHITE);

        imageGroup.getChildren().clear();
        imageGroup.getChildren().addAll(cardImageView, cardFrame, minionName);
    }

    public void setCard(Card card){
        this.card = card;
    }
    public Card getCard(){
        return this.card;
    }

    public boolean hasCard(){
        return card != null;
    }

    public Group getCardSlotImage(){
        return this.imageGroup;
    }

    @Override
    public String toString() {
        return "CardSlot{" +
                "slotNumber=" + slotNumber +
                ", card=" + card +
                '}';
    }
}
