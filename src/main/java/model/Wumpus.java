package model;

public class Wumpus extends GameObject implements Hazard{
    final String warningInTheSameCave = "you've been eaten by the Wumpus";
    final String warningInTheLinkedCave = "there's an awful smell";
    boolean dead;

    @Override
    public String getWarningInTheLinkedCave() {
        return this.warningInTheLinkedCave;
    }

    @Override
    public String getWarningInTheSameCave() {
        return this.warningInTheSameCave;
    }

    public void wakeup(Player player,int randomLinkedCaveIndex) {
        this.move(randomLinkedCaveIndex);
        
        if(isWumpusInTheSameCaveWithPlayer()){
            player.setDead(true);
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

}
