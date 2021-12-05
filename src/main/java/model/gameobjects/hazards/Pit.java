package model.gameobjects.hazards;

import model.gameobjects.GameObject;
import model.gameobjects.Player;

public class Pit extends GameObject implements WarnableHazard {
    final String warningInTheSameCave = "you fell into a pit";
    final String warningInTheLinkedCave = "you feel a draft";

    public String getWarningInTheLinkedCave() {
        return warningInTheLinkedCave;
    }

    public String getWarningInTheSameCave() {
        return warningInTheSameCave;
    }

    @Override
    public void executeActionOnPlayer(Player player) {
        player.setDead(true);
        player.addWarning(this.warningInTheSameCave);
    }
}
