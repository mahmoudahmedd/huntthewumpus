package model;

import utilities.RandomNumberGenerator;

public class Bat extends GameObject implements Hazard{

    final String warningInTheSameCave = "a bat dropped you in a random cave";
    final String warningInTheLinkedCave = "you hear a rustling";

    private RandomNumberGenerator randomNumberGenerator;
    private GameMap gameMap;

    public Bat(RandomNumberGenerator randomNumberGenerator,GameMap gameMap){
        this.randomNumberGenerator=randomNumberGenerator;
        this.gameMap=gameMap;
    }

    public void move(Cave caveToMoveTo) {
        this.getCave().removeGameObject(this);
        this.setCave(caveToMoveTo);
        caveToMoveTo.addGameObject(this);
    }

    @Override
    public void executeActionOnPlayer(Player player) {
        System.out.println("Bats moved player");
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
