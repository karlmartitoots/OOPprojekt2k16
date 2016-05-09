package userinterface;

import board.CreaturesOnBoard;
import card.GeneralCard;
import card.MinionCard;
import javafx.application.Platform;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

class Game extends Scene{

    private GameBoard gameBoard = new GameBoard();
    private Player playerWhite = new Player(Side.WHITE);
    private Player playerBlack = new Player(Side.BLACK);
    private int timerStartTime = 60;
    private InteractionState state = InteractionState.NONE;
    private final ScheduledExecutorService timerScheduler =
            Executors.newScheduledThreadPool(1);
    //white starts
    private Side currentSide = Side.WHITE;
    private Player currentPlayer = playerWhite;
    private int turnCounter = 0;
    private Label currentStateLabel;

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
        setGeneralsOnGameboardAndShowImages(root,settings);
        setDefaultCardImagesAllOnCardSlots(root);

        //load the timer and a label that shows whos turn it is
        Text timerText = createAndPlaceTimer(root);
        Label turnLabel = createAndPlaceTurnLabel(root);
        currentStateLabel = createAndPlaceStateLabel(root);
        timerScheduler.scheduleAtFixedRate(
                (Runnable) () ->
                        Platform.runLater(() ->{
                            if(primaryStage.isFocused()) {
                                reduceTimerAndSwitchTurnIfTimeOver(timerText, turnLabel, root);
                            }
                        }), 1L, 1L, SECONDS);

        //load the turn ending button
        Button endTurnButton = createAndPlaceEndTurnButton(root);
        Button moveButton = createAndPlaceButton(root, InteractionState.MOVE, 760, 380, 230, 40);
        Button attackButton = createAndPlaceButton(root, InteractionState.ATTACK, 760, 420, 230, 40);
        Button summonButton = createAndPlaceButton(root, InteractionState.SUMMON, 760, 460, 230, 40);




        //event listener for turn ending button
        endTurnButton.setOnAction(event -> {
            switchTurnAndResetToStartOfATurn(timerText, turnLabel, root);
        });
        moveButton.setOnAction(event ->
                updateLabel(currentStateLabel, InteractionState.MOVE)
        );
        attackButton.setOnAction(event ->
                updateLabel(currentStateLabel, InteractionState.ATTACK)
        );
        summonButton.setOnAction(event ->
                updateLabel(currentStateLabel, InteractionState.SUMMON)
        );

        //event listener for handling the clicks on the group.
        root.setOnMouseClicked((event) ->
                mouseEventHandler(root, event)
        );

        //When the GUI window is closed, stops the timer scheduler thread.
        primaryStage.setOnCloseRequest(event -> timerScheduler.shutdown());

