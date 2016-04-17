package userFeatures;

import java.util.*;

import card.*;
import javafx.geometry.Point2D;

public class GameBoard {

    private final int xDimension = 10;
    private final int yDimension = 10;
    private List<Square> board = new ArrayList<>();
    private int[][] gameBoard = new int[xDimension][yDimension];
    private Square selectedSquare = null;
    private List<Square> toRevert = new ArrayList<>();
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
        gameBoard[white.getStartingWhite().getxCordOnBoard()][white.getStartingWhite().getyCordOnBoard()] = white.getID();
        gameBoard[black.getStartingBlack().getxCordOnBoard()][black.getStartingBlack().getyCordOnBoard()] = black.getID() * (-1);
    }

    public void placeMinion(MinionCard minion, String side, int xCoord, int yCoord){
        gameBoard[xCoord][yCoord] = minion.getID()*(side.equals("white") ? 1 : -1);
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
     * @return All squares the minion can move
     */
    public List<Square> getAllPossibleSquares() {
        Queue<Square> queueOfSquaresToCheck = new LinkedList<>();
        List<Square> squaresPossibleToMoveTo = new ArrayList<>();
        queueOfSquaresToCheck.add(getSelectedSquare());
        boolean[] hasBeenVisited = new boolean[xDimension * yDimension];
        hasBeenVisited[getSelectedSquare().intValue(xDimension)] = true;
        while (!queueOfSquaresToCheck.isEmpty()) {
            Square nextSquareToCheck = queueOfSquaresToCheck.poll();
            List<Square> toExplore = expand(nextSquareToCheck);
            for (Square currentSquare : toExplore) {
                if (!hasBeenVisited[currentSquare.intValue(xDimension)] && squareIsEmpty(currentSquare) && (getSelectedSquare().getDistance(currentSquare) <= getSelectedSquare().getCard().getSpeed())) {
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
    public List<Square> expand(Square square) {
        List<Square> toGoto = new ArrayList<>();
        Square first = new Square(square.getxCordOnBoard() + 1, square.getyCordOnBoard(), null);
        Square second = new Square(square.getxCordOnBoard(), square.getyCordOnBoard() + 1, null);
        Square third = new Square(square.getxCordOnBoard() - 1, square.getyCordOnBoard(), null);
        Square forth = new Square(square.getxCordOnBoard(), square.getyCordOnBoard() - 1, null);
        if (belongsToBoard(first)) toGoto.add(first);
        if (belongsToBoard(second)) toGoto.add(second);
        if (belongsToBoard(third)) toGoto.add(third);
        if (belongsToBoard(forth)) toGoto.add(forth);
        return toGoto;
    }

    /**
     * Checks if the square with the given coordinates belongs to the board
     * @param square square to check if it belongs to the board
     * @return True if belongs, false otherwise
     */
    boolean belongsToBoard(Square square) {
        return square.getxCordOnBoard() >= 0 && square.getyCordOnBoard() >= 0 && square.getxCordOnBoard() < xDimension && square.getyCordOnBoard() < yDimension;
    }

    /**
     * Checks if the given square is currently occupied by any unit
     * @param square square to check if it is occupied
     * @return True if empty, false otherwise
     */
    boolean squareIsEmpty(Square square) {
        return !square.hasMinionOnSquare();
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
            selectedSquare = board.get((int) (point.getX() * xDimension + point.getY()));
        } else selectedSquare = null;
    }

    public Square getSelectedSquare() {
        return selectedSquare;
    }

    public void addSquare(Square square) {
        board.add(square);
    }

    public List<Square> getBoard() {
        return board;
    }

    public List<Square> getToRevert() {
        return toRevert;
    }

    public void setToRevert(List<Square> toRevert) {
        this.toRevert = toRevert;
    }

    public void clearRevertable() {
        toRevert.clear();
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "\nxDimension=" + xDimension +
                ", \nyDimension=" + yDimension +
                ", \nboard=" + board +
                ", \ngameBoard=" + Arrays.toString(gameBoard) +
                ", \nselectedSquare=" + selectedSquare +
                ", \ntoRevert=" + toRevert +
                '}';
    }
}
