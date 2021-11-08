package presenter;

import view.Wumpus;
import view.WumpusGameDTO;

import java.util.*;

public class WumpusPresenterImpl implements WumpusPresenter{

    int[][] rooms = {{334, 20}, {609, 220}, {499, 540}, {169, 540}, {62, 220},
            {169, 255}, {232, 168}, {334, 136}, {435, 168}, {499, 255}, {499, 361},
            {435, 447}, {334, 480}, {232, 447}, {169, 361}, {254, 336}, {285, 238},
            {387, 238}, {418, 336}, {334, 393}};

    int[][] links = {{4, 7, 1}, {0, 9, 2}, {1, 11, 3}, {4, 13, 2}, {0, 5, 3},
            {4, 6, 14}, {7, 16, 5}, {6, 0, 8}, {7, 17, 9}, {8, 1, 10}, {9, 18, 11},
            {10, 2, 12}, {13, 19, 11}, {14, 3, 12}, {5, 15, 13}, {14, 16, 19},
            {6, 17, 15}, {16, 8, 18}, {19, 10, 17}, {15, 12, 18}};


    enum Hazard {
        Wumpus("there's an awful smell"),
        Bat("you hear a rustling"),
        Pit("you feel a draft");
        Hazard(String warning) {
            this.warning = warning;
        }
        final String warning;
    }
    static final Random rand = new Random();

    final int roomSize = 45;
    final int playerSize = 16;

    boolean gameOver = true;
    int currRoom, numArrows, wumpusRoom;
    List<String> messages;
    Set<Hazard>[] hazards;

    WumpusGameDTO wumpusGameDTO;

    public WumpusPresenterImpl(){
        wumpusGameDTO = new WumpusGameDTO();
        wumpusGameDTO.setGameOver(gameOver);
        wumpusGameDTO.setWumpusRoom(wumpusRoom);
        wumpusGameDTO.setCurrRoom(currRoom);
        wumpusGameDTO.setLinks(links);
        wumpusGameDTO.setMessages(messages);
        wumpusGameDTO.setNumArrows(numArrows);
        wumpusGameDTO.setPlayerSize(playerSize);
        wumpusGameDTO.setRooms(rooms);
        wumpusGameDTO.setRoomSize(roomSize);
    }


    @Override
    public void startNewGame() {
        messages = new ArrayList<>();
        numArrows = 5;
        currRoom = rand.nextInt(rooms.length);

        hazards = new Set[rooms.length];
        for (int i = 0; i < rooms.length; i++)
            hazards[i] = EnumSet.noneOf(Hazard.class);

        // hazards can share rooms (unless they are identical)
        int[] ordinals = {0, 1, 1, 1, 2, 2};
        Hazard[] values = Hazard.values();
        for (int ord : ordinals) {
            int room;
            do {
                room = rand.nextInt(rooms.length);
            } while (tooClose(room) || hazards[room].contains(values[ord]));

            if (ord == 0)
                wumpusRoom = room;

            hazards[room].add(values[ord]);
        }

        gameOver = false;

    }

    @Override
    public void move() {

    }

    @Override
    public void shoot(int room) {

    }

    @Override
    public WumpusGameDTO getWumpusGameDTO() {
        return wumpusGameDTO;
    }

    private boolean tooClose(int room) {
        if (currRoom == room)
            return true;
        for (int link : links[currRoom])
            if (room == link)
                return true;
        return false;
    }
}
