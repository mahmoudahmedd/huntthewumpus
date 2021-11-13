package presenter;

import model.LegacyGame;

import java.util.List;

public interface WumpusPresenter {
    void startNewGame();
    void move(int cave);
    int getPlayerCave();
    boolean isGameOver();
    void shoot(int cave);
    int getNumberOfArrows();
    List<String> getMessages();
    int getWumpusCave();
}
