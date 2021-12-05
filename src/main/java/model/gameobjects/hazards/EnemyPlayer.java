package model.gameobjects.hazards;

import model.gameobjects.Player;

public class EnemyPlayer extends Player implements Hazard  {

    public EnemyPlayer(int numberOfArrows) {
        super(numberOfArrows);
    }


    public void executeActionOnPlayer(Player player) {
        player.setDead(true);
        player.addWarning("this.warningInTheSameCave");
    }
}
