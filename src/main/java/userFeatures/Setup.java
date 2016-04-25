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
    //private Image settingsIcon = new Image("settingsIcon.png");//will be Setup's icon if gameIcon gets fixed
    private Image gameIcon = new Image("gameIcon.jpg");

    /**
     * The constructor of Setup is like the main class of Setup.
     * @param root  Main class passes Setup a Group to place everything onto.
     * @param primaryStage The stage that everything happens in.
     */
    public Setup(Group root, Stage primaryStage) {
        super(root);

        Label chooseGeneralLabel = createLabel("Choose your general", 20, 50, 0);
        Label whiteChoiceBoxLabel = createLabel("White", 16, 50, 50);
        Label blackChoiceBoxLabel = createLabel("Black", 16, 50, 100);

        ChoiceBox<String> whiteGeneralNames = createGeneralChoiceBox(100, 50);
        ChoiceBox<String> blackGeneralNames = createGeneralChoiceBox(100, 100);

        Button enterButton = new Button();
        enterButton.setText("ENTER");
        enterButton.relocate(100, 150);

        root.getChildren().addAll(chooseGeneralLabel,
                whiteGeneralNames,
                whiteChoiceBoxLabel,
                blackChoiceBoxLabel,
                blackGeneralNames,
                enterButton);

        boolean[] generalIsChosen = {false, false};
        whiteGeneralNames.getSelectionModel().selectedItemProperty().addListener(event -> {
            generalIsChosen[0] = true;
        });

        blackGeneralNames.getSelectionModel().selectedItemProperty().addListener(event -> {
            generalIsChosen[1] = true;
        });

        enterButton.setOnAction(event -> {
            whiteGeneral = allGeneralCards.get(whiteGeneralNames.getValue());
            blackGeneral = allGeneralCards.get(blackGeneralNames.getValue());
            if(!(generalIsChosen[0] && generalIsChosen[1])){
                showPopupDialog(primaryStage);
            }else{
                primaryStage.close();
                primaryStage.setScene(new Game(new Group(), primaryStage, new Settings(whiteGeneral, blackGeneral)));
            }
        });

        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(250);
        primaryStage.getIcons().add(gameIcon);
        primaryStage.setTitle("Setup");
        primaryStage.setScene(this);
        primaryStage.show();
    }

    /**
     * A labelmaker for easier reading of code.
     * @param text  Text on the label.
     * @param fontSize  Font size of the text.
     * @param locationX X-coordinate of location on the group.
     * @param locationY Y-coordinate of location on the group.
     * @return Returns the label, made with the specified parameters.
     */
    private Label createLabel(String text, int fontSize, int locationX, int locationY) {
        Label chooseGeneralLabel = new Label(text);
        chooseGeneralLabel.setFont(new Font(fontSize));
        chooseGeneralLabel.relocate(locationX, locationY);
        return chooseGeneralLabel;
    }

    /**
     * Shows a popup window, when a player hasn't chosen a general yet.
     * @param primaryStage Stage where the popup will be shown from.
     */
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

    /**
     * Creates a choiceBox with all available generals in the drop-down menu.
     * @param locationX X-coordinate on the screen.
     * @param locationY Y-coordinate on the screen.
     * @return Returns a choicebox with the specified location on the group. Ready to be deployed.
     */
    private ChoiceBox<String> createGeneralChoiceBox(int locationX, int locationY) {
        ChoiceBox<String> generalNames = new ChoiceBox<>();
        for (String generalName : allGeneralCards.keySet()) {
            generalNames.getItems().add(generalName);
        }
        generalNames.relocate(locationX, locationY);
        return generalNames;
    }
}
