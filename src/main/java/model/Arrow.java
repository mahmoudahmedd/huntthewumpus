package model;

public class Arrow {
    private int numberOfArrow;

    public void initializeNumberOfArrows(int numberOfArrow) {
        this.numberOfArrow = numberOfArrow;
    }

    public void decrementByOne() {
        this.numberOfArrow--;
    }

    public int getNumberOfArrows() {
        return this.numberOfArrow;
    }

    public int getNumber() {
        return numberOfArrow;
    }
}
