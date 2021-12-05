package acceptance.steps;

import utilities.ScenarioWorld;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesSteps {
    private ScenarioWorld world;

    public MessagesSteps(ScenarioWorld world) {
        this.world = world;
    }

    @Then("game messages contains {string}")
    public void gameMessagesContains(String expectedMessage) {
        List<String> messages = world.getWumpusPresenter().getMessages();
        assertTrue(messages.contains(expectedMessage));
    }
}
