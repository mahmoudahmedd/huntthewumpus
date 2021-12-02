package acceptance.steps;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.When;
import acceptance.utilities.ScenarioWorld;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveSteps {
    private ScenarioWorld world;

    public MoveSteps(ScenarioWorld world) {
        this.world = world;
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
}
