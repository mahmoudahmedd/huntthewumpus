package presenter;

import model.Game;
import model.GameWorld;
import utilities.RandomNumberGenerator;

import java.util.List;


public class WumpusPresenterImpl implements WumpusPresenter {
    Game gameModel;

    public WumpusPresenterImpl(){
        this.gameModel=new GameWorld();
    }

    public WumpusPresenterImpl(RandomNumberGenerator randomNumberGenerator){
        this.gameModel=new GameWorld(randomNumberGenerator);
    }

    @Override
    public void startNewGame() {
        gameModel.startGame();
    }

    @Override
    public void move(int cave) {
        gameModel.playerMovesToCave(cave);
    }

    @Override
    public void shoot(Integer... caves) {
        gameModel.playerShootsToCave(caves);
    }


    @Override
    public int getWumpusCave() {
        return gameModel.getWumpusCave();
    }

    @Override
    public int getEnemyPlayerCave() {
        return gameModel.getEnemyPlayer().getCave().getNumber();
    }

    @Override
    public void moveEnemyPlayer() {
        gameModel.enemyPlayerMovesToCave();
    }

    @Override
    public boolean isEnemyPlayerDead() {
        return gameModel.getEnemyPlayer().isDead();
    }

    @Override
    public void enemyPlayerShootsToCave() {
        gameModel.enemyPlayerShootsToCave();
    }


    @Override
    public int getPlayerCave() {
        return gameModel.getPlayerCave();
    }

    @Override
    public boolean isGameOver() {
        return gameModel.isGameOver();
    }

    @Override
    public int getNumberOfArrows() {
        return gameModel.getNumberOfArrows();
    }

    @Override
    public List<String> getMessages() {
        return gameModel.getMessages();
    }

}
