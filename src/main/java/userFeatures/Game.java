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
                showPossibleSquares(root, event)
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
     * Triggers when a minion/general is pressed on on the gameboard. Shows possible squares for
     * the said minion/general.
     * @param root  Group that shows the scene, where events are listened.
     * @param event Event to be listened for.
     */
    private void showPossibleSquares(Group root, MouseEvent event) {
        //saves the pixel x and y coordinates for the point clicked on
        Point2D point2D = getSquare(event.getSceneX(), event.getSceneY());
        //if a list called toRevert has size more than 0, execute the next lines
        if (gameBoard.getToRevert().size() > 0) {
            //iterate through that list of squares and set all the squares into defaultimages
            for (Square square : gameBoard.getToRevert()) {
                square.setNotOnThePath();
                //show each of those squares
                root.getChildren().add(square.getImageView());
            }
            //still, if the list has size more than 0:
            //and if a variable in gameBoard called selectedSquare is not null
            //AND if that same square doesnt have a card on it
            if (gameBoard.getSelectedSquare() != null && gameBoard.getSelectedSquare().hasMinionOnSquare()) {
                //if the saved point is not -1, meaning getSquare function didn't find it on the gameboard...
                if (point2D.getX() != -1) {
                    //then the square was found and is chosen on the board
                    Square target = gameBoard.getBoardBySquares().get((int) (point2D.getX() * gameBoard.getxDimension() + point2D.getY()));
                    //a card is moved to that square
                    gameBoard.moveCard(gameBoard.getSelectedSquare(), target);
                    //each squares image is updated
                    for (Square square : gameBoard.getBoardBySquares()) {
                        square.updateImage();
                        root.getChildren().add(square.getImageView());
                    }
                }
            }
            gameBoard.clearRevertable();
        }
        //if there isn't anything to revert, set the selected square as the square clicked on
        gameBoard.setSelectedSquare(point2D);
        //
        if (gameBoard.getSelectedSquare() != null && gameBoard.getSelectedSquare().hasMinionOnSquare()) {
            List<Square> possibleSquares = gameBoard.getAllPossibleSquares();
            gameBoard.setToRevert(possibleSquares);
            for (Square possibleSquare : possibleSquares) {
                possibleSquare.setOnThePath();
                root.getChildren().add(possibleSquare.getImageView());
            }
        }
    }

    /**
     * Gets the X coordinates of the given square in pixels.
     *
     * @param squaresXCoordOnBoard The X coordinate of the square on the board
     * @return The X Pixel coordinates of the given square.
     */
    public double getSquaresXCoordinatesInPixels(int squaresXCoordOnBoard) {
        return squaresXCoordOnBoard * Square.getWidth() + Square.getxTopMostValue();
    }

    /**
     * Gets the Y coordinates of the given square in pixels.
     *
     * @param squaresYCoord The Y coordinate of the square on the board
     * @return The Y Pixel coordinates of the given square.
     */
    public double getSquaresYCoordinatesInPixels(int squaresYCoord) {
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
    public Point2D getSquare(double pixelX, double pixelY) {
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
