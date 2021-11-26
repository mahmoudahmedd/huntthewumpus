package acceptance;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.GameInitialConfigurations;
import model.LegacyHazard;
import org.junit.Assert;
import org.mockito.Mockito;
import presenter.WumpusPresenter;
import presenter.WumpusPresenterImpl;
import utilities.RandomNumberGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class MovePlayerToCave {

    RandomNumberGenerator randomNumberGenerator;
    WumpusPresenter wumpusPresenter;

    final int wumpusStartingCave = 18;
    final int firstBatStartingCave = 19;
    final int secondBatStartingCave = 13;
    final int thirdBatStartingCave = 14;
    final int firstPitCave = 3;
    final int secondPitCave = 13;

    @Given("player is in cave {int}")
    public void player_is_in_cave(Integer playerStartingCave) {
        randomNumberGenerator=mock(RandomNumberGenerator.class);
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                playerStartingCave,
                wumpusStartingCave,
                firstBatStartingCave,
                secondBatStartingCave,
                thirdBatStartingCave,
                firstPitCave,
                secondPitCave);

        wumpusPresenter = new WumpusPresenterImpl(randomNumberGenerator);
        wumpusPresenter.startNewGame();
    }

    @ParameterType("\\[([0-9, ]*)\\]")
    public List<Integer> listOfIntegers(String integers) {
        return Arrays.stream(integers.split(", ?"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @When("player moves on the {listOfIntegers}")
    public void playerMovesOnThe(List<Integer> journeyPath) {
        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }
    }

    @Then("player will be at cave {int}")
    public void player_will_be_at_cave(Integer expectedPlayerCave) {
        final int playerCurrentRoom = wumpusPresenter.getPlayerCave();
        assertEquals(expectedPlayerCave, playerCurrentRoom);
    }

    @Then("player is {string}")
    public void playerIs(String playerState) {
        boolean expectedStatusOfGameIsOver;

        if (playerState.equals("dead")) {
            expectedStatusOfGameIsOver = true;
        } else {
            expectedStatusOfGameIsOver = false;
        }

        final boolean isGameOver = wumpusPresenter.isGameOver();
        assertEquals(isGameOver, expectedStatusOfGameIsOver);
    }

    @Then("player sense the warning {string}")
    public void playerSenseTheWarningWarning(String expectedWarning) {
        List<String> messages = wumpusPresenter.getMessages();
        assertTrue(messages.contains(expectedWarning));
    }
}
