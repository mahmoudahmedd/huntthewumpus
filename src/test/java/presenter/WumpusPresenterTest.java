package presenter;

import utilities.RandomNumberGeneratorBuilder;
import utilities.GameInitialConfigurations;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class WumpusPresenterTest {
    private RandomNumberGeneratorBuilder randomNumberGeneratorBuilder;

    private void configureRandomNumberGeneratorBuilderBasedOnDefaultLocation() {
        randomNumberGeneratorBuilder = new RandomNumberGeneratorBuilder();
        randomNumberGeneratorBuilder.setPlayerStartingCaveIndex(0);
        randomNumberGeneratorBuilder.setEnemyPlayerStartingCaveIndex(6);
        randomNumberGeneratorBuilder.setWumpusStartingCaveIndex(18);
        randomNumberGeneratorBuilder.setFirstBatStartingCaveIndex(19);
        randomNumberGeneratorBuilder.setSecondBatStartingCaveIndex(13);
        randomNumberGeneratorBuilder.setFirstPitCave(3);
        randomNumberGeneratorBuilder.setSecondPitCave(13);
    }

    @Test
    public void testMovingPlayerToCave() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(1,linkedCaveIndex);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        final int playerNextCave = 7;
        wumpusPresenter.move(playerNextCave);

        final int playerCurrentRoom = wumpusPresenter.getPlayerCave();
        assertEquals(playerNextCave, playerCurrentRoom);

        final boolean expectedStatusOfGameIsOver = false;
        final boolean isGameOver = wumpusPresenter.isGameOver();
        assertEquals(isGameOver, expectedStatusOfGameIsOver);

    }

    @Test
    public void testMoveToNonConnectedCave() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(1,linkedCaveIndex);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        final int playerNextCave = 16;
        wumpusPresenter.move(playerNextCave);

        final int playerCurrentRoom = wumpusPresenter.getPlayerCave();
        assertEquals(randomNumberGeneratorBuilder.getPlayerStartingCaveIndex(), playerCurrentRoom);

        final boolean gameIsNotOver = false;
        final boolean isGameOver = wumpusPresenter.isGameOver();
        assertEquals(isGameOver, gameIsNotOver);
    }

    @Test
    public void testMovingPlayerToCaveThatHasAWumups() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();

        final int[] journeyPath = {1, 9, 10, 18};
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(journeyPath.length,linkedCaveIndex);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testMovingPlayerToACaveNearAWumpusAndSensingTheWumpus() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();

        final int[] journeyPath = {1, 9, 10};
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(journeyPath.length,linkedCaveIndex);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        List<String> messages = wumpusPresenter.getMessages();
        assertTrue(messages.contains("there's an awful smell"));

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = false;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerEnterRoomWithBat() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();
        final int playerDropDownCave = 8;

        final int[] journeyPath = {12, 19};
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(journeyPath.length,linkedCaveIndex);

        randomNumberGeneratorBuilder.setPlayerStartingCaveIndex(11);
        randomNumberGeneratorBuilder.setPlayerDropDownCave(playerDropDownCave);
        randomNumberGeneratorBuilder.setFirstBatFinalCave(2);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final int playerCurrentCave = wumpusPresenter.getPlayerCave();
        assertEquals(playerDropDownCave, playerCurrentCave);

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = false;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerEnterRoomWithPit() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();

        final int[] journeyPath = {4, 3};
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(journeyPath.length,linkedCaveIndex);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testKillingTheWumpus() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();

        final int[] journeyPath = {1, 9, 10};
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(journeyPath.length,linkedCaveIndex);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final int shootToCave = 18;
        wumpusPresenter.shoot(shootToCave);

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerEnterRoomWithPitAndBat() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();

        final int[] journeyPath = {12, 13};
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(journeyPath.length,linkedCaveIndex);

        randomNumberGeneratorBuilder.setPlayerStartingCaveIndex(11);
        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();


        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = true;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerShootsAnArrowThatMissesTheWumpusAndWumpusRemainsSleeping() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();

        final int[] journeyPath = {1, 9, 10};
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(journeyPath.length,linkedCaveIndex);

        randomNumberGeneratorBuilder.setNumberAtWhichWumpusWillRemainSleeping(0);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final int shootToCave = 11;
        wumpusPresenter.shoot(shootToCave);

        final int wumpusCaveLocation = wumpusPresenter.getWumpusCave();
        assertEquals(wumpusCaveLocation, randomNumberGeneratorBuilder.getWumpusStartingCaveIndex());

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = false;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerShootsAnArrowThatMissesTheWumpusAndWumpusMoves() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();

        final int[] journeyPath = {1, 9, 10};
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(journeyPath.length,linkedCaveIndex);

        randomNumberGeneratorBuilder.setNumberAtWhichWumpusWillRemainSleeping(1);
        randomNumberGeneratorBuilder.addLinkedCaveIndex(2);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final int shootToCave = 11;
        wumpusPresenter.shoot(shootToCave);

        final int wumpusCaveLocation = wumpusPresenter.getWumpusCave();
        final int wumpusCaveCurrLocation = 17;
        assertEquals(wumpusCaveCurrLocation, wumpusCaveLocation);

        final boolean actualGameState = wumpusPresenter.isGameOver();
        final boolean gameIsOver = false;
        assertEquals(actualGameState, gameIsOver);
    }

    @Test
    public void testThatPlayerRunsOutOfArrowsWithoutKillingWumpus() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();


        for (int i = 0; i < GameInitialConfigurations.NUMBER_OF_ARROWS; i++) {
            randomNumberGeneratorBuilder.setNumberAtWhichWumpusWillRemainSleeping(0);
        }

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        final int caveToShoot = 7;
        for (int i = 0; i < GameInitialConfigurations.NUMBER_OF_ARROWS; i++) {
            wumpusPresenter.shoot(caveToShoot);
        }

        final String runningOutOfArrowsMessage = "You ran out of arrows";
        final List<String> actualGameMessages = wumpusPresenter.getMessages();
        assertTrue(actualGameMessages.contains(runningOutOfArrowsMessage));

        final boolean expectedGameStateGameIsOver = true;
        final boolean actualGameState = wumpusPresenter.isGameOver();
        assertEquals(expectedGameStateGameIsOver, actualGameState);
    }

    @Test
    public void testThatPlayerShootsAnArrowMissesWumpusAndWumpusWakesUpAndMoveToEatThePlayer() {
        configureRandomNumberGeneratorBuilderBasedOnDefaultLocation();
        final int[] journeyPath = {1, 9, 10};
        int linkedCaveIndex = 0;
        randomNumberGeneratorBuilder.addNumberOfMovesToTheEnemyPlayer(journeyPath.length,linkedCaveIndex);

        randomNumberGeneratorBuilder.setNumberAtWhichWumpusWillRemainSleeping(1);
        randomNumberGeneratorBuilder.addLinkedCaveIndex(1);

        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(randomNumberGeneratorBuilder.build());
        wumpusPresenter.startNewGame();

        for (int caveNumber : journeyPath) {
            wumpusPresenter.move(caveNumber);
        }

        final int shootToCave = 11;
        wumpusPresenter.shoot(shootToCave);

        final int wumpusCaveLocation = wumpusPresenter.getWumpusCave();
        final int wumpusCaveCurrLocation = wumpusPresenter.getPlayerCave();
        assertEquals(wumpusCaveCurrLocation, wumpusCaveLocation);

        final String wokeTheWumpusMessage = "You woke the Wumpus and it ate you";
        final List<String> actualGameMessages = wumpusPresenter.getMessages();
        assertTrue(actualGameMessages.contains(wokeTheWumpusMessage));

        final boolean expectedGameStateGameIsOver = true;
        final boolean actualGameState = wumpusPresenter.isGameOver();
        assertEquals(expectedGameStateGameIsOver, actualGameState);
    }
}