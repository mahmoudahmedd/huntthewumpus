package model;

import java.util.*;

public class Cave {
    private int number;
    private Set<Cave> linkedCaves;
    private List<GameObject> gameObjects;

    public Cave(int number){
        this.number = number;
        this.linkedCaves = new HashSet<>();
        this.gameObjects=new ArrayList<>();
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

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }
}
