package view;

import java.util.List;
import java.util.Random;

public class WumpusGameDTO {
    int[][] rooms;
    int[][] links;
    int roomSize;
    int playerSize;

    boolean gameOver;
    int currRoom;
    int numArrows;
    int wumpusRoom;
    List<String> messages;

    public int[][] getRooms() {
        return rooms;
    }

    public void setRooms(int[][] rooms) {
        this.rooms = rooms;
    }

    public int[][] getLinks() {
        return links;
    }

    public void setLinks(int[][] links) {
        this.links = links;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public int getPlayerSize() {
        return playerSize;
    }

    public void setPlayerSize(int playerSize) {
        this.playerSize = playerSize;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getCurrRoom() {
        return currRoom;
    }

    public void setCurrRoom(int currRoom) {
        this.currRoom = currRoom;
    }

    public int getNumArrows() {
        return numArrows;
    }

    public void setNumArrows(int numArrows) {
        this.numArrows = numArrows;
    }

    public int getWumpusRoom() {
        return wumpusRoom;
    }

    public void setWumpusRoom(int wumpusRoom) {
        this.wumpusRoom = wumpusRoom;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
