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
1- move to non connected cave.
2- room has Wumpus.
3- room has bat
4- kill wumpus
5- cave has pit
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

        wumpusPresenter.setCurrRoom(playerNextCave);
        wumpusPresenter.move();

        final int playCurrentRoom=wumpusPresenter.getCurrRoom();
        final boolean isGameOver=wumpusPresenter.isGameOver();

        assertEquals(playerNextCave,playCurrentRoom);
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

        wumpusPresenter.setCurrRoom(playerNextCave);
        wumpusPresenter.move();

        final int playCurrentRoom = wumpusPresenter.getCurrRoom();
        final boolean isGameOver = wumpusPresenter.isGameOver();

        assertEquals(playerStartingCave, playCurrentRoom);
        assertEquals(isGameOver, gameIsNotOver);
    }

}