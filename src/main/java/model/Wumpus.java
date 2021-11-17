package model;

public class Wumpus extends GameObject implements Hazard{
    final String warningInTheSameCave = "you've been eaten by the Wumpus";
    final String warningInTheLinkedCave = "there's an awful smell";
    private boolean playerEaten;

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
        
        if(isWumpusInTheSameCaveWithPlayer()){
            this.eat();
        }
    }

    private void move(int randomLinkedCaveIndex) {
        this.getCave().removeGameObject(this);

        Cave caveToMoveTo = (Cave) this.getCave().getLinkedCaves().toArray()[randomLinkedCaveIndex];
        this.setCave(caveToMoveTo);
        caveToMoveTo.addGameObject(this);
    }

    private boolean isWumpusInTheSameCaveWithPlayer() {
        return this.getCave().getGameObjects().stream().anyMatch(gameObject -> gameObject instanceof Player);
    }

    private void eat() {
        this.playerEaten = true;
    }

    @Override
    public void executeActionOnPlayer(Player player) {
        player.setDead(true);
        player.addWarning(this.warningInTheSameCave);
    }
}
