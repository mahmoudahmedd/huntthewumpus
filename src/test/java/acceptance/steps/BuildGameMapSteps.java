package acceptance.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Cave;
import model.GameInitialConfigurations;
import model.gameobjects.Player;
import model.gameobjects.hazards.Bat;
import model.gameobjects.hazards.Pit;
import model.gameobjects.hazards.Wumpus;
import acceptance.utilities.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BuildGameMapSteps {
    World world;

    public BuildGameMapSteps(World world) {
        this.world = world;
    }
    @Given("location of game objects on map is initialized")
    public void locationOfGameObjectsOnMapIsInitialized() {

    }

    @When("game starts")
    public void gameStartsWithCaves() {

    }

    @Then("cave {int} will contain the first bat and cave {int} will contain the second bat")
    public void firstBatWillBeAtCaveAndSecondBatWillBeAtCave(Integer expectedFirstBatCave, Integer expectedSecondBatCave) {
        int[] batsStartingCavesIndexes = {expectedFirstBatCave, expectedSecondBatCave};
        List<Bat> listOfBats = this.world.getGameWorld().getBats();
        for(int i = 0; i < listOfBats.size(); i++) {
            assertEquals(batsStartingCavesIndexes[i], listOfBats.get(i).getCave().getNumber());
            Cave batCave= this.world.getGameWorld().getGameMap().getCaves().get(batsStartingCavesIndexes[i]);
            Bat bat = listOfBats.get(i);
            assertTrue(batCave.getGameObjects().contains(bat));
        }
    }

    @And("cave {int} will contain the player object")
    public void caveWillContainThePlayer(int expectedPlayerCave) {
        Cave playerCave = this.world.getGameWorld().getGameMap().getCaves().get(expectedPlayerCave);
        Player player = this.world.getGameWorld().getPlayer();
        assertTrue(playerCave.getGameObjects().contains(player));
    }

    @And("player cave index will be {int}")
    public void playerCaveIndexWillBe(int expectedPlayerCaveIndex) {
        final int actualPlayerCaveIndex = this.world.getGameWorld().getPlayerCave();
        assertEquals(expectedPlayerCaveIndex, actualPlayerCaveIndex);
    }

    @Then("cave {int} will contain the wumpus object")
    public void caveWillContainTheWumpusObject(int expectedWumpusCave) {
        Cave wumpusCave = this.world.getGameWorld().getGameMap().getCaves().get(expectedWumpusCave);
        Wumpus wumpus = this.world.getGameWorld().getWumpus();
        assertTrue(wumpusCave.getGameObjects().contains(wumpus));
    }

    @And("wumpus cave index will be {int}")
    public void wumpusCaveIndexWillBe(int expectedWumpusCave) {
        final int actualWumpusCaveIndex = this.world.getGameWorld().getWumpusCave();
        assertEquals(expectedWumpusCave,actualWumpusCaveIndex);
    }

    @And("cave {int} will contain the first pit and cave {int} will contain the second pit")
    public void caveWillContainTheFirstPitAndCaveWillContainTheSecondPit(Integer expectedFirstPitCave, Integer expectedSecondPitCave) {
        List<Pit> listOfPits = this.world.getGameWorld().getPits();
        int[] pitsInCavesIndexes = {expectedFirstPitCave, expectedSecondPitCave};

        for(int i = 0; i < listOfPits.size(); i++) {
            assertEquals(pitsInCavesIndexes[i], listOfPits.get(i).getCave().getNumber());
            Cave PitInCave= this.world.getGameWorld().getGameMap().getCaves().get(pitsInCavesIndexes[i]);
            Pit pit = listOfPits.get(i);
            assertTrue(PitInCave.getGameObjects().contains(pit));
        }
    }

    @Then("number of {string} will be {int}")
    public void numberOfWillBe(String gameObject, Integer expectedNumber) {
        Map<String, Integer> mapperForTheNumberOfGameObjects  = configureNumberOfTheGameObjects();
        assertEquals(expectedNumber, mapperForTheNumberOfGameObjects.get(gameObject));
    }

    private Map configureNumberOfTheGameObjects() {
        Map<String, Integer> mapperForTheNumberOfGameObjects  = new HashMap<>();

        // Add the actual number of arrows
        Integer actualNumberOfArrows = this.world.getGameWorld().getPlayer().getArrows().getNumber();
        mapperForTheNumberOfGameObjects.put("arrows", actualNumberOfArrows);

        // Add the actual number of bats
        Integer actualNumberOfBats = this.world.getGameWorld().getBats().size();
        mapperForTheNumberOfGameObjects.put("bats", actualNumberOfBats);

        // Add the actual number of pits
        Integer actualNumberOfPits = this.world.getGameWorld().getPits().size();
        mapperForTheNumberOfGameObjects.put("pits", actualNumberOfPits);

        // Add the actual number of wumpus
        Integer actualNumberOfWumpus = GameInitialConfigurations.NUMBER_OF_WUMPUS;
        mapperForTheNumberOfGameObjects.put("wumpus", actualNumberOfWumpus);

        // Add the actual number of players
        Integer actualNumberOfPlayers = GameInitialConfigurations.NUMBER_OF_PLAYERS;
        mapperForTheNumberOfGameObjects.put("players", actualNumberOfPlayers);

        return mapperForTheNumberOfGameObjects;
    }
}
