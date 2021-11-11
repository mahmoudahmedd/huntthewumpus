package presenter;

import utilities.RandomNumberGenerator;

import java.util.*;


public class WumpusPresenterImpl implements WumpusPresenter {

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


    final int roomSize = 45;
    final int playerSize = 16;
    boolean gameOver = true;
    int currRoom, numArrows, wumpusRoom;
    List<String> messages;
    Set<Hazard>[] hazards;
    final int numberOfCaves =20;

    RandomNumberGenerator randomNumberGenerator;

    public WumpusPresenterImpl(){
        this.randomNumberGenerator=new RandomNumberGenerator();
    }

    public WumpusPresenterImpl(RandomNumberGenerator randomNumberGenerator){
        this.randomNumberGenerator=randomNumberGenerator;
    }


    @Override
    public void startNewGame() {
        messages = new ArrayList<>();
        numArrows = 5;
        final int numberOfRooms = getNumberOfCaves();
        setCurrentCave(getRandomRoom(numberOfRooms));

        hazards = new Set[numberOfRooms];
        for (int i = 0; i < numberOfRooms; i++)
            hazards[i] = EnumSet.noneOf(Hazard.class);

        // hazards can share rooms (unless they are identical)
        int[] ordinals = {0, 1, 1, 1, 2, 2};
        Hazard[] values = Hazard.values();
        for (int ord : ordinals) {
            int room;
            do {
                room = getRandomRoom(numberOfRooms);
            } while (tooClose(room) || hazards[room].contains(values[ord]));

            if (ord == 0)
                wumpusRoom = room;

            hazards[room].add(values[ord]);
        }

        gameOver = false;

    }

    private int getNumberOfCaves() {
        return this.numberOfCaves;
    }

    private int getRandomRoom(int numberOfRooms) {
        return randomNumberGenerator.generateNumber(numberOfRooms);
    }

    public void move() {

        Set<Hazard> set = hazards[getCurrentCave()];

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
                setCurrentCave(getRandomRoom(getNumberOfCaves()));
            } while (hazards[getCurrentCave()].contains(Hazard.Bat));

            // relocate the bat, but not to the player room or a room with a bat
            set.remove(Hazard.Bat);
            int newRoom;
            do {
                newRoom = getRandomRoom(getNumberOfCaves());
            } while (newRoom == getCurrentCave() || hazards[newRoom].contains(Hazard.Bat));
            hazards[newRoom].add(Hazard.Bat);

            // re-evaluate
            move();

        } else {

            // look around
            for (int link : getLinks()[getCurrentCave()]) {
                for (Hazard hazard : hazards[link])
                    messages.add(hazard.warning);
            }
        }

    }

    @Override
    public void move(int cave) {
        validateThatRoomIsCorrect(cave);
        setCurrentCave(cave);
        move();
    }

    private void validateThatRoomIsCorrect(int room) {
    }

    @Override
    public void setCurrentCave(int selectedCave) {
        currRoom = selectedCave;
    }


    @Override
    public int[][] getLinks() {
        return links;
    }

    @Override
    public int getCurrentCave() {
        return currRoom;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
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

            } else if (getRandomRoom(4) != 0) { // 75 %
                hazards[wumpusRoom].remove(Hazard.Wumpus);
                wumpusRoom = getLinks()[wumpusRoom][getRandomRoom(3)];

                if (wumpusRoom == getCurrentCave()) {
                    messages.add("You woke the view.Wumpus and he ate you");
                    gameOver = true;

                } else {
                    messages.add("You woke the view.Wumpus and he moved");
                    hazards[wumpusRoom].add(Hazard.Wumpus);
                }
            }
        }

    }


    private boolean tooClose(int room) {
        if (getCurrentCave() == room)
            return true;
        for (int link : getLinks()[getCurrentCave()])
            if (room == link)
                return true;
        return false;
    }

    @Override
    public int getCaveCount() {
        return roomSize;
    }

    @Override
    public int getPlayerSize() {
        return playerSize;
    }

    @Override
    public int getNumArrows() {
        return numArrows;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }
}
