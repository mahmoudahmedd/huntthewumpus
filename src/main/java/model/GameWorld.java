package model;

import model.gameobjects.*;
import model.gameobjects.hazards.Bat;
import model.gameobjects.hazards.EnemyPlayer;
import model.gameobjects.hazards.Pit;
import model.gameobjects.hazards.Wumpus;
import utilities.GameInitialConfigurations;
import utilities.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWorld implements Game {

    private GameMap gameMap;
    private RandomNumberGenerator randomNumberGenerator;
    private Player player;
    private Wumpus wumpus;
    private List<Bat> bats;
    private List<Pit> pits;
    private Map<String, List<? extends GameObject>> hazardsMap = new HashMap<>();
    private EnemyPlayer enemyPlayer;

    public GameWorld() {
        this.randomNumberGenerator = new RandomNumberGenerator();
    }

    public GameWorld(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    @Override
    public void startGame() {
        buildGameMap();

        initializePlayer();
        initializeEnemyPlayer();
        initializeWumpus();
        initializeBats();
        initializePits();
    }

    private void initializePlayer() {
        player = new Player(GameInitialConfigurations.NUMBER_OF_ARROWS);
        player.setId(GameInitialConfigurations.PLAYER_ID);
        setGameObjectInitialCave(player);
    }

    private void initializeEnemyPlayer() {
        this.enemyPlayer = new EnemyPlayer(GameInitialConfigurations.NUMBER_OF_ARROWS);
        this.enemyPlayer.setId(GameInitialConfigurations.ENEMY_PLAYER_ID);
        setGameObjectInitialCave(this.enemyPlayer);
    }

    private void initializeWumpus() {
        wumpus = new Wumpus(randomNumberGenerator);
        wumpus.setPrecedence(2);
        wumpus.setId(GameInitialConfigurations.WUMPUS_ID);
        setGameObjectInitialCave(wumpus);
        List<Wumpus> wumpusList = new ArrayList<>();
        wumpusList.add(wumpus);
        hazardsMap.put(Wumpus.class.getSimpleName(), wumpusList);
    }

    private void initializeBats() {
        bats = new ArrayList<>();
        for (int index = 0; index < GameInitialConfigurations.NUMBER_OF_BATS; index++) {
            Bat bat = new Bat(randomNumberGenerator, gameMap);
            bat.setPrecedence(3);
            bats.add(bat);
            bats.get(index).setId(GameInitialConfigurations.BAT_ID_PREFIX + index);
            setGameObjectInitialCave(bats.get(index));
            hazardsMap.put(Bat.class.getSimpleName(), bats);
        }

    }

    private void initializePits() {
        pits = new ArrayList<>();
        for (int index = 0; index < GameInitialConfigurations.NUMBER_OF_PITS; index++) {
            Pit pit = new Pit();
            pit.setPrecedence(1);
            pits.add(pit);
            pits.get(index).setId(GameInitialConfigurations.PITS_ID_PREFIX + index);
            setGameObjectInitialCave(pits.get(index));
            hazardsMap.put(Pit.class.getSimpleName(), pits);
        }
    }

    private void setGameObjectInitialCave(GameObject gameObject) {
        Cave cave = getRandomCave();

        if (caveIsNotValidForGameObject(gameObject, cave)) {
            setGameObjectInitialCave(gameObject);
        } else {
            gameObject.setCave(cave);
            cave.addGameObject(gameObject);
        }

    }

    private boolean caveIsNotValidForGameObject(GameObject gameObject, Cave cave) {
        if (!isGameObjectInTheSameCaveAsPlayer(cave)) {
            return isHazardousGameObjectLocatedNearPlayerAsItsLikes(cave) ||
                    isHazardousGameObjectLocatedInTheSameCaveAsItsLikes(gameObject, cave);
        }

        return true;
    }

    private boolean isHazardousGameObjectLocatedNearPlayerAsItsLikes(Cave cave) {
        List<Cave> linkedCaves = cave.getLinkedCaves();
        Cave playerCave = player.getCave();
        return linkedCaves.contains(playerCave);
    }

    private boolean isHazardousGameObjectLocatedInTheSameCaveAsItsLikes(GameObject gameObject, Cave cave) {
        List<? extends GameObject> hazardsList = hazardsMap.get(gameObject.getClass().getSimpleName());
        if (hazardsList != null) {
            for (GameObject hazard : hazardsList) {
                Cave hazardCave = hazard.getCave();
                if (cave.equals(hazardCave)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isGameObjectInTheSameCaveAsPlayer(Cave cave) {
        Cave playerCave = player.getCave();

        if (playerCave != null) {
            return cave.equals(playerCave);
        }

        return false;
    }

    private Cave getRandomCave() {
        int randomCaveIndex = randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES);
        return gameMap.getCaves().get(randomCaveIndex);
    }

    private void buildGameMap() {
        gameMap = new GameMap();
        List<Cave> caves = new ArrayList<>();
        buildCaves(caves);
        gameMap.setCaves(caves);
        buildCaveLinks(caves);
    }

    private void buildCaves(List<Cave> caves) {
        for (int i = 0; i < GameInitialConfigurations.NUMBER_OF_CAVES; i++) {
            caves.add(new Cave(i));
        }
    }

    private void buildCaveLinks(List<Cave> caves) {
        for (int i = 0; i < caves.size(); i++) {
            int[] link = GameInitialConfigurations.CAVE_LINKS[i];
            Cave cave = caves.get(i);
            for (int caveNumber : link) {
                Cave linkedCave = caves.get(caveNumber);
                cave.addLink(linkedCave);
            }
        }
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    @Override
    public void playerMovesToCave(int cave) {
        Cave caveToMoveTo = gameMap.getCaves().get(cave);
        player.move(caveToMoveTo);
        Teleportation teleportation= player.getTeleportation();
        if(teleportation != null) {
            caveToMoveTo= getRandomCave();
            player.teleport(caveToMoveTo);
        }
        enemyPlayerMovesToCave();
    }

    @Override
    public void playerShootsToCave(Integer... caves) {
        for(Integer cave: caves) {
            Cave caveToShoot = gameMap.getCaves().get(cave);
            player.shoot(caveToShoot);
        }

        if (!wumpus.isDead()) {
            wumpus.attemptToWakeup();
        }
    }

    @Override
    public boolean isGameOver() {
        return player.isDead() || player.hasNoArrows() || wumpus.isDead();
    }

    @Override
    public int getNumberOfArrows() {
        return player.getArrows().getNumber();
    }

    @Override
    public List<String> getMessages() {
        List<String> allMessage = new ArrayList<>();
        allMessage.addAll(this.player.getWarnings());
        allMessage.addAll(this.enemyPlayer.getWarnings());
        return allMessage;
    }

    @Override
    public int getWumpusCave() {
        return wumpus.getCave().getNumber();
    }

    @Override
    public int getPlayerCave() {
        return player.getCave().getNumber();
    }

    @Override
    public EnemyPlayer getEnemyPlayer() {
        return this.enemyPlayer;
    }

    @Override
    public void enemyPlayerMovesToCave() {
        Cave caveToMoveTo = getRandomLinkedCaveForEnemyPlayer();
        this.enemyPlayer.move(caveToMoveTo);
        Teleportation teleportation = this.enemyPlayer.getTeleportation();
        if(teleportation != null) {
            caveToMoveTo = getRandomCave();
            this.enemyPlayer.teleport(caveToMoveTo);
        }
    }

    @Override
    public void enemyPlayerShootsToCave() {
        Cave caveToShoot = getRandomLinkedCaveForEnemyPlayer();
        this.enemyPlayer.shoot(caveToShoot);
        
        if (!wumpus.isDead()) {
            wumpus.attemptToWakeup();
        }
    }

    private Cave getRandomLinkedCaveForEnemyPlayer() {
        int randomLinkedCaveIndex = randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_LINKED_CAVES);
        return this.enemyPlayer.getCave().getLinkedCaves().get(randomLinkedCaveIndex);
    }

    public Player getPlayer() {
        return this.player;
    }

    public Wumpus getWumpus() {
        return this.wumpus;
    }

    public List<Bat> getBats() {
        return this.bats;
    }

    public List<Pit> getPits() {
        return this.pits;
    }
}
