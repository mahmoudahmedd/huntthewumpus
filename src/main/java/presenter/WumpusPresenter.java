package presenter;

import java.util.List;

public interface WumpusPresenter {
    void startNewGame();
    void move();

    void setCurrRoom(int selectedRoom);

    int[][] getLinks();

    int getCurrRoom();

    boolean isGameOver();

    void shoot(int room);


    int getRoomSize();

    int getPlayerSize();

    int getNumArrows();

    List<String> getMessages();
}
