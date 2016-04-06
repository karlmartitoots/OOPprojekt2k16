package userFeatures;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Animation extends Application {

    private Square exampleSquare = new Square(0, 0);
    private GameBoard gameBoard = new GameBoard();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        root.setOnMouseClicked((event) -> {
            System.out.println(event.getSceneX() + " , " + event.getSceneY());
            System.out.println(getSquare(event.getSceneX(), event.getSceneY()));
        });
        root.setPrefSize(2 * gameBoard.getxDimension() * exampleSquare.getWidth(), gameBoard.getyDimension() * exampleSquare.getHeigth());
        for (int i = 0; i < gameBoard.getxDimension(); i++) {
            for (int j = 0; j < gameBoard.getyDimension(); j++) {
                Square square = new Square(i, j);
                square.setFill(Color.LIGHTBLUE);
                square.setStroke(Color.BLACK);
                root.getChildren().add(square);


            }
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Card game");
        primaryStage.show();


    }

    public double getPixelsX(int x) {
        return x * exampleSquare.getWidth();
    }

    public double getPixelsY(int y) {
        return y * exampleSquare.getHeigth();
    }

    public Point2D getSquare(double pixelX, double pixelY) {
        for (int x = 0; x < gameBoard.getxDimension(); x++) {
            for (int y = 0; y < gameBoard.getyDimension(); y++) {
                double left = getPixelsX(x);
                double top = getPixelsY(y);
                Rectangle rectangle = new Rectangle(left, top, exampleSquare.getWidth(), exampleSquare.getHeigth());
                if (rectangle.contains(pixelX, pixelY)) {
                    return new Point2D(x, y);
                }
            }

        }
        return new Point2D(-1, -1);
    }
}
