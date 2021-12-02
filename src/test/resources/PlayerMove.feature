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

  Scenario Outline: Player moves to connected cave
    Given player in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    Then player will be at cave <ExpectedPlayerCave>
    Examples:
    | PlayerStartingCave | JourneyPath    | ExpectedPlayerCave |
    | 0                  | [7]            | 7                  |
    | 0                  | [1]            | 1                  |
    | 8                  | [9]            | 9                  |

  Scenario Outline: Player moves to non connected cave
    Given player in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    Then player will be at cave <ExpectedPlayerCave>
    Examples:
    | PlayerStartingCave | JourneyPath    | ExpectedPlayerCave |
    | 0                  | [16]           | 0                  |
    | 0                  | [9]            | 0                  |

  Scenario Outline: Player moves to cave that has a wumpus and the player will die
    Given player in cave <PlayerStartingCave>
    And wumpus in cave <WumpusStartingCave>
    When player moves on the <JourneyPath>
    Then player will be at cave <ExpectedPlayerCave>
    And player is <PlayerState>
    Examples:
    | PlayerStartingCave | WumpusStartingCave | JourneyPath    | ExpectedPlayerCave | PlayerState |
    | 0                  | 18                 | [1, 9, 10, 18] | 18                 | dead        |
    | 16                 | 9                  | [17, 8, 9]     | 9                  | dead        |

  Scenario Outline: Player moves to a cave near a wumpus and sensing that there's an awful smell
    Given player in cave <PlayerStartingCave>
    And wumpus in cave <WumpusStartingCave>
    When player moves on the <JourneyPath>
    Then player is <PlayerState>
    And player sense the warning <Warning>
    Examples:
    | PlayerStartingCave | WumpusStartingCave | JourneyPath | PlayerState | Warning                  |
    | 0                  | 18                 | [1, 9, 10]  | alive       | "there's an awful smell" |
    | 0                  | 18                 | [7, 8, 17]  | alive       | "there's an awful smell" |
    | 16                 | 9                  | [17, 8]     | alive       | "there's an awful smell" |

  Scenario Outline: Player moves to a cave near a bat and sensing that the player hear a rustling
    Given player in cave <PlayerStartingCave>
    And first bat in cave <FirstBatStartingCave>
    And second bat in cave <SecondBatStartingCave>
    When player moves on the <JourneyPath>
    Then player is <PlayerState>
    And player sense the warning <Warning>
    Examples:
    | PlayerStartingCave | FirstBatStartingCave | SecondBatStartingCave | JourneyPath    | PlayerState | Warning                  |
    | 0                  | 19                   | 13                    | [4, 5, 14]     | alive       | "you hear a rustling"    |
    | 0                  | 19                   | 13                    | [4, 5, 14, 15] | alive       | "you hear a rustling"    |

  Scenario Outline: Player moves to a cave near a pit and sensing that the player feel a draft
    Given player in cave <PlayerStartingCave>
    And first pit in cave <FirstPitStartingCave>
    And second pit in cave <SecondPitStartingCave>
    When player moves on the <JourneyPath>
    Then player is <PlayerState>
    And player sense the warning <Warning>
    Examples:
    | PlayerStartingCave | FirstPitStartingCave | SecondPitStartingCave | JourneyPath | PlayerState | Warning                  |
    | 0                  | 3                    | 13                    | [4]         | alive       | "you feel a draft"       |
    | 0                  | 3                    | 13                    | [4, 5, 14]  | alive       | "you feel a draft"       |

  Scenario: Player moves to a cave that has a bat
    Given player in cave 11
    And first bat in cave 19
    And player drop down cave 8
    And first bat in final cave 2
    When player moves on the [12, 19]
    Then player is alive
    And player will be at cave 8

  Scenario: Player moves to a cave that has a pit
    Given player in cave 0
    And first pit in cave 3
    When player moves on the [4, 3]
    Then player is dead



