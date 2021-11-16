package model;

public class Wumpus extends GameObject {
    final String warning = "there's an awful smell";
    private boolean playerEaten;

    public String getWarning() {
        return this.warning;
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

    public boolean hasEatenThePlayer() {
        return this.playerEaten;
    }
}
