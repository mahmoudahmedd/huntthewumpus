Feature: Player move
  In order to win the game I would like to go to the linked cave to the Wumpus,
  I want to be able to move for caves.

  Scenario Outline: Player moves to cave that not contains any hazards
    Given player is in cave <PlayerStartingCave>
    When player moves to cave <CaveToMoveTo>
    Then player will be at cave <ExpectedPlayerCave>

    Examples:
    | PlayerStartingCave | CaveToMoveTo | ExpectedPlayerCave |
    | 0                  | 7            | 7                  |
    | 0                  | 16           | 0                  |



