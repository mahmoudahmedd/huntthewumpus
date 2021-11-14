package model;

import java.util.*;

public class Cave {
    private int number;
    private Set<Cave> linkedCaves;

    public Cave(int number){
        this.number = number;
        this.linkedCaves = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return number == cave.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public void addLink(Cave linkedCave) {
        this.linkedCaves.add(linkedCave);
    }

    public Set<Cave> getLinkedCaves() {
        return linkedCaves;
    }

    public int getNumber() {
        return number;
    }
}
