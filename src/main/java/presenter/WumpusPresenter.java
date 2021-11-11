package presenter;

import java.util.List;

public interface WumpusPresenter {
    void startNewGame();
    void move(int cave);
    void setCurrentCave(int selectedCave);
    int[][] getCavesLinks();
    int getCurrentCave();
    boolean isGameOver();
    void shoot(int cave);
    int getNumArrows();
    List<String> getMessages();

    int getWumpusCave();
}
