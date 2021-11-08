package presenter;

import view.WumpusGameDTO;

public interface WumpusPresenter {
    void startNewGame();
    void move();
    void shoot(int room);
    WumpusGameDTO getWumpusGameDTO();
}
