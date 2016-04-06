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

    public int integerValue(int xDim) {
        return xDim * xCord + yCord;
    }

    public int getDistance(Square square) {
        return Math.abs(xCord - square.getxCord()) + Math.abs(yCord - square.getyCord());
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
