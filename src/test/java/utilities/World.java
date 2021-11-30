package utilities;

import model.GameWorld;
import presenter.WumpusPresenter;
import presenter.WumpusPresenterImpl;

public class World {
    WumpusPresenter wumpusPresenter;
    GameWorld gameWorld;
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

    public GameWorld getGameWorld() {
        if(this.gameWorld == null) {
            this.gameWorld = new GameWorld(randomNumberGeneratorBuilder.build());
            this.gameWorld.startGame();
        }

        return this.gameWorld;
    }
}
