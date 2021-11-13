package model;

import presenter.WumpusPresenterImpl;
import utilities.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class LegacyGame implements Game{

    int[][] caveslinks = {{4, 7, 1}, {0, 9, 2}, {1, 11, 3}, {4, 13, 2}, {0, 5, 3},
            {4, 6, 14}, {7, 16, 5}, {6, 0, 8}, {7, 17, 9}, {8, 1, 10}, {9, 18, 11},
            {10, 2, 12}, {13, 19, 11}, {14, 3, 12}, {5, 15, 13}, {14, 16, 19},
            {6, 17, 15}, {16, 8, 18}, {19, 10, 17}, {15, 12, 18}};

    
    boolean gameOver;
    int numberOfArrows;
    List<String> messages;
    int numberOfCaves;
    Set<Hazard>[] hazards;
    int playerCave;
    int wumpusCave;

    RandomNumberGenerator randomNumberGenerator;

    public LegacyGame(){
        randomNumberGenerator=new RandomNumberGenerator();
    }

    public LegacyGame(RandomNumberGenerator randomNumberGenerator){
        this.randomNumberGenerator=randomNumberGenerator;
    }

    @Override
    public void startGame() {
        initializeGameParameters();
        setPlayerCave(getRandomCave());
        initializeHazards();
    }

    @Override
    public void playerMovesToCave(int cave) {
        boolean isCurrentCaveIsConnectedToTargetCave= checkThatCurrentCaveIsConnectedToTargetCave(cave);
        if(isCurrentCaveIsConnectedToTargetCave){
            setPlayerCave(cave);
            move();
        }
    }

    private void move() {

        Set<Hazard> set = hazards[playerCave];

        if (set.contains(Hazard.Wumpus)) {
            messages.add("you've been eaten by the Wumpus");
            gameOver = true;

        } else if (set.contains(Hazard.Pit)) {
            messages.add("you fell into a pit");
            gameOver = true;

        } else if (set.contains(Hazard.Bat)) {
            messages.add("a bat dropped you in a random cave");

            // teleport, but avoid 2 teleports in a row
            do {
                setPlayerCave(getRandomCave());
            } while (hazards[playerCave].contains(Hazard.Bat));

            // relocate the bat, but not to the player cave or a cave with a bat
            set.remove(Hazard.Bat);
            int newCave;
            do {
                newCave = getRandomCave();
            } while (newCave == playerCave || hazards[newCave].contains(Hazard.Bat));
            hazards[newCave].add(Hazard.Bat);

            // re-evaluate
            move();

        } else {

            // look around
            for (int link : caveslinks[playerCave]) {
                for (Hazard hazard : hazards[link])
                    messages.add(hazard.warning);
            }
        }

    }

    @Override
    public void playerShootsToCave(int cave) {
        final int maximumNumberForCalculatingWumpusWakeupProbability=4;
        if (hazards[cave].contains(Hazard.Wumpus)) {
            messages.add("You win! You've killed the Wumpus!");
            gameOver = true;

        } else {
            numberOfArrows--;
            if (numberOfArrows == 0) {
                messages.add("You ran out of arrows.");
                gameOver = true;

            } else if (randomNumberGenerator.generateNumber(maximumNumberForCalculatingWumpusWakeupProbability) != 0) { // 75 %
                hazards[wumpusCave].remove(Hazard.Wumpus);
                final int numberOfLinkedCaves=3;
                wumpusCave = caveslinks[wumpusCave][randomNumberGenerator.generateNumber(numberOfLinkedCaves)];

                if (wumpusCave == playerCave) {
                    messages.add("You woke the Wumpus and it ate you");
                    gameOver = true;

                } else {
                    messages.add("You woke the Wumpus and it moved");
                    hazards[wumpusCave].add(Hazard.Wumpus);
                }
            }
        }
    }

    private void initializeGameParameters() {
        messages = new ArrayList<>();
        numberOfArrows = GameInitialConfigurations.NUMBER_OF_ARROWS;
        gameOver = GameInitialConfigurations.GAME_OVER;
        numberOfCaves=GameInitialConfigurations.NUMBER_OF_CAVES;
    }

    private void initializeHazards() {
        hazards = new Set[numberOfCaves];
        for (int i = 0; i < numberOfCaves; i++){
            hazards[i] = EnumSet.noneOf(Hazard.class);
        }

        // hazards can share caves (unless they are identical)
        int[] ordinals = {0, 1, 1, 1, 2, 2};
        Hazard[] values = Hazard.values();
        for (int ord : ordinals) {
            int cave;
            do {
                cave = getRandomCave();
            } while (tooClose(cave) || hazards[cave].contains(values[ord]));

            if (ord == 0) {
                wumpusCave = cave;
            }

            hazards[cave].add(values[ord]);
        }
    }

    private boolean tooClose(int cave) {
        if (playerCave == cave)
            return true;
        for (int link : caveslinks[playerCave])
            if (cave == link)
                return true;
        return false;
    }

    private int getRandomCave() {
        return randomNumberGenerator.generateNumber(numberOfCaves);
    }

    public void setPlayerCave(int playerCave) {
        this.playerCave = playerCave;
    }

    private boolean checkThatCurrentCaveIsConnectedToTargetCave(int targetCave) {
        boolean isCurrentCaveIsConnectedToTargetCave=false;
        for (int caveLink : caveslinks[playerCave]) {
            if (caveLink==targetCave) {
                isCurrentCaveIsConnectedToTargetCave=true;
                break;
            }
        }
        return isCurrentCaveIsConnectedToTargetCave;
    }

    @Override
    public int[][] getCaveslinks() {
        return caveslinks;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public int getNumberOfArrows() {
        return numberOfArrows;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public int getWumpusCave() {
        return wumpusCave;
    }

    @Override
    public int getPlayerCave() {
        return playerCave;
    }

}
