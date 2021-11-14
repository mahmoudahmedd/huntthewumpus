package model;

public class Wumpus extends GameObject {
    private Cave cave;

    public void setCave(Cave cave) {
        this.cave = cave;
    }

    public Cave getCave() {
        return this.cave;
    }
}
