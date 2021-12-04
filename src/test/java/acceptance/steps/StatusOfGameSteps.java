package acceptance.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import acceptance.utilities.ScenarioWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusOfGameSteps {
    private ScenarioWorld world;

    public StatusOfGameSteps(ScenarioWorld world) {
        this.world = world;
    }

    @Then("^player is dead$|^player is loser$|^player is winner$|^game state is game over")
    public void assertOnExpectedStatusOfGameIsOver() {
        boolean expectedStatusOfGameIsOver = true;
        final boolean isGameOver = world.getWumpusPresenter().isGameOver();
        assertEquals(isGameOver, expectedStatusOfGameIsOver);
    }

    @Then("^player is alive$|^game state is game not over$")
    public void assertOnExpectedStatusOfGameIsNotOver() {
        boolean expectedStatusOfGameIsNotOver = false;
        final boolean isGameOver = world.getWumpusPresenter().isGameOver();
        assertEquals(isGameOver, expectedStatusOfGameIsNotOver);
    }
}
