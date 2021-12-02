package acceptance.steps;

import io.cucumber.java.en.Then;
import acceptance.utilities.World;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusOfGameSteps {
    private World world;

    public StatusOfGameSteps(World world) {
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
