package model.gameobjects.hazards;

import model.gameobjects.Player;
import model.gameobjects.hazards.Hazard;

public interface WarnableHazard extends Hazard {
    void executeActionOnPlayer(Player player);
    String getWarningInTheLinkedCave();
    String getWarningInTheSameCave();
}
