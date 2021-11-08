package presenter;

import view.Wumpus;
import view.WumpusGameDTO;
import view.WumpusView;

import java.util.*;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class WumpusPresenterImpl implements WumpusPresenter {

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
    private final WumpusView view;

    public WumpusPresenterImpl(WumpusView view) {
        this.view = view;
        wumpusGameDTO = new WumpusGameDTO();
        updateDTO();

    }

    private void updateDTO(){
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
        wumpusGameDTO.setGameOver(gameOver);
        updateDTO();
    }

    @Override
    public void move() {

        Set<Hazard> set = hazards[currRoom];

        if (set.contains(Hazard.Wumpus)) {
            messages.add("you've been eaten by the view.Wumpus");
            gameOver = true;

        } else if (set.contains(Hazard.Pit)) {
            messages.add("you fell into a pit");
            gameOver = true;

        } else if (set.contains(Hazard.Bat)) {
            messages.add("a bat dropped you in a random room");

            // teleport, but avoid 2 teleports in a row
            do {
                currRoom = rand.nextInt(rooms.length);
            } while (hazards[currRoom].contains(Hazard.Bat));

            // relocate the bat, but not to the player room or a room with a bat
            set.remove(Hazard.Bat);
            int newRoom;
            do {
                newRoom = rand.nextInt(rooms.length);
            } while (newRoom == currRoom || hazards[newRoom].contains(Hazard.Bat));
            hazards[newRoom].add(Hazard.Bat);

            // re-evaluate
            move();

        } else {

            // look around
            for (int link : links[currRoom]) {
                for (Hazard hazard : hazards[link])
                    messages.add(hazard.warning);
            }
        }
        updateDTO();
    }

    @Override
    public void handlePlayerPosition(int ex, int ey, boolean leftClick, boolean rightClick) {
        if (gameOver) {
            startNewGame();
        } else {
            int selectedRoom = -1;

            for (int link : links[currRoom]) {
                int cx = rooms[link][0];
                int cy = rooms[link][1];
                if (insideRoom(ex, ey, cx, cy)) {
                    selectedRoom = link;
                    break;
                }
            }

            if (selectedRoom == -1)
                return;

            if (leftClick) {
                // TODO write presenter.updateRoom(int room) ?????
                currRoom = selectedRoom;
                move();

            } else if (rightClick) {
                shoot(selectedRoom);
            }
        }
        updateDTO();
    }

    private boolean insideRoom(int ex, int ey, int cx, int cy) {
        return ((ex > cx && ex < cx + roomSize)
                && (ey > cy && ey < cy + roomSize));
    }

    @Override
    public void shoot(int room) {
        if (hazards[room].contains(Hazard.Wumpus)) {
            messages.add("You win! You've killed the view.Wumpus!");
            gameOver = true;

        } else {
            numArrows--;
            if (numArrows == 0) {
                messages.add("You ran out of arrows.");
                gameOver = true;

            } else if (rand.nextInt(4) != 0) { // 75 %
                hazards[wumpusRoom].remove(Hazard.Wumpus);
                wumpusRoom = links[wumpusRoom][rand.nextInt(3)];

                if (wumpusRoom == currRoom) {
                    messages.add("You woke the view.Wumpus and he ate you");
                    gameOver = true;

                } else {
                    messages.add("You woke the view.Wumpus and he moved");
                    hazards[wumpusRoom].add(Hazard.Wumpus);
                }
            }
        }
        updateDTO();
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
