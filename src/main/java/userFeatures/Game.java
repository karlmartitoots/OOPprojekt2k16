package userFeatures;

import board.CreaturesOnBoard;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game extends Scene{

    private GameBoard gameBoard = new GameBoard();
    private CreaturesOnBoard creaturesOnBoard = new CreaturesOnBoard();
    private ImageView gameFrame = new ImageView(new Image("GUI frame.jpg"));
    private String gameTitle = "Card Game 1.0";
    private int turnCounter = 0;
    private Player white, black;
    private String side = "white";
    private Map<String, Player> playerBySideString = new HashMap<>();

    /**
     * The constructor of Game conducts all whats happening in gamelogic on gui.
     * @param root  Group, that everything is set on.
     * @param primaryStage Stage for the scene.
     * @param settings Initial settings that are loaded, when the game begins.
     */
    public Game(Group root, Stage primaryStage, Settings settings) {
        super(root);

        //playerBySideString.put("white", white);
        //playerBySideString.put("black", black);

        creaturesOnBoard.setAllGeneralsOnBoard(settings.getWhiteGeneral(), settings.getBlackGeneral());

        gameBoard.placeGenerals(settings.getWhiteGeneral(), settings.getBlackGeneral(),
                settings.getWhiteStartingSquare(), settings.getBlackStartingSquare());

        root.getChildren().add(gameFrame);

        loadBoard(root);

        root.setOnMouseClicked((event) ->
                mouseEventHandler(root, event)
        );

        //probably make 2 Player objects and a Gamecycle object, then start tossing those around in a while(!gameOver()) loop

        setPrimaryStageProperties(primaryStage, root);
        primaryStage.setScene(this);
        //primaryStage.getIcons().add(gameIcon);//don't know why this doesn't work
        primaryStage.setTitle(gameTitle);
        primaryStage.show();
    }

    /**
     * Initiates the board on the root pane.
     * @param root Pane to initiate board on.
     */
    private void loadBoard(Group root) {
        for (int x = 0; x < gameBoard.getxDimension(); x++) {
            for (int y = 0; y < gameBoard.getyDimension(); y++) {
                Square square;
                if (gameBoard.getGameBoard()[x][y] == 0) {
                    square = new Square(x, y, null);
                    root.getChildren().add(square.getImageView());
                } else if(gameBoard.getGameBoard()[x][y] <= 100){
                    square = new Square(x, y, CreaturesOnBoard.getAllGeneralsOnBoard().get(Math.abs(gameBoard.getGameBoard()[x][y])));
                    root.getChildren().add(square.getImageView());
                } else {
                    square = new Square(x, y, CreaturesOnBoard.getAllMinionsOnBoard().get(Math.abs(gameBoard.getGameBoard()[x][y])));
                    root.getChildren().add(square.getImageView());
                }
                gameBoard.addSquare(square);
            }
        }
    }
    /**
     * Method for declaring the properties of the Main pane.
     * @param primaryStage Primary stage of Main.
     * @param gamePane The Main's pane.
     */
    private void setPrimaryStageProperties(Stage primaryStage, Group gamePane) {
        int GUITopPanelHeight = 47;//pixels
        int preferredGUIWidth = 1000;
        int preferredGUIHeight = 800 + GUITopPanelHeight;
        int preferredCardHeight = 250;//pixels
        int preferredCardWidth = 125;//pixels
        primaryStage.setMaxWidth(preferredGUIWidth);
        primaryStage.setMaxHeight(preferredGUIHeight);
        primaryStage.setMinWidth(preferredGUIWidth);
        primaryStage.setMinHeight(preferredGUIHeight);
    }

    /**
     * Handles events in the case mouse is pressed
     * @param root  Group that shows the scene, where events are listened.
     * @param event Event to be listened for.
     */
    private void mouseEventHandler(Group root, MouseEvent event) {
        Point2D squareCoordinates = getSquare(event.getSceneX(), event.getSceneY());
        setSquaresNotOnPath(root);
        moveMinionOnScene(root, squareCoordinates);
        gameBoard.clearSquaresPossibleToMove();
        gameBoard.setSelectedSquare(squareCoordinates);
        getSquaresPossibleToMove(root);
    }

    /**
     * If event is registered on square that has a minion, the method displays all the squares the minion can move, as well as saves them.
     *
     * @param root Group that shows the scene, where events are listned
     */
    private void getSquaresPossibleToMove(Group root) {
        if (gameBoard.getSelectedSquare().hasMinionOnSquare() && !gameBoard.getSelectedSquare().getCard().hasMoved()) {
            List<Square> possibleSquares = gameBoard.getAllPossibleSquares();
            gameBoard.setSquaresPossibleToMove(possibleSquares);
            for (Square possibleSquare : possibleSquares) {
                possibleSquare.setOnThePath();
                root.getChildren().add(possibleSquare.getImageView());
            }
        }
    }

    /**
     * If the previously selected square had a minion on it, then the method will reposition the minion on the game board, if the minion has not moved this turn;
     *
     * @param root              Group that shows the scene, where events are listned
     * @param squareCoordinates coordinates of the square clicked on
     */
    private void moveMinionOnScene(Group root, Point2D squareCoordinates) {
        if (gameBoard.getSelectedSquare().hasMinionOnSquare()) {
            Square target = gameBoard.getBoardBySquares().get((int) (squareCoordinates.getX() * gameBoard.getxDimension() + squareCoordinates.getY()));
            gameBoard.moveCard(gameBoard.getSelectedSquare(), target);
            for (Square square : gameBoard.getBoardBySquares()) {
                square.updateImage();
                root.getChildren().add(square.getImageView());
            }
        }
    }

    /**
     * Deselects the squares which were able to move to by the previously selected minion.
     *
     * @param root Group that shows the scene, where events are listned
     */
    private void setSquaresNotOnPath(Group root) {
        for (Square square : gameBoard.getSquaresPossibleToMove()) {
            square.setNotOnThePath();
            root.getChildren().add(square.getImageView());
        }
    }

    /**
     * Gets the X coordinates of the given square in pixels.
     * @param squaresXCoordOnBoard The X coordinate of the square on the board
     * @return The X Pixel coordinates of the given square.
     */
    private double getSquaresXCoordinatesInPixels(int squaresXCoordOnBoard) {
        return squaresXCoordOnBoard * Square.getWidth() + Square.getxTopMostValue();
    }

    /**
     * Gets the Y coordinates of the given square in pixels.
     *
     * @param squaresYCoord The Y coordinate of the square on the board
     * @return The Y Pixel coordinates of the given square.
     */
    private double getSquaresYCoordinatesInPixels(int squaresYCoord) {
        return squaresYCoord * Square.getHeight() + Square.getyLeftMostValue();
    }

    /**
     * Finds the coordinates of the square on the board by their pixel values
     *
     * @param pixelX X pixel coordinates of the square
     * @param pixelY Y pixel coordinates of the square
     * @return Coordinates of the square.
     */
    //I have no idea what is the best data structure to use to store the coordinates of points so it is subject to change still.
    private Point2D getSquare(double pixelX, double pixelY) {
        for (int x = 0; x < gameBoard.getxDimension(); x++) {
            for (int y = 0; y < gameBoard.getyDimension(); y++) {
                double left = getSquaresXCoordinatesInPixels(x);
                double top = getSquaresYCoordinatesInPixels(y);
                Rectangle rectangle = new Rectangle(left, top, Square.getWidth(), Square.getHeight());
                if (rectangle.contains(pixelX, pixelY)) {
                    return new Point2D(x, y);
                }
            }

        }
        return new Point2D(-1, -1);
    }
}
