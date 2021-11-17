package model;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private boolean dead;
    private Arrow arrow;

    public Player(int numberOfArrows) {
        arrow = new Arrow();
        this.arrow.initializeNumberOfArrows(numberOfArrows);
    }

    public void move(Cave caveToMoveTo) {
        if (this.isMoveValid(caveToMoveTo)) {
            changeTheCaveLocation(caveToMoveTo);
        }

        executePostMoveActions();
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
        List<String> warnings = new ArrayList<>();
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


        List<GameObject> gameObjects = this.getCave().getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof Wumpus){
                warnings.add(((Wumpus) gameObject).getWarningInTheSameCave());
                break;
            } else if(gameObject instanceof Pit) {
                warnings.add(((Pit) gameObject).getWarningInTheSameCave());
                break;
            }
        }


        return warnings;
    }

    private void executePostMoveActions() {
        executeWumpusActions();
        executePitsActions();
    }

    private void executeWumpusActions() {
        List<GameObject> gameObjects = this.getCave().getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof Wumpus){
                killed();
            }
        }
    }

    private void executePitsActions() {
        List<GameObject> gameObjects = this.getCave().getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof Pit){
                killed();
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
}
