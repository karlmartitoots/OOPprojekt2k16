/**
 * Created by Alex on 02/04/2016.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Animation extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setPrefSize(10 * 50, 10 * 50);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Square square = new Square(i, j);
                if ((i + j) % 2 == 0) {
                    square.setFill(Color.RED);
                } else {
                    square.setFill(Color.BLUE);
                }
                root.getChildren().add(square);


            }
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }
}
