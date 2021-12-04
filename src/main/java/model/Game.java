package model;

import model.gameobjects.Player;
import model.gameobjects.hazards.Wumpus;

import java.util.List;

public interface Game {
    void startGame();
    void playerMovesToCave(int cave);
    void playerShootsToCave(Integer... caves);
    boolean isGameOver();
    int getNumberOfArrows();
    List<String> getMessages();
    int getWumpusCave();
    int getPlayerCave();
    Player getEnemyPlayer();
    void enemyPlayerMovesToCave();
}