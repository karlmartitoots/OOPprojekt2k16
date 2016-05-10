package userinterface;

import card.MinionCard;
import javafx.geometry.Point2D;

import java.util.*;

class GameBoard {
    private final static int xDimension = 10;
    private final static int yDimension = 10;
    private List<Square> boardBySquares = new ArrayList<>();
    //should make a few things easier to implement
    //!!should only be changed when currentlySelectedSquare is updated - then the previouslySelectedSquare is set as the previous current (obviously)
    private Square previouslySelectedSquare = new Square();
    private Square currentlySelectedSquare = new Square();
    private List<Square> squarePossibleToInteractWith = new ArrayList<>();

    /**
     * Gets the length of the boardBySquares on the X axis
     * @return x Dimension length
     */
    static int getxDimension() {
        return xDimension;
    }

    /**
     * Gets the length of the boardBySquares on the Y axis
     * @return y Dimension length
     */
    static int getyDimension() {
        return yDimension;
    }

    /**
     * Moves a minion from a previous square to the next square.
     * @param previousSquare The previous square the minion was on (or is on before moving).
     * @param nextSquare The next square the minion will be on.
     */
    void moveCardIfPossible(Square previousSquare, Square nextSquare) {
        boolean squaresPossibleToMoveContainsSquareToMoveTo = false;
        for (Square square : squarePossibleToInteractWith) {
            if (nextSquare.getxCordOnBoard() == square.getxCordOnBoard() && nextSquare.getyCordOnBoard() == square.getyCordOnBoard())
                squaresPossibleToMoveContainsSquareToMoveTo = true;
        }
        if (!nextSquare.hasMinionOnSquare() && squaresPossibleToMoveContainsSquareToMoveTo) {
            MinionCard minion = previousSquare.getCard();
            previousSquare.removeCardIfHas();
            boardBySquares.set(previousSquare.squares1DPosition(), previousSquare);
            nextSquare.setSquaresCard(minion);
            boardBySquares.set(nextSquare.squares1DPosition(), nextSquare);
            nextSquare.getCard().blockMovement();
        }
    }

    /**
     * Finds all the possible squares a minion can go to from the given position using BFS
     * @return All squares the minion can move
     */
    List<Square> getAllPossibleSquares() {
        Map<Square, Integer> movesUsedToGo = new HashMap<>();
        movesUsedToGo.put(getCurrentlySelectedSquare(), 0);
        Queue<Square> queueOfSquaresToCheck = new LinkedList<>();
        List<Square> squaresPossibleToMoveTo = new ArrayList<>();
        queueOfSquaresToCheck.add(getCurrentlySelectedSquare());
        boolean[] hasBeenVisited = new boolean[xDimension * yDimension];
        hasBeenVisited[getCurrentlySelectedSquare().squares1DPosition()] = true;
        boardBySquares.forEach(square -> {
            if (square.hasMinionOnSquare()) hasBeenVisited[square.squares1DPosition()] = true;
        });
        while (!queueOfSquaresToCheck.isEmpty()) {
            Square nextSquareToCheck = queueOfSquaresToCheck.poll();
            List<Square> toExplore = expand(nextSquareToCheck);
            toExplore.forEach(currentSquare -> {
                if (!hasBeenVisited[currentSquare.squares1DPosition()] && squareIsEmpty(currentSquare) && (movesUsedToGo.get(nextSquareToCheck) < getCurrentlySelectedSquare().getCard().getMovementReach())) {
                    hasBeenVisited[currentSquare.squares1DPosition()] = true;
                    squaresPossibleToMoveTo.add(currentSquare);
                    queueOfSquaresToCheck.add(currentSquare);
                    movesUsedToGo.put(currentSquare, movesUsedToGo.get(nextSquareToCheck) + 1);
                }
            });
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
        if (square.squares1DPosition() + yDimension < xDimension * yDimension) {
            toGoto.add(boardBySquares.get(square.squares1DPosition() + yDimension));
        }
        if (square.squares1DPosition() - yDimension >= 0) {
            toGoto.add(boardBySquares.get(square.squares1DPosition() - yDimension));
        }
        if (square.squares1DPosition() % xDimension < (square.squares1DPosition() + 1) % xDimension) {
            toGoto.add(boardBySquares.get(square.squares1DPosition() + 1));
        }
        if (square.squares1DPosition() % xDimension > ((square.squares1DPosition() - 1)) % xDimension && square.squares1DPosition() - 1 >= 0) {
            toGoto.add(boardBySquares.get(square.squares1DPosition() - 1));
        }
        return toGoto;
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
    void setCurrentlySelectedSquare(Point2D point) {
        if (point.getX() >= 0) {
            previouslySelectedSquare = currentlySelectedSquare;
            currentlySelectedSquare = boardBySquares.get(Square.pointToSquare1DPosition(point));
        } else currentlySelectedSquare = new Square();
    }

    /**
     * Getter method for the gameboard square, previously clicked on.
     * @return Returns the previously selected square.
     */
    Square getCurrentlySelectedSquare() {
        return currentlySelectedSquare;
    }

    public Square getPreviouslySelectedSquare() {
        return previouslySelectedSquare;
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
     * Getter method for the squarePossibleToInteractWith field.
     * @return Returns a previously saved list of squares that a minion could have moved to.
     */
    List<Square> getSquarePossibleToInteractWith() {
        return squarePossibleToInteractWith;
    }

    /**
     * Setter method for the squarePossibleToInteractWith field.
     * @param squarePossibleToInteractWith The list given as a parameter for the method is set as the objects squarePossibleToMove.
     */
    void setSquarePossibleToInteractWith(List<Square> squarePossibleToInteractWith) {
        this.squarePossibleToInteractWith = squarePossibleToInteractWith;
    }

    /**
     * Removes all squares from squarePossibleToInteractWith .
     */
    void clearSquaresPossibleToInteractWith() {
        squarePossibleToInteractWith.clear();
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "\nxDimension=" + xDimension +
                ", \nyDimension=" + yDimension +
                ", \nboardBySquares=" + boardBySquares +
                ", \ncurrentlySelectedSquare=" + currentlySelectedSquare +
                ", \nsquarePossibleToInteractWith=" + squarePossibleToInteractWith +
                '}';
    }
}
