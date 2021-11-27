package acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.GameInitialConfigurations;
import model.NewGame;
import model.gameobjects.hazards.Bat;
import org.mockito.Mockito;
import utilities.RandomNumberGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class BuildGameMap {

    RandomNumberGenerator randomNumberGenerator;
    NewGame game;

    final int PLAYER_STARTING_CAVE_INDEX = 9;
    final int WUMPUS_STARTING_CAVE_INDEX = 18;
    final int FIRST_BAT_STARTING_CAVE_INDEX = 19;
    final int SECOND_BAT_STARTING_CAVE_INDEX = 13;
    final int THIRD_BAT_STARTING_CAVE_INDEX = 14;
    final int FIRST_PIT_CAVE = 3;
    final int SECOND_PIT_CAVE = 13;

    @Given("first bat in cave {int} and second bat in cave {int}")
    public void firstBatInCaveAndSecondBatInCave(Integer firstBatStartingCaveIndex, Integer secondBatStartingCaveIndex) {
        randomNumberGenerator=mock(RandomNumberGenerator.class);
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                WUMPUS_STARTING_CAVE_INDEX,
                firstBatStartingCaveIndex,
                SECOND_BAT_STARTING_CAVE_INDEX,
                THIRD_BAT_STARTING_CAVE_INDEX,
                FIRST_PIT_CAVE,
                SECOND_PIT_CAVE
        );



    }

    @When("game starts")
    public void gameStartsWithCaves() {
        game = new NewGame();
        game.startGame();
    }

    @Then("Then number of bats will be {int}")
    public void thenNumberOfBatsWillBe(int numberOfBats) {
        List<Bat> listOfBats = game.getBats();
        assertEquals(numberOfBats, listOfBats.size());

    }
}
