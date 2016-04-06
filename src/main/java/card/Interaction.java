package card;
public interface Interaction {
    public void interact(Card target);

    boolean isExpired();
}
