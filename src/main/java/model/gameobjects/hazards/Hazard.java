package model.gameobjects.hazards;

import model.gameobjects.Player;

public interface Hazard {
    void executeActionOnPlayer(Player player);
}
