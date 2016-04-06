package userFeatures;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import card.*;

public class Pakk {
    private List<Card> kaardiPakk = new ArrayList<>();
    private final int pakisuurus = 30;


    public Pakk(List<Card> kaardiPakk) {
        this.kaardiPakk = kaardiPakk;
    }

    public void shuffle() {
        for (int i = 0; i < kaardiPakk.size(); i++) {
            swap(i, (int) (Math.random() * kaardiPakk.size()));
        }
    }

    private void swap(int i, int j) {
        Card holder = kaardiPakk.get(i);
        kaardiPakk.set(i, kaardiPakk.get(j));
        kaardiPakk.set(j, holder);
    }

    public Card draw() {
        if (kaardiPakk.size() > 0) {
            return kaardiPakk.remove(0);
        } else return null;
    }
    /*
    public List<Card> loadPaki(String filename) {
        filename += ".txt";
        List<Card> pakk = new ArrayList<>();
        Andmebaas andmebaas = new Andmebaas();
        try {
            int counter = 0;
            Scanner sc = new Scanner(new File(filename), "UTF-8");
            while (sc.hasNext() && counter < pakisuurus) {
                pakk.add(andmebaas.readKaart(sc.nextLine()));
                counter++;
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return pakk;
    }
    */

    public List<Card> getKaardiPakk() {
        return kaardiPakk;
    }
}
