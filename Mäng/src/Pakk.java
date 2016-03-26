import java.util.ArrayList;
import java.util.List;

public class Pakk {
    private List<Kaart> kaardiPakk = new ArrayList<>();

    public Pakk(List<Kaart> kaardiPakk) {
        this.kaardiPakk = kaardiPakk;
    }

    public void shuffle() {
        for (int i = 0; i < kaardiPakk.size(); i++) {
            swap(i, (int) (Math.random() * kaardiPakk.size()));
        }
    }

    private void swap(int i, int j) {
        Kaart holder = kaardiPakk.get(i);
        kaardiPakk.set(i, kaardiPakk.get(j));
        kaardiPakk.set(j, holder);
    }

    public Kaart draw() {
        if (kaardiPakk.size() > 0) {
            return kaardiPakk.remove(0);
        } else return null;
    }

}
