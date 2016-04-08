package userFeatures;

import java.util.*;

import card.*;

public class GameBoard {
    private final int xDimension = 10;
    private final int yDimension = 10;
    private int[][] gameBoard = new int[xDimension][yDimension];
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
    public void placeGenerals(General white, General black) {
        gameBoard[white.getStartingWhite().getxCord()][white.getStartingWhite().getyCord()] = white.getID();
        gameBoard[black.getStartingBlack().getxCord()][black.getStartingBlack().getyCord()] = black.getID() * (-1);
    }

    /**
     * Moves the selected unit to the target square
     * @param minion Minion to move
     * @param target Target square
     */
    public void moveUnit(Minion minion, Square target) {
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
    public List<Square> getAllPossibleSquares(Minion minion) {
        Queue<Square> queue = new LinkedList<>();
        Square start = minion.getCurrentPosition();
        List<Square> possibleToMove = new ArrayList<>();
        queue.add(start);
        boolean[] hasBeenVisited = new boolean[xDimension * yDimension];
        hasBeenVisited[start.integerValue(xDimension)] = true;
        while (!queue.isEmpty()) {
            Square parent = queue.poll();
            List<Square> toExplore = expand(parent);
            for (Square square : toExplore) {
                if (!hasBeenVisited[square.integerValue(xDimension)] && squareIsEmpty(square) && start.getDistance(square) <= minion.getSpeed()) {
                    hasBeenVisited[square.integerValue(xDimension)] = true;
                    possibleToMove.add(square);
                    queue.add(square);
                }
            }
        }
        return possibleToMove;
    }

    //Should merge wtih the other BFS method TODO
    public Stack<Square> getPath(Square start, Square end) {
        //Does a BFS for the path;
        Queue<Square> queue = new LinkedList<>();
        Map<Square, Square> paths = new HashMap<>();
        queue.add(start);
        boolean[] hasBeenVisited = new boolean[xDimension * yDimension];
        hasBeenVisited[start.integerValue(xDimension)] = true;
        while (!queue.isEmpty()) {

            Square parent = queue.poll();
            if (parent == end) break;
            List<Square> toExplore = expand(parent);
            for (Square square : toExplore) {
                if (!hasBeenVisited[square.integerValue(xDimension)] && squareIsEmpty(square)) {
                    hasBeenVisited[square.integerValue(xDimension)] = true;
                    paths.put(square, parent);
                    queue.add(square);
                }
            }
        }
        Stack<Square> path = generatePath(paths, start, end);
        return path;
    }

    /**
     * Expands the current search space for searching
     * @param square toExpand
     * @return Expanded squares
     */
    private List<Square> expand(Square square) {
        List<Square> toGoto = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Square pot = new Square(square.getxCord() + i, square.getyCord() + j, null);
                if (Math.abs(i - j) == 1 && belongsToBoard(pot)) {
                    toGoto.add(pot);
                }
            }
        }
        return toGoto;
    }

    /**
     * Checks if the square with the given coordinates belongs to the board
     * @param square toCheck
     * @return True if belongs, false otherwise
     */
    boolean belongsToBoard(Square square) {
        if (square.getxCord() >= 0 && square.getxCord() < xDimension && square.getyCord() >= 0 && square.getyCord() < 0)
            return true;
        else return false;
    }

    /**
     * Checks if the given square is currently occupied by any unit
     * @param square to Check
     * @return True if empty, False otherwise
     */
    boolean squareIsEmpty(Square square) {
        if (gameBoard[square.getxCord()][square.getyCord()] == 0) return true;
        else return false;
    }

    /**
     * Generets the path to move from the given map into the stack for easier processing
     * @param paths Map containing paths
     * @param start Starting Square
     * @param end Ending  Square
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


}
