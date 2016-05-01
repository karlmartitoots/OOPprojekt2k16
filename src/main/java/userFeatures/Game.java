package userFeatures;

import board.CreaturesOnBoard;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Game extends Scene{

    private GameBoard gameBoard = new GameBoard();
    private CreaturesOnBoard creaturesOnBoard = new CreaturesOnBoard();
    private int timerStartTime = 60;
    private final ScheduledExecutorService timerScheduler =
            Executors.newScheduledThreadPool(1);
    private ImageView gameFrame = new ImageView(new Image("GUI frame.jpg"));
    private String gameTitle = "Card Game 1.0";
    private int turnCounter = 0;//currently has no application
    private Player white, black;//currently have no application
    //white starts
    private Side currentSide = Side.WHITE;

    /**
     * The constructor of Game conducts all whats happening in gamelogic on gui.
     * @param root  Group, that everything is set on.
     * @param primaryStage Stage for the scene.
     * @param settings Initial settings that are loaded, when the game begins.
     */
    Game(Group root, Stage primaryStage, Settings settings) {
        super(root);

        //set generals in  creaturesOnBoard
        creaturesOnBoard.setAllGeneralsOnBoard(settings.getWhiteGeneral(), settings.getBlackGeneral());

        //set sides on the generals
        settings.getWhiteGeneral().setSide(Side.WHITE);
        settings.getBlackGeneral().setSide(Side.BLACK);

        //place generals on board
        gameBoard.placeGenerals(settings.getWhiteGeneral(), settings.getBlackGeneral(),
                settings.getWhiteStartingSquare(), settings.getBlackStartingSquare());

        //load the gameframe onto the gui
        root.getChildren().add(gameFrame);

        //load the board onto the gui
        loadBoard(root);

        //load the timer and a label that shows whos turn it is
        Text timerText = placeTimer(root);
        Label turnLabel = loadTurnLabel(root);
        ScheduledFuture<?> timerControl = timerScheduler.scheduleAtFixedRate((Runnable) () ->
                reduceTimer(timerText, turnLabel),0L, 1L, SECONDS);

        //load the turn ending button
        Button endTurnButton = loadEndTurnButton(root);

        //event listener for turn ending button
        endTurnButton.setOnAction(event ->
                switchTurn(timerText, turnLabel));

        //event listener for handling the clicks on the group.
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
     * Creates a label that shows, who's turn it is currently.
     * @param root Group that the label will be shown on.
     * @return Returns the created label
     */
    private Label loadTurnLabel(Group root) {
        Label turnLabel = new Label(currentSide.toString());
        turnLabel.relocate(80, 10);
        turnLabel.setFont(new Font(30));
        root.getChildren().add(turnLabel);
        return turnLabel;
    }

    /**
     * Creates a button to end the current players turn.
     * @param root Group that the button will be shown on.
     * @return Returns the button itself.
     */
    private Button loadEndTurnButton(Group root) {
        Button endTurnButton = new Button("End turn");
        endTurnButton.relocate(100, 250);
        root.getChildren().add(endTurnButton);
        return endTurnButton;
    }

    /**
     * Initiates the board on the root group.
     * @param root Group to initiate board on.
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
     * @param root Group that shows the scene, where events are listned
     */
    private void getSquaresPossibleToMove(Group root) {
        if (gameBoard.getSelectedSquare().hasMinionOnSquare() && !gameBoard.getSelectedSquare().getCard().hasMoved() && gameBoard.getSelectedSquare().getCard().getSide() == currentSide) {
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
     * @param root Group that shows the scene, where events are listened
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

    /**
     * Changes the currentSide variable into the opposite side.
     */
    private void switchSides(){
        if(this.currentSide == Side.WHITE)
            this.currentSide = Side.BLACK;
        else this.currentSide = Side.WHITE;
    }

    /**
     * Getter method for getting the side who is currently playing.
     * @return Returns the side who's turn it is currently.
     */
    private Side getCurrentSide(){
        return this.currentSide;
    }

    /**
     * Creates and places the timer on the game GUI.
     * @param root Group, that the timer text will be placed on.
     * @return Returns the text that shows the current time left on someone's turn.
     */
    private Text placeTimer(Group root){
        Text timerText = new Text(10, 210, String.valueOf(timerStartTime));
        timerText.setFont(new Font(220));
        root.getChildren().add(timerText);
        return timerText;
    }

    /**
     * Method for reducing the timer by one unit or switching turns when the time is up.
     * @param timerText The text, which shows the time.
     * @param sideLabel Label, that shows who's turn it currently is.
     */
    private void reduceTimer(Text timerText, Label sideLabel){
        if(timerText.getText().equals("1")){
            switchTurn(timerText, sideLabel);
        }else {
            timerText.setText(String.valueOf(Integer.parseInt(timerText.getText()) - 1));
        }
    }

    /**
     * Method for resetting the timer to the time in the beginning of a turn.
     * @param timerText The text, which shows the time.
     */
    private void resetTimer(Text timerText){
        timerText.setText(String.valueOf(timerStartTime));
    }

    /**
     * Switches turns and does all the operations needed in the end and start of a turn.
     * @param timerText The text, which shows the time.
     * @param sideLabel Label, that shows who's turn it currently is.
     */
    private void switchTurn(Text timerText, Label sideLabel){
        resetTimer(timerText);
        setAllCreaturesToHaventMoved();
        switchSides();
        sideLabel.setText(currentSide.toString());
        increaseTurnCounter();
    }

    private void increaseTurnCounter(){
        turnCounter += 1;
    }
    /**
     * Finds all the creatures on the gameboard and sets their hasMoved state to false.
     */
    private void setAllCreaturesToHaventMoved(){
        for (Square square : gameBoard.getBoardBySquares()) {
            if(square.hasMinionOnSquare()){
                square.getCard().setMoved(false);
            }
        }
    }

}
