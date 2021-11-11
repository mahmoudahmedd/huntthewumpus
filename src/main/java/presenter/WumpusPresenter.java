package presenter;

import java.util.List;

public interface WumpusPresenter {
    void startNewGame();
    void move(int cave);
    void setCurrentCave(int selectedCave);
    int[][] getLinks();
    int getCurrentCave();
    boolean isGameOver();
    void shoot(int room);
    int getCaveCount();
    int getPlayerSize();
    int getNumArrows();
    List<String> getMessages();
}
