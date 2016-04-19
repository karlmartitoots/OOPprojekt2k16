package userFeatures;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;

public class TurnLoopHandler implements Runnable{

    Pane root;
    GameBoard gameBoard;
    Timeline timeline;

    public TurnLoopHandler(Pane pane){
        this.root = pane;
    }

    @Override
    public void run() {



    }
}
