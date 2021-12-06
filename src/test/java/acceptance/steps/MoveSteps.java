package acceptance.steps;


import io.cucumber.java.en.When;
import utilities.ScenarioWorld;

import java.util.List;

public class MoveSteps {
    private ScenarioWorld world;

    public MoveSteps(ScenarioWorld world) {
        this.world = world;
    }

    @When("player moves on the {listOfIntegers}")
    public void playerMovesOnThe(List<Integer> journeyPath) {
        int linkedCaveIndex = 0;
        world.getRandomNumberGeneratorBuilder().addNumberOfMovesToTheEnemyPlayer(journeyPath.size(), linkedCaveIndex);

        for (int caveNumber : journeyPath) {
            world.getWumpusPresenter().move(caveNumber);
        }
    }
}
