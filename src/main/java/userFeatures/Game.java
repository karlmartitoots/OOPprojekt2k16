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

class Game extends Scene{

    private GameBoard gameBoard = new GameBoard();
    private int timerStartTime = 60;
    private final ScheduledExecutorService timerScheduler =
            Executors.newScheduledThreadPool(1);
    //white starts
    private Side currentSide = Side.WHITE;
    private int turnCounter = 0;

    /**
     * The constructor of Game conducts all whats happening in gamelogic on gui.
     * @param root  Group, that everything is set on.
     * @param primaryStage Stage for the scene.
     * @param settings Initial settings that are loaded, when the game begins.
     */
    Game(Group root, Stage primaryStage, Settings settings) {
        super(root);

        //set generals in  creaturesOnBoard
        CreaturesOnBoard creaturesOnBoard = new CreaturesOnBoard();
        creaturesOnBoard.setAllGeneralsOnBoard(settings.getWhiteGeneral(), settings.getBlackGeneral());

        //load the gameframe onto the gui
        ImageView gameFrame = new ImageView(new Image("GUI frame.jpg"));
        root.getChildren().add(gameFrame);

        //load the board onto the gui
        loadGameboardForStartOfGame(root);

        //load the generals onto the board
        //TODO: FIX generals loading, some line of code is missing somewhere probably
        placeGenerals(root,settings);

        //load the timer and a label that shows whos turn it is
        Text timerText = createAndPlaceTimer(root);
        Label turnLabel = createAndPlaceTurnLabel(root);
        ScheduledFuture<?> timerControl = timerScheduler.scheduleAtFixedRate((Runnable) () ->
                reduceTimerAndSwitchTurnIfTimeOver(timerText, turnLabel),0L, 1L, SECONDS);

        //load the turn ending button
        Button endTurnButton = createAndPlaceEndTurnButton(root);

        //event listener for turn ending button
        endTurnButton.setOnAction(event ->
                switchTurnAndResetToStartOfATurn(timerText, turnLabel));

        //event listener for handling the clicks on the group.
        //TODO: sometimes the mouseclick eventlistener gets negative coordinates. Needs to be fixed somehow.
        root.setOnMouseClicked((event) ->
                mouseEventHandler(root, event)
        );

        //When the GUI window is closed, stops the timer scheduler thread.
        primaryStage.setOnCloseRequest(event -> timerScheduler.shutdown());

        setPrimaryStageProperties(primaryStage);
        primaryStage.setScene(this);
        //primaryStage.getIcons().add(gameIcon);//don't know why this doesn't work
        String gameTitle = "Card Game 1.0";
        primaryStage.setTitle(gameTitle);
        primaryStage.show();
    }

    /**
     * Method to initially place both generals on the GUI and in gameboard class.
     * @param settings Settings for getting which GeneralCard's and where they will be placed.
     */
    private void placeGenerals(Group root, Settings settings){
        Point2D whiteGeneralStartingCoordinates = settings.getWhiteStartingSquare();
        Point2D blackGeneralStartingCoordinates = settings.getBlackStartingSquare();
        //get the squares for generals
        Square whiteGeneralSquare = gameBoard.getBoardBySquares().get(
                (int) (whiteGeneralStartingCoordinates.getX()*gameBoard.getxDimension() + whiteGeneralStartingCoordinates.getY())),
                blackGeneralSquare = gameBoard.getBoardBySquares().get(
                (int) (blackGeneralStartingCoordinates.getX()*gameBoard.getxDimension() + blackGeneralStartingCoordinates.getY()));
        //add in the generals and set their sides
        whiteGeneralSquare.setCard(settings.getWhiteGeneral());
        whiteGeneralSquare.getCard().setSide(Side.WHITE);
        blackGeneralSquare.setCard(settings.getBlackGeneral());
        blackGeneralSquare.getCard().setSide(Side.BLACK);
        root.getChildren().add(whiteGeneralSquare.getImageView());
        root.getChildren().add(blackGeneralSquare.getImageView());
    }

    /**
     * Creates a label that shows, who's turn it is currently.
     * @param root Group that the label will be shown on.
     * @return Returns the created label
     */
    private Label createAndPlaceTurnLabel(Group root) {
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
    private Button createAndPlaceEndTurnButton(Group root) {
        Button endTurnButton = new Button("END TURN");
        endTurnButton.relocate(5, 250);
        endTurnButton.setMinSize(250, 245);
        endTurnButton.setFont(new Font(40));
        root.getChildren().add(endTurnButton);
        return endTurnButton;
    }

    /**
     * Initiates the board on the root group.
     * @param root Group that the squares will be loaded in on.
     */
    private void loadGameboardForStartOfGame(Group root) {
        for (int x = 0; x < gameBoard.getxDimension(); x++) {
            for (int y = 0; y < gameBoard.getyDimension(); y++) {
                Square initialSquare;
                gameBoard.addSquareToBoardBySquares(initialSquare = new Square(x, y, null));
                root.getChildren().add(initialSquare.getImageView());
            }
        }
    }
    /**
     * Method for declaring the properties of the game GUI.
     * @param primaryStage Primary stage of GUI.
     */
    private void setPrimaryStageProperties(Stage primaryStage) {
        int GUITopPanelHeight = 47;//pixels
        int preferredGUIWidth = 1000;
        int preferredGUIHeight = 800 + GUITopPanelHeight;
        int preferredCardHeight = 250;//pixels
        int preferredCardWidth = 140;//pixels
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
        int cardSlotNumber = getCardSlot(event.getSceneX(), event.getSceneY());
        System.out.println(cardSlotNumber);
        setSquaresNotOnPath(root);
        moveMinionOnScene(root, squareCoordinates);
        gameBoard.clearSquaresPossibleToMove();
        gameBoard.setSelectedSquare(squareCoordinates);
        getSquaresPossibleToMove(root);
        System.out.println("X: " + event.getX() + ", Y:" + event.getY());
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
     * @return Coordinates of the square. -1,-1 if not a square
     */

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
     * Finds the card slot on the board by their pixel value.
     *
     * @param pixelX X position of the mouse event
     * @param pixelY Y position of the mouse event
     * @return The card slot number that has been currently clicked on, -1 if not a card slot
     */
    private int getCardSlot(double pixelX, double pixelY) {
        //Currently has magic values for total number of card slots, and the pixel values. I think we can make a new class for the slots
        //Similar to square, and hand can be the place where all of them are.
        for (int possibleCardSlot = 0; possibleCardSlot < 7; possibleCardSlot++) {
            double left = possibleCardSlot * 140;
            double top = 500;
            Rectangle rectangle = new Rectangle(left, top, 140, 250);
            if (rectangle.contains(pixelX, pixelY)) {
                return possibleCardSlot;
            }
        }
        return -1;
    }

    /**
     * Changes the currentSide variable into the opposite side.
     */
    private void switchCurrentSide(){
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
    private Text createAndPlaceTimer(Group root){
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
    private void reduceTimerAndSwitchTurnIfTimeOver(Text timerText, Label sideLabel){
        if(timerText.getText().equals("1")){
            switchTurnAndResetToStartOfATurn(timerText, sideLabel);
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
    private void switchTurnAndResetToStartOfATurn(Text timerText, Label sideLabel){
        resetTimer(timerText);
        setAllCreaturesToHaventMoved();
        switchCurrentSide();
        sideLabel.setText(currentSide.toString());
        incrementTurnCounter();
    }

    /**
     * Increases the turn counter by 1.
     */
    private void incrementTurnCounter(){
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
