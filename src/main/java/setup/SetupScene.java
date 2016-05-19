package setup;

import card.GeneralCard;
import collection.Collection;
import game.GameScene;
import javafx.collections.FXCollections;
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
import settings.SetupSettings;

import java.util.Map;

public class SetupScene extends Scene {
    private Collection collection = new Collection();
    private Map<String, GeneralCard> allGeneralCards = collection.getAllGeneralCardsByName();
    private GeneralCard whiteGeneral = null, blackGeneral = null;
    private String startingPositionName = "";
    private Image popupIcon = new Image("errorPopupIcon.jpg");

    /**
     * The constructor of Setup is like the main class of Setup.
     * @param root  Main class passes Setup a Group to place everything onto.
     * @param primaryStage The stage that everything happens in.
     */
    public SetupScene(Group root, Stage primaryStage) {
        super(root);
        Label chooseGeneralLabel = createLabel("Choose your general", 20, 50, 0);
        Label whiteChoiceBoxLabel = createLabel("White", 16, 50, 50),
                blackChoiceBoxLabel = createLabel("Black", 16, 50, 100);
        Label beginningPositionsChoiceBoxLabel = createLabel("Choose beginning positions", 20, 30, 150);

        ChoiceBox<String> whiteGeneralNamesChoiceBox = createGeneralChoiceBox(100, 50);
        ChoiceBox<String> blackGeneralNamesChoiceBox = createGeneralChoiceBox(100, 100);

        ChoiceBox<String> positionNamesChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                "Corners",
                "GoFace",
                "Chess"
        ));
        positionNamesChoiceBox.relocate(100, 200);

        Button enterButton = new Button();
        enterButton.setText("ENTER");
        enterButton.relocate(100, 250);
        Button deckbutton = new Button();
        deckbutton .setText("Deck building");
        deckbutton .relocate(220, 100);
        Button deckbutton2 = new Button();
        deckbutton2 .setText("Deck building");
        deckbutton2 .relocate(220, 50);
        root.getChildren().addAll(chooseGeneralLabel,
                whiteChoiceBoxLabel,
                whiteGeneralNamesChoiceBox,
                blackChoiceBoxLabel,
                blackGeneralNamesChoiceBox,
                beginningPositionsChoiceBoxLabel,
                positionNamesChoiceBox,
                enterButton,
                deckbutton,
                deckbutton2);

        boolean[] generalIsChosen = {false, false};
        whiteGeneralNamesChoiceBox.getSelectionModel().selectedItemProperty().addListener(event -> {
            generalIsChosen[0] = true;
        });

        blackGeneralNamesChoiceBox.getSelectionModel().selectedItemProperty().addListener(event -> {
            generalIsChosen[1] = true;
        });

        boolean[] positionIsChosen = {false};
        positionNamesChoiceBox.getSelectionModel().selectedItemProperty().addListener(event -> {
            positionIsChosen[0] = true;
        });
        Stage deckStage = new Stage();
        deckbutton.setOnAction(event -> deckStage.setScene(new DeckBuildingScene((new Group()), primaryStage)));
        deckbutton2.setOnAction(event -> deckStage.setScene(new DeckBuildingScene((new Group()), primaryStage)));
        enterButton.setOnAction(event -> {
            if(!(generalIsChosen[0] && generalIsChosen[1] && positionIsChosen[0])){
                showPopupDialog(primaryStage);
            }else{
                whiteGeneral = GeneralCard.createWhiteGeneral(allGeneralCards.get(whiteGeneralNamesChoiceBox.getValue()));
                blackGeneral = GeneralCard.createBlackGeneral(allGeneralCards.get(blackGeneralNamesChoiceBox.getValue()));
                startingPositionName = positionNamesChoiceBox.getValue();
                primaryStage.close();
                primaryStage.setScene(new GameScene((new Group()), primaryStage, new SetupSettings(whiteGeneral, blackGeneral, startingPositionName)));
                //primaryStage.setScene(new CardDeck((new Group()), primaryStage, new SetupSettings(whiteGeneral, blackGeneral, startingPositionName)));
            }
        });

        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(350);
        Image gameIcon = new Image("settingsIcon.png");
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
        Text dialog = new Text("Please choose both generals and the starting position!");
        dialog.setFont(new Font(25.0));
        dialog.relocate(25, 25);
        dialogBoxGroup.getChildren().add(dialog);
        Scene dialogScene = new Scene(dialogBoxGroup, 700, 100);
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
