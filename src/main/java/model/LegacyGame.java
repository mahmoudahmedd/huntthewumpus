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


    enum Hazard {
        Wumpus("there's an awful smell"),
        Bat("you hear a rustling"),
        Pit("you feel a draft");

        Hazard(String warning) {
            this.warning = warning;
        }

        final String warning;
    }
    
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

    public void initializeGameParameters() {
        messages = new ArrayList<>();
        numberOfArrows = GameInitialConfigurations.NUMBER_OF_ARROWS;
        gameOver = GameInitialConfigurations.GAME_OVER;
        numberOfCaves=GameInitialConfigurations.NUMBER_OF_CAVES;
    }

    public void initializeHazards() {
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
}
