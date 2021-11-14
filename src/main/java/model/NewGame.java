package model;

import utilities.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NewGame implements Game{

    GameMap gameMap;
    RandomNumberGenerator randomNumberGenerator;
    Player player;

    public NewGame() {
        this.randomNumberGenerator=new RandomNumberGenerator();
    }

    public NewGame(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    @Override
    public void startGame(){
        buildGameMap();
        setPlayerInitialCave();

    }

    private void setPlayerInitialCave() {
        player = new Player();
        final String playerId="The Player";
        player.setId(playerId);
        int playerRandomCaveIndex=this.randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES);
        Cave playerCave = gameMap.getCaves().get(playerRandomCaveIndex);
        player.setCave(playerCave);
        playerCave.addGameObject(player);
    }

    private void buildGameMap() {
        gameMap = new GameMap();
        List<Cave> caves = new ArrayList<>();
        buildCaves(caves);
        gameMap.setCaves(caves);
        buildCaveLinks(caves);
    }

    private void buildCaves(List<Cave> caves) {
        for(int i = 0; i < GameInitialConfigurations.NUMBER_OF_CAVES; i++){
            caves.add(new Cave(i));
        }
    }

    private void buildCaveLinks(List<Cave> caves) {
        for(int i = 0; i < caves.size(); i++) {
            int[] link = GameInitialConfigurations.CAVE_LINKS[i];
            Cave cave=caves.get(i);
            for(int caveNumber : link) {
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

    }

    @Override
    public void playerShootsToCave(int cave) {

    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public int getNumberOfArrows() {
        return 0;
    }

    @Override
    public List<String> getMessages() {
        return null;
    }

    @Override
    public int getWumpusCave() {
        return 0;
    }

    @Override
    public int getPlayerCave() {
        return player.getCave().getNumber();
    }

    public Player getPlayer() {
        return this.player;
    }
}
