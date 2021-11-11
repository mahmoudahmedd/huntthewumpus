package presenter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import utilities.RandomNumberGenerator;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/*
TODO Test list
1- Player can sense nearby wumpus
2- room has bat
3- kill wumpus
4- cave has pit
*/

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
    final int numberOfCaves = 20;

    @Test
    public void testMovingPlayerToCave() {

        final int playerNextCave = 7;
        final boolean gameIsNotOver = false;

        Mockito.when(randomNumberGenerator.generateNumber(numberOfCaves)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter=new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        wumpusPresenter.move(playerNextCave);

        final int playerCurrentRoom=wumpusPresenter.getCurrentCave();
        final boolean isGameOver=wumpusPresenter.isGameOver();

        assertEquals(playerNextCave,playerCurrentRoom);
        assertEquals(isGameOver,gameIsNotOver);

    }

    @Test
    public void testMoveToNonConnectedCave() {
        final int playerNextCave = 16;
        final boolean gameIsNotOver = false;

        Mockito.when(randomNumberGenerator.generateNumber(numberOfCaves)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();

        wumpusPresenter.move(playerNextCave);

        final int playerCurrentRoom = wumpusPresenter.getCurrentCave();
        final boolean isGameOver = wumpusPresenter.isGameOver();

        assertEquals(playerStartingCave, playerCurrentRoom);
        assertEquals(isGameOver, gameIsNotOver);
    }

    @Test
    public void testMovingPlayerToCaveThatHasAWumups() {
        final boolean gameIsOver = true;
        final int[] journeyPath = {1, 9, 10, 18};

        Mockito.when(randomNumberGenerator.generateNumber(numberOfCaves)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();
        
        for(int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final boolean actualGameState = wumpusPresenter.isGameOver();
        assertEquals(actualGameState, gameIsOver);
    }

}