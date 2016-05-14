package userinterface;

import card.Card;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

class CardDeck extends Scene {
    private Group parentGroup;

    CardDeck(Group root, Stage primaryStage, SetupSettings setupSettings) {
        super(root);
        parentGroup = root;

        //load the gameframe onto the gui



        Map<Integer, Card> allCards = new collection.Collection().getCardsWithoutGenerals();
        List<Card> cardList = new ArrayList<>(allCards.values());
        int i = 0;
        Page page = new Page();
        drawBackground();

        CreateButton("right arrow.jpg", 300, 400, page, cardList);
        CreateButton("left arrow.jpg", 150, 400, page, cardList);
        primaryStage.setScene(this);
        primaryStage.getIcons().clear();
        primaryStage.getIcons().add(new Image("gameIcon.jpg"));
        String gameTitle = "Card deck building";
        primaryStage.setTitle(gameTitle);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    void drawPage(int kaardiLehekülg, List<Card> cardList) {
        int pageCards = 0;
        int rowSeperator = 0;
        for (Card card : cardList
                ) {
            if (kaardiLehekülg * 9 <= pageCards && pageCards < 9 + kaardiLehekülg * 9  && card.getChosen() == false) {
                Button cardViewer = new Button();
                ImageView cardViewerImage = new ImageView((card.getImage()));
                cardViewerImage.setFitWidth(100);
                cardViewerImage.setFitHeight(100);
                cardViewer.setGraphic(cardViewerImage);


                if (rowSeperator < 3) {
                    //drawing on the first row
                    cardViewer.setLayoutY(50);
                    cardViewer.setLayoutX(rowSeperator * 150 + 50);

                } else if (3 <= rowSeperator && rowSeperator < 6) {
                    //drawing on the second row
                    cardViewer.setLayoutY(170);
                    cardViewer.setLayoutX((rowSeperator - 3) * 150 + 50);
                } else if (6 <= rowSeperator) {
                    //drawing on the third row
                    cardViewer.setLayoutY(290);
                    cardViewer.setLayoutX((rowSeperator - 6) * 150 + 50);
                }

                cardViewer.setOnMouseClicked(event -> {
                    //TODO clear only cards so it stays at the same page
                    if (cardsChosenCount(cardList) < 20) {
                        card.setChosen(true);
                        parentGroup.getChildren().clear();
                        drawBackground();
                        CreateButton("right arrow.jpg", 300, 400, new Page(), cardList);
                        CreateButton("left arrow.jpg", 150, 400, new Page(), cardList);
                        drawChosenCards(cardList);

                    } else {
                        //// TODO: 5/14/2016 go to gameScene 
                    }
                });
                parentGroup.getChildren().add(cardViewer);
                rowSeperator += 1;
            }
            pageCards++;
        }
    }

    private void CreateButton(String suund, int X, int Y, Page page, List<Card> cardList) {

        drawPage(page.get(), cardList);
        Button button1 = new Button();
        ImageView buttonImage = new ImageView(new Image(suund));
        buttonImage.setFitWidth(50);
        buttonImage.setFitHeight(50);
        button1.setGraphic(buttonImage);
        button1.setLayoutX(X);
        button1.setLayoutY(Y);
        button1.setOnMouseClicked(event -> {
            if (suund.equals( "right arrow.jpg")) {
                page.increase();
            }
            if (suund.equals( "left arrow.jpg")) {
                page.decrease();
            }
            parentGroup.getChildren().clear();
            drawBackground();
            drawPage(page.get(), cardList);
            CreateButton("right arrow.jpg", 300, 400, page, cardList);
            CreateButton("left arrow.jpg", 150, 400, page, cardList);
            drawChosenCards(cardList);

        });
        parentGroup.getChildren().add(button1);
    }

    private void drawChosenCards(List<Card> cardList) {
        int textSpaceY = 50;
        for (Card card : cardList
                ) {
            if (card.getChosen()) {

                Text text = new Text();
                text.setText(card.getCost() + "  " + card.getName());
                text.setLayoutX(480);
                text.setLayoutY(textSpaceY);
                textSpaceY += 20;
                parentGroup.getChildren().add(text);
            }

        }
        Text text = new Text();
        text.setText("Cards chosen: " + cardsChosenCount(cardList) + "/20");//maximum cards: 20
        text.setLayoutX(480);
        text.setLayoutY(450);

        parentGroup.getChildren().add(text);
    }

    private int cardsChosenCount(List<Card> cardList) {
        int i = 0;
        for (Card card : cardList) {
            if (card.getChosen()) {
                i += 1;
            }
        }
        return i;
    }
    private void drawBackground(){
        ImageView gameFrame = new ImageView(new Image("deck background.jpg"));
        gameFrame.setFitHeight(500);
        gameFrame.setFitWidth(600);
        parentGroup.getChildren().add(gameFrame);
    }
}