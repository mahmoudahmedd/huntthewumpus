package acceptance;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Cave;
import model.GameInitialConfigurations;
import model.GameWorld;
import model.gameobjects.Player;
import model.gameobjects.hazards.Bat;
import model.gameobjects.hazards.Pit;
import model.gameobjects.hazards.Wumpus;
import org.mockito.Mockito;
import utilities.RandomNumberGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BuildGameMapSteps {

    RandomNumberGenerator randomNumberGenerator;
    GameWorld game;

    final int PLAYER_STARTING_CAVE_INDEX = 9;
    final int WUMPUS_STARTING_CAVE_INDEX = 18;
    final int FIRST_BAT_STARTING_CAVE_INDEX = 19;
    final int SECOND_BAT_STARTING_CAVE_INDEX = 13;
    final int FIRST_PIT_CAVE = 3;
    final int SECOND_PIT_CAVE = 13;


    @Given("location of game objects on map is initialized")
    public void locationOfGameObjectsOnMapIsInitialized() {
        randomNumberGenerator = mock(RandomNumberGenerator.class);
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES)).thenReturn(
                PLAYER_STARTING_CAVE_INDEX,
                WUMPUS_STARTING_CAVE_INDEX,
                FIRST_BAT_STARTING_CAVE_INDEX,
                SECOND_BAT_STARTING_CAVE_INDEX,
                FIRST_PIT_CAVE,
                SECOND_PIT_CAVE
        );
    }

    @When("game starts")
    public void gameStartsWithCaves() {
        game = new GameWorld(randomNumberGenerator);
        game.startGame();
    }

    @Then("cave {int} will contain the first bat and cave {int} will contain the second bat")
    public void firstBatWillBeAtCaveAndSecondBatWillBeAtCave(Integer expectedFirstBatCave, Integer expectedSecondBatCave) {
        int[] batsStartingCavesIndexes = {expectedFirstBatCave, expectedSecondBatCave};
        List<Bat> listOfBats = game.getBats();
        for(int i = 0; i < listOfBats.size(); i++) {
            assertEquals(batsStartingCavesIndexes[i], listOfBats.get(i).getCave().getNumber());
            Cave batCave= game.getGameMap().getCaves().get(batsStartingCavesIndexes[i]);
            Bat bat = listOfBats.get(i);
            assertTrue(batCave.getGameObjects().contains(bat));
        }
    }

    @And("cave {int} will contain the player object")
    public void caveWillContainThePlayer(int expectedPlayerCave) {
        Cave playerCave=game.getGameMap().getCaves().get(expectedPlayerCave);
        Player player=game.getPlayer();
        assertTrue(playerCave.getGameObjects().contains(player));
    }

    @And("player cave index will be {int}")
    public void playerCaveIndexWillBe(int expectedPlayerCaveIndex) {
        final int actualPlayerCaveIndex = game.getPlayerCave();
        assertEquals(expectedPlayerCaveIndex, actualPlayerCaveIndex);
    }

    @Then("cave {int} will contain the wumpus object")
    public void caveWillContainTheWumpusObject(int expectedWumpusCave) {
        Cave wumpusCave = game.getGameMap().getCaves().get(expectedWumpusCave);
        Wumpus wumpus = game.getWumpus();
        assertTrue(wumpusCave.getGameObjects().contains(wumpus));
    }

    @And("wumpus cave index will be {int}")
    public void wumpusCaveIndexWillBe(int expectedWumpusCave) {
        final int actualWumpusCaveIndex = game.getWumpusCave();
        assertEquals(expectedWumpusCave,actualWumpusCaveIndex);
    }

    @And("cave {int} will contain the first pit and cave {int} will contain the second pit")
    public void caveWillContainTheFirstPitAndCaveWillContainTheSecondPit(Integer expectedFirstPitCave, Integer expectedSecondPitCave) {
        List<Pit> listOfPits = game.getPits();
        int[] pitsInCavesIndexes = {expectedFirstPitCave, expectedSecondPitCave};

        for(int i = 0; i < listOfPits.size(); i++) {
            assertEquals(pitsInCavesIndexes[i], listOfPits.get(i).getCave().getNumber());
            Cave PitInCave= game.getGameMap().getCaves().get(pitsInCavesIndexes[i]);
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
        Integer actualNumberOfArrows = game.getPlayer().getArrows().getNumber();
        mapperForTheNumberOfGameObjects.put("arrows", actualNumberOfArrows);

        // Add the actual number of bats
        Integer actualNumberOfBats = game.getBats().size();
        mapperForTheNumberOfGameObjects.put("bats", actualNumberOfBats);

        // Add the actual number of pits
        Integer actualNumberOfPits = game.getPits().size();
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
