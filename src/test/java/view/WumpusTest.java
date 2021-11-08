package view;

import org.junit.jupiter.api.Test;
import view.Wumpus;


import static org.junit.jupiter.api.Assertions.*;

class WumpusTest {

    @Test
    void validateNumberOfArrowsOnGameInit() {
        //Given
        int expectedArrowsNum = 5;

        //when
        Wumpus wumpus = new Wumpus();
        wumpus.startNewGame();

        //THEN
        assertEquals(expectedArrowsNum, wumpus.numArrows);
    }
}