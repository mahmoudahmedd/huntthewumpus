package model;

import java.util.Objects;

public class Player extends GameObject{
    private Cave cave;

    public void setCave(Cave cave) {
        this.cave = cave;
    }

    public Cave getCave() {
        return cave;
    }

}
