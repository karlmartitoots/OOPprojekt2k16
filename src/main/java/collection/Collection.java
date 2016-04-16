package collection;

import card.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Collection {

    //ID -> Card
    private Map<Integer, Card> allCards = new HashMap<>();

    public Collection(){
        try(Scanner fileReader = new Scanner("collection.txt")){
            String line;
            while(fileReader.hasNextLine()){
                line = fileReader.nextLine();
                if(!line.startsWith("/")){
                    String[] parts = line.split(";");
                    createCard(parts);
                }
            }
        }
    }

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
                        Integer.parseInt(parts[5])
                ));
        }
    }

    public Map<Integer, Card> getAllCards(){
        return allCards;
    }

}
