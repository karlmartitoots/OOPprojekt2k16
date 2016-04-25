package userFeatures;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Gamecycle {

    final Integer turnStartTime = 60;
    private Player player;
    IntegerProperty timeSeconds = new SimpleIntegerProperty(turnStartTime);
    Timeline turnTimeline;
    private ImageView buttonView = new ImageView();

    Gamecycle(Player currentPlayer, Group root){
        this.player = currentPlayer;

    }

    public void getTime(){

    }

    /*
    private void startTurnCounter(Pane root) {


        Image buttonImageOn = new Image("buttonon.png");
        Image buttonImageOff = new Image("buttonoff.png");

        Label timerLabel = new Label();

        timerLabel.setText(timeSeconds.toString());

        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");
        timerLabel.textProperty().bind(timeSeconds.asString());


        root.getChildren().add(timerLabel);

        EventHandler<ActionEvent> whenFinished = t -> {
            root.getChildren().remove(timerLabel);

            buttonView.setImage(buttonImageOff);

        };
        timeSeconds.set(turnStartTime);
        turnTimeline = new Timeline();
        turnTimeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(turnStartTime + 1),
                        new KeyValue(timeSeconds,0)),

                new KeyFrame(Duration.seconds(turnStartTime + 1),
                        whenFinished

                ));
        turnTimeline.play();




        buttonView.setImage(buttonImageOn);
        buttonView.setX(500);
        root.getChildren().add(buttonView);


        //TODO: add interrupt for ending turn with a button
        root.setOnMouseClicked((event) -> {showPossibleSquares(root, event);
            if(event.getX()>buttonView.getX()&& buttonView.getX()+150>event.getX()&&
                    event.getY()>buttonView.getY()&& buttonView.getY()+150>event.getY()){
                buttonView.setImage(buttonImageOff);
                root.getChildren().remove(timerLabel);}});
        //TODO: (high priority)add event listener function for making a move
        //TODO: (high priority)add event listener for attacking

        //TODO: add event listener function for summoning minion

        //TODO: (low priority)add event listener function for using equipment card

        //TODO: (low priority)add event listener function for using spell card

    }
    */

}
