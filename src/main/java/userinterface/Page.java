package userinterface;

/**
 * Created by PusH on 5/14/2016.
 */
public class Page {
    private int page = 0;

    public void increase() {
        page++;
    }
    public void decrease(){
        page--;
    }

    public int get() {
        return page;
    }

}
