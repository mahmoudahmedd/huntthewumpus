package model;

import java.util.List;
import java.util.Set;

public interface Game {
     void startGame();
     void playerMovesToCave(int cave);
     void playerShootsToCave(int cave);
     int[][] getCaveslinks();
     boolean isGameOver();
     int getNumberOfArrows();
     List<String> getMessages();
     int getWumpusCave();
     int getPlayerCave();
}
