package userFeatures;

import card.GeneralCard;
import card.MinionCard;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Square extends Parent {
    private int xCord;
    private int yCord;
    private final int width = 50;
    private final int heigth = 50;
    private Rectangle rectangle;
    private ImageView imageView;
    private MinionCard card;
    /**
     * Constructor
     *
     * @param xCord x Coordinates of the square on the game board
     * @param yCord Y Coordinates of the square on the game board
     */
    public Square(int xCord, int yCord, MinionCard card) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.card = card;

        if (hasCardOnSquare()) {
            imageView = new ImageView(card.getSmallImage());
            imageView.setX(xCord*50);
            imageView.setY(yCord*50);
            getChildren().add(imageView);
        } else {
            rectangle = new Rectangle(xCord * width, yCord * heigth, width, heigth);
            getChildren().add(rectangle);
            
        }
    }

    /**
     * Calculates the value of the square as if all the squares on the board would be in straight line. This is done using the formula xDim*xCord+yCord
     * @param xDim The X dimension of the board
     * @return The place the square would be on the line
     */
    public int intValue(int xDim) {
        return xDim * xCord + yCord;
    }

    /**
     * Calculates the moves that are needed to move from toMoveTo A to toMoveTo B. Movement is horizontal and vertical.
     *
     * @param toMoveTo toMove to
     * @return the Moves needed to make
     */
    public int getDistance(Square toMoveTo) {
        return Math.abs(xCord - toMoveTo.getxCord()) + Math.abs(yCord - toMoveTo.getyCord());
    }
    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    public void setFill(Paint paint) {
        rectangle.setFill(paint);
    }

    public void setStroke(Paint paint) {

        rectangle.setStroke(paint);
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    /**
     * Places the card on the board;
     *
     * @param card card to place on square
     */
    public void placeCard(GeneralCard card) {
        this.card = card;
    }

    /**
     * Removes a card from the current square
     */
    public void removeCard() {
        card = null;
    }

    /**
     * Checks if the square conatains a card
     *
     * @return true if has a card on the square, false if otherwise
     */
    public boolean hasCardOnSquare() {
        return card != null;
    }

    public ImageView getImageView() {
        return imageView;
    }


    public MinionCard getCard() {
        return card;
    }

    public boolean equals(Square square) {
        if (square.getxCord() == xCord && square.getyCord() == yCord) return true;
        else return false;
    }

    @Override
    public String toString() {
        return "Square{" +
                "xCord=" + xCord +
                ", yCord=" + yCord +
                ", rectangle=" + rectangle +
                ", imageView=" + imageView +
                '}';
    }
    }
