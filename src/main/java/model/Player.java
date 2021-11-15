package model;

import java.util.Objects;

public class Player extends GameObject{
    public void move(Cave caveToMoveTo) {
        this.getCave().removeGameObject(this);
        this.setCave(caveToMoveTo);
        caveToMoveTo.addGameObject(this);
    }
}
