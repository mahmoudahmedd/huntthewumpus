package model.gameobjects.hazards;

import model.*;
import model.gameobjects.GameObject;
import model.gameobjects.Player;
import acceptance.utilities.RandomNumberGenerator;

public class Bat extends GameObject implements Hazard {

    final String warningInTheSameCave = "a bat dropped you in a random cave";
    final String warningInTheLinkedCave = "you hear a rustling";

    private RandomNumberGenerator randomNumberGenerator;
    private GameMap gameMap;

    public Bat(RandomNumberGenerator randomNumberGenerator,GameMap gameMap){
        this.randomNumberGenerator=randomNumberGenerator;
        this.gameMap=gameMap;
    }

    public void move() {
        Cave caveToMoveTo = getValidRelocationCave();
        this.getCave().removeGameObject(this);
        this.setCave(caveToMoveTo);
        caveToMoveTo.addGameObject(this);
    }

    @Override
    public void executeActionOnPlayer(Player player) {
          Teleportation teleportation=new Teleportation();
          teleportation.setBat(this);
          player.setTeleportation(teleportation);
    }

    private Cave getValidRelocationCave() {
        Cave caveToMoveTo;
        do {
            caveToMoveTo = getInitialRandomCave();
        } while (caveToMoveTo.getGameObjects().stream().anyMatch(gameObject -> gameObject instanceof Player)|| isTheCaveContainsBats(caveToMoveTo));
        return caveToMoveTo;
    }

    private boolean isTheCaveContainsBats(Cave caveToMoveTo) {
        return caveToMoveTo.getGameObjects().stream().anyMatch(gameObject -> gameObject instanceof Bat);
    }

    private Cave getInitialRandomCave() {
        int randomCaveIndex = randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES);
        return gameMap.getCaves().get(randomCaveIndex);
    }


    @Override
    public String getWarningInTheLinkedCave() {
        return this.warningInTheLinkedCave;
    }

    @Override
    public String getWarningInTheSameCave() {
        return this.warningInTheSameCave;
    }
}
