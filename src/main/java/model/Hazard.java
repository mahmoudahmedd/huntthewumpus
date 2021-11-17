package model;

public interface Hazard {
    void executeActionOnPlayer(Player player);
    String getWarningInTheLinkedCave();
    String getWarningInTheSameCave();
}
