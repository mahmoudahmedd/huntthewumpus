Feature: Enemy Player shoot
  In order to kill the wumpus & player,
  As a enemy player,
  I want to shoot arrows.

  Background:
    Given player in cave 9
    And enemy player in cave 6
    And wumpus in cave 18
    And first bat in cave 19
    And second bat in cave 13
    And first pit in cave 3
    And second pit in cave 13

  Scenario: Enemy player shoots an arrow to a cave that contains the wumpus
    Given enemy player in cave 6
    And wumpus in cave 18
    And cave with index 1 to cave 6 is 16
    And cave with index 1 to cave 16 is 17
    And cave with index 2 to cave 17 is 18
    When enemy player moves on the [1, 1]
    And enemy player shoots to cave [2]
    Then game state is game over

  Scenario: Enemy player shoots an arrow that misses the wumpus and wumpus remains sleeping
    Given enemy player in cave 6
    And wumpus in cave 18
    And wumpus not attempt to wakeup
    And cave with index 1 to cave 6 is 16
    And cave with index 1 to cave 16 is 17
    And cave with index 1 to cave 17 is 8
    When enemy player moves on the [1, 1]
    And enemy player shoots to cave [1]
    Then game state is game not over
    And wumpus will be at cave 18

  Scenario: Enemy player shoots an arrow to a cave that contains the player
    Given enemy player in cave 6
    And player in cave 0
    And wumpus not attempt to wakeup
    And cave with index 0 to cave 6 is 7
    And cave with index 1 to cave 7 is 0
    When enemy player moves on the [0]
    And enemy player shoots to cave [1]
    Then player is dead