public class Minion extends Card {
    private Square currentPosition;
    private final int attack;
    private final int maxHp;
    private final int speed;
    private int currentHp;


    public Minion(String name, int cost, String effect, int attack, int maxHp, int speed) {
        super(name, cost, effect);
        this.attack = attack;
        this.maxHp = maxHp;
        this.speed = speed;
        this.currentHp = maxHp;
    }

    public Square getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Square currentPosition) {
        this.currentPosition = currentPosition;
    }
}
