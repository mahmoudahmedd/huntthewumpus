package acceptance;


import presenter.WumpusPresenter;
import presenter.WumpusPresenterImpl;



public class PresenterSingleton {
    private static PresenterSingleton instance;
    public WumpusPresenter value;

    private PresenterSingleton() {

        this.value = new WumpusPresenterImpl();
    }

    public static PresenterSingleton getInstance() {
        if (instance == null) {
            instance = new PresenterSingleton();
        }
        return instance;
    }
}
