package model.gameobjects.hazards;

import model.Cave;
import model.GameInitialConfigurations;
import model.gameobjects.GameObject;
import model.gameobjects.Player;
import utilities.RandomNumberGenerator;

import java.util.Optional;

public class Wumpus extends GameObject implements Hazard {
    final String warningInTheSameCave = "You woke the Wumpus and it ate you";
    final String warningInTheLinkedCave = "there's an awful smell";
    private final RandomNumberGenerator randomNumberGenerator;
    boolean dead;

    public Wumpus(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    @Override
    public String getWarningInTheLinkedCave() {
        return this.warningInTheLinkedCave;
    }

    @Override
    public String getWarningInTheSameCave() {
        return this.warningInTheSameCave;
    }

    public void wakeup(int randomLinkedCaveIndex) {
        this.move(randomLinkedCaveIndex);

        Optional<GameObject> player = this.getCave().getGameObjects().stream().filter(gameObject -> gameObject instanceof Player).findFirst();
        if(player.isPresent()){
            executeActionOnPlayer((Player) player.get());
        }
    }

    private void move(int randomLinkedCaveIndex) {
        this.getCave().removeGameObject(this);

        Cave caveToMoveTo = (Cave) this.getCave().getLinkedCaves().toArray()[randomLinkedCaveIndex];
        this.setCave(caveToMoveTo);
        caveToMoveTo.addGameObject(this);
    }

    @Override
    public void executeActionOnPlayer(Player player) {
        player.setDead(true);
        player.addWarning(this.warningInTheSameCave);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void attemptToWakeup() {
            int maximumNumberForCalculatingWumpusWakeupProbability = GameInitialConfigurations.MAXIMUM_NUMBER_FOR_CALCULATING_WUMPUS_WAKEUP_PROBABILITY;
            if (randomNumberGenerator.generateNumber(maximumNumberForCalculatingWumpusWakeupProbability) != 0) { // 75 %
                int randomLinkedCaveIndex = randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_LINKED_CAVES);
                wakeup(randomLinkedCaveIndex);
        }
    }
}
