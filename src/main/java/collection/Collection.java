package collection;

import card.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Collection {

    //ID -> Card
    private Map<Integer, Card> allCards = new HashMap<>();

    public Collection(){
        try(Scanner fileReader = new Scanner(new File("src/main/resources/collection.txt"))){
            String line;
            while(fileReader.hasNextLine()){
                line = fileReader.nextLine();
                if(!line.startsWith("/") && !line.equals("")){
                    String[] parts = line.split(";");
                    createCard(parts);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dis collection file is impossible to find.");
            System.exit(-1);
        }
    }

    /**
     * Creates a card by reading it in from collection textfile. What different parts mean, is documented in
     * collection.txt.
     * @param parts The method is passed a string array with the card specific elements.
     */
    private void createCard(String[] parts) {
        switch (parts[0]){
            case "GENERAL":
                allCards.put(Integer.parseInt(parts[4]), new GeneralCard(
                        parts[1],
                        Integer.parseInt(parts[2]),
                        parts[3],
                        Integer.parseInt(parts[4]),
                        Integer.parseInt(parts[5]),
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[7])
                ));
                break;
            case "MINION":
                allCards.put(Integer.parseInt(parts[4]), new MinionCard(
                        parts[1],
                        Integer.parseInt(parts[2]),
                        parts[3],
                        Integer.parseInt(parts[4]),
                        Integer.parseInt(parts[5]),
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[7])
                ));
                break;
            case "SPELL":
                allCards.put(Integer.parseInt(parts[4]), new SpellCard(
                        parts[1],
                        Integer.parseInt(parts[2]),
                        parts[3]
                        //,Integer.parseInt(parts[4]),
                        //Integer.parseInt(parts[5])
                        //TODO: add spellcard effects
                ));
                break;
            case "EQUIPMENT":
                allCards.put(Integer.parseInt(parts[4]), new EquipmentCard(
                        parts[1],
                        Integer.parseInt(parts[2]),
                        parts[3],
                        Integer.parseInt(parts[4]),
                        Integer.parseInt(parts[5]),
                        Integer.parseInt(parts[6])
                ));
        }
    }

    /**
     * Getter for all the cards that exist
     * @return Returns a map of integer to card correspondence elements.
     */
    public Map<Integer, Card> getAllCards(){
        return allCards;
    }

    /**
     * Getter for every general card in the game
     * @return Returns a map of string to GeneralCard correspondence elements.
     */
    public Map<String, GeneralCard> getAllGeneralCardsByName(){
        Map<String, GeneralCard> generalCards = new HashMap<>();
        for (Card card : allCards.values()) {
            if(card instanceof GeneralCard)
                generalCards.put(card.getName(),(GeneralCard) card);
        }
        return generalCards;
    }

}
