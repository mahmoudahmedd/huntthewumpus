Feature: Player move
  In order to win the game I would like to go to the linked cave to the Wumpus,
  I want to be able to move for caves.

  Scenario Outline: Player moves to cave
    Given player is in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    Then player will be at cave <ExpectedPlayerCave>
    And player is <PlayerState>

    Examples:
    | PlayerStartingCave | JourneyPath    | ExpectedPlayerCave | PlayerState |
    | 0                  | [7]            | 7                  | "alive"     |
    | 0                  | [16]           | 0                  | "alive"     |
    | 0                  | [1, 9, 10, 18] | 18                 | "dead"      |




