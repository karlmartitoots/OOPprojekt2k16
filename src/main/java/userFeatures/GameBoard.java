package userFeatures;

import java.util.*;

import card.*;
import javafx.geometry.Point2D;
//TODO: implement better - a very messy class right now
//probably the reason for slowing down development on the game
public class GameBoard {
    private final int xDimension = 10;
    private final int yDimension = 10;
    private List<Square> boardBySquares = new ArrayList<>();
    private int[][] gameBoard = new int[xDimension][yDimension];
    private Square selectedSquare = new Square();
    private List<Square> squaresPossibleToMove = new ArrayList<>();

    /*Every minion can be stored as an integer on the boardBySquares - negative value for player a, positive for b. Possible to put unique id for every card.
    Although keeping the data of the boardBySquares can be subject to change if there is a better data structure
    */

    /**
     * Gets the length of the boardBySquares on the X axis
     * @return x Dimension length
     */
    public int getxDimension() {
        return xDimension;
    }

    /**
     * Gets the length of the boardBySquares on the Y axis
     * @return y Dimension length
     */
    public int getyDimension() {
        return yDimension;
    }

    /**
     * Gets the current state of the game boardBySquares.
     * @return gameBoard
     */
    public int[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Setups the game by placing the generals of both players on the boardBySquares.
     * @param white The general used by the white player
     * @param black The general used by the black player
     * @param whiteStartingSquare Starting location for the white general
     * @param blackStartingSquare Starting location for the black general
     */
    public void placeGenerals(GeneralCard white, GeneralCard black, Point2D whiteStartingSquare, Point2D blackStartingSquare) {
        gameBoard[(int)whiteStartingSquare.getX()][(int)whiteStartingSquare.getY()] = white.getID();
        gameBoard[(int)blackStartingSquare.getX()][(int)blackStartingSquare.getY()] = black.getID() * (-1);
    }

    public void updateBoard() {
        for (Square square : boardBySquares) {
            if (square.hasMinionOnSquare()) {
                gameBoard[square.getxCordOnBoard()][square.getyCordOnBoard()] = square.getCard().getID();
            } else gameBoard[square.getxCordOnBoard()][square.getyCordOnBoard()] = 0;
        }

    }

    public void moveCard(Square previous, Square current) {
        boolean contains = false;
        for (Square square : squaresPossibleToMove) {
            if (current.getxCordOnBoard() == square.getxCordOnBoard() && current.getyCordOnBoard() == square.getyCordOnBoard())
                contains = true;
        }
        if (!current.hasMinionOnSquare() && contains) {
            MinionCard minion = previous.getCard();
            previous.placeCard(null);
            boardBySquares.set(previous.squares1DPosition(xDimension), previous);
            current.placeCard(minion);
            boardBySquares.set(current.squares1DPosition(xDimension), current);
        }
    }

    public void placeMinion(MinionCard minion, String side, int xCoord, int yCoord){
        gameBoard[xCoord][yCoord] = minion.getID()*(side.equals("white") ? 1 : -1);
    }
    /**
     * Finds all the possible squares a minion can go to from the given position using BFS
     * @return All squares the minion can move
     */
    public List<Square> getAllPossibleSquares() {
        Map<Square, Integer> movesUsedToGo = new HashMap<>();
        movesUsedToGo.put(getSelectedSquare(), 0);
        Queue<Square> queueOfSquaresToCheck = new LinkedList<>();
        List<Square> squaresPossibleToMoveTo = new ArrayList<>();
        queueOfSquaresToCheck.add(getSelectedSquare());
        boolean[] hasBeenVisited = new boolean[xDimension * yDimension];
        hasBeenVisited[getSelectedSquare().squares1DPosition(xDimension)] = true;
        for (Square square : boardBySquares) {
            if (square.hasMinionOnSquare()) hasBeenVisited[square.squares1DPosition(xDimension)] = true;
        }
        while (!queueOfSquaresToCheck.isEmpty()) {
            Square nextSquareToCheck = queueOfSquaresToCheck.poll();
            List<Square> toExplore = expand(nextSquareToCheck);

            for (Square currentSquare : toExplore) {

                if (!hasBeenVisited[currentSquare.squares1DPosition(xDimension)] && squareIsEmpty(currentSquare) && (movesUsedToGo.get(nextSquareToCheck) < getSelectedSquare().getCard().getSpeed())) {
                    hasBeenVisited[currentSquare.squares1DPosition(xDimension)] = true;
                    squaresPossibleToMoveTo.add(currentSquare);
                    queueOfSquaresToCheck.add(currentSquare);
                    movesUsedToGo.put(currentSquare, movesUsedToGo.get(nextSquareToCheck) + 1);
                }
            }
        }
        return squaresPossibleToMoveTo;
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
     * Checks if the square with the given coordinates belongs to the boardBySquares
     * @param square square to check if it belongs to the boardBySquares
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
            selectedSquare = boardBySquares.get((int) (point.getX() * xDimension + point.getY()));
        } else selectedSquare = new Square();
    }

    public Square getSelectedSquare() {
        return selectedSquare;
    }

    public void addSquare(Square square) {
        boardBySquares.add(square);
    }

    public List<Square> getBoardBySquares() {
        return boardBySquares;
    }

    public List<Square> getSquaresPossibleToMove() {
        return squaresPossibleToMove;
    }

    public void setSquaresPossibleToMove(List<Square> squaresPossibleToMove) {
        this.squaresPossibleToMove = squaresPossibleToMove;
    }

    public void clearSquaresPossibleToMove() {
        squaresPossibleToMove.clear();
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "\nxDimension=" + xDimension +
                ", \nyDimension=" + yDimension +
                ", \nboardBySquares=" + boardBySquares +
                ", \ngameBoard=" + Arrays.toString(gameBoard) +
                ", \nselectedSquare=" + selectedSquare +
                ", \nsquaresPossibleToMove=" + squaresPossibleToMove +
                '}';
    }
}
