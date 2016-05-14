package setup;

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
