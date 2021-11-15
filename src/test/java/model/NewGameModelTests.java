package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utilities.RandomNumberGenerator;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class NewGameModelTests {

    @Mock
    RandomNumberGenerator randomNumberGenerator;

    @Test
    public void testGameMapInitializationProducedTheCorrectNumberOfCaves() {
        NewGame game = new NewGame();
        game.startGame();
        GameMap gameMap = game.getGameMap();

        assertEquals(GameInitialConfigurations.NUMBER_OF_CAVES,gameMap.getCaves().size());
    }

    @Test
    public void testThatGameMapInitializationProducedTheCorrectCaveLinks() {
        NewGame game = new NewGame();
        game.startGame();
        GameMap gameMap = game.getGameMap();
        final List<Cave> mapCaves = gameMap.getCaves();

        final int[] caveLinkIndexesToTest= new int[]{0,9,3,4};

        for(int caveLinkIndexToTest:caveLinkIndexesToTest){
            final Cave firstCave = mapCaves.get(
                    caveLinkIndexToTest);

            final int connectedCavesCount = 3;
            final Set<Cave> actualLinkedCavesToFirstCave = firstCave.getLinkedCaves();
            assertEquals(connectedCavesCount,actualLinkedCavesToFirstCave.size());

            final int[] expectedLinkedCavesToFirstCave = GameInitialConfigurations.CAVE_LINKS[caveLinkIndexToTest];

            for(int caveLink:expectedLinkedCavesToFirstCave){
                assertTrue(actualLinkedCavesToFirstCave.contains(new Cave(caveLink)));
            }
        }
    }

    @Test
    public void testThatPlayerIsAddedToInitialCave() {
        final int playerStartingCaveIndex = 9;
        final int wumpusStartingCaveIndex = 15;
        final int firstBatStartingCaveIndex = 19;
        final int secondBatStartingCaveIndex = 13;
        final int thirdBatStartingCaveIndex = 14;
        final int firstPitCave = 3;
        final int secondPitCave = 13;

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCaveIndex,
                wumpusStartingCaveIndex,
                firstBatStartingCaveIndex,
                secondBatStartingCaveIndex,
                thirdBatStartingCaveIndex,
                firstPitCave,
                secondPitCave
        );

        NewGame game = new NewGame(randomNumberGenerator);
        game.startGame();

        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertEquals(playerStartingCaveIndex, actualPlayerCaveIndex);
    }

    @Test
    public void testThatPlayerCaveIsAddedToGameMap() {
        final int playerStartingCaveIndex = 9;
        final int wumpusStartingCaveIndex = 15;
        final int firstBatStartingCaveIndex = 19;
        final int secondBatStartingCaveIndex = 13;
        final int thirdBatStartingCaveIndex = 14;
        final int firstPitCave = 3;
        final int secondPitCave = 13;
        int[] pitsInCavesIndexes = {firstPitCave, secondPitCave};

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCaveIndex,
                wumpusStartingCaveIndex,
                firstBatStartingCaveIndex,
                secondBatStartingCaveIndex,
                thirdBatStartingCaveIndex,
                firstPitCave,
                secondPitCave
        );

        NewGame game = new NewGame(randomNumberGenerator);
        game.startGame();

        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertEquals(playerStartingCaveIndex, actualPlayerCaveIndex);

        Cave playerCave=game.getGameMap().getCaves().get(playerStartingCaveIndex);
        Player player=game.getPlayer();
        assertTrue(playerCave.getGameObjects().contains(player));
    }

    @Test
    public void testThatWumpusIsAddedToCaveGameMap(){
        final int playerStartingCaveIndex = 9;
        final int wumpusStartingCaveIndex = 15;
        final int firstBatStartingCaveIndex = 19;
        final int secondBatStartingCaveIndex = 13;
        final int thirdBatStartingCaveIndex = 14;

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCaveIndex,
                wumpusStartingCaveIndex,
                firstBatStartingCaveIndex,
                secondBatStartingCaveIndex,
                thirdBatStartingCaveIndex
        );


        NewGame game = new NewGame(randomNumberGenerator);
        game.startGame();

        final int actualWumpusCaveIndex = game.getWumpusCave();
        assertEquals(wumpusStartingCaveIndex,actualWumpusCaveIndex);

        Cave wumpusCave=game.getGameMap().getCaves().get(wumpusStartingCaveIndex);
        Wumpus wumpus=game.getWumpus();
        assertTrue(wumpusCave.getGameObjects().contains(wumpus));

    }

    @Test
    public void testThatThreeBatsAreAddedToCaveGameMap() {
        final int playerStartingCaveIndex = 9;
        final int wumpusStartingCaveIndex = 15;
        final int firstBatStartingCaveIndex = 19;
        final int secondBatStartingCaveIndex = 13;
        final int thirdBatStartingCaveIndex = 14;
        int[] batsStartingCavesIndexes = {firstBatStartingCaveIndex, secondBatStartingCaveIndex, thirdBatStartingCaveIndex};

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCaveIndex,
                wumpusStartingCaveIndex,
                firstBatStartingCaveIndex,
                secondBatStartingCaveIndex,
                thirdBatStartingCaveIndex
                );

        NewGame game = new NewGame(randomNumberGenerator);
        game.startGame();

        List<Bat> listOfBats = game.getBats();

        assertEquals(GameInitialConfigurations.NUMBER_OF_BATS,listOfBats.size());

        for(int i = 0; i < listOfBats.size(); i++) {
            assertEquals(batsStartingCavesIndexes[i], listOfBats.get(i).getCave().getNumber());
            Cave batCave= game.getGameMap().getCaves().get(batsStartingCavesIndexes[i]);
            Bat bat = listOfBats.get(i);
            assertTrue(batCave.getGameObjects().contains(bat));
        }
    }

    @Test
    public void testThatTwoPitsAreAddedToCaveGameMap(){
        final int playerStartingCaveIndex = 9;
        final int wumpusStartingCaveIndex = 15;
        final int firstBatStartingCaveIndex = 19;
        final int secondBatStartingCaveIndex = 13;
        final int thirdBatStartingCaveIndex = 14;
        final int firstPitCave = 3;
        final int secondPitCave = 13;
        int[] pitsInCavesIndexes = {firstPitCave, secondPitCave};

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCaveIndex,
                wumpusStartingCaveIndex,
                firstBatStartingCaveIndex,
                secondBatStartingCaveIndex,
                thirdBatStartingCaveIndex,
                firstPitCave,
                secondPitCave
        );

        NewGame game = new NewGame(randomNumberGenerator);
        game.startGame();

        List<Pit> listOfPits = game.getPits();

        assertEquals(GameInitialConfigurations.NUMBER_OF_PITS,listOfPits.size());

        for(int i = 0; i < listOfPits.size(); i++) {
            assertEquals(pitsInCavesIndexes[i], listOfPits.get(i).getCave().getNumber());
            Cave PitInCave= game.getGameMap().getCaves().get(pitsInCavesIndexes[i]);
            Pit pit = listOfPits.get(i);
            assertTrue(PitInCave.getGameObjects().contains(pit));
        }

    }

    @Test
    public void testThatWumpusIsNotInitializedInSameCaveAsPlayer(){
        final int playerStartingCaveIndex = 9;
        final int wumpusStartingWrongCaveIndex = 9;
        final int wumpusStartingCorrectCaveIndex = 17;
        final int firstBatStartingCaveIndex = 19;
        final int secondBatStartingCaveIndex = 13;
        final int thirdBatStartingCaveIndex = 14;

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCaveIndex,
                wumpusStartingWrongCaveIndex,
                wumpusStartingCorrectCaveIndex,
                firstBatStartingCaveIndex,
                secondBatStartingCaveIndex,
                thirdBatStartingCaveIndex
                );

        NewGame game = new NewGame(randomNumberGenerator);
        game.startGame();

        final int actualWumpusCaveIndex = game.getWumpusCave();
        assertEquals(wumpusStartingCorrectCaveIndex,actualWumpusCaveIndex);

        Cave wumpusCave=game.getGameMap().getCaves().get(wumpusStartingCorrectCaveIndex);
        Wumpus wumpus=game.getWumpus();
        assertTrue(wumpusCave.getGameObjects().contains(wumpus));
    }

    @Test
    public void testThatBatsAreNotInitializedAtSameLocation(){
        final int playerStartingCaveIndex = 9;
        final int wumpusStartingCaveIndex = 15;

        final int firstBatStartingCaveIndex = 19;

        final int secondBatWrongStartingCaveIndex = 19;
        final int thirdBatWrongStartingCaveIndex = 19;

        final int secondBatCorrectStartingCaveIndex = 13;
        final int thirdBatCorrectStartingCaveIndex = 14;
        int[] batsStartingCavesIndexes = {firstBatStartingCaveIndex, secondBatCorrectStartingCaveIndex, thirdBatCorrectStartingCaveIndex};


        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCaveIndex,
                wumpusStartingCaveIndex,
                firstBatStartingCaveIndex,
                secondBatWrongStartingCaveIndex,
                secondBatCorrectStartingCaveIndex,
                thirdBatWrongStartingCaveIndex,
                thirdBatCorrectStartingCaveIndex
        );

        NewGame game = new NewGame(randomNumberGenerator);
        game.startGame();

        List<Bat> listOfBats = game.getBats();

        assertEquals(GameInitialConfigurations.NUMBER_OF_BATS,listOfBats.size());

        for(int i = 0; i < listOfBats.size(); i++) {
            assertEquals(batsStartingCavesIndexes[i], listOfBats.get(i).getCave().getNumber());
            Cave batCave= game.getGameMap().getCaves().get(batsStartingCavesIndexes[i]);
            Bat bat = listOfBats.get(i);
            assertTrue(batCave.getGameObjects().contains(bat));
        }
    }

    //TODO Write a test to check that hazards are not initialized near the player
    //TODO Implement same test cases as those in presenter

    
}
