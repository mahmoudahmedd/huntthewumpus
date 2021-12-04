package acceptance.steps;

import acceptance.utilities.ScenarioWorld;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyPlayerSteps {
    private ScenarioWorld world;

    public EnemyPlayerSteps(ScenarioWorld world) {
        this.world = world;
    }

    @Then("^enemy player is dead$")
    public void enemyPlayerIsPlayerState() {
        boolean isEnemyPlayerIsPlayerState = true;
        boolean actualIsEnemyPlayerDead = world.getWumpusPresenter().isEnemyPlayerDead();
        assertEquals(isEnemyPlayerIsPlayerState, actualIsEnemyPlayerDead);
    }
}
