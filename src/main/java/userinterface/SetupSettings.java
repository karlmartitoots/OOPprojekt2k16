package userinterface;

import card.GeneralCard;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//basically a "struct" to pass along to the game's GUI
class SetupSettings {
    private GeneralCard whiteGeneral, blackGeneral;
    private Point2D whiteStartingSquare, blackStartingSquare;
    private static Map<String, ArrayList<Point2D>> allStartingPositions;
    static{
        //static initializer, where additional starting positions can always be added.
        //Another idea, is to make a txt file containing the points and load them in that way.

        //Add a new starting position by reinitializing startingSquares here and adding two points
        //!!!then you need to also add the name of it on Setup choiceBox or it wont work!!!
        //TODO:implement better
        allStartingPositions = new HashMap<>();
        ArrayList<Point2D> startingSquares = new ArrayList<>();
        startingSquares.add(new Point2D(1, 1));
        startingSquares.add(new Point2D(8, 8));
        allStartingPositions.put("Corners", startingSquares);
        startingSquares = new ArrayList<>();
        startingSquares.add(new Point2D(3, 6));
        startingSquares.add(new Point2D(6, 3));
        allStartingPositions.put("GoFace", startingSquares);
        startingSquares = new ArrayList<>();
        startingSquares.add(new Point2D(5, 9));
        startingSquares.add(new Point2D(5, 0));
        allStartingPositions.put("Chess", startingSquares);
    }

    /**
     * An object generated for holding the game settings/passing them from Setup to Game
     * @param whiteGeneral Holds the white sides GeneralCard
     * @param blackGeneral Holds the black sides GeneralCard
     * @param startingPositionName Holds the name of the starting position, passed to the Settings class.
     */
    SetupSettings(GeneralCard whiteGeneral, GeneralCard blackGeneral, String startingPositionName) {
        this.whiteGeneral = whiteGeneral;
        this.blackGeneral = blackGeneral;
        this.whiteStartingSquare = allStartingPositions.get(startingPositionName).get(0);
        this.blackStartingSquare = allStartingPositions.get(startingPositionName).get(1);
    }

    /**
     * Getter method to get the white sides generalCard.
     * @return Returns the white side's generalCard.
     */
    GeneralCard getWhiteGeneral() {
        return whiteGeneral;
    }

    /**
     * Getter method to get the black sides generalCard.
     * @return Returns the black side's generalCard.
     */
    GeneralCard getBlackGeneral() {
        return blackGeneral;
    }

    /**
     * Getter method to get the white side's generals starting square coordinates.
     * @return Returns white's generals starting square coordinates as a Point2D object.
     */
    Point2D getWhiteStartingSquare(){
        return whiteStartingSquare;
    }

    /**
     * Getter method to get the black side's generals starting square coordinates.
     * @return Returns black's generals starting square coordinates as a Point2D object.
     */
    Point2D getBlackStartingSquare(){
        return blackStartingSquare;
    }

}
