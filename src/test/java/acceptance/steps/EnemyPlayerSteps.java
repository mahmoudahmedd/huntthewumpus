package acceptance.steps;

import utilities.ScenarioWorld;
import io.cucumber.java.en.Then;

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
}
