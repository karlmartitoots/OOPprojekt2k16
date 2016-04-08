package userFeatures;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Square extends Parent {
    private int xCord;
    private int yCord;
    private final int width = 50;
    private final int heigth = 50;
    private Rectangle rectangle;

    /**
     * Constructor
     *
     * @param xCord x Coordinates of the square on the game board
     * @param yCord Y Coordinates of the square on the game board
     */
    public Square(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
        rectangle = new Rectangle(xCord * width, yCord * heigth, width, heigth);
        getChildren().add(rectangle);
        setOnMouseClicked(event -> {
            if (rectangle.getFill() == Color.RED) {
                rectangle.setFill(Color.BLUE);
            } else rectangle.setFill(Color.RED);
        });
    }

    /**
     * Calculates the value of the square as if all the squares on the board would be in straight line. This is done using the formula xDim*xCord+yCord
     * @param xDim The X dimension of the board
     * @return The place the square would be on the line
     */
    public int integerValue(int xDim) {
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

    @Override
    public String toString() {
        return "Node{" +
                "xCord=" + xCord +
                ", yCord=" + yCord +
                '}';
    }
}
