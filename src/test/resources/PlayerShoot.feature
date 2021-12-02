Feature: Player shoot
  In order to kill the wumpus & win the game,
  As a player,
  I want to shoot arrows.

  Background:
    Given player in cave 9
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
      | 0                  | [1, 9, 10]  | 18          | winner      |

  Scenario: Player shoots an arrow that misses the wumpus and wumpus remains sleeping
    Given player in cave 5
    And wumpus in cave 18
    When player moves on the [1, 9, 10]
    And player shoots to cave 11
    Then game state is game not over
    And wumpus will be at cave 18