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
        for (int caveNumber : journeyPath) {
            world.getWumpusPresenter().move(caveNumber);
        }
    }

    @When("enemy player moves on the {listOfIntegers}")
    public void enemyPlayerMovesOnTheJourneyPath(List<Integer> journeyPathIndices) {
        for (int cavesToMoveIndex : journeyPathIndices) {
            this.world.getRandomNumberGeneratorBuilder().addLinkedCaveIndex(cavesToMoveIndex);
        }

        for(int numberOfCavesToMove = 0; numberOfCavesToMove < journeyPathIndices.size(); numberOfCavesToMove++) {
            world.getWumpusPresenter().moveEnemyPlayer();
        }
    }
}
