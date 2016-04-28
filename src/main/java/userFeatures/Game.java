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

        playerBySideString.put("white", white);
        playerBySideString.put("black", black);

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
        for (int i = 0; i < gameBoard.getxDimension(); i++) {
            for (int j = 0; j < gameBoard.getyDimension(); j++) {
                Square square;
                if (gameBoard.getGameBoard()[i][j] == 0) {
                    square = new Square(i, j, null);
                    root.getChildren().add(square.getImageView());
                } else if(gameBoard.getGameBoard()[i][j] <= 100){
                    square = new Square(i, j, CreaturesOnBoard.getAllGeneralsOnBoard().get(Math.abs(gameBoard.getGameBoard()[i][j])));
                    root.getChildren().add(square.getImageView());
                } else {
                    square = new Square(i, j, CreaturesOnBoard.getAllMinionsOnBoard().get(Math.abs(gameBoard.getGameBoard()[i][j])));
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
        Point2D point2D = getSquare(event.getSceneX(), event.getSceneY());
        if (gameBoard.getToRevert().size() > 0) {
            for (Square square : gameBoard.getToRevert()) {
                if (!root.getChildren().contains(square)) {
                    square.setNotOnThePath();
                    root.getChildren().add(square.getImageView());
                }
            }
            if (gameBoard.getSelectedSquare() != null && gameBoard.getSelectedSquare().hasMinionOnSquare()) {
                if (point2D.getX() != -1) {
                    Square target = gameBoard.getBoardBySquares().get((int) (point2D.getX() * gameBoard.getxDimension() + point2D.getY()));
                    gameBoard.moveCard(gameBoard.getSelectedSquare(), target);
                    gameBoard.updateBoard();
                    for (Square square : gameBoard.getBoardBySquares()) {
                        square.updateImage();
                        root.getChildren().add(square.getImageView());
                    }
                }
            }
            gameBoard.clearRevertable();
        }
        gameBoard.setSelectedSquare(point2D);

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
     * @param x The X coordinate of the square on the board
     * @return The X Pixel coordinates of the given square.
     */
    public double getPixelsForSquareX(int x) {
        return x * Square.getWidth() + Square.getxTopMostValue();
    }

    /**
     * Gets the Y coordinates of the given square in pixels.
     *
     * @param y The Y coordinate of the square on the board
     * @return The Y Pixel coordinates of the given square.
     */
    public double getPixelsForSquareY(int y) {
        return y * Square.getHeight() + Square.getyLeftMostValue();
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
                Rectangle rectangle = new Rectangle(left, top, Square.getWidth(), Square.getHeight());
                if (rectangle.contains(pixelX, pixelY)) {
                    return new Point2D(x, y);
                }
            }

        }
        return new Point2D(-1, -1);
    }


}
