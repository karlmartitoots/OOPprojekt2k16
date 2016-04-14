package userFeatures;

import java.util.*;

import card.*;
import javafx.geometry.Point2D;

public class GameBoard {

    private final int xDimension = 10;
    private final int yDimension = 10;
    private int[][] gameBoard = new int[xDimension][yDimension];
    private int selectedSquare = -1;
    /*Every minion can be stored as an integer on the board - negative value for player a, positive for b. Possible to put unique id for every card.
    Although keeping the data of the board can be subject to change if there is a better data structure
    */

    /**
     * Gets the length of the board on the X axis
     *
     * @return x Dimension length
     */
    public int getxDimension() {
        return xDimension;
    }

    /**
     * Gets the length of the board on the Y axis
     * @return y Dimension length
     */
    public int getyDimension() {
        return yDimension;
    }

    /**
     * Gets the current state of the game board.
     * @return gameBoard
     */
    public int[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Setups the game by placing the generals of both players on the board.
     * @param white The general used by the white player
     * @param black The general used by the black player
     */
    public void placeGenerals(GeneralCard white, GeneralCard black) {
        gameBoard[white.getStartingWhite().getxCord()][white.getStartingWhite().getyCord()] = white.getID();
        gameBoard[black.getStartingBlack().getxCord()][black.getStartingBlack().getyCord()] = black.getID() * (-1);
    }

    /**
     * Moves the selected unit to the target square
     * @param minion MinionCard to move
     * @param target Target square
     */
    public void moveUnit(MinionCard minion, Square target) {
        Square parent = minion.getCurrentPosition();
        Stack<Square> path = getPath(parent, target);
        while (!path.isEmpty()) {
            minion.setCurrentPosition(path.pop());
        }

    }

    /**
     * Finds all the possible squares a minion can go to from the given position using BFS
     * @param minion Selected minion
     * @return All squares the minion can move
     */
    public List<Square> getAllPossibleSquares(MinionCard minion) {

        Queue<Square> queueOfSquaresToCheck = new LinkedList<>();
        List<Square> squaresPossibleToMoveTo = new ArrayList<>();

        Square startSquare = minion.getCurrentPosition();
        queueOfSquaresToCheck.add(startSquare);
        boolean[] hasBeenVisited = new boolean[xDimension * yDimension];
        hasBeenVisited[startSquare.intValue(xDimension)] = true;
        while (!queueOfSquaresToCheck.isEmpty()) {
            Square nextSquareToCheck = queueOfSquaresToCheck.poll();
            List<Square> toExplore = expand(nextSquareToCheck);
            for (Square currentSquare : toExplore) {
                if (!hasBeenVisited[currentSquare.intValue(xDimension)] && squareIsEmpty(currentSquare) && (startSquare.getDistance(currentSquare) <= minion.getSpeed())) {
                    hasBeenVisited[currentSquare.intValue(xDimension)] = true;
                    squaresPossibleToMoveTo.add(currentSquare);
                    queueOfSquaresToCheck.add(currentSquare);
                }
            }
        }
        return squaresPossibleToMoveTo;
    }


    /**
     * Finds the fastest path to the pointed square in parameter end using BFS
     * @param start Starting square
     * @param end End square
     * @return Returns a stack. The next square on the path is on top of the stack.
     */
    public Stack<Square> getPath(Square start, Square end) {
        //TODO: Should merge with the other BFS method if possible
        Queue<Square> queueOfSquaresToCheck = new LinkedList<>();
        Map<Square, Square> paths = new HashMap<>();

        queueOfSquaresToCheck.add(start);
        boolean[] hasBeenVisited = new boolean[xDimension * yDimension];
        hasBeenVisited[start.intValue(xDimension)] = true;
        while (!queueOfSquaresToCheck.isEmpty()) {

            Square nextSquare = queueOfSquaresToCheck.poll();
            if (nextSquare == end) break;
            List<Square> toExplore = expand(nextSquare);
            for (Square currentSquare : toExplore) {
                if (!hasBeenVisited[currentSquare.intValue(xDimension)] && squareIsEmpty(currentSquare)) {
                    hasBeenVisited[currentSquare.intValue(xDimension)] = true;
                    paths.put(currentSquare, nextSquare);
                    queueOfSquaresToCheck.add(currentSquare);
                }
            }
        }
        return generatePath(paths, start, end);
    }

    /**
     * Expands the current search space for searching
     * @param square square to expand from
     * @return Expanded squares
     */
    private List<Square> expand(Square square) {
        List<Square> toGoto = new ArrayList<>();
        Square nextSquare;
        for(int i = -1; i < 1; i++){
            nextSquare = new Square(square.getxCord() + i, square.getyCord() + (i +1), null);
            if (belongsToBoard(nextSquare)) {
                toGoto.add(nextSquare);
            }
            nextSquare = new Square(square.getxCord() - i, square.getyCord() - (i +1), null);
            if (belongsToBoard(nextSquare)) {
                toGoto.add(nextSquare);
            }
        }
        return toGoto;
    }

    /**
     * Checks if the square with the given coordinates belongs to the board
     * @param square square to check if it belongs to the board
     * @return True if belongs, false otherwise
     */
    boolean belongsToBoard(Square square) {
        return square.getxCord() >= 0 && square.getxCord() < xDimension && square.getyCord() >= 0 && square.getyCord() < 0;
    }

    /**
     * Checks if the given square is currently occupied by any unit
     * @param square square to check if it is occupied
     * @return True if empty, false otherwise
     */
    boolean squareIsEmpty(Square square) {
        return gameBoard[square.getxCord()][square.getyCord()] == 0;
    }

    /**
     * Generates the path to move from the given map into the stack for easier processing
     * @param paths Map containing paths
     * @param start Starting Square
     * @param end Ending Square
     * @return Stack of the path to move.
     */
    private Stack<Square> generatePath(Map<Square, Square> paths, Square start, Square end) {

        Stack<Square> pathToGo = new Stack<>();
        Square toGo;

        Square previous = end;
        while ((toGo = paths.get(previous)) != start) {
            pathToGo.add(toGo);
            previous = toGo;
        }
        return pathToGo;
    }

    public void setSelectedSquare(Point2D point) {
        if (point.getX() >= 0) {
            selectedSquare = (int) (point.getX() * xDimension + point.getY());
        } else selectedSquare = -1;
    }

    public int getSelectedSquare() {
        return selectedSquare;
    }
}
