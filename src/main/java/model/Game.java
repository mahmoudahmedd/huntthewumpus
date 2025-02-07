package model;

import model.gameobjects.hazards.EnemyPlayer;

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
    EnemyPlayer getEnemyPlayer();
    void enemyPlayerMovesToCave();
    void enemyPlayerShootsToCave();
}