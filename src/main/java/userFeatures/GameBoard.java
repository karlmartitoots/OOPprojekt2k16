package userFeatures;

import card.MinionCard;
import javafx.geometry.Point2D;

import java.util.*;

class GameBoard {
    private final int xDimension = 10;
    private final int yDimension = 10;
    private List<Square> boardBySquares = new ArrayList<>();
    private Square selectedSquare = new Square();
    private List<Square> squaresPossibleToMove = new ArrayList<>();

    /*Every minion can be stored as an integer on the boardBySquares - negative value for player a, positive for b. Possible to put unique id for every card.
    Although keeping the data of the boardBySquares can be subject to change if there is a better data structure
    */

    /**
     * Gets the length of the boardBySquares on the X axis
     * @return x Dimension length
     */
    int getxDimension() {
        return xDimension;
    }

    /**
     * Gets the length of the boardBySquares on the Y axis
     * @return y Dimension length
     */
    int getyDimension() {
        return yDimension;
    }

    /**
     * Moves a minion from a previous square to the next square.
     * @param previousSquare The previous square the minion was on (or is on before moving).
     * @param nextSquare The next square the minion will be on.
     */
    void moveCard(Square previousSquare, Square nextSquare) {
        boolean squaresPossibleToMoveContainsSquareToMoveTo = false;
        for (Square square : squaresPossibleToMove) {
            if (nextSquare.getxCordOnBoard() == square.getxCordOnBoard() && nextSquare.getyCordOnBoard() == square.getyCordOnBoard())
                squaresPossibleToMoveContainsSquareToMoveTo = true;
        }
        if (!nextSquare.hasMinionOnSquare() && squaresPossibleToMoveContainsSquareToMoveTo) {
            MinionCard minion = previousSquare.getCard();
            previousSquare.removeCard();
            boardBySquares.set(previousSquare.squares1DPosition(xDimension), previousSquare);
            nextSquare.setCard(minion);
            boardBySquares.set(nextSquare.squares1DPosition(xDimension), nextSquare);
            nextSquare.getCard().setMoved(true);
        }
    }

    /**
     * Finds all the possible squares a minion can go to from the given position using BFS
     * @return All squares the minion can move
     */
    List<Square> getAllPossibleSquares() {
        Map<Square, Integer> movesUsedToGo = new HashMap<>();
        movesUsedToGo.put(getSelectedSquare(), 0);
        Queue<Square> queueOfSquaresToCheck = new LinkedList<>();
        List<Square> squaresPossibleToMoveTo = new ArrayList<>();
        queueOfSquaresToCheck.add(getSelectedSquare());
        boolean[] hasBeenVisited = new boolean[xDimension * yDimension];
        hasBeenVisited[getSelectedSquare().squares1DPosition(xDimension)] = true;
        //TODO: see if what intellij suggests: boardBySquares.stream().filter(square -> square.hasMinionOnSquare()).forEach(square -> hasBeenVisited[square.squares1DPosition(xDimension)] = true); might work better
        for (Square square : boardBySquares) {
            if (square.hasMinionOnSquare()) hasBeenVisited[square.squares1DPosition(xDimension)] = true;
        }
        while (!queueOfSquaresToCheck.isEmpty()) {
            Square nextSquareToCheck = queueOfSquaresToCheck.poll();
            List<Square> toExplore = expand(nextSquareToCheck);

            //TODO: see if foreach lambda works better
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
    private boolean belongsToBoard(Square square) {
        return square.getxCordOnBoard() >= 0 && square.getyCordOnBoard() >= 0 && square.getxCordOnBoard() < xDimension && square.getyCordOnBoard() < yDimension;
    }

    /**
     * Checks if the given square is currently occupied by any unit
     * @param square square to check if it is occupied
     * @return True if empty, false otherwise
     */
    private boolean squareIsEmpty(Square square) {
        return !square.hasMinionOnSquare();
    }

    /**
     * Translates Point2D coordinates on the screen into gameboard coordinates and if the point is on the gameboard,
     * sets that square as the current previously selected square.
     * @param point Point on the GUI that will be translated to gameboard coordinates.
     */
    void setSelectedSquare(Point2D point) {
        if (point.getX() >= 0) {
            selectedSquare = boardBySquares.get((int) (point.getX() * xDimension + point.getY()));
        } else selectedSquare = new Square();
    }

    /**
     * Getter method for the gameboard square, previously clicked on.
     * @return Returns the previously selected square.
     */
    Square getSelectedSquare() {
        return selectedSquare;
    }

    /**
     * Add method for boardBySquares for adding squares in, in the beginning of the game.
     * TODO: we can make a static initializer for the board in GUI, to add all the squares so we might not need a loadBoard
     * @param square The square added to boardBySquares.
     */
    void addSquareToBoardBySquares(Square square) {
        boardBySquares.add(square);
    }

    /**
     * Getter method to get boardBySquares list. BoardBySquares contains all the squares on the gameboard.
     * @return Returns a list of all the squares on the gameboard.
     */
    List<Square> getBoardBySquares() {
        return boardBySquares;
    }

    /**
     * Getter method for the squaresPossibleToMove field.
     * @return Returns a previously saved list of squares that a minion could have moved to.
     */
    List<Square> getSquaresPossibleToMove() {
        return squaresPossibleToMove;
    }

    /**
     * Setter method for the squaresPossibleToMove field.
     * @param squaresPossibleToMove The list given as a parameter for the method is set as the objects squarePossibleToMove.
     */
    void setSquaresPossibleToMove(List<Square> squaresPossibleToMove) {
        this.squaresPossibleToMove = squaresPossibleToMove;
    }

    /**
     * Removes all squares from squaresPossibleToMove .
     */
    void clearSquaresPossibleToMove() {
        squaresPossibleToMove.clear();
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "\nxDimension=" + xDimension +
                ", \nyDimension=" + yDimension +
                ", \nboardBySquares=" + boardBySquares +
                ", \nselectedSquare=" + selectedSquare +
                ", \nsquaresPossibleToMove=" + squaresPossibleToMove +
                '}';
    }
}
