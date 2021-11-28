package acceptance;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Cave;
import model.GameInitialConfigurations;
import model.NewGame;
import model.gameobjects.hazards.Bat;
import model.gameobjects.hazards.Pit;
import org.mockito.Mockito;
import utilities.RandomNumberGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BuildGameMap {

    RandomNumberGenerator randomNumberGenerator;
    NewGame game;

    final int PLAYER_STARTING_CAVE_INDEX = 9;
    final int WUMPUS_STARTING_CAVE_INDEX = 18;
    final int FIRST_BAT_STARTING_CAVE_INDEX = 19;
    final int SECOND_BAT_STARTING_CAVE_INDEX = 13;
    final int FIRST_PIT_CAVE = 3;
    final int SECOND_PIT_CAVE = 13;


    @Given("location of game objects on map is initialized")
    public void locationOfGameObjectsOnMapIsInitialized() {
        randomNumberGenerator = mock(RandomNumberGenerator.class);
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                WUMPUS_STARTING_CAVE_INDEX,
                FIRST_BAT_STARTING_CAVE_INDEX,
                SECOND_BAT_STARTING_CAVE_INDEX,
                FIRST_PIT_CAVE,
                SECOND_PIT_CAVE
        );
    }

    @When("game starts")
    public void gameStartsWithCaves() {
        game = new NewGame(randomNumberGenerator);
        game.startGame();
    }

    @Then("number of bats will be {int}")
    public void numberOfBatsWillBe(int numberOfBats) {
        List<Bat> listOfBats = game.getBats();
        assertEquals(numberOfBats, listOfBats.size());
    }

    @Then("first bat will be at cave {int} and second bat will be at cave {int}")
    public void firstBatWillBeAtCaveAndSecondBatWillBeAtCave(Integer expectedFirstBatCave, Integer expectedSecondBatCave) {
        int[] batsStartingCavesIndexes = {expectedFirstBatCave, expectedSecondBatCave};
        List<Bat> listOfBats = game.getBats();
        for(int i = 0; i < listOfBats.size(); i++) {
            assertEquals(batsStartingCavesIndexes[i], listOfBats.get(i).getCave().getNumber());
            Cave batCave= game.getGameMap().getCaves().get(batsStartingCavesIndexes[i]);
            Bat bat = listOfBats.get(i);
            assertTrue(batCave.getGameObjects().contains(bat));
        }
    }
}
