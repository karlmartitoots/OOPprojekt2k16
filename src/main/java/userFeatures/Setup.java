package userFeatures;

import card.GeneralCard;
import collection.Collection;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;

public class Setup extends Scene {

    private Collection collection = new Collection();
    private Map<String, GeneralCard> allGeneralCards = collection.getAllGeneralCardsByName();
    private GeneralCard whiteGeneral = null, blackGeneral = null;
    private Image popupIcon = new Image("errorPopupIcon.jpg");
    private Image settingsIcon = new Image("settingsIcon.png");

    public Setup(Group root, Stage primaryStage) {
        super(root);

        Label chooseGeneralLabel = new Label("Choose your general");
        chooseGeneralLabel.setFont(new Font(20));
        chooseGeneralLabel.relocate(50, 0);
        Label whiteChoiceBoxLabel = new Label("White");
        whiteChoiceBoxLabel.relocate(50, 50);
        Label blackChoiceBoxLabel = new Label("Black");
        blackChoiceBoxLabel.relocate(50, 100);

        ChoiceBox<String> whiteGeneralNames = generalChoiceBox();
        ChoiceBox<String> blackGeneralNames = generalChoiceBox();
        whiteGeneralNames.relocate(100, 50);
        blackGeneralNames.relocate(100, 100);

        Button enterButton = new Button();
        enterButton.setText("ENTER");
        enterButton.relocate(100, 150);

        root.getChildren().addAll(chooseGeneralLabel,whiteGeneralNames, whiteChoiceBoxLabel, blackChoiceBoxLabel, blackGeneralNames, enterButton);
        boolean[] generalIsSet = {false, false};
        whiteGeneralNames.getSelectionModel().selectedItemProperty().addListener(event -> {
            generalIsSet[0] = true;
        });

        blackGeneralNames.getSelectionModel().selectedItemProperty().addListener(event -> {
            generalIsSet[1] = true;
        });

        enterButton.setOnAction(event -> {
            whiteGeneral = allGeneralCards.get(whiteGeneralNames.getValue());
            blackGeneral = allGeneralCards.get(blackGeneralNames.getValue());
            if(!(generalIsSet[0] && generalIsSet[1])){
                showPopupDialog(primaryStage);
            }else{
                primaryStage.close();
                primaryStage.setScene(new Game(new Group(), primaryStage, new Settings(whiteGeneral, blackGeneral)));
            }
        });

        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(250);
        primaryStage.getIcons().add(settingsIcon);
        primaryStage.setTitle("Setup");
        primaryStage.setScene(this);
        primaryStage.show();
    }

    private void showPopupDialog(Stage primaryStage) {
        final Stage dialogBox = new Stage();
        Group dialogBoxGroup = new Group();
        dialogBox.initModality(Modality.APPLICATION_MODAL);
        dialogBox.initOwner(primaryStage);
        Text dialog = new Text("Please choose both generals!");
        dialog.setFont(new Font(25.0));
        dialog.relocate(25, 25);
        dialogBoxGroup.getChildren().add(dialog);
        Scene dialogScene = new Scene(dialogBoxGroup, 500, 100);
        dialogBox.setScene(dialogScene);
        dialogBox.getIcons().add(popupIcon);
        dialogBox.setTitle("ERROR");
        dialogBox.show();
    }

    private ChoiceBox<String> generalChoiceBox() {
        ChoiceBox<String> generalNames = new ChoiceBox<>();
        for (String generalName : allGeneralCards.keySet()) {
            generalNames.getItems().add(generalName);
        }
        return generalNames;
    }
}
