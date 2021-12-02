package acceptance.steps;

import acceptance.utilities.ScenarioWorld;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class WumpusSteps {
    private ScenarioWorld world;

    public WumpusSteps(ScenarioWorld world) {
        this.world = world;
    }

    @Given("wumpus attempt to wakeup")
    public void wumpusAttemptToWakeup() {
        Integer numberAtWhichWumpusWillRemainSleeping = 1;
        this.world.getRandomNumberGeneratorBuilder().setNumberAtWhichWumpusWillRemainSleeping(numberAtWhichWumpusWillRemainSleeping);
    }

    @Given("wumpus not attempt to wakeup")
    public void wumpusNotAttemptToWakeup() {
        Integer numberAtWhichWumpusWillRemainSleeping = 0;
        this.world.getRandomNumberGeneratorBuilder().setNumberAtWhichWumpusWillRemainSleeping(numberAtWhichWumpusWillRemainSleeping);
    }

    @When("wumpus attempts to wake up wumpus moves to third linked cave")
    public void wumpusAttemptsToWakeUpWumpusMovesToThirdLinkedCave() {
        int  thirdLinkedCaveIndex = 2;
        this.world.getRandomNumberGeneratorBuilder().setWumpusLinkedCaveIndex(thirdLinkedCaveIndex);
    }

    @When("wumpus attempts to wake up wumpus moves to second linked cave")
    public void wumpusAttemptsToWakeUpWumpusMovesToSecondLinkedCave() {
        int  secondLinkedCaveIndex = 1;
        this.world.getRandomNumberGeneratorBuilder().setWumpusLinkedCaveIndex(secondLinkedCaveIndex);
    }
}
