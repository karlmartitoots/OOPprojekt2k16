package userinterface;

import card.MinionCard;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Square {
    private int xCordOnBoard;
    private int yCordOnBoard;
    private final static int xTopMostValue = 260;
    private final static int yLeftMostValue = 0;
    private final static int width = 50;
    private final static int height = 50;
    private int xPixelCoordinate;
    private int yPixelCoordinate;
    private MinionCard card;
    private Image defaultSquare = new Image("defaultSquare.jpg");
    private Image squareInMovementReach = new Image("chosenSquare.jpg");
    private ImageView imageView = new ImageView(defaultSquare);

    /**
     * Default constructor
     */
    Square() {
    }
    /**
     * Constructor
     *
     * @param xCordOnBoard x Coordinates of the square on the Main
     * @param yCordOnBoard Y Coordinates of the square on the Main
     */
    Square(int xCordOnBoard, int yCordOnBoard, MinionCard card) {
        this.xCordOnBoard = xCordOnBoard;
        this.yCordOnBoard = yCordOnBoard;
        this.card = card;
        xPixelCoordinate = xCordOnBoard * width + xTopMostValue;
        yPixelCoordinate = yCordOnBoard * height + yLeftMostValue;
        if (hasMinionOnSquare()) {
            imageView.setImage(card.getSmallImage());
        }
        imageView.setX(xPixelCoordinate);
        imageView.setY(yPixelCoordinate);
    }

    /**
     * For getting the adjuster of the gameboard position on GUI.
     * @return Returns the x-coordinate adjustment.
     */
    static int getxTopMostValue() {
        return xTopMostValue;
    }

    /**
     * For getting the adjuster of the gameboard position on GUI.
     * @return Returns the y-coordinate adjustment.
     */
    static int getyLeftMostValue() {
        return yLeftMostValue;
    }

    /**
     * Calculates the value of the square as if all the squares on the board would be in straight line. This is done using the formula xDim*xCordOnBoard+yCordOnBoard
     * @return The place the square would be on the line
     */
    public int squares1DPosition() {
        return GameBoard.getxDimension() * xCordOnBoard + yCordOnBoard;
    }

    /**
     * Static factory method that takes a Point2D and returns the position of the specific point on the game board
     *
     * @param point2D 2 dimensional coordinate of the square
     * @return 1 dimension coordinate of the squaare
     */

    public static int pointToSquare1DPosition(Point2D point2D) {
        return (int) (GameBoard.getxDimension() * point2D.getX() + point2D.getY());
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
    int getxCordOnBoard() {
        return xCordOnBoard;
    }

    /**
     * Getter method for the y-coordinate of the square on the gameboard.
     * @return Returns the y-coordinate in gameboard unit lengths.
     */
    int getyCordOnBoard() {
        return yCordOnBoard;
    }

    /**
     * A method, for when the gameboard shows a path on some action, to change the square to
     * a picture of a path square.
     */
    void setImageAsMoveableSquare() {
        setSquaresImageView(squareInMovementReach);
    }

    /**
     * A method, for when the gameboard shows a path on some action, to change the square back
     * to what it is like by default.
     */
    void setImageToCardImageOrDefault() {
        if (card == null) {
            setSquaresImageView(defaultSquare);
        } else setSquaresImageView(card.getSmallImage());
    }

    /**
     * Getter for the width of the square.
     * @return Returns the width of the square in pixels.
     */
    static int getSquareWidth() {
        return width;
    }

    /**
     * Getter method for square height in pixels.
     * @return Returns the height of the square in pixels.
     */
    static int getSquareHeight() {
        return height;
    }


    /**
     * Places the card on the board.
     * @param minionCard card to place on square
     */
    public void setSquaresCard(MinionCard minionCard) {
        if (minionCard == null) {
            this.card = null;
            imageView.setImage(defaultSquare);
        } else {
            this.card = minionCard;
            minionCard.setCurrentPosition(this);
            setSquaresImageView(minionCard.getSmallImage());
        }
    }

    /**
     * Method that sets an image on the imageView
     * @param image Image to be placed on board
     */
    private void setSquaresImageView(Image image) {
        //For future reference: imageView.updateImage(image) breaks the code so make the change only when it does not brake it!!!
        imageView = new ImageView(image);
        imageView.setX(xPixelCoordinate);
        imageView.setY(yPixelCoordinate);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }
    /**
     * Updates the image of the square when gameboard is changed.
     */
    void updateImage() {
        Image image;
        if (hasMinionOnSquare()) {
            image = card.getSmallImage();
        } else {
            image = defaultSquare;
        }
        setSquaresImageView(image);
    }

    /**
     * Removes a card from the current square
     */
    void removeCardIfHas() {
        card = null;
    }

    /**
     * Checks if the square conatains a card
     *
     * @return true if has a card on the square, false if otherwise
     */
    boolean hasMinionOnSquare() {
        return card != null;
    }

    boolean doesNotHaveMinionOnSquare(){
        return card == null;
    }

    /**
     * Gets the picture, that the square will currently display.
     * @return Returns that pictures ImageView.
     */
    ImageView getImageView() {
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
