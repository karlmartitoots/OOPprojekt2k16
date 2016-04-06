package userFeatures;

import card.Card;

import java.util.ArrayList;
import java.util.List;

public class Käe {
    private final int käeSuurus = 7;
    private List<Card> käe = new ArrayList<>();

    public void lisaKaart(Card card) {
        käe.add(card);
    }

    public void visataKäe() {
        käe.clear();
    }

    public void visataSuvalineKaart() {
        käe.remove((int) (Math.random() * käe.size()));
    }

    public void visataKaart(int i) {
        käe.remove(i);
    }
    
}
