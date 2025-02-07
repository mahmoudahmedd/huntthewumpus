package acceptance.steps;

import utilities.ScenarioWorld;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SenseSteps {
    private ScenarioWorld world;

    public SenseSteps(ScenarioWorld world) {
        this.world = world;
    }

    @Then("player sense the warning {string}")
    public void playerSenseTheWarningWarning(String expectedWarning) {
        List<String> messages = world.getWumpusPresenter().getMessages();
        assertTrue(messages.contains(expectedWarning));
    }
}
