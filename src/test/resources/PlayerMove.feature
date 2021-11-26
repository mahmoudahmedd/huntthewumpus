Feature: Player move
  In order to win the game I would like to go to the linked cave to the Wumpus,
  I want to be able to move for caves.

  Scenario: Player moves to cave
    Given player is in cave 0
    When player moves to cave 7
    Then player will be at cave 7


