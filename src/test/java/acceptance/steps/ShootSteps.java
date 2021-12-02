package acceptance.steps;

import io.cucumber.java.en.When;
import acceptance.utilities.ScenarioWorld;
import java.util.List;

public class ShootSteps {
    private ScenarioWorld world;

    public ShootSteps(ScenarioWorld world) {
        this.world = world;
    }

    @When("player shoots to cave {listOfIntegers}")
    public void playerShootsToCave(List<Integer> caveToShoot) {
        world.getWumpusPresenter().shoot(caveToShoot.toArray(new Integer[0]));
    }
}
