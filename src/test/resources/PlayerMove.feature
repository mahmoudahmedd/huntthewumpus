Feature: Player move
  In order to enjoy the adventure & avoid the danger,
  As a player,
  I want to move across the caves.

  Scenario Outline: Player moves to cave
    Given player is in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    Then player will be at cave <ExpectedPlayerCave>
    And game state is <GameState>

    Examples:
    | PlayerStartingCave | JourneyPath    | ExpectedPlayerCave | GameState       |
    | 0                  | [7]            | 7                  | "game not over" |
    | 0                  | [16]           | 0                  | "game not over" |
    | 0                  | [1, 9, 10, 18] | 18                 | "game over"     |

  Scenario Outline: Player moves to a cave near a hazard and sensing the hazard
    Given player is in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    Then game state is <GameState>
    And player sense the warning <Warning>

    Examples:
      | PlayerStartingCave | JourneyPath | GameState     | Warning                  |
      | 0                  | [1, 9, 10]  | "game not over" | "there's an awful smell" |
      | 0                  | [4, 5, 14]  | "game not over" | "you hear a rustling"    |
      | 0                  | [4]         | "game not over" | "you feel a draft"       |



