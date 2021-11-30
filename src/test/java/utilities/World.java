package utilities;

import presenter.WumpusPresenter;
import presenter.WumpusPresenterImpl;

public class World {
    WumpusPresenter wumpusPresenter;
    RandomNumberGeneratorBuilder randomNumberGeneratorBuilder;

    public World() {
        this.randomNumberGeneratorBuilder = new RandomNumberGeneratorBuilder();
    }

    public RandomNumberGeneratorBuilder getRandomNumberGeneratorBuilder() {
        return randomNumberGeneratorBuilder;
    }

    public WumpusPresenter getWumpusPresenter() {
        if(this.wumpusPresenter == null) {
            wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
            wumpusPresenter.startNewGame();
        }

        return this.wumpusPresenter;
    }
}