        setPrimaryStageProperties(primaryStage);
        primaryStage.setScene(this);
        primaryStage.getIcons().clear();
        primaryStage.getIcons().add(new Image("gameIcon.jpg"));
        String gameTitle = "Card Game 1.0";
        primaryStage.setTitle(gameTitle);
        primaryStage.show();
    }

    /**
     * Creates a button that allows the user to change to given state in his turn
     *
     * @param root      Parent of the button
     * @param state     State to switch to
     * @param xStarting starting x position of the button
     * @param yStarting starting y position of the button
     * @param width     width of the button
     * @param height    height of the button
     * @return          Returns the button that was created.
     */
    private Button createAndPlaceButton(Group root, InteractionState state, int xStarting, int yStarting, int width, int height) {
        Button button = new Button(state.toString());
        button.relocate(xStarting, yStarting);
        button.setMinSize(width, height);
        button.setFont(new Font(20));
        root.getChildren().add(button);
        return button;
    }

    private Label createAndPlaceStateLabel(Group root) {
        Label turnLabel = new Label(state.toString());
        turnLabel.relocate(760, 120);
        turnLabel.setFont(new Font(20));
        root.getChildren().add(turnLabel);
        return turnLabel;
    }

    private void updateLabel(Label label, InteractionState state) {
        this.state = state;
        label.setText(state.toString());
    }


    /**
     * Method loads the images of cards in the given card slots
     *
     * @param root Group that the label will be shown on.
     */
    private void setDefaultCardImagesAllOnCardSlots(Group root) {
        for (int cardSlot = 0; cardSlot < Hand.getMaximumHandSize(); cardSlot++) {
            ImageView defaultCard = new ImageView(new Image("sampleCard.png"));
            defaultCard.setX(cardSlot * Hand.getPreferredCardWidth() + Hand.getLeftMostPixelValue());
            defaultCard.setY(Hand.getTopMostPixelValue());
            root.getChildren().add(defaultCard);
        }

    }
    /**
     * Method to initially place both generals on the GUI and in gameboard class.
     * @param settings Settings for getting, which GeneralCard's have been chosen and where they will be placed.
     */
    private void setGeneralsOnGameboardAndShowImages(Group root, Settings settings){
        Point2D whiteGeneralStartingCoordinates = settings.getWhiteStartingSquare();
        Point2D blackGeneralStartingCoordinates = settings.getBlackStartingSquare();
        //get the squares for generals
        Square whiteGeneralSquare = gameBoard.getBoardBySquares().get(
                Square.pointToSquare1DPosition(whiteGeneralStartingCoordinates)),
                blackGeneralSquare = gameBoard.getBoardBySquares().get(
                        Square.pointToSquare1DPosition(blackGeneralStartingCoordinates));
        //add in the generals and set their sides
        whiteGeneralSquare.setSquaresCard(settings.getWhiteGeneral());
        blackGeneralSquare.setSquaresCard(settings.getBlackGeneral());
        whiteGeneralSquare.updateImage();
        root.getChildren().add(whiteGeneralSquare.getImageView());
        blackGeneralSquare.updateImage();
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
        for (int x = 0; x < GameBoard.getxDimension(); x++) {
            for (int y = 0; y < GameBoard.getyDimension(); y++) {
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
        int cardSlotNumber = getCardSlotNumber(event.getSceneX(), event.getSceneY());
        System.out.println(cardSlotNumber);
        checkStateAndprocessClickOnBoard(root, squareCoordinates);
        System.out.println("X: " + event.getX() + ", Y:" + event.getY());
    }

    /**
     * Handles events that happens when the mouse is clicked on the board
     * @param root Group that shows the scene, where events are listened.
     * @param squareCoordinates coordinates of the square clicked on
     */
    private void checkStateAndprocessClickOnBoard(Group root, Point2D squareCoordinates) {
        if (squareCoordinates.getX() >= 0) {
            switch (state) {
                case MOVE:
                    System.out.println("MOVE");
                    setSquareImagesToCardImagesOrDefaults(root);
                    moveCardAndUpdateAllSquares(root, squareCoordinates);
                    gameBoard.clearSquaresPossibleToMoveTo();
                    gameBoard.setCurrentlySelectedSquare(squareCoordinates);
                    getSquaresPossibleToMove(root);
                    break;
                case SUMMON:
                    System.out.println("SUMMON");
                    interactionWithNeighbourSquares(root, squareCoordinates, false);
                    break;
                case ATTACK:
                    System.out.println("ATTACK");
                    interactionWithNeighbourSquares(root, squareCoordinates, true);
                    break;
            }
        }
    }

    /**
     * Handles events that are related to interactions only with squares next to the given minion.
     *
     * @param root              Group that shows the scene, where events are listened.
     * @param squareCoordinates coordinates of the square clicked on
     * @param offensive         interaction with enemy units
     */
    private void interactionWithNeighbourSquares(Group root, Point2D squareCoordinates, boolean offensive) {
        processAttackAction(squareCoordinates, offensive);
        gameBoard.setCurrentlySelectedSquare(squareCoordinates);
        setSquareImagesToCardImagesOrDefaults(root);
        gameBoard.clearSquaresPossibleToMoveTo();
        Square currentSquare = gameBoard.getCurrentlySelectedSquare();
        if (currentSquare.hasMinionOnSquare() && currentSquare.getCard().getSide() == currentSide) {
            List<Square> possibleSquaresToUse = gameBoard.expand(currentSquare);
            List<Square> squaresUsed = new ArrayList<>();
            for (Square surroundingSquare : possibleSquaresToUse) {
                // the second part of the and checks if the current square contains a general, or if the adjecent square contains an enemy minion
                if (surroundingSquare.hasMinionOnSquare() == offensive && (isConditionForEventMet(offensive, currentSquare) || isConditionForEventMet(offensive, surroundingSquare))) {
                    surroundingSquare.setImageAsMoveableSquare();
                    root.getChildren().add(surroundingSquare.getImageView());
                    squaresUsed.add(surroundingSquare);
                }
            }
            gameBoard.setSquaresPossibleToMoveTo(squaresUsed);
        }
    }

    private void processAttackAction(Point2D squareCoordinates, boolean offensive) {
        if (offensive && gameBoard.getSquaresPossibleToMoveTo().size() > 0) {
            MinionCard firstMinion = gameBoard.getBoardBySquares().get(Square.pointToSquare1DPosition(squareCoordinates)).getCard();
            MinionCard secondMinion = gameBoard.getCurrentlySelectedSquare().getCard();
            attackAndRetaliate(firstMinion, secondMinion);
            attackAndRetaliate(secondMinion, firstMinion);
        }
    }

    private void attackAndRetaliate(MinionCard firstMinion, MinionCard secondMinion) {
        secondMinion.setCurrentHp(secondMinion.getCurrentHp() - firstMinion.getAttack());
    }

    /**
     * Chekcs if the specific condition to use Summon or Attack action is met.
     *
     * @param offensive Parameter that shows if it affects squares with minions or not
     * @param square    Current square clicked on
     * @return True if the conditions for the SUMMON/ATTACK are met, false if otherwise
     */
    private boolean isConditionForEventMet(boolean offensive, Square square) {
        if (offensive) {
            if (square.getCard().getSide() != currentSide) return true;
        } else {
            if (square.getCard() instanceof GeneralCard) return true;
        }
        return false;
    }

    /**
     * If event is registered on square that has a minion, the method displays all the squares the minion can move, as well as saves them.
     * @param root Group that shows the scene, where events are listned
     */
    private void getSquaresPossibleToMove(Group root) {
        if (gameBoard.getCurrentlySelectedSquare().hasMinionOnSquare() && gameBoard.getCurrentlySelectedSquare().getCard().hasNotMoved() && gameBoard.getCurrentlySelectedSquare().getCard().getSide() == currentSide) {
            List<Square> possibleSquares = gameBoard.getAllPossibleSquares();
            gameBoard.setSquaresPossibleToMoveTo(possibleSquares);
            setSquaresImagesAsMoveableSquares(root, possibleSquares);
        }
    }

    private void setSquaresImagesAsMoveableSquares(Group root, List<Square> possibleSquares) {
        for (Square possibleSquare : possibleSquares) {
            possibleSquare.setImageAsMoveableSquare();
            root.getChildren().add(possibleSquare.getImageView());
        }
    }

    /**
     * If the previously selected square had a minion on it, then the method will reposition the minion on the game board, if the minion has not moved this turn;
     * @param root Group that shows the scene, where events are listened
     * @param squareCoordinates coordinates of the square clicked on
     */
    private void moveCardAndUpdateAllSquares(Group root, Point2D squareCoordinates) {
        if (gameBoard.getCurrentlySelectedSquare().hasMinionOnSquare()) {
            Square target = gameBoard.getBoardBySquares().get(Square.pointToSquare1DPosition(squareCoordinates));
            gameBoard.moveCardIfPossible(gameBoard.getCurrentlySelectedSquare(), target);
            updateAllSquares(root);

        }
    }

    private void updateAllSquares(Group root) {
        for (Square square : gameBoard.getBoardBySquares()) {
            square.updateImage();
            root.getChildren().add(square.getImageView());
        }
    }

    /**
     * Deselects the squares which were able to move to by the previously selected minion.
     * @param root Group that shows the scene, where events are listned
     */
    private void setSquareImagesToCardImagesOrDefaults(Group root) {
        for (Square square : gameBoard.getSquaresPossibleToMoveTo()) {
            square.setImageToCardImageOrDefault();
            root.getChildren().add(square.getImageView());
        }
    }

    /**
     * Gets the X coordinates of the given square in pixels.
     * @param squaresXCoordOnBoard The X coordinate of the square on the board
     * @return The X Pixel coordinates of the given square.
     */
    private double getSquaresXCoordinatesInPixels(int squaresXCoordOnBoard) {
        return squaresXCoordOnBoard * Square.getSquareWidth() + Square.getxTopMostValue();
    }

    /**
     * Gets the Y coordinates of the given square in pixels.
     *
     * @param squaresYCoord The Y coordinate of the square on the board
     * @return The Y Pixel coordinates of the given square.
     */
    private double getSquaresYCoordinatesInPixels(int squaresYCoord) {
        return squaresYCoord * Square.getSquareHeight() + Square.getyLeftMostValue();
    }

    /**
     * Finds the coordinates of the square on the board by their pixel values
     *
     * @param pixelX X pixel coordinates of the square
     * @param pixelY Y pixel coordinates of the square
     * @return Coordinates of the square. -1,-1 if not a square
     */

    private Point2D getSquare(double pixelX, double pixelY) {
        for (int x = 0; x < GameBoard.getxDimension(); x++) {
            for (int y = 0; y < GameBoard.getyDimension(); y++) {
                double left = getSquaresXCoordinatesInPixels(x);
                double top = getSquaresYCoordinatesInPixels(y);
                Rectangle rectangle = new Rectangle(left, top, Square.getSquareWidth(), Square.getSquareHeight());
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
    private int getCardSlotNumber(double pixelX, double pixelY) {
        for (int possibleCardSlot = 0; possibleCardSlot < Hand.getMaximumHandSize(); possibleCardSlot++) {
            double left = possibleCardSlot * Hand.getPreferredCardWidth() + Hand.getLeftMostPixelValue();
            double top = Hand.getTopMostPixelValue();
            Rectangle rectangle = new Rectangle(left, top, Hand.getPreferredCardWidth(), Hand.getPreferredCardHeight());
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
    private void reduceTimerAndSwitchTurnIfTimeOver(Text timerText, Label sideLabel, Group root){
        if(timerText.getText().equals("1")) {
            switchTurnAndResetToStartOfATurn(timerText, sideLabel, root);
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
    private void switchTurnAndResetToStartOfATurn(Text timerText, Label sideLabel, Group root) {
        updateLabel(currentStateLabel, InteractionState.NONE);
        resetTimer(timerText);
        setAllCreaturesToHaventMoved(root);
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
    private void setAllCreaturesToHaventMoved(Group root) {
        setSquareImagesToCardImagesOrDefaults(root);
        gameBoard.clearSquaresPossibleToMoveTo();
        for (Square square : gameBoard.getBoardBySquares()) {
            if(square.hasMinionOnSquare()){
                square.getCard().allowMovement();
            }
        }
    }

}
