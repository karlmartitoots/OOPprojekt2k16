import java.util.ArrayList;
import java.util.List;

public class Käe {
    private final int käeSuurus = 7;
    private List<Kaart> käe = new ArrayList<>();

    public void lisaKaart(Kaart kaart) {
        käe.add(kaart);
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
