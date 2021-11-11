package presenter;

import utilities.RandomNumberGenerator;

import java.util.*;


public class WumpusPresenterImpl implements WumpusPresenter {

    int[][] caveslinks = {{4, 7, 1}, {0, 9, 2}, {1, 11, 3}, {4, 13, 2}, {0, 5, 3},
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


    boolean gameOver = true;
    int currentCave;
    int numArrows;
    int wumpusCave;
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

        setCurrentCave(getRandomCave());

        hazards = new Set[numberOfCaves];
        for (int i = 0; i < numberOfCaves; i++)
            hazards[i] = EnumSet.noneOf(Hazard.class);

        // hazards can share caves (unless they are identical)
        int[] ordinals = {0, 1, 1, 1, 2, 2};
        Hazard[] values = Hazard.values();
        for (int ord : ordinals) {
            int cave;
            do {
                cave = getRandomCave();
            } while (tooClose(cave) || hazards[cave].contains(values[ord]));

            if (ord == 0)
                wumpusCave = cave;

            hazards[cave].add(values[ord]);
        }

        gameOver = false;

    }

    private int getRandomCave(int numberOfCaves) {
        return randomNumberGenerator.generateNumber(numberOfCaves);
    }

    private int getRandomCave() {
        return randomNumberGenerator.generateNumber(numberOfCaves);
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
            messages.add("a bat dropped you in a random cave");

            // teleport, but avoid 2 teleports in a row
            do {
                setCurrentCave(getRandomCave());
            } while (hazards[getCurrentCave()].contains(Hazard.Bat));

            // relocate the bat, but not to the player cave or a cave with a bat
            set.remove(Hazard.Bat);
            int newCave;
            do {
                newCave = getRandomCave();
            } while (newCave == getCurrentCave() || hazards[newCave].contains(Hazard.Bat));
            hazards[newCave].add(Hazard.Bat);

            // re-evaluate
            move();

        } else {

            // look around
            for (int link : getCavesLinks()[getCurrentCave()]) {
                for (Hazard hazard : hazards[link])
                    messages.add(hazard.warning);
            }
        }

    }

    @Override
    public void move(int cave) {
        boolean isCurrentCaveIsConnectedToTargetCave=checkThatCurrentCaveIsConnectedToTargetCave(cave);
        if(isCurrentCaveIsConnectedToTargetCave){
            setCurrentCave(cave);
            move();
        }
    }

    @Override
    public void setCurrentCave(int selectedCave) {
        currentCave = selectedCave;
    }

    @Override
    public int getWumpusCave() {
        return wumpusCave;
    }

    @Override
    public int[][] getCavesLinks() {
        return caveslinks;
    }

    @Override
    public int getCurrentCave() {
        return currentCave;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void shoot(int cave) {
        if (hazards[cave].contains(Hazard.Wumpus)) {
            messages.add("You win! You've killed the view.Wumpus!");
            gameOver = true;

        } else {
            numArrows--;
            if (numArrows == 0) {
                messages.add("You ran out of arrows.");
                gameOver = true;

            } else if (getRandomCave(4) != 0) { // 75 %
                hazards[wumpusCave].remove(Hazard.Wumpus);
                wumpusCave = getCavesLinks()[wumpusCave][getRandomCave(3)];

                if (wumpusCave == getCurrentCave()) {
                    messages.add("You woke the view.Wumpus and he ate you");
                    gameOver = true;

                } else {
                    messages.add("You woke the view.Wumpus and he moved");
                    hazards[wumpusCave].add(Hazard.Wumpus);
                }
            }
        }

    }


    private boolean tooClose(int cave) {
        if (getCurrentCave() == cave)
            return true;
        for (int link : getCavesLinks()[getCurrentCave()])
            if (cave == link)
                return true;
        return false;
    }


    @Override
    public int getNumArrows() {
        return numArrows;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    private boolean checkThatCurrentCaveIsConnectedToTargetCave(int targetCave) {
        boolean isCurrentCaveIsConnectedToTargetCave=false;
        for (int caveLink : getCavesLinks()[getCurrentCave()]) {
            if (caveLink==targetCave) {
                isCurrentCaveIsConnectedToTargetCave=true;
                break;
            }
        }
        return isCurrentCaveIsConnectedToTargetCave;
    }
}
