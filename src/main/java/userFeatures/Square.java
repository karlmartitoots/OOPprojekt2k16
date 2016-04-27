package userFeatures;

import card.MinionCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Square {
    private int xCordOnBoard;
    private int yCordOnBoard;
    private final static int xTopMostValue = 260;
    private final static int yLeftMostValue = 0;
    private final static int width = 50;
    private final static int height = 50;
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
        }
        imageView.setX(xCordOnBoard * width + xTopMostValue);
        imageView.setY(yCordOnBoard * height + yLeftMostValue);
    }

    /**
     * For getting the adjuster of the gameboard position on GUI.
     * @return Returns the x-coordinate adjustment.
     */
    public static int getxTopMostValue() {
        return xTopMostValue;
    }

    /**
     * For getting the adjuster of the gameboard position on GUI.
     * @return Returns the y-coordinate adjustment.
     */
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
     * Calculates the moves that are needed to move from this Square to toMoveTo. Movement is horizontal and vertical.
     * @param toMoveTo The square to move to.
     * @return Returns the amount of steps needed to take to reach the square toMoveTo.
     */
    public int getDistance(Square toMoveTo) {
        return Math.abs(xCordOnBoard - toMoveTo.getxCordOnBoard()) + Math.abs(yCordOnBoard - toMoveTo.getyCordOnBoard());
    }

    /**
     * Getter method for the x-coordinate of the square on the gameboard.
     * @return Returns the x-coordinate in gameboard unit lengths.
     */
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

    /**
     * A method, for when the gameboard shows a path on some action, to change the square to
     * a picture of a path square.
     */
    public void setOnThePath() {
        double tempX = imageView.getX(), tempY = imageView.getY();
        this.imageView = new ImageView(new Image("chosenSquare.jpg"));
        imageView.setX(tempX);
        imageView.setY(tempY);
    }

    /**
     * A method, for when the gameboard shows a path on some action, to change the square back
     * to what it is like by default.
     */
    public void setNotOnThePath(){
        double tempX = imageView.getX(), tempY = imageView.getY();
        this.imageView = new ImageView(new Image("defaultSquare.jpg"));
        imageView.setX(tempX);
        imageView.setY(tempY);
    }

    /**
     * Getter for the width of the square.
     * @return Returns the width of the square in pixels.
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Getter method for square height in pixels.
     * @return Returns the height of the square in pixels.
     */
    public static int getHeight() {
        return height;
    }


    /**
     * Places the card on the board.
     * @param card card to place on square
     */
    public void placeCard(MinionCard card) {
        this.card = card;
    }

    /**
     * Updates the image of the square when gameboard is changed.
     */
    public void updateImage() {
        if (hasMinionOnSquare()) {
            imageView = new ImageView(card.getSmallImage());
            imageView.setX(xCordOnBoard * width + xTopMostValue);
            imageView.setY(yCordOnBoard * height + yLeftMostValue);
        } else {
            imageView = new ImageView(new Image("defaultSquare.jpg"));
            imageView.setX(xCordOnBoard * width + xTopMostValue);
            imageView.setY(yCordOnBoard * height + yLeftMostValue);
        }

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

    /**
     * Gets the picture, that the square will currently display.
     * @return Returns that pictures ImageView.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Getter for the squares minionCard.
     * @return Gets the minionCard that the square currently holds.
     */
    public MinionCard getCard() {
        return card;
    }

    /**
     * A square is different from another square, if their coordinates differ between eachother.
     * @param square The Square that this square will be compared to.
     * @return Returns true, if the square has the exact same coordinates and false otherwise.
     */
    public boolean equals(Square square) {
        return square.getxCordOnBoard() == xCordOnBoard && square.getyCordOnBoard() == yCordOnBoard;
    }

    @Override
    public String toString() {
        return "Square{" +
                "xCordOnBoard=" + xCordOnBoard +
                ", yCordOnBoard=" + yCordOnBoard +
                ", width=" + width +
                ", height=" + height +
                ", imageView=" + imageView +
                ", card=" + card +
                '}';
    }
}
