package userFeatures;

import card.GeneralCard;
import card.MinionCard;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Square extends Parent {
    private int xCordOnGUI;
    private int yCordOnGUI;

    private int xCordOnBoard;
    private int yCordOnBoard;

    private final int width = 50;
    private final int heigth = 50;
    private Rectangle rectangle;
    private ImageView imageView;
    private MinionCard card;
    /**
     * Constructor
     *
     * @param xCordOnGUI x Coordinates of the square on the GUI
     * @param yCordOnGUI Y Coordinates of the square on the GUI
     */
    public Square(int xCordOnGUI, int yCordOnGUI, MinionCard card) {
        this.xCordOnGUI = xCordOnGUI;
        this.yCordOnGUI = yCordOnGUI;
        this.xCordOnBoard = xCordOnGUI;
        this.yCordOnBoard = yCordOnGUI;
        this.card = card;

        if (hasMinionOnSquare()) {
            imageView = new ImageView(card.getSmallImage());
            imageView.setX(xCordOnGUI *50);
            imageView.setY(yCordOnGUI *50);
            getChildren().add(imageView);
        } else {
            rectangle = new Rectangle(xCordOnGUI * width, yCordOnGUI * heigth, width, heigth);
            getChildren().add(rectangle);

        }
    }

    /**
     * Calculates the value of the square as if all the squares on the board would be in straight line. This is done using the formula xDim*xCordOnGUI+yCordOnGUI
     * @param xDim The X dimension of the board
     * @return The place the square would be on the line
     */
    public int intValue(int xDim) {
        return xDim * xCordOnBoard + yCordOnBoard;
    }

    /**
     * Calculates the moves that are needed to move from toMoveTo A to toMoveTo B. Movement is horizontal and vertical.
     *
     * @param toMoveTo toMove to
     * @return the Moves needed to make
     */
    public int getDistance(Square toMoveTo) {
        return Math.abs(xCordOnBoard - toMoveTo.getxCordOnGUI()) + Math.abs(yCordOnBoard - toMoveTo.getyCordOnGUI());
    }
    public int getxCordOnGUI() {
        return xCordOnGUI;
    }

    public void setxCordOnGUI(int xCordOnGUI) {
        this.xCordOnGUI = xCordOnGUI;
    }

    public int getyCordOnGUI() {
        return yCordOnGUI;
    }

    public void setyCordOnGUI(int yCordOnGUI) {
        this.yCordOnGUI = yCordOnGUI;
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
    public int getxCordOnBoard() {
        return xCordOnBoard;
    }

    public void setxCordOnBoard(int xCordOnBoard) {
        this.xCordOnBoard = xCordOnBoard;
    }

    public int getyCordOnBoard() {
        return yCordOnBoard;
    }

    public void setyCordOnBoard(int yCordOnBoard) {
        this.yCordOnBoard = yCordOnBoard;
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
    public boolean hasMinionOnSquare() {
        return card != null;
    }

    public ImageView getImageView() {
        return imageView;
    }


    public MinionCard getCard() {
        return card;
    }

    public boolean equals(Square square) {
        return square.getxCordOnGUI() == xCordOnGUI && square.getyCordOnGUI() == yCordOnGUI;
    }

    @Override
    public String toString() {
        return "Square{" +
                "xCordOnGUI=" + xCordOnGUI +
                ", yCordOnGUI=" + yCordOnGUI +
                ", xCordOnBoard=" + xCordOnBoard +
                ", yCordOnBoard=" + yCordOnBoard +
                ", width=" + width +
                ", heigth=" + heigth +
                ", rectangle=" + rectangle +
                ", imageView=" + imageView +
                ", card=" + card +
                '}';
    }
}
