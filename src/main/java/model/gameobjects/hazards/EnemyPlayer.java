package model.gameobjects.hazards;

import model.gameobjects.Player;

public class EnemyPlayer extends Player implements Hazard {
    private boolean dead;

    public EnemyPlayer(int numberOfArrows) {
        super(numberOfArrows);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
