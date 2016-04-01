public class General extends Minion {
    private final Square startingWhite = new Square(1, 5);
    private final Square startingBlack = new Square(9, 6);

    public General(String name, int cost, String effect, int ID, int attack, int health, int speed) {
        super(name, cost, effect, ID, attack, health, speed);
    }

    public Square getStartingWhite() {
        return startingWhite;
    }

    public Square getStartingBlack() {
        return startingBlack;
    }
}
