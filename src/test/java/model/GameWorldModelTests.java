package model;

import model.gameobjects.hazards.Bat;
import model.gameobjects.hazards.Pit;
import model.gameobjects.Player;
import model.gameobjects.hazards.Wumpus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utilities.GameInitialConfigurations;
import utilities.RandomNumberGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class GameWorldModelTests {

    public static final int PLAYER_STARTING_CAVE_INDEX = 9;
    public static final int ENEMY_PLAYER_STARTING_CAVE_INDEX = 6;
    public static final int WUMPUS_STARTING_CAVE_INDEX = 18;
    public static final int FIRST_BAT_STARTING_CAVE_INDEX = 19;
    public static final int SECOND_BAT_STARTING_CAVE_INDEX = 13;
    public static final int FIRST_PIT_CAVE = 3;
    public static final int SECOND_PIT_CAVE = 13;
    @Mock
    RandomNumberGenerator randomNumberGenerator;

    private void configureMockingBasedOnDefaultLocationOfGameObjectsOnMap() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                ENEMY_PLAYER_STARTING_CAVE_INDEX,
                WUMPUS_STARTING_CAVE_INDEX,
                FIRST_BAT_STARTING_CAVE_INDEX,
                SECOND_BAT_STARTING_CAVE_INDEX,
                FIRST_PIT_CAVE,
                SECOND_PIT_CAVE
        );
    }

    @Test
    public void testGameMapInitializationProducedTheCorrectNumberOfCaves() {
        GameWorld game = new GameWorld();
        game.startGame();
        GameMap gameMap = game.getGameMap();

        assertEquals(GameInitialConfigurations.NUMBER_OF_CAVES,gameMap.getCaves().size());
    }

    @Test
    public void testThatGameMapInitializationProducedTheCorrectCaveLinks() {
        GameWorld game = new GameWorld();
        game.startGame();
        GameMap gameMap = game.getGameMap();
        final List<Cave> mapCaves = gameMap.getCaves();

        final int[] caveLinkIndexesToTest= new int[]{0,9,3,4};

        for(int caveLinkIndexToTest:caveLinkIndexesToTest){
            final Cave firstCave = mapCaves.get(
                    caveLinkIndexToTest);

            final int connectedCavesCount = 3;
            final List<Cave> actualLinkedCavesToFirstCave = firstCave.getLinkedCaves();
            assertEquals(connectedCavesCount,actualLinkedCavesToFirstCave.size());

            final int[] expectedLinkedCavesToFirstCave = GameInitialConfigurations.CAVE_LINKS[caveLinkIndexToTest];

            for(int caveLink:expectedLinkedCavesToFirstCave){
                assertTrue(actualLinkedCavesToFirstCave.contains(new Cave(caveLink)));
            }
        }
    }

    @Test
    public void testThatPlayerIsAddedToInitialCave() {
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertEquals(PLAYER_STARTING_CAVE_INDEX, actualPlayerCaveIndex);
    }

    @Test
    public void testThatPlayerCaveIsAddedToGameMap() {
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertEquals(PLAYER_STARTING_CAVE_INDEX, actualPlayerCaveIndex);

        Cave playerCave=game.getGameMap().getCaves().get(PLAYER_STARTING_CAVE_INDEX);
        Player player=game.getPlayer();
        assertTrue(playerCave.getGameObjects().contains(player));
    }

    @Test
    public void testThatWumpusIsAddedToCaveGameMap(){
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int actualWumpusCaveIndex = game.getWumpusCave();
        assertEquals(WUMPUS_STARTING_CAVE_INDEX,actualWumpusCaveIndex);

        Cave wumpusCave=game.getGameMap().getCaves().get(WUMPUS_STARTING_CAVE_INDEX);
        Wumpus wumpus=game.getWumpus();
        assertTrue(wumpusCave.getGameObjects().contains(wumpus));

    }

    @Test
    public void testThatThreeBatsAreAddedToCaveGameMap() {
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        int[] batsStartingCavesIndexes = {FIRST_BAT_STARTING_CAVE_INDEX, SECOND_BAT_STARTING_CAVE_INDEX};

        GameWorld game = new GameWorld(randomNumberGenerator);
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
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        int[] pitsInCavesIndexes = {FIRST_PIT_CAVE, SECOND_PIT_CAVE};

        GameWorld game = new GameWorld(randomNumberGenerator);
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
        final int wumpusStartingWrongCaveIndex = 9;
        final int wumpusStartingCorrectCaveIndex = 17;

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                ENEMY_PLAYER_STARTING_CAVE_INDEX,
                wumpusStartingWrongCaveIndex,
                wumpusStartingCorrectCaveIndex,
                FIRST_BAT_STARTING_CAVE_INDEX,
                SECOND_BAT_STARTING_CAVE_INDEX,
                FIRST_PIT_CAVE,
                SECOND_PIT_CAVE
                );

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int actualWumpusCaveIndex = game.getWumpusCave();
        assertEquals(wumpusStartingCorrectCaveIndex,actualWumpusCaveIndex);

        Cave wumpusCave=game.getGameMap().getCaves().get(wumpusStartingCorrectCaveIndex);
        Wumpus wumpus=game.getWumpus();
        assertTrue(wumpusCave.getGameObjects().contains(wumpus));
    }

    @Test
    public void testThatBatsAreNotInitializedAtSameLocation(){
        final int secondBatWrongStartingCaveIndex = 19;
        final int secondBatCorrectStartingCaveIndex = 13;

        int[] batsStartingCavesIndexes = {FIRST_BAT_STARTING_CAVE_INDEX, secondBatCorrectStartingCaveIndex};

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                ENEMY_PLAYER_STARTING_CAVE_INDEX,
                WUMPUS_STARTING_CAVE_INDEX,
                FIRST_BAT_STARTING_CAVE_INDEX,
                secondBatWrongStartingCaveIndex,
                secondBatCorrectStartingCaveIndex,
                FIRST_PIT_CAVE,
                SECOND_PIT_CAVE
        );

        GameWorld game = new GameWorld(randomNumberGenerator);
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
    public void testThatPitsAreNotInitializedAtSameLocation(){
        final int secondWrongPitCave = 3;
        final int secondCorrectPitCave = 13;
        int[] correctPitsInCavesIndexes = {FIRST_PIT_CAVE, secondCorrectPitCave};

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                ENEMY_PLAYER_STARTING_CAVE_INDEX,
                WUMPUS_STARTING_CAVE_INDEX,
                FIRST_BAT_STARTING_CAVE_INDEX,
                SECOND_BAT_STARTING_CAVE_INDEX,
                FIRST_PIT_CAVE,
                secondWrongPitCave,
                secondCorrectPitCave
        );

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        List<Pit> listOfPits = game.getPits();

        assertEquals(GameInitialConfigurations.NUMBER_OF_PITS,listOfPits.size());

        for(int i = 0; i < listOfPits.size(); i++) {
            assertEquals(correctPitsInCavesIndexes[i], listOfPits.get(i).getCave().getNumber());
            Cave PitInCave= game.getGameMap().getCaves().get(correctPitsInCavesIndexes[i]);
            Pit pit = listOfPits.get(i);
            assertTrue(PitInCave.getGameObjects().contains(pit));
        }

    }

    @Test
    public void testThatHazardsAreNotInitializedInTheCaveLinkedToThePlayer() {
        final int wumpusStartingWrongCaveIndex = 1;
        final int wumpusStartingCorrectCaveIndex = 17;

        final int firstBatStartingWrongCaveIndex = 10;
        final int firstBatStartingCorrectCaveIndex = FIRST_BAT_STARTING_CAVE_INDEX;

        final int firstPitStartingWrongCaveIndex = 8;
        final int firstPitStartingCorrectCaveIndex = FIRST_PIT_CAVE;

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                ENEMY_PLAYER_STARTING_CAVE_INDEX,
                wumpusStartingWrongCaveIndex,
                wumpusStartingCorrectCaveIndex,
                firstBatStartingWrongCaveIndex,
                firstBatStartingCorrectCaveIndex,
                SECOND_BAT_STARTING_CAVE_INDEX,
                firstPitStartingWrongCaveIndex,
                firstPitStartingCorrectCaveIndex,
                SECOND_PIT_CAVE
        );

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int actualWumpusCaveIndex = game.getWumpusCave();
        assertEquals(wumpusStartingCorrectCaveIndex,actualWumpusCaveIndex);

        Cave wumpusCave =game.getGameMap().getCaves().get(wumpusStartingCorrectCaveIndex);
        Wumpus wumpus= game.getWumpus();
        assertTrue(wumpusCave.getGameObjects().contains(wumpus));

        final int actualFirstBatCaveIndex = game.getBats().get(0).getCave().getNumber();
        assertEquals(firstBatStartingCorrectCaveIndex,actualFirstBatCaveIndex);

        Cave firstBatCave = game.getGameMap().getCaves().get(firstBatStartingCorrectCaveIndex);
        Bat bat = game.getBats().get(0);
        assertTrue(firstBatCave.getGameObjects().contains(bat));

        final int actualFirstPitCaveIndex = game.getPits().get(0).getCave().getNumber();
        assertEquals(firstPitStartingCorrectCaveIndex,actualFirstPitCaveIndex);

        Cave firstPitCave = game.getGameMap().getCaves().get(firstPitStartingCorrectCaveIndex);
        Pit pit = game.getPits().get(0);
        assertTrue(firstPitCave.getGameObjects().contains(pit));
    }

    @Test
    public void testThatThePlayerCanMoveToALinkedCave(){
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int caveIndexToMoveTo = 1;
        game.playerMovesToCave(caveIndexToMoveTo);

        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertEquals(caveIndexToMoveTo, actualPlayerCaveIndex);

        Cave currentPlayerCave = game.getGameMap().getCaves().get(caveIndexToMoveTo);
        Player player = game.getPlayer();
        assertTrue(currentPlayerCave.getGameObjects().contains(player));

        Cave pastPlayerCave = game.getGameMap().getCaves().get(PLAYER_STARTING_CAVE_INDEX);
        assertFalse(pastPlayerCave.getGameObjects().contains(player));
    }

    @Test
    public void testMoveToNonConnectedCave() {
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int caveIndexToMoveTo = 17;
        game.playerMovesToCave(caveIndexToMoveTo);

        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertNotEquals(caveIndexToMoveTo, actualPlayerCaveIndex);
        assertEquals(PLAYER_STARTING_CAVE_INDEX, actualPlayerCaveIndex);

        Cave currentPlayerCave = game.getGameMap().getCaves().get(caveIndexToMoveTo);
        Player player = game.getPlayer();
        assertFalse(currentPlayerCave.getGameObjects().contains(player));

        Cave pastPlayerCave = game.getGameMap().getCaves().get(PLAYER_STARTING_CAVE_INDEX);
        assertTrue(pastPlayerCave.getGameObjects().contains(player));
    }

    @Test
    public void testMovingPlayerToCaveThatHasAWumups(){
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        int[] journeyPath = new int[] {10, 18};

        for(int cave:journeyPath){
            game.playerMovesToCave(cave);
        }


        final boolean actualGameState = game.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(gameIsOver, actualGameState);

        List<String> messages = game.getMessages();
        assertTrue(messages.contains(game.getWumpus().getWarningInTheSameCave()));
    }

    @Test
    public void testMovingPlayerToACaveNearAWumpusAndSensingTheWumpus() {
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        int[] journeyPath = new int[] {10};

        for(int cave:journeyPath){
            game.playerMovesToCave(cave);
        }


        final boolean actualGameState = game.isGameOver();
        final boolean gameIsNotOver = false;
        assertEquals(gameIsNotOver, actualGameState);

        List<String> messages = game.getMessages();
        assertTrue(messages.contains(game.getWumpus().getWarningInTheLinkedCave()));
    }

    @Test
    public void testThatPlayerEnterRoomWithPit() {
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int[] journeyPath = {10, 11, 2, 3};
        for (int caveNumber : journeyPath) {
            game.playerMovesToCave(caveNumber);
        }

        final boolean actualGameState = game.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);

        List<String> messages = game.getMessages();
        assertTrue(messages.contains(game.getPits().get(0).getWarningInTheSameCave()));
    }

    @Test
    public void testKillingTheWumpus(){
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        game.playerMovesToCave(10);

        final int caveToShootTo = WUMPUS_STARTING_CAVE_INDEX;
        game.playerShootsToCave(caveToShootTo);

        final boolean actualGameState = game.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerShootsAnArrowThatMissesTheWumpusAndWumpusRemainsSleeping() {
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        final int maximumNumberForCalculatingWumpusWakeupProbability = 4;
        final int numberAtWhichWumpusWillRemainSleeping = 0;
        Mockito.when(randomNumberGenerator.generateNumber(maximumNumberForCalculatingWumpusWakeupProbability)).thenReturn(
                numberAtWhichWumpusWillRemainSleeping);

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();
        game.playerMovesToCave(10);

        final int caveToShootTo = 11;
        game.playerShootsToCave(caveToShootTo);

        final int wumpusCaveLocation = game.getWumpusCave();
        assertEquals(wumpusCaveLocation, WUMPUS_STARTING_CAVE_INDEX);

        final boolean actualGameState = game.isGameOver();
        final boolean gameIsNotOver = false;
        assertEquals(actualGameState, gameIsNotOver);
    }

    @Test
    public void testThatPlayerShootsAnArrowThatMissesTheWumpusAndWumpusMoves() {
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        final int maximumNumberForCalculatingWumpusWakeupProbability = 4;
        final int numberAtWhichWumpusWillWakeUp = 1;
        Mockito.when(randomNumberGenerator.generateNumber(maximumNumberForCalculatingWumpusWakeupProbability)).thenReturn(
                numberAtWhichWumpusWillWakeUp);

        final int numberOfLinkedCaves = 3;
        final int wumpusLinkedCaveIndex = 2;
        Mockito.when(randomNumberGenerator.generateNumber(numberOfLinkedCaves)).thenReturn(
                wumpusLinkedCaveIndex);

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int caveToShootTo = 1;
        game.playerShootsToCave(caveToShootTo);

        final int actualWumpusCaveIndex = game.getWumpusCave();
        final int expectedWumpusCaveIndex = 17;
        assertEquals(expectedWumpusCaveIndex, actualWumpusCaveIndex);

        final Cave initialWumpusCave = game.getGameMap().getCaves().get(WUMPUS_STARTING_CAVE_INDEX);
        assertFalse(initialWumpusCave.getGameObjects().contains(game.getWumpus()));

        final boolean actualGameState = game.isGameOver();
        final boolean gameIsNotOver = false;
        assertEquals(actualGameState, gameIsNotOver);
    }

    @Test
    public void testThatPlayerRunsOutOfArrowsWithoutKillingWumpus() {
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        final int maximumNumberForCalculatingWumpusWakeupProbability = 4;
        final int numberAtWhichWumpusWillWakeUp = 0;
        Mockito.when(randomNumberGenerator.generateNumber(maximumNumberForCalculatingWumpusWakeupProbability)).thenReturn(
                numberAtWhichWumpusWillWakeUp);

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int caveToShoot = 1;
        for (int i = 0; i < GameInitialConfigurations.NUMBER_OF_ARROWS; i++) {
            game.playerShootsToCave(caveToShoot);
        }

        final boolean actualGameState = game.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerShootsAnArrowMissesWumpusAndWumpusWakesUpAndMoveToEatThePlayer(){
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        final int maximumNumberForCalculatingWumpusWakeupProbability = 4;
        final int numberAtWhichWumpusWillWakeUp = 1;
        Mockito.when(randomNumberGenerator.generateNumber(maximumNumberForCalculatingWumpusWakeupProbability)).thenReturn(
                numberAtWhichWumpusWillWakeUp);

        final int numberOfLinkedCaves = 3;
        final int wumpusLinkedCaveIndex = 1;
        Mockito.when(randomNumberGenerator.generateNumber(numberOfLinkedCaves)).thenReturn(
                wumpusLinkedCaveIndex);

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        game.playerMovesToCave(10);


        final int caveToShootTo = 11;
        game.playerShootsToCave(caveToShootTo);

        final int actualWumpusCaveIndex = game.getWumpusCave();
        final int expectedWumpusCaveIndex = 10;
        assertEquals(expectedWumpusCaveIndex, actualWumpusCaveIndex);

        final Cave initialWumpusCave = game.getGameMap().getCaves().get(WUMPUS_STARTING_CAVE_INDEX);
        assertFalse(initialWumpusCave.getGameObjects().contains(game.getWumpus()));

        final boolean actualGameState = game.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerEnterRoomWithBatTwice() {
        final int playerFirstDropDownCaveIndex = 14;
        final int firstBatFinalCaveIndex = 2;

        final int playerSecondDropDownCaveIndex = 6;
        final int secondBatFinalCaveIndex = 0;

        final int SECOND_PIT_CAVE = 2;
        
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                ENEMY_PLAYER_STARTING_CAVE_INDEX,
                WUMPUS_STARTING_CAVE_INDEX,
                FIRST_BAT_STARTING_CAVE_INDEX,
                SECOND_BAT_STARTING_CAVE_INDEX,
                FIRST_PIT_CAVE,
                SECOND_PIT_CAVE,
                playerFirstDropDownCaveIndex,
                firstBatFinalCaveIndex,
                playerSecondDropDownCaveIndex,
                secondBatFinalCaveIndex
        );


        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int[] journeyPath = {10, 11, 12, FIRST_BAT_STARTING_CAVE_INDEX};
        for(int cave: journeyPath){
            game.playerMovesToCave(cave);
        }

        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertEquals(playerFirstDropDownCaveIndex, actualPlayerCaveIndex);


        final Bat firstBat = game.getBats().get(0);
        final Cave firstBatActualCave = firstBat.getCave();
        assertEquals(firstBatFinalCaveIndex, firstBatActualCave.getNumber());

        final Cave previousCave = game.getGameMap().getCaves().get(FIRST_BAT_STARTING_CAVE_INDEX);
        assertFalse(previousCave.getGameObjects().contains(game.getPlayer()));
        assertFalse(previousCave.getGameObjects().contains(firstBat));

        game.playerMovesToCave(SECOND_BAT_STARTING_CAVE_INDEX);


        final int actualPlayerSecondCaveIndex = game.getPlayerCave();
        assertEquals(playerSecondDropDownCaveIndex, actualPlayerSecondCaveIndex);


        final Bat secondBat = game.getBats().get(1);
        final Cave secondBatActualCave = secondBat.getCave();
        assertEquals(secondBatFinalCaveIndex, secondBatActualCave.getNumber());

        final Cave secondBatPreviousCave = game.getGameMap().getCaves().get(SECOND_BAT_STARTING_CAVE_INDEX);
        assertFalse(secondBatPreviousCave.getGameObjects().contains(game.getPlayer()));
        assertFalse(secondBatPreviousCave.getGameObjects().contains(secondBat));
    }

    @Test
    public void testThatPlayerEnterRoomWithBat() {
        final int playerDropDownCaveIndex = 8;
        final int firstBatFinalCaveIndex = 2;

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                ENEMY_PLAYER_STARTING_CAVE_INDEX,
                WUMPUS_STARTING_CAVE_INDEX,
                FIRST_BAT_STARTING_CAVE_INDEX,
                SECOND_BAT_STARTING_CAVE_INDEX,
                FIRST_PIT_CAVE,
                SECOND_PIT_CAVE,
                playerDropDownCaveIndex,
                firstBatFinalCaveIndex
        );


        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int[] journeyPath = {10, 11, 12, FIRST_BAT_STARTING_CAVE_INDEX};
        for(int cave: journeyPath){
            game.playerMovesToCave(cave);
        }

        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertEquals(playerDropDownCaveIndex, actualPlayerCaveIndex);


        final Bat firstBat = game.getBats().get(0);
        final Cave firstBatActualCave = firstBat.getCave();
        assertEquals(firstBatFinalCaveIndex, firstBatActualCave.getNumber());

        final Cave previousCave = game.getGameMap().getCaves().get(FIRST_BAT_STARTING_CAVE_INDEX);
        assertFalse(previousCave.getGameObjects().contains(game.getPlayer()));
        assertFalse(previousCave.getGameObjects().contains(firstBat));
    }

    @Test
    public void testThatPlayerEnterRoomWithPitAndBat(){
        configureMockingBasedOnDefaultLocationOfGameObjectsOnMap();

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        final int[] journeyPath = {10, 11, 12, 13};
        for (int caveNumber : journeyPath) {
            game.playerMovesToCave(caveNumber);
        }

        final boolean actualGameState = game.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatHazardsInASingleCaveAreSortedByPrecedence(){
        final int WUMPUS_STARTING_CAVE_INDEX = 18;
        final int FIRST_BAT_STARTING_CAVE_INDEX = 18;
        final int FIRST_PIT_CAVE = 18;

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                ENEMY_PLAYER_STARTING_CAVE_INDEX,
                WUMPUS_STARTING_CAVE_INDEX,
                FIRST_BAT_STARTING_CAVE_INDEX,
                SECOND_BAT_STARTING_CAVE_INDEX,
                FIRST_PIT_CAVE,
                SECOND_PIT_CAVE
        );

        GameWorld game = new GameWorld(randomNumberGenerator);
        game.startGame();

        Cave cave=game.getGameMap().getCaves().get(WUMPUS_STARTING_CAVE_INDEX);

        assertTrue(cave.getGameObjects().get(0) instanceof Pit);
        assertTrue(cave.getGameObjects().get(1) instanceof Wumpus);
        assertTrue(cave.getGameObjects().get(2) instanceof Bat);

    }

    //TODO Implement same test cases as those in presenter
    /*
    TODO
    1- Add assert on gameOverMessages for testThatPlayerEnterRoomWithPit & testMovingPlayerToCaveThatHasAWumups
    2- Ask Ahmed to suggest any idea about a good design for coupling random number with player
    3- Add test for Pits & Bats warning
    4- Re-arrange tests into logical game play order
    5- Write a test for  shooting non-linked cave
     */
}
