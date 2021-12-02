package acceptance.steps;

import acceptance.utilities.World;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationOfGameObjectsOnMapSteps {
    private World world;

    public LocationOfGameObjectsOnMapSteps(World world) {
        this.world = world;
    }

    @Given("player in cave {int}")
    public void playerIsInCave(Integer playerStartingCave) {
        this.world.getRandomNumberGeneratorBuilder().setPlayerStartingCaveIndex(playerStartingCave);
    }

    @Given("wumpus in cave {int}")
    public void wumpusInCave(Integer wumpusStartingCave) {
        this.world.getRandomNumberGeneratorBuilder().setWumpusStartingCaveIndex(wumpusStartingCave);
    }

    @Given("first bat in cave {int}")
    public void firstBatInCave(Integer firstBatStartingCave) {
        this.world.getRandomNumberGeneratorBuilder().setFirstBatStartingCaveIndex(firstBatStartingCave);
    }

    @Given("second bat in cave {int}")
    public void secondBatInCave(Integer secondBatStartingCave) {
        this.world.getRandomNumberGeneratorBuilder().setSecondBatStartingCaveIndex(secondBatStartingCave);
    }

    @Given("first pit in cave {int}")
    public void firstPitInCave(Integer firstPitStartingCave) {
        this.world.getRandomNumberGeneratorBuilder().setFirstPitCave(firstPitStartingCave);
    }

    @Given("second pit in cave {int}")
    public void secondPitInCave(Integer secondPitStartingCave) {
        this.world.getRandomNumberGeneratorBuilder().setSecondPitCave(secondPitStartingCave);
    }

    @Given("player drop down cave {int}")
    public void playerDropDownCave(Integer playerDropDownCave) {
        this.world.getRandomNumberGeneratorBuilder().setPlayerDropDownCave(playerDropDownCave);
    }

    @Given("first bat in final cave {int}")
    public void firstBatInFinalCave(Integer firstBatFinalCave) {
        this.world.getRandomNumberGeneratorBuilder().setFirstBatFinalCave(firstBatFinalCave);
    }

    @Then("player will be at cave {int}")
    public void playerWillBeAtCave(Integer expectedPlayerCave) {
        final int playerCurrentRoom = world.getWumpusPresenter().getPlayerCave();
        assertEquals(expectedPlayerCave, playerCurrentRoom);
    }

    @Then("wumpus will be at cave {int}")
    public void wumpusWillBeAtCave(Integer expectedWumpusCave) {
        final int wumpusCurrentRoom = world.getWumpusPresenter().getWumpusCave();
        assertEquals(expectedWumpusCave, wumpusCurrentRoom);
    }
}
