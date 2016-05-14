package userinterface;

import card.MinionCard;
import javafx.animation.PauseTransition;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
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
        imageView.setImage(squareInMovementReach);
    }

    /**
     * A method, for when the gameboard shows a path on some action, to change the square back
     * to what it is like by default.
     */
    void setImageToCardImageOrDefault() {
        if (card == null) {
            imageView.setImage(defaultSquare);
        } else imageView.setImage(card.getSmallImage());
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
            imageView.setImage(minionCard.getSmallImage());
        }
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
        imageView.setImage(image);
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

    public void flashSquareInColor(Color color, Group root){
        Platform.runLater(() -> {
            Rectangle rect = new Rectangle ();
            rect.setStrokeWidth(4);
            rect.setFill(Color.TRANSPARENT);
            rect.setX(xPixelCoordinate);
            rect.setY(yPixelCoordinate);
            rect.setWidth(width);
            rect.setHeight(height);

            StrokeTransition ft = new StrokeTransition(Duration.millis(300), rect, Color.BLACK, color);
            ft.setCycleCount(4);
            ft.setAutoReverse(true);
            root.getChildren().add(rect);

            ft.play();
            ft.setOnFinished(event -> {
                root.getChildren().removeAll(rect, this.imageView);
                root.getChildren().add(this.imageView);
            });
        });
    }

    public void showHitSplatOnSquare(int damage, Group parentGroup){
        Platform.runLater(() -> {
            ImageView hitsplat = new ImageView(new Image("hitsplat.png"));
            hitsplat.setX(xPixelCoordinate);
            hitsplat.setY(yPixelCoordinate);
            hitsplat.setFitWidth(width);
            hitsplat.setFitHeight(height);

            int textAdjustmentFromSquareCorner = 15;//pixels
            Text damageText = new Text(String.valueOf(damage));//square is 50x50 so this should be 30x30
            damageText.setFont(new Font(25));
            int damageTextWidth = 5;
            damageText.setX(xPixelCoordinate + textAdjustmentFromSquareCorner - damageTextWidth);
            damageText.setY(yPixelCoordinate + Square.getSquareHeight() - textAdjustmentFromSquareCorner);

            parentGroup.getChildren().addAll(hitsplat, damageText);
            PauseTransition pt = new PauseTransition(Duration.millis(700));

            pt.play();
            pt.setOnFinished(event -> {
                parentGroup.getChildren().removeAll(hitsplat, damageText, this.imageView);
                parentGroup.getChildren().add(this.imageView);
            });
        });
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
                ", card=" + card.getName() +
                '}';
    }
}
