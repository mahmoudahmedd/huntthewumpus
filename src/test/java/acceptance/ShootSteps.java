package acceptance;

import io.cucumber.java.en.When;
import utilities.World;

public class ShootSteps {
    private World world;

    public ShootSteps(World world) {
        this.world = world;
    }

    @When("player shoots to cave {int}")
    public void playerShootsToCave(Integer caveToShoot) {
        world.getWumpusPresenter().shoot(caveToShoot);
    }
}
