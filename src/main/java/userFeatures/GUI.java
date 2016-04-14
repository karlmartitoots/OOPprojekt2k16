package userFeatures;

import card.GeneralCard;
import card.Generals;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GUI extends Application {

    private Square exampleSquare = new Square(0, 0, null);
    private GameBoard gameBoard = new GameBoard();
    private Generals generals = new Generals();

    public GUI() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GeneralCard taavi = generals.getAllGenerals().get(1);
        GeneralCard märt = generals.getAllGenerals().get(2);
        gameBoard.placeGenerals(taavi, märt);
        Pane root = new Pane();
        root.setOnMouseClicked((event) -> {
            Point2D point2D = getSquare(event.getSceneX(), event.getSceneY());
            gameBoard.setSelectedSquare(point2D);
            System.out.println(gameBoard.getSelectedSquare());
            if (gameBoard.getSelectedSquare() != null && gameBoard.getSelectedSquare().hasCardOnSquare()) {
                List<Square> possibleSquares = gameBoard.getAllPossibleSquares();
                for (Square possibleSquare : possibleSquares) {
                    possibleSquare.setFill(Color.RED);
                    possibleSquare.setStroke(Color.BLACK);
                    root.getChildren().add(possibleSquare);
                }
            }


        });

        double prefWidth = 2 * gameBoard.getxDimension() * exampleSquare.getWidth();
        double prefHeight = gameBoard.getyDimension() * exampleSquare.getHeigth() + 50;

        root.setPrefSize(prefWidth, prefHeight);
        for (int i = 0; i < gameBoard.getxDimension(); i++) {
            for (int j = 0; j < gameBoard.getyDimension(); j++) {
                Square square;
                if (gameBoard.getGameBoard()[i][j] == 0) {
                    square = new Square(i, j, null);
                    square.setFill(Color.LIGHTBLUE);
                    square.setStroke(Color.BLACK);
                    root.getChildren().add(square);

                } else {
                    square = new Square(i, j, generals.getAllGenerals().get(Math.abs(gameBoard.getGameBoard()[i][j])));
                    root.getChildren().add(square.getImageView());
                }
                gameBoard.addSquare(square);
            }
        }

        Scene scene = new Scene(root);
        /*
        Width and Height listeners for resizable support
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Height: " + newSceneHeight);
            }
        });
        TODO: make resizable
        */

        primaryStage.setMaxWidth(prefWidth);
        primaryStage.setMaxHeight(prefHeight);
        primaryStage.setMinWidth(prefWidth);
        primaryStage.setMinHeight(prefHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Card game");
        primaryStage.show();


    }

    /**
     * Gets the X coordinates of the given square in pixels.
     *
     * @param x The X coordinate of the square on the board
     * @return The X Pixel coordinates of the given square.
     */
    public double getPixelsForSquareX(int x) {
        return x * exampleSquare.getWidth();
    }

    /**
     * Gets the Y coordinates of the given square in pixels.
     *
     * @param y The Y coordinate of the square on the board
     * @return The Y Pixel coordinates of the given square.
     */
    public double getPixelsForSquareY(int y) {
        return y * exampleSquare.getHeigth();
    }

    /**
     * Finds the coordinates of the square on the board by their pixel values
     *
     * @param pixelX X pixel coordinates of the square
     * @param pixelY Y pixel coordinates of the square
     * @return Coordinates of the square.
     */
    //I have no idea what is the best data structure to use to store the coordinates of points so it is subject to change still.
    public Point2D getSquare(double pixelX, double pixelY) {
        for (int x = 0; x < gameBoard.getxDimension(); x++) {
            for (int y = 0; y < gameBoard.getyDimension(); y++) {
                double left = getPixelsForSquareX(x);
                double top = getPixelsForSquareY(y);
                Rectangle rectangle = new Rectangle(left, top, exampleSquare.getWidth(), exampleSquare.getHeigth());
                if (rectangle.contains(pixelX, pixelY)) {
                    return new Point2D(x, y);
                }
            }

        }
        return new Point2D(-1, -1);
    }
}
