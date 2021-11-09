package presenter;

import view.WumpusGameDTO;

import java.util.List;

public interface WumpusPresenter {
    void startNewGame();
    void move();

    void setCurrRoom(int selectedRoom);

    int[][] getRooms();

    int[][] getLinks();

    int getCurrRoom();

    boolean isGameOver();

    void shoot(int room);


    int getRoomSize();

    int getPlayerSize();

    int getNumArrows();

    List<String> getMessages();
}
