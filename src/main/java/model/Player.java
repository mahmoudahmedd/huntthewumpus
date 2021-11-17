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

    public void teleport(Cave caveToMoveTo) {
        this.warnings.clear();
        changeTheCaveLocation(caveToMoveTo);
        addWarning("a bat dropped you in a random cave");
        executePostMoveActions();
        senseWarning();
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
                if(gameObject instanceof Hazard){
                    warnings.add(((Hazard) gameObject).getWarningInTheLinkedCave());
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


    private boolean isMoveValid(Cave caveToMoveTo) {
        return this.getCave().getLinkedCaves().contains(caveToMoveTo);
    }

    public boolean isDead() {
        return dead;
    }

    public void shoot(Cave caveToShoot) {
        this.warnings.clear();

        arrow.decrementByOne();

        List<GameObject> gameObjects = caveToShoot.getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof Wumpus){
                ((Wumpus)gameObject).setDead(true);
            }
        }

    }

    public boolean hasNoArrows(){
        return getArrows().getNumber()==0;
    }

    public Arrow getArrows() {
        return this.arrow;
    }
}
