package presenter;

import view.WumpusGameDTO;

public interface WumpusPresenter {
    void startNewGame();

    void move();

    void handlePlayerPosition(int ex, int ey, boolean leftClick, boolean rightClick);

    void shoot(int room);

    WumpusGameDTO getWumpusGameDTO();
}
