package presenter;

import model.GameInitialConfigurations;
import model.Hazard;
import utilities.RandomNumberGenerator;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WumpusPresenterTest {

    @Mock
    RandomNumberGenerator randomNumberGenerator;

    final int playerStartingCave = 0;
    final int wumpusStartingCave = 18;
    final int firstBatStartingCave = 19;
    final int secondBatStartingCave = 13;
    final int thirdBatStartingCave = 14;
    final int firstPitCave = 3;
    final int secondPitCave = 13;

    @Test
    public void testMovingPlayerToCave() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter=new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int playerNextCave = 7;
        wumpusPresenter.move(playerNextCave);

        final int playerCurrentRoom=wumpusPresenter.getPlayerCave();
        assertEquals(playerNextCave,playerCurrentRoom);

        final boolean expectedStatusOfGameIsOver = false;
        final boolean isGameOver=wumpusPresenter.isGameOver();
        assertEquals(isGameOver,expectedStatusOfGameIsOver);

    }

    @Test
    public void testMoveToNonConnectedCave() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int playerNextCave = 16;
        wumpusPresenter.move(playerNextCave);

        final int playerCurrentRoom = wumpusPresenter.getPlayerCave();
        assertEquals(playerStartingCave, playerCurrentRoom);

        final boolean gameIsNotOver = false;
        final boolean isGameOver = wumpusPresenter.isGameOver();
        assertEquals(isGameOver, gameIsNotOver);
    }

    @Test
    public void testMovingPlayerToCaveThatHasAWumups() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int[] journeyPath = {1, 9, 10, 18};
        for(int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testMovingPlayerToACaveNearAWumpusAndSensingTheWumpus(){
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int[] journeyPath = {1, 9, 10};
        for(int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        List<String> messages = wumpusPresenter.getMessages();
        assertTrue(messages.contains(Hazard.Wumpus.getWarning()));

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = false;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerEnterRoomWithBat() {
        final int playerStartingCave = 11;
        final int playerDropDownCave = 8;
        final int firstBatFinalCave = 2;

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave,
                playerDropDownCave,
                firstBatFinalCave);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int[] journeyPath = {12, 19};
        for(int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final int playerCurrentCave = wumpusPresenter.getPlayerCave();
        assertEquals(playerDropDownCave, playerCurrentCave);

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = false;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerEnterRoomWithPit() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int[] journeyPath = {4, 3};
        for(int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testKillingTheWumpus() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int[] journeyPath = {1, 9, 10};
        for(int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final int shootToCave = 18;
        wumpusPresenter.shoot(shootToCave);

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerEnterRoomWithPitAndBat() {
        final int playerStartingCave = 11;
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int[] journeyPath = {12, 13};
        for(int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }


        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerShootsAnArrowThatMissesTheWumpusAndWumpusRemainsSleeping() {




        final int maximumNumberForCalculatingWumpusWakeupProbability = 4;
        final int numberAtWhichWumpusWillRemainSleeping = 0;

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);


        Mockito.when(randomNumberGenerator.generateNumber(maximumNumberForCalculatingWumpusWakeupProbability)).thenReturn(
                numberAtWhichWumpusWillRemainSleeping);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int[] journeyPath = {1, 9, 10};
        for(int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final int shootToCave = 11;
        wumpusPresenter.shoot(shootToCave);

        final int wumpusCaveLocation = wumpusPresenter.getWumpusCave();
        assertEquals(wumpusCaveLocation, wumpusStartingCave);

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = false;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerShootsAnArrowThatMissesTheWumpusAndWumpusMoves() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        final int maximumNumberForCalculatingWumpusWakeupProbability = 4;
        final int numberAtWhichWumpusWillRemainSleeping = 1;
        Mockito.when(randomNumberGenerator.generateNumber(maximumNumberForCalculatingWumpusWakeupProbability)).thenReturn(
                numberAtWhichWumpusWillRemainSleeping);

        final int numberOfLinkedCaves = 3;
        final int wumpusLinkedCaveIndex = 2;
        Mockito.when(randomNumberGenerator.generateNumber(numberOfLinkedCaves)).thenReturn(
                wumpusLinkedCaveIndex);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        final int[] journeyPath = {1, 9, 10};
        for(int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final int shootToCave = 11;
        wumpusPresenter.shoot(shootToCave);

        final int wumpusCaveLocation = wumpusPresenter.getWumpusCave();
        final int wumpusCaveCurrLocation = 17;
        assertEquals(wumpusCaveCurrLocation, wumpusCaveLocation);

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = false;
        assertEquals(actualGameState, gameIsOver);
    }

}