Feature: Player move
  In order to enjoy the adventure & avoid the danger,
  As a player,
  I want to move across the caves.

  Background:
    Given player in cave 9
    And enemy player in cave 6
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
    | 11                  | 9                 | [10, 9]        | 9                  | dead        |

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
    | 11                 | 9                  | [10]        | alive       | "there's an awful smell" |

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

  Scenario: Player moves to a cave that has a pit and bat
    Given player in cave 11
    And first bat in cave 19
    And first pit in cave 3
    When player moves on the [12, 13]
    Then player is dead

  Scenario Outline: Enemy player moves to connected cave
    Given enemy player in cave <PlayerStartingCave>
    And cave with index 1 to cave 6 is 16
    When enemy player moves on the <JourneyPathIndices>
    Then enemy player will be at cave <ExpectedPlayerCave>
    Examples:
      | PlayerStartingCave | JourneyPathIndices | ExpectedPlayerCave |
      | 6                  | [1]                | 16                 |

  Scenario Outline: Enemy player moves to cave that has a wumpus and the enemy player will die
    Given enemy player in cave <PlayerStartingCave>
    And wumpus in cave <WumpusStartingCave>
    And cave with index 1 to cave 6 is 16
    And cave with index 1 to cave 16 is 17
    And cave with index 2 to cave 17 is 18
    When enemy player moves on the <JourneyPathIndices>
    Then enemy player will be at cave <ExpectedEnemyPlayerCave>
    And enemy player is <EnemyPlayerState>
    Examples:
    | PlayerStartingCave | WumpusStartingCave | JourneyPathIndices | ExpectedEnemyPlayerCave | EnemyPlayerState |
    | 6                  | 18                 | [1, 1, 2]          | 18                      | dead             |

  Scenario Outline: Enemy player moves to a cave near a wumpus and sensing that there's an awful smell
    Given enemy player in cave 6
    And wumpus in cave 18
    And cave with index 1 to cave 6 is 16
    And cave with index 1 to cave 16 is 17
    When enemy player moves on the <JourneyPathIndices>
    Then enemy player is <EnemyPlayerState>
    And enemy player will be at cave <ExpectedEnemyPlayerCave>
    And game messages contains <Warning>
    Examples:
    | JourneyPathIndices | EnemyPlayerState| ExpectedEnemyPlayerCave | Warning                  |
    | [1, 1]             | alive           | 17                      | "there's an awful smell" |

  Scenario Outline: Enemy player moves to a cave near a bat and sensing that the enemy player hear a rustling
    Given enemy player in cave 6
    And first bat in cave 19
    And second bat in cave 13
    And cave with index 1 to cave 6 is 16
    And cave with index 2 to cave 16 is 15
    And cave with index 0 to cave 15 is 14
    When enemy player moves on the <JourneyPathIndices>
    Then enemy player is <EnemyPlayerState>
    And enemy player will be at cave <ExpectedEnemyPlayerCave>
    And game messages contains <Warning>
    Examples:
      | JourneyPathIndices | EnemyPlayerState| ExpectedEnemyPlayerCave | Warning               |
      | [1, 2]             | alive           | 15                      | "you hear a rustling" |
      | [1, 2, 0]          | alive           | 14                      | "you hear a rustling" |

  Scenario Outline: Enemy player moves to a cave near a pit and sensing that the player feel a draft
    Given enemy player in cave 6
    And first pit in cave 3
    And second pit in cave 13
    And cave with index 2 to cave 6 is 5
    And cave with index 0 to cave 5 is 4
    And cave with index 1 to cave 6 is 16
    And cave with index 2 to cave 16 is 15
    And cave with index 0 to cave 15 is 14
    When enemy player moves on the <JourneyPathIndices>
    Then enemy player is <EnemyPlayerState>
    And enemy player will be at cave <ExpectedEnemyPlayerCave>
    And game messages contains <Warning>
    Examples:
    | JourneyPathIndices | EnemyPlayerState| ExpectedEnemyPlayerCave | Warning            |
    | [2, 0]             | alive           | 4                       | "you feel a draft" |
    | [1, 2, 0]          | alive           | 14                      | "you feel a draft" |

  Scenario: Enemy player moves to a cave that has a bat
    Given enemy player in cave 6
    And first bat in cave 19
    And enemy player drop down cave 8
    And first bat in final cave 2
    And cave with index 1 to cave 6 is 16
    And cave with index 2 to cave 16 is 15
    And cave with index 2 to cave 15 is 19
    When enemy player moves on the [1, 2, 2]
    Then enemy player is alive
    And enemy player will be at cave 8

  Scenario: Enemy player moves to a cave that has a pit
    Given enemy player in cave 6
    And first pit in cave 3
    And cave with index 2 to cave 6 is 5
    And cave with index 0 to cave 5 is 4
    And cave with index 2 to cave 4 is 3
    When enemy player moves on the [2, 0, 2]
    Then enemy player is dead

  Scenario: Enemy player moves to a cave that has a pit and bat
    Given enemy player in cave 6
    And first bat in cave 19
    And first pit in cave 3
    And cave with index 1 to cave 6 is 16
    And cave with index 2 to cave 16 is 15
    And cave with index 0 to cave 15 is 14
    And cave with index 2 to cave 14 is 13
    When enemy player moves on the [1, 2, 0, 2]
    Then enemy player is dead

  Scenario: Enemy player moves to a cave that has a player and enemy player will not die as case with the player
    Given enemy player in cave 6
    And player in cave 8
    And cave with index 0 to cave 6 is 7
    And cave with index 2 to cave 7 is 8
    When enemy player moves on the [0, 2]
    Then enemy player is alive
    And player is alive
    And enemy player will be at cave 8
    And player will be at cave 8







