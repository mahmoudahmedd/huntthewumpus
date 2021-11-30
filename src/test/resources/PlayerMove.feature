Feature: Player move
  In order to enjoy the adventure & avoid the danger,
  As a player,
  I want to move across the caves.

  Background:
    Given player in cave 9
    And wumpus in cave 18
    And first bat in cave 19
    And second bat in cave 13
    And first pit in cave 3
    And second pit in cave 13


  Scenario Outline: Player moves to cave
    Given player in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    Then player will be at cave <ExpectedPlayerCave>
    And player is <PlayerState>

    Examples:
    | PlayerStartingCave | JourneyPath    | ExpectedPlayerCave | PlayerState |
    | 0                  | [7]            | 7                  | alive       |
    | 0                  | [16]           | 0                  | alive       |
    | 0                  | [1, 9, 10, 18] | 18                 | dead        |

  Scenario Outline: Player moves to a cave near a hazard and sensing the hazard
    Given player in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    Then player is <PlayerState>
    And player sense the warning <Warning>

    Examples:
      | PlayerStartingCave | JourneyPath | PlayerState | Warning                  |
      | 0                  | [1, 9, 10]  | alive       | "there's an awful smell" |
      | 0                  | [4, 5, 14]  | alive       | "you hear a rustling"    |
      | 0                  | [4]         | alive       | "you feel a draft"       |



