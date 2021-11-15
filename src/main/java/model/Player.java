package model;

import java.util.List;
import java.util.Objects;

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

    private void executePostMoveActions() {
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
