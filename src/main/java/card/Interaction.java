package card;

/**
 * Interface for general effects cards have.
 */
public interface Interaction {
    public void interact(Card target);

    boolean isExpired();
}
