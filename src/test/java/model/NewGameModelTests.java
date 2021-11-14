package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class NewGameModelTests {

    @Test
    public void testGameMapInitializationProducedTheCorrectNumberOfCaves() {
        NewGame game = new NewGame();
        game.startGame();
        GameMap gameMap = game.getGameMap();

        assertEquals(gameMap.getCaves().size(), GameInitialConfigurations.NUMBER_OF_CAVES);
    }

    //TODO write a test to check that cave links are correct
    @Test
    public void testThatGameMapInitializationProducedTheCorrectCaveLinks() {
        NewGame game = new NewGame();
        game.startGame();
        GameMap gameMap = game.getGameMap();
        final List<Cave> mapCaves = gameMap.getCaves();

        final int firstCaveIndex = 0;
        final Cave firstCave = mapCaves.get(
                firstCaveIndex);

        final int connectedCavesCount = 3;
        final Set<Cave> actualLinkedCavesToFirstCave = firstCave.getLinkedCaves();
        assertEquals(actualLinkedCavesToFirstCave.size(), connectedCavesCount);

        final int[] expectedLinkedCavesToFirstCave = GameInitialConfigurations.CAVE_LINKS[firstCaveIndex];


    }

}
