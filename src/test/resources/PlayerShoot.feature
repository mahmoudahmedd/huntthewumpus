Feature: Player shoot
  In order to kill the wumpus & win the game,
  As a player,
  I want to shoot arrows.

  Scenario Outline: Player shoots an arrow to a cave that contains the wumpus
    Given player in cave <PlayerStartingCave>
    When player moves on the <JourneyPath>
    And player shoots to cave <CaveToShoot>
    Then player is <GameState>

    Examples:
      | PlayerStartingCave | JourneyPath | CaveToShoot | GameState |
      | 0                  | [1, 9, 10]  | 18          | winner    |
