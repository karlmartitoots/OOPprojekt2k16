package userinterface;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameOverScene extends Scene{
    private double deathSceneWidth = 500;
    private double deathSceneHeight = 350;
    private double nodeWidth = 400;
    private double nodeHeight = 50;

    public GameOverScene(Group parentGroup, Stage primaryStage, Player winner) {
        super(parentGroup);

        setUpDeathScene(parentGroup, primaryStage, winner);
        playDeathScene(primaryStage);
    }

    private void setUpDeathScene(Group parentGroup, Stage primaryStage, Player winner) {
        Label gameOverLabel = createLabel("GAMEOVER", 40);
        gameOverLabel.setTextFill(Color.RED);

        String winnerText = getWinnerText(winner);

        Label winnerLabel = createLabel(winnerText, 20);
        winnerLabel.setTextFill(Color.GREEN);

        Button replayButton = new Button("NEW GAME");
        replayButton.setPrefSize(nodeWidth, nodeHeight);

        placeScreenRows(parentGroup, gameOverLabel, winnerLabel, replayButton);

        listenButtonForReplay(primaryStage, replayButton);
    }

    private String getWinnerText(Player winner) {
        String winnerText;
        if(winner == null){
            winnerText = "It's a draw!";
        }else{
            winnerText = "Winner is " + String.valueOf(winner.getSide());
        }
        return winnerText;
    }

    private void listenButtonForReplay(Stage primaryStage, Button replayButton) {
        replayButton.setOnAction(event -> {
            primaryStage.close();
            primaryStage.setScene(new SetupScene(new Group(), primaryStage));
        });
    }

    private void placeScreenRows(Group parentGroup, Label gameOverLabel, Label winnerLabel, Button replayButton) {
        VBox screenVBox = new VBox(gameOverLabel, winnerLabel, replayButton);
        parentGroup.getChildren().add(screenVBox);
        screenVBox.setSpacing(10);
        screenVBox.setPrefSize(deathSceneWidth - 100, deathSceneHeight - 50);
        screenVBox.setLayoutX((deathSceneWidth - screenVBox.getPrefWidth())/2);
        screenVBox.setLayoutY((deathSceneHeight - screenVBox.getPrefHeight())/2);
    }

    private Label createLabel(String text, int font){
        Label label = new Label(text);
        label.setFont(new Font(font));
        label.setPrefSize(nodeWidth, nodeHeight);
        return label;
    }

    private void playDeathScene(Stage primaryStage) {
        setStageSize(primaryStage);
        primaryStage.setTitle("Gameover!");
        primaryStage.setScene(this);
        primaryStage.show();
    }

    private void setStageSize(Stage primaryStage) {
        primaryStage.setMinWidth(deathSceneWidth);
        primaryStage.setMinHeight(deathSceneHeight);
        primaryStage.setMaxWidth(deathSceneWidth);
        primaryStage.setMaxHeight(deathSceneHeight);
    }
}
