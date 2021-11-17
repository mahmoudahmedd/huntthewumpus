package model;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private boolean dead;
    private Arrow arrow;
    List<String> warnings;

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Player(int numberOfArrows) {
        warnings = new ArrayList<>();
        arrow = new Arrow();
        this.arrow.initializeNumberOfArrows(numberOfArrows);
    }

    public void move(Cave caveToMoveTo) {
        this.warnings.clear();

        if (this.isMoveValid(caveToMoveTo)) {
            changeTheCaveLocation(caveToMoveTo);
        }

        executePostMoveActions();
        senseWarning();
    }

    public void fly(Cave caveToMoveTo) {
        changeTheCaveLocation(caveToMoveTo);
        executePostMoveActions();
    }

    private void changeTheCaveLocation(Cave caveToMoveTo) {
        this.getCave().removeGameObject(this);
        this.setCave(caveToMoveTo);
        caveToMoveTo.addGameObject(this);
    }

    public List<String> getWarnings() {
        return warnings;
    }

    private void senseWarning() {
        List<Cave> linkedCaves = this.getCave().getLinkedCaves();

        for(Cave linkedCave: linkedCaves) {
            List<GameObject> gameObjects = linkedCave.getGameObjects();
            for(GameObject gameObject : gameObjects){
                if(gameObject instanceof Wumpus){
                    warnings.add(((Wumpus) gameObject).getWarningInTheLinkedCave());
                } else if(gameObject instanceof Pit) {
                    warnings.add(((Pit) gameObject).getWarningInTheLinkedCave());
                }
            }
        }
    }

    public void addWarning(String warning) {
        warnings.add(warning);
    }

    private void executePostMoveActions() {
        List<GameObject> gameObjects = this.getCave().getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof Hazard){
                ((Hazard)gameObject).executeActionOnPlayer(this);
            }
        }
    }


    private void killed() {
        this.dead = true;
    }

    private boolean isMoveValid(Cave caveToMoveTo) {
        return this.getCave().getLinkedCaves().contains(caveToMoveTo);
    }

    public boolean isDead() {
        return dead;
    }

    public void shoot(Cave caveToShoot) {
        this.warnings.clear();
        this.shootArrow();
        List<GameObject> gameObjects = caveToShoot.getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof Wumpus){
                killed();
            }
        }

        executePostShootActions();
    }

    private void shootArrow() {
        arrow.decrementByOne();
    }

    private void executePostShootActions() {
        if(arrow.getNumberOfArrows() == 0) {
            this.killed();
        }
    }

    public Arrow getArrows() {
        return this.arrow;
    }
}
