package userFeatures;

import card.GeneralCard;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//basically a "struct" to pass along to the game's GUI
public class Settings {
    private GeneralCard whiteGeneral, blackGeneral;
    private Point2D whiteStartingSquare, blackStartingSquare;
    private static Map<String, ArrayList<Point2D>> allStartingPositions;
    static{
        //static initializer, where additional starting positions can always be added.
        //Another idea, is to make a txt file containing the points and load them in that way.
        //Add a new starting position by reinitializing startingSquares here and adding two points
        //!!!then you need to also add the name of it on Setup choiceBox or it wont work!!!
        allStartingPositions = new HashMap<>();
        ArrayList<Point2D> startingSquares = new ArrayList<>();
        startingSquares.add(new Point2D(1, 1));
        startingSquares.add(new Point2D(8, 8));
        allStartingPositions.put("Corners", startingSquares);
        startingSquares = new ArrayList<>();
        startingSquares.add(new Point2D(3, 6));
        startingSquares.add(new Point2D(6, 3));
        allStartingPositions.put("GoFace", startingSquares);
    }

    /**
     * An object generated for holding the game settings/passing them from Setup to Game
     * @param whiteGeneral Holds the white sides GeneralCard
     * @param blackGeneral Holds the black sides GeneralCard
     * @param startingPositionName Holds the name of the starting position, passed to the Settings class.
     */
    Settings(GeneralCard whiteGeneral, GeneralCard blackGeneral, String startingPositionName) {
        this.whiteGeneral = whiteGeneral;
        this.blackGeneral = blackGeneral;
        this.whiteStartingSquare = allStartingPositions.get(startingPositionName).get(0);
        this.blackStartingSquare = allStartingPositions.get(startingPositionName).get(1);
    }

    GeneralCard getWhiteGeneral() {
        return whiteGeneral;
    }

    GeneralCard getBlackGeneral() {
        return blackGeneral;
    }

    Point2D getWhiteStartingSquare(){
        return whiteStartingSquare;
    }

    Point2D getBlackStartingSquare(){
        return blackStartingSquare;
    }

}
