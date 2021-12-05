package model.gameobjects.hazards.warnablehazard;

import model.gameobjects.hazards.Hazard;

public interface WarnableHazard extends Hazard {
    String getWarningInTheLinkedCave();
    String getWarningInTheSameCave();
}
