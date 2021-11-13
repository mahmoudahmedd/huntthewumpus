package model;

import java.util.ArrayList;
import java.util.List;

public class NewGame implements Game{

    @Override
    public void startGame(){
        buildGameMap();
    }

    @Override
    public void playerMovesToCave(int cave) {

    }

    @Override
    public void playerShootsToCave(int cave) {

    }

    @Override
    public int[][] getCaveslinks() {
        return new int[0][];
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public int getNumberOfArrows() {
        return 0;
    }

    @Override
    public List<String> getMessages() {
        return null;
    }

    @Override
    public int getWumpusCave() {
        return 0;
    }

    @Override
    public int getPlayerCave() {
        return 0;
    }


    private void buildGameMap() {
        GameMap gameMap = new GameMap();
        List<Cave> caves = new ArrayList<>();
        buildCaves(caves);
        gameMap.setCaves(caves);
        buildCaveLinks(caves);
    }

    private void buildCaves(List<Cave> caves) {
        for(int i = 1; i <= 20; i++){
            caves.add(new Cave(i));
        }
    }

    private void buildCaveLinks(List<Cave> caves) {
        int[][] links = {{4, 7, 1}, {0, 9, 2}, {1, 11, 3}, {4, 13, 2}, {0, 5, 3},
                {4, 6, 14}, {7, 16, 5}, {6, 0, 8}, {7, 17, 9}, {8, 1, 10}, {9, 18, 11},
                {10, 2, 12}, {13, 19, 11}, {14, 3, 12}, {5, 15, 13}, {14, 16, 19},
                {6, 17, 15}, {16, 8, 18}, {19, 10, 17}, {15, 12, 18}};

        for(int i = 0; i < caves.size(); i++) {
            int[] link = links[i];
            Cave cave=caves.get(i);
            for(int caveNumber : link) {
                Cave linkedCave = caves.get(caveNumber);
                cave.addLink(linkedCave);
            }
        }
    }
}
