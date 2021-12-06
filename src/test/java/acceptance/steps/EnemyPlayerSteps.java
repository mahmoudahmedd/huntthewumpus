package acceptance.steps;

import io.cucumber.java.en.When;
import utilities.ScenarioWorld;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyPlayerSteps {
    private ScenarioWorld world;

    public EnemyPlayerSteps(ScenarioWorld world) {
        this.world = world;
    }

    @Then("^enemy player is dead$")
    public void enemyPlayerIsPlayerState() {
        boolean isEnemyPlayerIsDead = true;
        boolean actualIsEnemyPlayerDead = world.getWumpusPresenter().isEnemyPlayerDead();
        assertEquals(isEnemyPlayerIsDead, actualIsEnemyPlayerDead);
    }

    @Then("^enemy player is alive")
    public void enemyPlayerIsEnemyPlayerState() {
        boolean isEnemyPlayerIsDead = false;
        boolean actualIsEnemyPlayerDead = world.getWumpusPresenter().isEnemyPlayerDead();
        assertEquals(isEnemyPlayerIsDead, actualIsEnemyPlayerDead);
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
