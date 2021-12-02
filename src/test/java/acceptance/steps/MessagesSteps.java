package acceptance.steps;

import acceptance.utilities.World;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesSteps {
    private World world;

    public MessagesSteps(World world) {
        this.world = world;
    }

    @Then("game messages contains {string}")
    public void gameMessagesContains(String expectedMessage) {
        List<String> messages = world.getWumpusPresenter().getMessages();
        assertTrue(messages.contains(expectedMessage));
    }
}
