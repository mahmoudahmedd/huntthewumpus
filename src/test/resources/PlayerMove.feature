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

  Scenario Outline: Player moves to a cave near a hazard and sensing the hazard
    Given player is in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    Then player is <PlayerState>
    And player sense the warning <Warning>

    Examples:
      | PlayerStartingCave | JourneyPath | PlayerState | Warning                  |
      | 0                  | [1, 9, 10]  | "alive"     | "there's an awful smell" |
      | 0                  | [4, 5]      | "alive"     | "you hear a rustling"    |




