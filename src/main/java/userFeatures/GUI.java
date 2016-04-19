package userFeatures;

import board.CreaturesOnBoard;
import card.*;
import collection.Collection;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GUI extends Application {
    /*
    At the start of the game, GUI will load the board with generals on the board and cards in the players hands.
    Then a turnCycle begins. While its one players turn, the other can not alter the board in any way.
    A turnCycle lasts for 60 seconds. Whether the player does or does not do anything, if his turn is up,
    the turnCycle starts over and the other player can play.

    In the beginning of a turnCycle, the player receives one mana. During the turnCycle he can see what moves
    he can do, make moves using mana and read the cards he has. Ending the turn earlier will also be implemented.
     */
    private Square exampleSquare = new Square(0, 0, null);
    private GameBoard gameBoard = new GameBoard();
    private CreaturesOnBoard creaturesOnBoard = new CreaturesOnBoard();
    private Collection collection = new Collection();
    private boolean isTurn = true;
    private boolean turnFinished = true;
    private Hand playerHand = new Hand();
    private Deck playerDeck = new Deck(new ArrayList<>());
    private int playerMana = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //choose generals

        Pane choosingPane = new Pane();
        Scene scene = new Scene(choosingPane);
        Label chooseGeneralLabel = new Label("Choose your general: ");
        chooseGeneralLabel.relocate(50, 50);
        List<GeneralCard> allGeneralCards = collection.getAllGeneralCards();
        ChoiceBox<String> generalNames = new ChoiceBox<>();
        generalNames.relocate(50, 100);
        for (GeneralCard generalCard : allGeneralCards) {
            generalNames.getItems().add(generalCard.getName());
        }
        choosingPane.getChildren().addAll(chooseGeneralLabel,generalNames);

        generalNames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GeneralCard whiteGeneral = stringToGeneralCard(generalNames.getValue());
                primaryStage.close();
            }
        });

        primaryStage.setMinWidth(150);
        primaryStage.setMinHeight(200);
        primaryStage.setScene(scene);
        primaryStage.show();

        /*
        //TODO: add choice root
        GeneralCard whiteGeneral = creaturesOnBoard.getAllGenerals().get(1);
        GeneralCard blackGeneral = creaturesOnBoard.getAllGenerals().get(2);

        //first time loading:

        Pane gamePane = new Pane();
        Scene scene = new Scene(gamePane);

        loadGenerals(whiteGeneral, blackGeneral);
        ImageView gamePaneFrame = new ImageView(new Image("GUI frame.jpg"));
        gamePane.getChildren().add(gamePaneFrame);

        loadBoard(gamePane);
        */
        /*
        Map<Integer, Card> allCards = new Collection().getAllCards();
        List<Card> listOfCards = new ArrayList<>();
        synchronized (allCards) {
            for (Card card : allCards.values()) {
                listOfCards.add(card);
            }
        }
        playerDeck.loadDeck(listOfCards);
        */

        //TODO: fix loadPlayerDeck
        //start turnCycle loop

        /*
        TurnLoopHandler turnLoopHandler = new TurnLoopHandler(gamePane);
        new Thread(turnLoopHandler).start();
        */

        //first turnCycle:
        //load all cards in hand
        //load the board in case the opponent started first


        //one turnCycle:
        //load all cards in hand
        //if isTurn add mana, event listeners, show scene
        //else show scene

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

    }

    /**
     * Method for declaring the properties of the GUI root.
     * @param primaryStage Primary stage of GUI.
     * @param gamePane The GUI's root.
     */
    private void setPrimaryStageProperties(Stage primaryStage, Pane gamePane) {
        int GUITopPanelHeight = 47;//pixels
        int preferredGUIWidth = 2 * gameBoard.getxDimension() * exampleSquare.getWidth();
        int preferredGUIHeight = (gameBoard.getyDimension() + 6)* exampleSquare.getHeigth() + GUITopPanelHeight;
        int preferredCardHeight = 250;//pixels
        int preferredCardWidth = 125;//pixels
        gamePane.setPrefSize(preferredGUIWidth, preferredGUIHeight);
        primaryStage.setMaxWidth(preferredGUIWidth);
        primaryStage.setMaxHeight(preferredGUIHeight);
        primaryStage.setMinWidth(preferredGUIWidth);
        primaryStage.setMinHeight(preferredGUIHeight);
    }

    /**
     * Places generals on the gameboard.
     * @param whiteGeneral White side general card.
     * @param blackGeneral Black side general card.
     */
    private void loadGenerals(GeneralCard whiteGeneral, GeneralCard blackGeneral) {
        gameBoard.placeGenerals(whiteGeneral, blackGeneral);
    }

    /**
     * Initiates the board on the root root.
     * @param root Pane to initiate board on.
     */
    private void loadBoard(Pane root) {
        for (int i = 0; i < gameBoard.getxDimension(); i++) {
            for (int j = 0; j < gameBoard.getyDimension(); j++) {
                Square square;
                if (gameBoard.getGameBoard()[i][j] == 0) {
                    square = new Square(i, j, null);
                    square.setFill(Color.LIGHTBLUE);
                    square.setStroke(Color.BLACK);
                    root.getChildren().add(square);
                } else if(gameBoard.getGameBoard()[i][j] <= 100){
                    square = new Square(i, j, creaturesOnBoard.getAllGenerals().get(Math.abs(gameBoard.getGameBoard()[i][j])));
                    root.getChildren().add(square.getImageView());
                } else {
                    square = new Square(i, j, creaturesOnBoard.getAllMinions().get(Math.abs(gameBoard.getGameBoard()[i][j])));
                    root.getChildren().add(square.getImageView());
                }
                gameBoard.addSquare(square);
            }
        }
    }

    /**
     * Triggers when a minion/general is pressed on on the gameboard. Shows possible squares for
     * the said minion/general.
     * @param root  Pane that shows the scene, where events are listened.
     * @param event Event to be listened for.
     */
    private void showPossibleSquares(Pane root, MouseEvent event) {
        Point2D point2D = getSquare(event.getSceneX(), event.getSceneY());
        gameBoard.setSelectedSquare(point2D);
        System.out.println(gameBoard.getSelectedSquare());
        if (gameBoard.getToRevert().size() > 0) {
            for (Square square : gameBoard.getToRevert()) {
                square.setFill(Color.LIGHTBLUE);
                square.setStroke(Color.BLACK);
                if (!root.getChildren().contains(square)) {
                    root.getChildren().add(square);
                }
            }
            gameBoard.clearRevertable();
        }
        if (gameBoard.getSelectedSquare() != null && gameBoard.getSelectedSquare().hasMinionOnSquare()) {
            List<Square> possibleSquares = gameBoard.getAllPossibleSquares();
            gameBoard.setToRevert(possibleSquares);
            for (Square possibleSquare : possibleSquares) {
                possibleSquare.setFill(Color.RED);
                possibleSquare.setStroke(Color.BLACK);
                root.getChildren().add(possibleSquare);
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

    /**
     * Triggers in the beginning of a turnCycle. Usually adds 1 mana to players manapool.
     * @param newMana The amount of mana to be added.
     */
    public void addMana(int newMana){
        this.playerMana += newMana;
    }

    public boolean gameOver(){
        return (creaturesOnBoard.getAllGenerals().get(1).isDead() || creaturesOnBoard.getAllGenerals().get(2).isDead());
    }

    public GeneralCard stringToGeneralCard(String generalName){
        List<GeneralCard> generalCards = collection.getAllGeneralCards();
        for (GeneralCard generalCard : generalCards) {
            if(generalCard.getName().equals(generalName))
                return generalCard;
        }
        return new GeneralCard("General Märt", 0, "This is Märt", 1, 0, 999, 5);
    }

}
