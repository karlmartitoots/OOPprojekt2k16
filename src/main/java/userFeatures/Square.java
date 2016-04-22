package userFeatures;

import card.GeneralCard;
import card.MinionCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Square {
    private int xCordOnBoard;
    private int yCordOnBoard;
    private final static int xTopMostValue = 260;
    private final static int yLeftMostValue = 0;
    private final static int width = 50;
    private final static int heigth = 50;
    private MinionCard card;
    private ImageView imageView = new ImageView(new Image("defaultSquare.jpg"));
    /**
     * Constructor
     *
     * @param xCordOnBoard x Coordinates of the square on the Main
     * @param yCordOnBoard Y Coordinates of the square on the Main
     */
    public Square(int xCordOnBoard, int yCordOnBoard, MinionCard card) {
        this.xCordOnBoard = xCordOnBoard;
        this.yCordOnBoard = yCordOnBoard;
        this.card = card;

        if (hasMinionOnSquare()) {
            imageView = new ImageView(card.getSmallImage());
            imageView.setX(xCordOnBoard * width + xTopMostValue);
            imageView.setY(yCordOnBoard * heigth + yLeftMostValue);
        }
        imageView.setX(xCordOnBoard * width + xTopMostValue);
        imageView.setY(yCordOnBoard * heigth + yLeftMostValue);
    }

    public static int getxTopMostValue() {
        return xTopMostValue;
    }

    public static int getyLeftMostValue() {
        return yLeftMostValue;
    }

    /**
     * Calculates the value of the square as if all the squares on the board would be in straight line. This is done using the formula xDim*xCordOnBoard+yCordOnBoard
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
        return Math.abs(xCordOnBoard - toMoveTo.getxCordOnBoard()) + Math.abs(yCordOnBoard - toMoveTo.getyCordOnBoard());
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

    public void setChosen() {
        double tempX = imageView.getX(), tempY = imageView.getY();
        this.imageView = new ImageView(new Image("chosenSquare.jpg"));
        imageView.setX(tempX);
        imageView.setY(tempY);
    }

    public void setNotChosen(){
        double tempX = imageView.getX(), tempY = imageView.getY();
        this.imageView = new ImageView(new Image("defaultSquare.jpg"));
        imageView.setX(tempX);
        imageView.setY(tempY);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeigth() {
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
        return square.getxCordOnBoard() == xCordOnBoard && square.getyCordOnBoard() == yCordOnBoard;
    }

    @Override
    public String toString() {
        return "Square{" +
                "xCordOnBoard=" + xCordOnBoard +
                ", yCordOnBoard=" + yCordOnBoard +
                ", width=" + width +
                ", heigth=" + heigth +
                ", imageView=" + imageView +
                ", card=" + card +
                '}';
    }
}
