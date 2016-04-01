import com.sun.xml.internal.bind.v2.TODO;

import java.util.*;


public class gameBoard {
    private final int xDimension = 10;
    private final int yDimension = 10;
    private int[][] gameBoard = new int[xDimension][yDimension];
    //Every minion can be stored as an integer on the board - negative value for player a, positive for b. Possible to put unique id for every card.

    public void placeGenerals(General white, General black) {
        gameBoard[white.getStartingWhite().getxCord()][white.getStartingWhite().getyCord()] = white.getID();
        gameBoard[black.getStartingBlack().getxCord()][black.getStartingBlack().getyCord()] = black.getID() * (-1);
    }

    public void moveUnit(Minion minion, Square target) {
        Square parent = minion.getCurrentPosition();
        Stack<Square> path = getPath(parent, target);
        while (!path.isEmpty()) {
            minion.setCurrentPosition(path.pop());
        }

    }

    public List<Square> getAllPossibleSquares(Minion minion) {
        //TODO make on function for BFS
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

    private List<Square> expand(Square square) {
        List<Square> toGoto = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Square pot = new Square(square.getxCord() + i, square.getyCord() + j);
                if (Math.abs(i - j) == 1 && belongsToBoard(pot)) {
                    toGoto.add(pot);
                }
            }
        }
        return toGoto;
    }

    boolean belongsToBoard(Square square) {
        if (square.getxCord() >= 0 && square.getxCord() < xDimension && square.getyCord() >= 0 && square.getyCord() < 0)
            return true;
        else return false;
    }

    boolean squareIsEmpty(Square square) {
        if (gameBoard[square.getxCord()][square.getyCord()] == 0) return true;
        else return false;
    }

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
