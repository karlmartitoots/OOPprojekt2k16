package card;

/**
 * Interface for general effects cards have.
 */
public interface Interaction {

    void interact(Card target);

    boolean isExpired();
}
