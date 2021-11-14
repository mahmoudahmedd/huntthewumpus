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


@ExtendWith(MockitoExtension.class)
public class NewGameModelTests {

    @Mock
    RandomNumberGenerator randomNumberGenerator;


    @Test
    public void testGameMapInitializationProducedTheCorrectNumberOfCaves() {
        NewGame game = new NewGame();
        game.startGame();
        GameMap gameMap = game.getGameMap();

        assertEquals(gameMap.getCaves().size(), GameInitialConfigurations.NUMBER_OF_CAVES);
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
            assertEquals(actualLinkedCavesToFirstCave.size(), connectedCavesCount);

            final int[] expectedLinkedCavesToFirstCave = GameInitialConfigurations.CAVE_LINKS[caveLinkIndexToTest];

            for(int caveLink:expectedLinkedCavesToFirstCave){
                assertTrue(actualLinkedCavesToFirstCave.contains(new Cave(caveLink+1)));
            }
        }
    }

    @Test
    public void testThatPlayerIsAddedToInitialCave() {
        final int playerStartingCaveIndex = 9;
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCaveIndex);

        NewGame game = new NewGame(randomNumberGenerator);
        game.startGame();

        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertEquals(playerStartingCaveIndex, actualPlayerCaveIndex);
    }
}
