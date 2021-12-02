package presenter;

import java.util.List;

public interface WumpusPresenter {
    void startNewGame();
    void move(int cave);
    int getPlayerCave();
    boolean isGameOver();
    void shoot(Integer... caves);
    int getNumberOfArrows();
    List<String> getMessages();
    int getWumpusCave();
}
