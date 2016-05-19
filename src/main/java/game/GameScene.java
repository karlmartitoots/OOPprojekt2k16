package game;

import attributes.Attribute;
import card.Card;
import card.EquipmentCard;
import card.GeneralCard;
import card.MinionCard;
import attributes.processReinforcmentAction;
import card.*;
import gamelogic.*;
import gamelogic.player.Hand;
import gamelogic.player.Player;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import settings.SetupSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class GameScene extends Scene {

    private GameBoard gameBoard = new GameBoard();
    private List<CardSlot> cardSlots = new ArrayList<>();
    private Player playerWhite = new Player(Side.WHITE), playerBlack = new Player(Side.BLACK);
    private int timerStartTime = 60;
    private double timerNodeWidthInPixels = 250;
    private InteractionState state = InteractionState.NONE;
    private Group parentGroup;
    private final ScheduledExecutorService timerScheduler =
            Executors.newScheduledThreadPool(1);
    //white starts
    private Player currentPlayer = playerWhite;
    //for the time being if currentActiveCard is used, needs to be checked with currentCardExists
    private Card currentActiveCard = null;
    private int turnCounter = 0;
    private Label currentStateLabel, generalHealthLabel, currentManaLabel, selectedMinionAttackLabel, selectedMinionHealthLabel, selectedCardNameLabel, selectedCardManaCostLabel, selectedMinionSideLabel, selectedMinionSpeedLabel;

    /**
     * The constructor of Game conducts all whats happening in gamelogic on gui.
     *
     * @param primaryStage  Stage for the scene.
     * @param setupSettings Initial settings that are loaded, when the game begins.
     */
    public GameScene(Group root, Stage primaryStage, SetupSettings setupSettings) {
        super(root);
        parentGroup = root;

        playerWhite.setGeneral(setupSettings.getWhiteGeneral());
        playerBlack.setGeneral(setupSettings.getBlackGeneral());

        //load the gameframe onto the gui
        ImageView gameFrame = new ImageView(new Image("GUI frame.jpg"));
        parentGroup.getChildren().add(gameFrame);

        //load the board onto the gui
        loadGameboardForStartOfGame();

        //load the generals onto the board
        setGeneralsOnGameboardAndShowImages(setupSettings);
        cardSlots = createAndGetCardSlots();
        updateCardSlots();

        Text timerText = createAndPlaceTimer();
        Label turnLabel = createAndPlaceLabel((timerNodeWidthInPixels - 50) / 2, 10, currentPlayer.getSide().toString());
        createInformationLabels();

        createAndPlaceInfoboxNode();

        timerScheduler.scheduleAtFixedRate(
                (Runnable) () ->
                        Platform.runLater(() -> {
                            if (primaryStage.isFocused()) {
                                if (!gameIsOver()) {
                                    reduceTimerAndSwitchTurnIfTimeOver(timerText, turnLabel);
                                } else {
                                    primaryStage.close();
                                    timerScheduler.shutdown();
                                    primaryStage.setScene(new GameOverScene(
                                                    new Group(),
                                                    primaryStage,
                                                    getWinner()
                                            )
                                    );
                                }
                            }
                        }), 1L, 1L, SECONDS);

        //load the turn ending button
        Button endTurnButton = createAndPlaceEndTurnButton();
        Button moveButton = createAndPlaceButton(InteractionState.MOVE, 760, 380, 230, 40);
        Button attackButton = createAndPlaceButton(InteractionState.ATTACK, 760, 420, 230, 40);

        //event listener for turn ending button
        endTurnButton.setOnAction(event ->
                switchTurnAndResetToStartOfTurn(timerText, turnLabel));
        moveButton.setOnAction(event ->{
                updateLabel(currentStateLabel, InteractionState.MOVE);
                flashAllyMinionSquares(Color.LIMEGREEN);
        }
        );
        attackButton.setOnAction(event ->{
                updateLabel(currentStateLabel, InteractionState.ATTACK);
                flashAllyMinionSquares(Color.LIMEGREEN);
        }
        );

        //event listener for handling the clicks on the group.
        parentGroup.setOnMouseClicked(this::mouseEventHandler);

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

    private List<CardSlot> createAndGetCardSlots() {
        List<CardSlot> cardSlots = new ArrayList<>();
        for (int cardSlotNr = 0; cardSlotNr < Hand.getMaximumHandSize(); cardSlotNr++) {
            CardSlot newCardSlot = new CardSlot(cardSlotNr);
            Group cardSlotImage = newCardSlot.getCardSlotImage();
            cardSlotImage.setLayoutX(cardSlotNr * Hand.getPreferredCardWidth() + Hand.getLeftMostPixelValue());
            cardSlotImage.setLayoutY(Hand.getTopMostPixelValue());
            parentGroup.getChildren().add(cardSlotImage);
            cardSlots.add(newCardSlot);
        }
        return cardSlots;
    }

    private void updateCardSlots(){
        CardSlot currentCardSlot;
        for (int cardSlotNr = 0; cardSlotNr < Hand.getMaximumHandSize(); cardSlotNr++) {
            //get the right cardslot
            currentCardSlot = cardSlots.get(cardSlotNr);
            //remove the old image
            parentGroup.getChildren().remove(currentCardSlot.getCardSlotImage());
            //set it's card if there is one
            if (cardSlotNr < currentPlayer.getPlayerHand().getCardsInHand().size()) {
                currentCardSlot.setCard(currentPlayer.getPlayerHand().getCardsInHand().get(cardSlotNr));
            } else {
                currentCardSlot.setCard(null);
            }
            //update the cardslot image according to if it has a card now or not (the function sorts it out)
            cardSlots.get(cardSlotNr).updateCardSlotImage();
            //set the new image and show it
            Group cardSlotImage = currentCardSlot.getCardSlotImage();
            cardSlotImage.setLayoutX(cardSlotNr * Hand.getPreferredCardWidth() + Hand.getLeftMostPixelValue());
            cardSlotImage.setLayoutY(Hand.getTopMostPixelValue());
            parentGroup.getChildren().add(cardSlotImage);
        }
    }

    private void flashAllyMinionSquares(Color color) {
        gameBoard.getBoardBySquares().forEach(square -> {
            if(square.hasMinionOnSquare()){
                if(square.getCard().getSide().equals(currentPlayer.getSide())){
                    square.flashSquareInColor(color, parentGroup);
                }
            }
        });
    }

    private void createInformationLabels() {
        currentStateLabel = createAndPlaceLabel(770, 350, "Current state: " + state.toString());
        generalHealthLabel = createLabel("");
        updateGeneralHealthLabel();
        currentManaLabel = createLabel("");
        updateManaLabel();
        selectedCardNameLabel = createLabel("");
        selectedMinionAttackLabel = createLabel("");
        selectedMinionHealthLabel = createLabel("");
        selectedCardManaCostLabel = createLabel("");
        selectedMinionSideLabel = createLabel("");
        selectedMinionSpeedLabel = createLabel("");
    }

    private void createAndPlaceInfoboxNode() {
        VBox infoBox = new VBox(generalHealthLabel, currentManaLabel, selectedCardNameLabel, selectedMinionAttackLabel, selectedMinionHealthLabel, selectedMinionSpeedLabel, selectedCardManaCostLabel, selectedMinionSideLabel);
        infoBox.setLayoutX(770);
        infoBox.setLayoutY(30);
        parentGroup.getChildren().add(infoBox);
    }

    /**
     * Creates a button that allows the user to change to given state in his turn
     *
     * @param state     State to switch to
     * @param xStarting starting x position of the button
     * @param yStarting starting y position of the button
     * @param width     width of the button
     * @param height    height of the button
     * @return Returns the button that was created.
     */
    private Button createAndPlaceButton(InteractionState state, int xStarting, int yStarting, int width, int height) {
        Button button = new Button(state.toString());
        button.relocate(xStarting, yStarting);
        button.setMinSize(width, height);
        button.setFont(new Font(20));
        parentGroup.getChildren().add(button);
        return button;
    }

    private Label createAndPlaceLabel(double xOnGui, double yOnGui, String message) {
        Label label = new Label(message);
        label.relocate(xOnGui, yOnGui);
        label.setFont(new Font(20));
        parentGroup.getChildren().add(label);
        return label;
    }

    private Label createLabel(String message) {
        Label label = new Label(message);
        label.setFont(new Font(20));
        return label;
    }


    private void updateLabel(Label label, InteractionState state) {
        this.state = state;
        label.setText("Current state: " + state.toString());
    }

    private void updateManaLabel() {
        currentManaLabel.setText(String.valueOf("Full mana crystals: " + currentPlayer.getFullManaCrystals()));
    }

    private void updateGeneralHealthLabel() {
        generalHealthLabel.setText(String.valueOf("General health: " + currentPlayer.getGeneral().getCurrentHp()));
    }

    /**
     * Method to initially place both generals on the GUI and in gameboard class.
     *
     * @param setupSettings Settings for getting, which GeneralCard's have been chosen and where they will be placed.
     */
    private void setGeneralsOnGameboardAndShowImages(SetupSettings setupSettings) {
        Point2D whiteGeneralStartingCoordinates = setupSettings.getWhiteStartingSquare();
        Point2D blackGeneralStartingCoordinates = setupSettings.getBlackStartingSquare();
        //get the squares for generals
        Square whiteGeneralSquare = gameBoard.getBoardBySquares().get(
                Square.pointToSquare1DPosition(whiteGeneralStartingCoordinates)),
                blackGeneralSquare = gameBoard.getBoardBySquares().get(
                        Square.pointToSquare1DPosition(blackGeneralStartingCoordinates));
        //add in the generals and set their sides
        whiteGeneralSquare.setSquaresCard(setupSettings.getWhiteGeneral());
        blackGeneralSquare.setSquaresCard(setupSettings.getBlackGeneral());
        whiteGeneralSquare.updateImage();
        parentGroup.getChildren().remove(whiteGeneralSquare.getImageView());
        parentGroup.getChildren().add(whiteGeneralSquare.getImageView());
        blackGeneralSquare.updateImage();
        parentGroup.getChildren().remove(blackGeneralSquare.getImageView());
        parentGroup.getChildren().add(blackGeneralSquare.getImageView());

    }

    /**
     * Creates a button to end the current players turn.
     *
     * @return Returns the button itself.
     */
    private Button createAndPlaceEndTurnButton() {
        Button endTurnButton = new Button("END TURN");
        endTurnButton.relocate(5, 250);
        endTurnButton.setMinSize(250, 245);
        endTurnButton.setFont(new Font(40));
        parentGroup.getChildren().add(endTurnButton);
        return endTurnButton;
    }

    private void loadGameboardForStartOfGame() {
        for (int x = 0; x < GameBoard.getxDimension(); x++) {
            for (int y = 0; y < GameBoard.getyDimension(); y++) {
                Square initialSquare;
                gameBoard.addSquareToBoardBySquares(initialSquare = new Square(x, y, null));
                parentGroup.getChildren().add(initialSquare.getImageView());
            }
        }
    }

    /**
     * Method for declaring the properties of the game GUI.
     *
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
     *
     * @param event Event to be listened for.
     */
    private void mouseEventHandler(MouseEvent event) {
        Point2D squareCoordinates = getSquareByPixelCoordinates(event.getSceneX(), event.getSceneY());
        int cardSlotNumber = getCardSlotNumber(event.getSceneX(), event.getSceneY());

        if (cardSlotNumber != -1 && cardSlotNumber < currentPlayer.getPlayerHand().getCardsInHand().size()) {
            state = InteractionState.NONE;
            setCurrentActiveCardByCardSlot(cardSlotNumber);
            setSquareImagesToCardImagesOrDefaults();
            if (currentActiveCard instanceof MinionCard) {
                if (currentActiveCard.getAttributes().contains(Attribute.AIRDROP)) {
                    highlightPossibleSummonSquaresWithAirdrop();
                } else {
                    highlightPossibleSummonSquares();
                }
            }
            if (currentActiveCard instanceof EquipmentCard) {
                highlightPossibleMinionsToGiveEquipment();
            }

        } else if(coordinatesOnGameboard(squareCoordinates)){//click on gameboard
            checkStateAndprocessClickOnBoard(squareCoordinates);
            currentActiveCard = null;
        }else{
            setSquareImagesToCardImagesOrDefaults();
        }
    }

    private void highlightPossibleSummonSquaresWithAirdrop() {
        List<Square> squares = new ArrayList<>();
        for (Square square : gameBoard.getBoardBySquares()) {
            if (!square.hasMinionOnSquare()) {
                squares.add(square);
            }
        }
        gameBoard.setSquarePossibleToInteractWith(squares);
        for (Square square : gameBoard.getSquarePossibleToInteractWith()) {
            parentGroup.getChildren().remove(square.getImageView());
            square.setImageAsMoveableSquare();
            parentGroup.getChildren().add(square.getImageView());
        }
    }

    private void highlightPossibleMinionsToGiveEquipment() {
        List<Square> currentPlayerMinionSquares = new ArrayList<>();
        gameBoard.getBoardBySquares().forEach(square -> {
            if (square.hasMinionOnSquare()) {
                if (square.getCard().getSide().equals(currentPlayer.getSide())) {
                    currentPlayerMinionSquares.add(square);
                }
            }
        });
        gameBoard.setSquarePossibleToInteractWith(currentPlayerMinionSquares);
        for (Square square : gameBoard.getSquarePossibleToInteractWith()) {
            parentGroup.getChildren().remove(square.getImageView());
            square.setImageAsMoveableSquare();
            parentGroup.getChildren().addAll(square.getImageView());
        }
    }

    private boolean coordinatesOnGameboard(Point2D squareCoordinates){
        return !squareCoordinates.equals(new Point2D(-1, -1));
    }

    private Square getCurrentGeneralSquare() {
        return currentPlayer.getGeneral().getCurrentPosition();
    }

    private void highlightPossibleSummonSquares() {
        Square currentGeneralSquare = getCurrentGeneralSquare();
        gameBoard.setSquarePossibleToInteractWith(
                gameBoard.getPossibleSquaresInGeneralsSummonRange(currentGeneralSquare));
        for (Square square : gameBoard.getSquarePossibleToInteractWith()) {
            parentGroup.getChildren().remove(square.getImageView());
            square.setImageAsMoveableSquare();
            parentGroup.getChildren().add(square.getImageView());
        }
    }

    private void setCurrentActiveCardByCardSlot(int cardSlotNumber) {
        int currentHandSize = currentPlayer.getPlayerHand().getCardsInHand().size();
        if (slotContainsCard(cardSlotNumber, currentHandSize)) {
            currentActiveCard = getCardByCardSlotNumber(cardSlotNumber);

        } else {
            currentActiveCard = null;
        }
        if (currentActiveCard instanceof MinionCard) {
            showEquipmentInformationOnScreen((MinionCard) currentActiveCard);
        }
        if (currentActiveCard instanceof EquipmentCard) {
            showEquipmentInformationOnScreen((EquipmentCard) currentActiveCard);
        }
        if (currentActiveCard instanceof SpellCard) {
            showSpellInformationOnScreen((SpellCard) currentActiveCard);
        }
    }

    private boolean slotContainsCard(int cardSlotNumber, int currentHandSize) {
        return (currentHandSize - 1) >= cardSlotNumber;
    }

    private boolean currentActiveCardExists() {
        return currentActiveCard != null;
    }

    private Card getCardByCardSlotNumber(int cardSlotNumber) {
        return currentPlayer.getPlayerHand().getCardsInHand().get(cardSlotNumber);
    }

    /**
     * Handles events that happens when the mouse is clicked on the board
     *
     * @param squareCoordinates coordinates of the square clicked on
     */
    private void checkStateAndprocessClickOnBoard(Point2D squareCoordinates) {
        summonMinionIfPossible(squareCoordinates);
        equipEquipmentIfPossible(squareCoordinates);
        useSpellIfPossible();
        if (squareCoordinates.getX() >= 0) {
            gameBoard.setCurrentlySelectedSquare(squareCoordinates);
            setSquareImagesToCardImagesOrDefaults();
            showEquipmentInformationOnScreen(gameBoard.getCurrentlySelectedSquare().getCard());
            switch (state) {
                case MOVE:
                    moveCardAndUpdateAllSquares(squareCoordinates);
                    gameBoard.clearSquaresPossibleToInteractWith();
                    getSquaresPossibleToMoveTo();
                    break;
                case ATTACK:
                    processAttackAction();
                    break;
            }
        }
    }

    private void useSpellIfPossible() {
        if (currentActiveCardExists() && currentActiveCard instanceof SpellCard) {
            SpellCard currentCard = (SpellCard) currentActiveCard;
            if (currentPlayer.useMana(currentActiveCard.getCost())) {
                if (currentCard.getAttributeListMap().keySet().contains(Attribute.REINFORCMENT)) {
                    processReinforcmentAction.summonSquires(currentCard.getAttributeListMap().get(Attribute.REINFORCMENT).get(0), gameBoard.getBoardBySquares(), currentPlayer.getSide());
                    currentPlayer.getPlayerHand().getCardsInHand().remove(currentActiveCard);
                    updateManaLabel();
                }
            }
        }
        updateCardSlots();
    }

    private void equipEquipmentIfPossible(Point2D squareCoordinates) {
        if (currentActiveCardExists() && currentActiveCard instanceof EquipmentCard) {
            Square possibleMinionSquare = gameBoard.getBoardBySquares().get(Square.pointToSquare1DPosition(squareCoordinates));
            if (gameBoard.getSquarePossibleToInteractWith().contains(possibleMinionSquare)) {
                if(currentPlayer.useMana(currentActiveCard.getCost())) {
                    possibleMinionSquare.getCard().addEquipment((EquipmentCard) currentActiveCard);
                    currentPlayer.getPlayerHand().getCardsInHand().remove(currentActiveCard);
                    updateManaLabel();
                }else{
                    showInformativeText("You don't have enough mana for that!");
                }
            }
        }
        updateCardSlots();
    }

    private void showInformativeText(String message) {
        Platform.runLater(() -> {
            int positionInX = 760, positionInY = 300;
            Text informativeText = new Text(message);
            informativeText.setWrappingWidth(250);
            informativeText.setFill(Color.RED);
            informativeText.setFont(new Font(20));
            informativeText.setX(positionInX);
            informativeText.setY(positionInY);

            parentGroup.getChildren().add(informativeText);
            PauseTransition pt = new PauseTransition(Duration.millis(1000));

            pt.play();
            pt.setOnFinished(event -> {
                informativeText.setText("");
                parentGroup.getChildren().remove(informativeText);
            });
        });
    }

    private void summonMinionIfPossible(Point2D squareCoordinates) {
        if (currentActiveCardExists() && currentActiveCard instanceof MinionCard) {
            Square squareToSummonOn = gameBoard.getBoardBySquares().get(Square.pointToSquare1DPosition(squareCoordinates));
            if (gameBoard.getSquarePossibleToInteractWith().contains(squareToSummonOn)) {
                if(currentPlayer.useMana(currentActiveCard.getCost())) {
                    MinionCard cardToSummon = (MinionCard) currentActiveCard;
                    squareToSummonOn.setSquaresCard(cardToSummon);
                    squareToSummonOn.getCard().setCurrentPosition(squareToSummonOn);
                    if (!cardToSummon.getAttributes().contains(Attribute.CHARGE)) {
                        squareToSummonOn.getCard().blockMovement();
                        squareToSummonOn.getCard().setHasAttacked(true);
                    }

                    currentPlayer.getPlayerHand().getCardsInHand().remove(currentActiveCard);
                    updateManaLabel();
                }else{
                    showInformativeText("You don't have enough mana for that!");
                }
            }
        }
        updateCardSlots();
    }

    private void showEquipmentInformationOnScreen(MinionCard card) {
        if (card != null) {
            selectedCardNameLabel.setText("NAME: " + card.getName());
            selectedMinionAttackLabel.setText("ATTACK: " + card.getAttack());
            selectedMinionHealthLabel.setText("HP: " + card.getCurrentHp());
            selectedMinionSpeedLabel.setText("SPEED: " + card.getMovementReach());
            selectedCardManaCostLabel.setText("MANACOST: " + card.getCost());
            selectedMinionSideLabel.setText("SIDE: " + card.getSide());
        } else {
            clearInformationLabels();
        }
    }

    private void showEquipmentInformationOnScreen(EquipmentCard card) {
        if (card != null) {
            selectedCardNameLabel.setText("NAME: " + card.getName());
            selectedMinionAttackLabel.setText("ATTACK BUFF: " + card.getBonusAttack());
            selectedMinionHealthLabel.setText("HEALTH BUFF: " + card.getBonusHealth());
            selectedMinionSpeedLabel.setText("SPEED BUFF: " + card.getBonusSpeed());
            selectedCardManaCostLabel.setText("MANACOST: " + card.getCost());
        } else {
            clearInformationLabels();
        }
    }

    private void showSpellInformationOnScreen(SpellCard card) {
        if (card != null) {
            selectedCardNameLabel.setText("NAME: " + card.getName());
            selectedCardManaCostLabel.setText("MANACOST: " + card.getCost());
        } else {
            clearInformationLabels();
        }
    }

    private void clearInformationLabels() {
        selectedCardNameLabel.setText("");
        selectedMinionAttackLabel.setText("");
        selectedMinionHealthLabel.setText("");
        selectedCardManaCostLabel.setText("");
        selectedMinionSideLabel.setText("");
        selectedMinionSpeedLabel.setText("");
    }

    /**
     * Handles events that are related
     */
    private void processAttackAction() {
        processCombat();
        gameBoard.clearSquaresPossibleToInteractWith();
        Square currentSquare = gameBoard.getCurrentlySelectedSquare();
        if (currentSquare.hasMinionOnSquare() && cardBelongsToCurrentSide(currentSquare.getCard())) {
            List<Square> possibleSquareToAttackTo = gameBoard.expand(currentSquare);
            List<Square> squaresUsed = new ArrayList<>();
            possibleSquareToAttackTo.forEach(surroundingSquare -> {
                if (surroundingSquare.hasMinionOnSquare() && surroundingSquare.getCard().getSide() != currentPlayer.getSide() && !currentSquare.getCard().hasAttacked()) {
                    surroundingSquare.setImageAsMoveableSquare();
                    parentGroup.getChildren().remove(surroundingSquare.getImageView());
                    parentGroup.getChildren().add(surroundingSquare.getImageView());
                    squaresUsed.add(surroundingSquare);
                }
            });
            gameBoard.setSquarePossibleToInteractWith(squaresUsed);
        }
    }

    private void processCombat() {
        if (gameBoard.getSquarePossibleToInteractWith().contains(gameBoard.getCurrentlySelectedSquare())) {
            MinionCard firstMinion = gameBoard.getPreviouslySelectedSquare().getCard();
            if (firstMinion.hasAttacked()) return;
            MinionCard secondMinion = gameBoard.getCurrentlySelectedSquare().getCard();
            attackAndRetaliate(firstMinion, secondMinion);
            if (firstMinion.getAttributes().contains(Attribute.FIRSTSTRIKE) || firstMinion.getAttributes().contains(Attribute.DOOUBLESTRIKE)) {
                processAttackWithFirstStrike(firstMinion, secondMinion);
            } else {
                processAttackWithoutFirstStrike(firstMinion, secondMinion);
            }
            if (firstMinion.getAttributes().contains(Attribute.DOOUBLESTRIKE)) {
                attackAndRetaliate(firstMinion, secondMinion);
                if (!firstMinion.isAlive()) {
                    parentGroup.getChildren().remove(gameBoard.getPreviouslySelectedSquare().getImageView());
                    gameBoard.getPreviouslySelectedSquare().setSquaresCard(null);
                    parentGroup.getChildren().add(gameBoard.getPreviouslySelectedSquare().getImageView());
                }
            }
            firstMinion.setHasAttacked(true);
            if (firstMinion.equals(currentPlayer.getGeneral())) {
                updateGeneralHealthLabel();
            }

        }
    }

    private void processAttackWithoutFirstStrike(MinionCard firstMinion, MinionCard secondMinion) {
        attackAndRetaliate(secondMinion, firstMinion);
        if (!firstMinion.isAlive()) {
            parentGroup.getChildren().remove(gameBoard.getPreviouslySelectedSquare().getImageView());
            gameBoard.getPreviouslySelectedSquare().setSquaresCard(null);
            parentGroup.getChildren().add(gameBoard.getPreviouslySelectedSquare().getImageView());
        }
        if (!secondMinion.isAlive()) {
            parentGroup.getChildren().remove(gameBoard.getCurrentlySelectedSquare().getImageView());
            gameBoard.getCurrentlySelectedSquare().setSquaresCard(null);
            parentGroup.getChildren().add(gameBoard.getCurrentlySelectedSquare().getImageView());
        }
    }

    private void processAttackWithFirstStrike(MinionCard firstMinion, MinionCard secondMinion) {
        if (!secondMinion.isAlive()) {
            parentGroup.getChildren().remove(gameBoard.getCurrentlySelectedSquare().getImageView());
            gameBoard.getCurrentlySelectedSquare().setSquaresCard(null);
            parentGroup.getChildren().add(gameBoard.getCurrentlySelectedSquare().getImageView());
        } else {
            attackAndRetaliate(secondMinion, firstMinion);
        }
        if (!firstMinion.isAlive()) {
            parentGroup.getChildren().remove(gameBoard.getPreviouslySelectedSquare().getImageView());
            gameBoard.getPreviouslySelectedSquare().setSquaresCard(null);
            parentGroup.getChildren().add(gameBoard.getPreviouslySelectedSquare().getImageView());
        }

    }

    private boolean cardBelongsToCurrentSide(MinionCard minionCard) {
        return minionCard.getSide().equals(currentPlayer.getSide());
    }

    private void attackAndRetaliate(MinionCard firstMinion, MinionCard secondMinion) {
        if (firstMinion.getAttributes().contains(Attribute.DEATHTOUCH) && !(secondMinion instanceof GeneralCard)) {
            secondMinion.setCurrentHp(0);
            secondMinion.getCurrentPosition().showHitSplatOnSquare(secondMinion.getCurrentHp(), parentGroup);
        } else {
            secondMinion.setCurrentHp(secondMinion.getCurrentHp() - firstMinion.getAttack());
            secondMinion.getCurrentPosition().showHitSplatOnSquare(firstMinion.getAttack(), parentGroup);
        }
    }

    private void bounce(Square square) {
        MinionCard cardOnSquare = square.getCard();
        square.setSquaresCard(null);
        if (cardOnSquare.getSide() == Side.WHITE) {
            playerWhite.getPlayerHand().addCardIfPossible(cardOnSquare);
        } else playerBlack.getPlayerHand().addCardIfPossible(cardOnSquare);
    }

    /**
     * If event is registered on square that has a minion, the method displays all the squares the minion can move, as well as saves them.
     */
    private void getSquaresPossibleToMoveTo() {
        if (gameBoard.getCurrentlySelectedSquare().hasMinionOnSquare() &&
                gameBoard.getCurrentlySelectedSquare().getCard().hasNotMoved() &&
                gameBoard.getCurrentlySelectedSquare().getCard().getSide().equals(currentPlayer.getSide())) {
            List<Square> possibleSquares = gameBoard.getAllPossibleSquares();
            gameBoard.setSquarePossibleToInteractWith(possibleSquares);
            setSquaresImagesAsMoveableSquares();
        }
    }

    private void setSquaresImagesAsMoveableSquares() {
        for (Square possibleSquare : gameBoard.getSquarePossibleToInteractWith()) {
            parentGroup.getChildren().remove(possibleSquare.getImageView());
            possibleSquare.setImageAsMoveableSquare();
            parentGroup.getChildren().add(possibleSquare.getImageView());
        }
    }

    /**
     * If the previously selected square had a minion on it, then the method will reposition the minion on the game board, if the minion has not moved this turn;
     *
     * @param squareCoordinates coordinates of the square clicked on
     */
    private void moveCardAndUpdateAllSquares(Point2D squareCoordinates) {
        if (gameBoard.getPreviouslySelectedSquare().hasMinionOnSquare()) {
            Square target = gameBoard.getBoardBySquares().get(Square.pointToSquare1DPosition(squareCoordinates));
            gameBoard.moveCardIfPossible(gameBoard.getPreviouslySelectedSquare(), target);
            updateAllSquares();
        }
    }

    private void updateAllSquares() {
        for (Square square : gameBoard.getBoardBySquares()) {
            square.updateImage();
            parentGroup.getChildren().remove(square.getImageView());
            parentGroup.getChildren().add(square.getImageView());
        }
    }

    /**
     * Deselects the squares which were able to move to by the previously selected minion.
     */
    private void setSquareImagesToCardImagesOrDefaults() {
        for (Square square : gameBoard.getSquarePossibleToInteractWith()) {
            square.setImageToCardImageOrDefault();
            parentGroup.getChildren().remove(square.getImageView());
            parentGroup.getChildren().add(square.getImageView());
        }
    }

    /**
     * Gets the X coordinates of the given square in pixels.
     *
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

    private Point2D getSquareByPixelCoordinates(double pixelX, double pixelY) {
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
     * Creates and places the timer on the game GUI.
     *
     * @return Returns the text that shows the current time left on someone's turn.
     */
    private Text createAndPlaceTimer() {
        Text timerText = new Text(10, 210, String.valueOf(timerStartTime));
        timerText.setFont(new Font(220));
        parentGroup.getChildren().add(timerText);
        return timerText;
    }

    /**
     * Method for reducing the timer by one unit or switching turns when the time is up.
     *
     * @param timerText The text, which shows the time.
     * @param sideLabel Label, that shows who's turn it currently is.
     */
    private void reduceTimerAndSwitchTurnIfTimeOver(Text timerText, Label sideLabel) {
        if (timerText.getText().equals("1")) {
            switchTurnAndResetToStartOfTurn(timerText, sideLabel);
        } else {
            timerText.setText(String.valueOf(Integer.parseInt(timerText.getText()) - 1));
        }
    }

    /**
     * Method for resetting the timer to the time in the beginning of a turn.
     *
     * @param timerText The text, which shows the time.
     */
    private void resetTimer(Text timerText) {
        timerText.setText(String.valueOf(timerStartTime));
    }

    /**
     * Switches turns and does all the operations needed in the end and start of a turn.
     *
     * @param timerText The text, which shows the time.
     * @param sideLabel Label, that shows who's turn it currently is.
     */
    private void switchTurnAndResetToStartOfTurn(Text timerText, Label sideLabel) {
        resetGameLogicToStartOfTurn();
        resetAllNodesToStartOfTurn(timerText, sideLabel);
    }

    private void resetGameLogicToStartOfTurn() {
        switchCurrentPlayer();
        currentPlayer.addManaCrystal();
        currentPlayer.refilManaCrystals();
        setAllCreaturesToStartOfTurn();
        incrementTurnCounter();
        clearInformationLabels();
        currentPlayer.getPlayerHand().addCardIfPossible(currentPlayer.getPlayerDeck().draw());
    }

    private void resetAllNodesToStartOfTurn(Text timerText, Label sideLabel) {
        updateLabel(currentStateLabel, InteractionState.NONE);
        updateCardSlots();
        resetTimer(timerText);
        sideLabel.setText(currentPlayer.getSide().toString());
        updateGeneralHealthLabel();
        updateManaLabel();
        flashAllyMinionSquares(Color.LIMEGREEN);
    }

    /**
     * Increases the turn counter by 1.
     */
    private void incrementTurnCounter() {
        turnCounter += 1;
    }

    /**
     * Finds all the creatures on the gameboard and sets their hasMoved state to false.
     */
    private void setAllCreaturesToStartOfTurn() {
        setSquareImagesToCardImagesOrDefaults();
        gameBoard.clearSquaresPossibleToInteractWith();
        allowMovementAndAttackForAllCreatures();
    }

    private void allowMovementAndAttackForAllCreatures() {
        gameBoard.getBoardBySquares().forEach(square -> {
            if (square.hasMinionOnSquare()) {
                square.getCard().allowMovement();
                square.getCard().setHasAttacked(false);
            }
        });
    }


    private void switchCurrentPlayer() {
        if (currentPlayer.getSide().equals(Side.WHITE)) {
            this.currentPlayer = playerBlack;
        } else {
            this.currentPlayer = playerWhite;
        }
    }

    private boolean gameIsOver() {
        return !(playerBlack.isAlive() && playerWhite.isAlive());
    }

    private Player getWinner() {
        if (!playerWhite.isAlive()) {
            if (!playerBlack.isAlive()) return null;
            return playerBlack;
        }
        return playerWhite;
    }

}
