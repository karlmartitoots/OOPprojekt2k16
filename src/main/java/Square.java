/**
 * Created by Alex on 31/03/2016.
 */
public class Square {
    private int xCord;
    private int yCord;

    public Square(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public int integerValue(int xDim) {
        return xDim * xCord + yCord;
    }

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    @Override
    public String toString() {
        return "Node{" +
                "xCord=" + xCord +
                ", yCord=" + yCord +
                '}';
    }
}
