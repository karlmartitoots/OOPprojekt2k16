package userFeatures;

import card.GeneralCard;
import collection.Collection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class GeneralChoosingScreen extends Application{

    static Collection collection = new Collection();
    Stage mainStage;

    GeneralCard chosenCard = null; //default

    GeneralChoosingScreen(Stage main){
        this.mainStage = main;
    }

    @Override
    public void start(Stage stage) throws Exception {

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
                setChosenCard(stringToGeneralCard(generalNames.getValue(), allGeneralCards));
                try(PrintWriter generalWriter = new PrintWriter(
                        new FileOutputStream(new File("src/main/resources/gameinfo.txt")))){
                    generalWriter.write(generalNames.getValue());
                } catch (FileNotFoundException e) {
                    System.out.println("Couldn't find gameinfo text file.");
                    System.exit(-1);
                } finally{
                    stage.close();
                }
            }
        });

        stage.setMinWidth(150);
        stage.setMinHeight(200);
        stage.setScene(scene);
        stage.show();
    }

    private GeneralCard stringToGeneralCard(String generalName, List<GeneralCard> allGeneralCards){
        for (GeneralCard generalCard : allGeneralCards) {
            if(generalCard.getName().equals(generalName))
                return generalCard;
        }
        System.out.println("Couldn't find the general, choosing default.");
        return chosenCard;
    }

    public GeneralCard getChosenCard() {
        return chosenCard;
    }

    public void setChosenCard(GeneralCard chosenCard) {
        this.chosenCard = chosenCard;
    }
}
