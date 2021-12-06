Feature: Player shoot
  In order to kill the wumpus & win the game,
  As a player,
  I want to shoot arrows.

  Background:
    Given player in cave 9
    And enemy player in cave 6
    And wumpus in cave 18
    And first bat in cave 19
    And second bat in cave 13
    And first pit in cave 3
    And second pit in cave 13

  Scenario Outline: Player shoots an arrow to a cave that contains the wumpus
    Given player in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    And player shoots to cave <CaveToShoot>
    Then player is <PlayerState>

    Examples:
      | PlayerStartingCave | JourneyPath | CaveToShoot | PlayerState |
      | 0                  | [1, 9, 10]  | [18]        | winner      |

  Scenario: Player shoots an arrow that misses the wumpus and wumpus remains sleeping
    Given player in cave 0
    And wumpus in cave 18
    And wumpus not attempt to wakeup
    When player moves on the [1, 9, 10]
    And player shoots to cave [11]
    Then game state is game not over
    And wumpus will be at cave 18

  Scenario: Player shoots an arrow that misses the wumpus and wumpus moves
    Given player in cave 0
    And wumpus in cave 18
    And wumpus attempt to wakeup
    And third linked cave to cave 18 is 17
    When player moves on the [1, 9, 10]
    And wumpus attempts to wake up wumpus moves to third linked cave
    And player shoots to cave [11]
    Then game state is game not over
    And wumpus will be at cave 17

  Scenario: Player runs out of arrows with out killing wumpus
    Given player in cave 0
    And wumpus in cave 18
    And wumpus not attempt to wakeup
    When player shoots to cave [7, 7, 7, 7, 7]
    Then game state is game over
    And game messages contains "You ran out of arrows"

  Scenario: Player shoots an arrow misses wumpus and wumpus wakes up and move to eat the player
    Given player in cave 0
    And wumpus in cave 18
    And wumpus attempt to wakeup
    And second linked cave to cave 18 is 10
    When player moves on the [1, 9, 10]
    And wumpus attempts to wake up wumpus moves to second linked cave
    And player shoots to cave [11]
    Then player is dead
    And game messages contains "You woke the Wumpus and it ate you"
    And wumpus in cave 10