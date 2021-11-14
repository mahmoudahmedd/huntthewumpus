package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class NewGameModelTests {
    
    @Test
    public void testGameMapInitializationProducedTheCorrectNumberOfCaves() {
        NewGame game = new NewGame();
        game.startGame();
        GameMap gameMap=game.getGameMap();

        assertEquals(gameMap.getCaves().size(),GameInitialConfigurations.NUMBER_OF_CAVES);
    }

    //TODO write a test to check that cave links are correct

}
