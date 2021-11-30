package acceptance.steps;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import acceptance.utilities.World;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveSteps {
    private World world;

    public MoveSteps(World world) {
        this.world = world;
    }

    @Given("player in cave {int}")
    public void player_is_in_cave(Integer playerStartingCave) {
        this.world.getRandomNumberGeneratorBuilder().setPlayerStartingCaveIndex(playerStartingCave);
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
            world.getWumpusPresenter().move(caveNumber);
        }
    }

    @Then("player will be at cave {int}")
    public void player_will_be_at_cave(Integer expectedPlayerCave) {
        final int playerCurrentRoom = world.getWumpusPresenter().getPlayerCave();
        assertEquals(expectedPlayerCave, playerCurrentRoom);
    }
}
