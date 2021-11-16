package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Player extends GameObject {
    private boolean dead;

    public void move(Cave caveToMoveTo) {
        if (this.isMoveValid(caveToMoveTo)) {
            this.getCave().removeGameObject(this);
            this.setCave(caveToMoveTo);
            caveToMoveTo.addGameObject(this);
        }

        executePostMoveActions();
    }

    public List<String> getWarnings() {
        List<String> warnings = new ArrayList<>();
        Set<Cave> linkedCaves = this.getCave().getLinkedCaves();

        for(Cave linkedCave: linkedCaves) {
            List<GameObject> gameObjects = linkedCave.getGameObjects();
            for(GameObject gameObject : gameObjects){
                if(gameObject instanceof Wumpus){
                    warnings.add(((Wumpus) gameObject).warning);
                }
            }
        }
        return warnings;
    }

    private void executePostMoveActions() {
        executeWumpusActions();
        executePitsActions();
    }

    private void executePitsActions() {
        List<GameObject> gameObjects = this.getCave().getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof Pit){
                this.dead = true;
            }
        }
    }

    private void executeWumpusActions() {
        List<GameObject> gameObjects = this.getCave().getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof Wumpus){
                this.dead = true;
            }
        }
    }

    private boolean isMoveValid(Cave caveToMoveTo) {
        return this.getCave().getLinkedCaves().contains(caveToMoveTo);
    }

    public boolean isDead() {
        return dead;
    }
}
