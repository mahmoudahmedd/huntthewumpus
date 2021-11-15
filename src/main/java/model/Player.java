package model;

import java.util.Objects;

public class Player extends GameObject{
    public void move(Cave caveToMoveTo) {
        if(this.isMoveValid(caveToMoveTo)) {
            this.getCave().removeGameObject(this);
            this.setCave(caveToMoveTo);
            caveToMoveTo.addGameObject(this);
        }
    }

    private boolean isMoveValid(Cave caveToMoveTo) {
        return this.getCave().getLinkedCaves().contains(caveToMoveTo);
    }
}
