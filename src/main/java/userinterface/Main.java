package userinterface;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

public class Main extends Application {
    /*
    At the start of the game, Main will load the board with generals on the board and cards in the players hands.
    Then a clock will start ticking down. While its one players turn, the other can not alter the board in any way.
    A turn lasts for 60 seconds. Whether the player does or does not do anything, if his turn is up,
    the turnCycle starts over and the other player can play.

    In the beginning of a turn, the player receives one mana. During the turn he can see what moves
    he can do, make moves using mana and read the cards he has. He can also end the turn early.
     */

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Setup(new Group(), primaryStage));
    }
}
