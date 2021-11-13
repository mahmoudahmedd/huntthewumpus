package presenter;

import java.util.List;

public interface WumpusPresenter {
    void startNewGame();
    void move(int cave);
    void setPlayerCave(int selectedCave);
    int[][] getCavesLinks();
    int getPlayerCave();
    boolean isGameOver();
    void shoot(int cave);
    int getNumberOfArrows();
    List<String> getMessages();

    int getWumpusCave();
}
