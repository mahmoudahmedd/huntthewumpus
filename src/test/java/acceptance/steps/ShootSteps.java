package acceptance.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import utilities.ScenarioWorld;
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

    @And("enemy player shoots to cave {listOfIntegers}")
    public void enemyPlayerShootsToCaveCaveToShoot(List<Integer> cavesToShoot) {
        for (int caveToShoot : cavesToShoot) {
            this.world.getRandomNumberGeneratorBuilder().addLinkedCaveIndex(caveToShoot);
        }

        for (int numberOfCavesToShoot = 0; numberOfCavesToShoot < cavesToShoot.size(); numberOfCavesToShoot++) {
            world.getWumpusPresenter().enemyPlayerShootsToCave();
        }
    }
}
