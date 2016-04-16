package userFeatures;

import card.Card;
import card.GeneralCard;
import card.Generals;
import card.MinionCard;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import board.CreaturesOnBoard;
import card.*;
import collection.Collection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.Map;

import static javafx.geometry.Insets.*;

public class GUI extends Application {
    /*
    At the start of the game, GUI will load the board with generals and cards in the players hands.
    Then a turnCycle begins. While its one players turn, the other can not alter the board in any way.
    A turnCycle lasts for 60 seconds. Whether the player does or does not do anything, if his turn is up,
    the turnCycle starts over and the other player can play.

    In the beginning of a turnCycle, the player receives one mana. During the turnCycle he can see what moves
    he can do, make moves using mana and read the cards he has.
     */
    private Square exampleSquare = new Square(0, 0, null);
    private GameBoard gameBoard = new GameBoard();
    private CreaturesOnBoard creaturesOnBoard = new CreaturesOnBoard();
    private boolean isTurn = true;
    private double preferredGUIWidth = 2 * gameBoard.getxDimension() * exampleSquare.getWidth();
    private double preferredGUIHeight = gameBoard.getyDimension() * exampleSquare.getHeigth()+ 360;
    private Hand playerHand = new Hand();
    private int playerMana = 0;
    //ugly
    Hand hand = new Hand();
    Hand hand2 = new Hand();
    private int x = -150;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //choose generals
        //TODO: add choice pane
        GeneralCard whiteGeneral = creaturesOnBoard.getAllGenerals().get(1);
        GeneralCard blackGeneral = creaturesOnBoard.getAllGenerals().get(2);

        //first time loading:

        //create pane and scene
        Pane root = new Pane();
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
        hand.addCard(new MinionCard("esimene",1,"taavi",1,1,1,1));//taavi.jpg taaviSmall.jpg
        hand.addCard(new MinionCard("teine",2,"taavi",2,2,2,2));//taavi.jpg taaviSmall.jpg
        hand.addCard(new MinionCard("esimene",2,"taavi",2,2,2,2));//taavi.jpg taaviSmall.jpg
        hand.addCard(new MinionCard("teine",2,"taavi",2,2,2,2));//taavi.jpg taaviSmall.jpg
        hand.addCard(new MinionCard("esimene",2,"taavi",2,2,2,2));//taavi.jpg taaviSmall.jpg
        hand.addCard(new MinionCard("teine",2,"taavi",2,2,2,2));//taavi.jpg taaviSmall.jpg
        hand2.addCard(new MinionCard("esimene",2,"taavi",2,2,2,2));//taavi.jpg taaviSmall.jpg
        hand2.addCard(new MinionCard("teine",2,"taavi",2,2,2,2));//taavi.jpg taaviSmall.jpg

        //add generals onto gameboard
        loadGenerals(whiteGeneral, blackGeneral);
        //load board
        loadBoard(root);
        //load collection
        Map<Integer, Card> allCards = new Collection().getAllCards();
        //start turnCycle loop
        /*
        ImageView imageView = new ImageView();
        Image image = null;
        imageView.setImage(image);
        imageView.setX(200);




final long startNanoTime = System.nanoTime();
        new AnimationTimer()
        {boolean once=false;
        double playerTurn=1;


public void handle(long currentNanoTime)
        {


        double time = (currentNanoTime - startNanoTime) / 1000000000.0;
        double turnTime=time;
        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {

            boolean once = false;
            double playerTurn=1;

            public void handle(long currentNanoTime)
            {
                double time = (currentNanoTime - startNanoTime) / 1000000000.0;
                double turnTime=time;

        if(turnTime>1) {
        if(turnStarted==true) {
        Image image = new Image("rope.gif");
        imageView.setImage(image);
        turnStarted=false;

        if(playerTurn==1){
        startTurn(hand,root);
        }}

        if(playerTurn==2)
        {
        startTurn(hand2,root);



        }
        }
        if(turnTime>5){
        playerTurn=2;
        if(once==false){
        turnStarted=true;
        once=true;

        root.getChildren().clear();
            drawBoard(root);
        }
        }



        }
                if(turnTime>1) {
                    if(isTurn ==true) {
                        Image image = new Image("rope.gif");
                        imageView.setImage(image);
                        isTurn = false;
                        if(playerTurn==1){
                            for(Card card:hand.getHand()){
                                x+=150;
                                ImageView imageView = new ImageView();
                                imageView.setY(500);
                                image = card.getImage();
                                imageView.setX(x);
                                imageView.setImage(image);
                                root.getChildren().add(imageView);
                            }
                        }
                        if(playerTurn==2)
                        {
                            x=0;
                            System.out.println("test");
                            for(Card card:hand2.getHand()){
                                x+=150;
                                ImageView imageView = new ImageView();
                                imageView.setY(500);
                                image = card.getImage();
                                imageView.setX(x);
                                imageView.setImage(image);
                                root.getChildren().add(imageView);
                            }
                        }
                    }
                }
                if(turnTime>5){
                    playerTurn=2;
                    if(once==false){
                        isTurn =true;
                        once=true;}
                }
            }
        }.start();
        */
        //first turnCycle:
        //load all cards in hand


        //one turnCycle:
        //load all cards in hand


        //if isTurn add mana, event listeners, show scene
        //else show scene
        root.setOnMouseClicked((event) -> showPossibleSquares(root, event));
        //TODO: (high priority)add event listener function for making a move

        //TODO: add event listener function for summoning minion

        //TODO: (low priority)add event listener function for using equipment card

        //TODO: (low priority)add event listener function for using spell card

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

        root.getChildren().add(imageView);
        primaryStage.setMaxWidth(prefWidth);
        primaryStage.setMaxHeight(prefHeight);
        primaryStage.setMinWidth(prefWidth);
        primaryStage.setMinHeight(prefHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Card game");
        primaryStage.show();


    }

    private void loadGenerals(GeneralCard whiteGeneral, GeneralCard blackGeneral) {
        gameBoard.placeGenerals(whiteGeneral, blackGeneral);
    }

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

    public void addMana(int newMana){
        this.playerMana += newMana;
    }

}
        }
public void startTurn(Hand hand, Pane root){
        int x=0;
        for(Card card:hand.getHand()){

        ImageView imageView = new ImageView();
        imageView.setY(500);
        Image image = card.getImage();
        imageView.setX(x);
        imageView.setImage(image);
        root.getChildren().add(imageView);
        x+=150;
        }}
public void drawBoard(Pane root){
    Point p = MouseInfo.getPointerInfo().getLocation();

    Point2D point2D = getSquare(p.getX(), p.getY());
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
        if (gameBoard.getSelectedSquare() != null && gameBoard.getSelectedSquare().hasCardOnSquare()) {
        List<Square> possibleSquares = gameBoard.getAllPossibleSquares();
        gameBoard.setToRevert(possibleSquares);
        for (Square possibleSquare : possibleSquares) {
        possibleSquare.setFill(Color.RED);
        possibleSquare.setStroke(Color.BLACK);
        root.getChildren().add(possibleSquare);
        }
        }



        double prefWidth = 2 * gameBoard.getxDimension() * exampleSquare.getWidth();
        double prefHeight = gameBoard.getyDimension() * exampleSquare.getHeigth()+handsize;

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
        }}
        }
