package model;

public class Pit extends GameObject {
    final String warningInTheSameCave = "you fell into a pit";
    final String warningInTheLinkedCave = "you feel a draft";

    public String getWarningInTheLinkedCave() {
        return warningInTheLinkedCave;
    }



    public String getWarningInTheSameCave() {
        return warningInTheSameCave;
    }


}
